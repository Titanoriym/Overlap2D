/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.uwsoft.editor.renderer.factory.component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TextureRegionComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationStateComponent;
import com.uwsoft.editor.renderer.factory.EntityFactory;
import com.uwsoft.editor.renderer.resources.IResourceRetriever;

import java.util.HashMap;

import box2dLight.RayHandler;

/**
 * Created by azakhary on 5/22/2015.
 */
public class SpriteComponentFactory extends ComponentFactory {

    public SpriteComponentFactory(RayHandler rayHandler, World world, IResourceRetriever rm) {
        super(rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, com.uwsoft.editor.renderer.systems.data.MainItemVO vo) {
        createCommonComponents(entity, vo, EntityFactory.SPRITE_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        createSpriteAnimationDataComponent(entity, (com.uwsoft.editor.renderer.systems.data.SpriteAnimationVO) vo);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, com.uwsoft.editor.renderer.systems.data.MainItemVO vo) {
        DimensionsComponent component = new DimensionsComponent();

        com.uwsoft.editor.renderer.systems.data.SpriteAnimationVO sVo = (com.uwsoft.editor.renderer.systems.data.SpriteAnimationVO) vo;
        Array<TextureAtlas.AtlasRegion> regions = getRegions(sVo.animationName);

        com.uwsoft.editor.renderer.systems.data.ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        com.uwsoft.editor.renderer.systems.data.ProjectInfoVO projectInfoVO = rm.getProjectVO();
        float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);
        component.width = (float) regions.get(0).getRegionWidth() * multiplier / projectInfoVO.pixelToWorld;
        component.height = (float) regions.get(0).getRegionHeight() * multiplier / projectInfoVO.pixelToWorld;

        entity.add(component);
        return component;
    }

    protected SpriteAnimationComponent createSpriteAnimationDataComponent(Entity entity, com.uwsoft.editor.renderer.systems.data.SpriteAnimationVO vo) {
        SpriteAnimationComponent spriteAnimationComponent = new SpriteAnimationComponent();
        spriteAnimationComponent.animationName = vo.animationName;

        spriteAnimationComponent.frameRangeMap = new HashMap<String, com.uwsoft.editor.renderer.systems.data.FrameRange>();
        for (int i = 0; i < vo.frameRangeMap.size(); i++) {
            spriteAnimationComponent.frameRangeMap.put(vo.frameRangeMap.get(i).name, vo.frameRangeMap.get(i));
        }
        spriteAnimationComponent.fps = vo.fps;
        spriteAnimationComponent.currentAnimation = vo.currentAnimation;

        if (vo.playMode == 0) spriteAnimationComponent.playMode = Animation.PlayMode.NORMAL;
        if (vo.playMode == 1) spriteAnimationComponent.playMode = Animation.PlayMode.REVERSED;
        if (vo.playMode == 2) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP;
        if (vo.playMode == 3) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP_REVERSED;
        if (vo.playMode == 4) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP_PINGPONG;
        if (vo.playMode == 5) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP_RANDOM;
        if (vo.playMode == 6) spriteAnimationComponent.playMode = Animation.PlayMode.NORMAL;

        // filtering regions by name
        Array<TextureAtlas.AtlasRegion> regions = getRegions(spriteAnimationComponent.animationName);

        AnimationComponent animationComponent = new AnimationComponent();
        SpriteAnimationStateComponent stateComponent = new SpriteAnimationStateComponent(regions);

        if (spriteAnimationComponent.frameRangeMap.isEmpty()) {
            spriteAnimationComponent.frameRangeMap.put("Default", new com.uwsoft.editor.renderer.systems.data.FrameRange("Default", 0, regions.size - 1));
        }
        if (spriteAnimationComponent.currentAnimation == null) {
            spriteAnimationComponent.currentAnimation = (String) spriteAnimationComponent.frameRangeMap.keySet().toArray()[0];
        }
        if (spriteAnimationComponent.playMode == null) {
            spriteAnimationComponent.playMode = Animation.PlayMode.LOOP;
        }

        stateComponent.set(spriteAnimationComponent);

        TextureRegionComponent textureRegionComponent = new TextureRegionComponent();
        textureRegionComponent.region = regions.get(0);

        entity.add(textureRegionComponent);
        entity.add(stateComponent);
        entity.add(animationComponent);
        entity.add(spriteAnimationComponent);

        return spriteAnimationComponent;
    }

    private Array<TextureAtlas.AtlasRegion> getRegions(String filter) {
        // filtering regions by name
        Array<TextureAtlas.AtlasRegion> allRegions = rm.getSpriteAnimation(filter).getRegions();
        Array<TextureAtlas.AtlasRegion> regions = new Array<TextureAtlas.AtlasRegion>();
        for (TextureAtlas.AtlasRegion region : allRegions) {
            if (region.name.contains(filter)) {
                regions.add(region);
            }
        }

        return regions;
    }
}
