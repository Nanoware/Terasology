/*
 * Copyright 2011 Benjamin Glatzel <benjamin.glatzel@me.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.begla.blockmania.rendering.cameras;

import com.github.begla.blockmania.configuration.ConfigurationManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * Provides global access to fonts.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class FirstPersonCamera extends Camera {

    public static final float FOV = ((Double) ConfigurationManager.getInstance().getConfig().get("Graphics.fov")).floatValue();
    public static final float ASPECT_RATIO = ((Double) ConfigurationManager.getInstance().getConfig().get("Graphics.aspectRatio")).floatValue();

    double _bobbingRotationOffsetFactor, _bobbingVerticalOffsetFactor = 0.0;

    public void loadProjectionMatrix() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        gluPerspective(FOV, ASPECT_RATIO, 0.1f, 1024f);

        glMatrixMode(GL11.GL_MODELVIEW);
    }

    public void loadModelViewMatrix() {
        glMatrixMode(GL11.GL_MODELVIEW);
        glLoadIdentity();

        Vector3f right = new Vector3f();
        right.cross(_viewingDirection, _up);
        right.scale((float) _bobbingRotationOffsetFactor);

        right.y += _bobbingVerticalOffsetFactor;

        GLU.gluLookAt(_position.x + right.x, _position.y + (float) _bobbingVerticalOffsetFactor * 2.0f + right.y, _position.z + right.z, _position.x + _viewingDirection.x + right.x, _position.y + _viewingDirection.y + (float) _bobbingVerticalOffsetFactor * 2.0f + right.y, _position.z + _viewingDirection.z + right.z, _up.x + right.x, _up.y + right.y, _up.z + right.z);

        _viewFrustum.updateFrustum();
    }

    public void loadNormalizedModelViewMatrix() {
        glMatrixMode(GL11.GL_MODELVIEW);
        glLoadIdentity();

        Vector3f right = new Vector3f();
        right.cross(_viewingDirection, _up);
        right.scale((float) _bobbingRotationOffsetFactor);

        right.y += _bobbingVerticalOffsetFactor;

        GLU.gluLookAt(right.x, right.y, right.z, _viewingDirection.x + right.x, _viewingDirection.y + right.y, _viewingDirection.z + right.z, _up.x + right.x, _up.y + right.y, _up.z + right.z);
    }

    public void setBobbingRotationOffsetFactor(double f) {
        _bobbingRotationOffsetFactor = f;
    }

    public void setBobbingVerticalOffsetFactor(double f) {
        _bobbingVerticalOffsetFactor = f;
    }

}
