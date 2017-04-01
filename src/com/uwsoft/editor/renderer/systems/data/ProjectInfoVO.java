package com.uwsoft.editor.renderer.systems.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectInfoVO {

    public int pixelToWorld = 1;

    public ResolutionEntryVO originalResolution = new ResolutionEntryVO();

    public Array<ResolutionEntryVO> resolutions = new Array<ResolutionEntryVO>();
    public ArrayList<SceneVO> scenes = new ArrayList<SceneVO>();

    public HashMap<String, CompositeItemVO> libraryItems = new HashMap<String, CompositeItemVO>();

    public String constructJsonString() {
        String str = "";
        Json json = new Json();
        json.setOutputType(OutputType.json);
        str = json.toJson(this);
        json.prettyPrint(str);
        return str;
    }

    public ResolutionEntryVO getResolution(String name) {
        for (ResolutionEntryVO resolution : resolutions) {
            if (resolution.name.equalsIgnoreCase(name)) {
                return resolution;
            }
        }
        return null;
    }
}
