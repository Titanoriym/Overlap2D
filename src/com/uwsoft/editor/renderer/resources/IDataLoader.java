package com.uwsoft.editor.renderer.resources;

import com.uwsoft.editor.renderer.systems.data.ProjectInfoVO;
import com.uwsoft.editor.renderer.systems.data.SceneVO;

/**
 * Created by azakhary on 9/9/2014.
 */
public interface IDataLoader {

    public SceneVO loadSceneVO(String sceneName);
    public ProjectInfoVO loadProjectVO();

}
