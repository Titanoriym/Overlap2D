package com.overlap2d.extensions.spine;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.MeshAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.uwsoft.editor.renderer.components.DimensionsComponent;

public class SpineObjectComponent implements Component {
    public SkeletonData skeletonData;
    public Skeleton skeleton;
    public SkeletonJson skeletonJson;
    public AnimationState state;
    public float minX;
    public float minY;


    public Array<Animation> getAnimations() {
        return skeletonData.getAnimations();
    }

    public void setAnimation(String animName) {
        state.setAnimation(0, animName, true);
    }

    public AnimationState getState() {
        return state;
    }

    public void computeBoundBox(DimensionsComponent dimensionsComponent) {
        skeleton.updateWorldTransform();
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
        for (int i = 0, n = skeleton.getSlots().size; i < n; i++) {
            Slot slot = skeleton.getSlots().get(i);
            Attachment attachment = slot.getAttachment();
            if (attachment == null) continue;
            if (!((attachment instanceof RegionAttachment) || (attachment instanceof MeshAttachment)))
                continue;
            float[] vertices = new float[0];
            if ((attachment instanceof RegionAttachment)) {
                RegionAttachment region = (RegionAttachment) attachment;
                region.updateWorldVertices(slot, false);
                vertices = region.getWorldVertices();
            }
            if ((attachment instanceof MeshAttachment)) {
                MeshAttachment region = (MeshAttachment) attachment;
                region.updateWorldVertices(slot, false);
                vertices = region.getWorldVertices();
            }

            for (int ii = 0, nn = vertices.length; ii < nn; ii += 5) {
                minX = Math.min(minX, vertices[ii]);
                minY = Math.min(minY, vertices[ii + 1]);
                maxX = Math.max(maxX, vertices[ii]);
                maxY = Math.max(maxY, vertices[ii + 1]);
            }
        }

        dimensionsComponent.width = (maxX - minX);
        dimensionsComponent.height = (maxY - minY);
    }
}
