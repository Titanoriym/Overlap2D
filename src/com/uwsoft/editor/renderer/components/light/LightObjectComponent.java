package com.uwsoft.editor.renderer.components.light;

import com.badlogic.ashley.core.Component;
import com.uwsoft.editor.renderer.systems.data.LightVO.LightType;

import box2dLight.Light;

public class LightObjectComponent implements Component {
    private LightType type;

    public int rays = 12;
    public float distance = 300;
    public float directionDegree = 0;
    public float coneDegree = 30;
    public float softnessLength = 1f;
    public boolean isStatic = true;
    public boolean isXRay = true;
    public Light lightObject = null;

    public LightObjectComponent(LightType type) {
        this.type = type;
    }

    public LightType getType() {
        return type;
    }
}
