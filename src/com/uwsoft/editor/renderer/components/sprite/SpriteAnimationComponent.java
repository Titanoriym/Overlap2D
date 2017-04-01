package com.uwsoft.editor.renderer.components.sprite;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.uwsoft.editor.renderer.systems.data.FrameRange;

import java.util.HashMap;

public class SpriteAnimationComponent implements Component {
    public String animationName = "";
    public int fps = 24;
    public HashMap<String, FrameRange> frameRangeMap = new HashMap<String, FrameRange>();
    public String currentAnimation;
    public Animation.PlayMode playMode = Animation.PlayMode.LOOP;

}
