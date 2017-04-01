package com.uwsoft.editor.renderer.components.sprite;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

public class AnimationComponent implements Component {
    public HashMap<String, Animation> animations = new HashMap<String, Animation>();
}
