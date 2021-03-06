// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.engine.rendering.assets.material;

import org.joml.Matrix3fc;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.terasology.gestalt.assets.AssetType;
import org.terasology.gestalt.assets.DisposableResource;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.engine.rendering.assets.shader.ShaderProgramFeature;
import org.terasology.engine.rendering.assets.texture.Texture;
import org.terasology.engine.rendering.cameras.Camera;

import java.nio.FloatBuffer;

public abstract class BaseMaterial extends Material {

    protected BaseMaterial(ResourceUrn urn, AssetType<?, MaterialData> assetType, DisposableResource resource) {
        super(urn, assetType,resource);
    }

    protected BaseMaterial(ResourceUrn urn, AssetType<?, MaterialData> assetType) {
        super(urn, assetType);
    }


    @Override
    public abstract void recompile();

    @Override
    public abstract void enable();

    @Override
    public abstract void setFloat(String name, float f, boolean currentOnly);

    @Override
    public abstract void setFloat1(String name, FloatBuffer buffer, boolean currentOnly);

    @Override
    public abstract void setFloat2(String name, float f1, float f2, boolean currentOnly);

    @Override
    public abstract void setFloat2(String name, FloatBuffer buffer, boolean currentOnly);

    @Override
    public abstract void setFloat3(String name, float f1, float f2, float f3, boolean currentOnly);

    @Override
    public abstract void setFloat3(String name, FloatBuffer buffer, boolean currentOnly);

    @Override
    public abstract void setFloat4(String name, float f1, float f2, float f3, float f4, boolean currentOnly);

    @Override
    public abstract void setFloat4(String name, FloatBuffer buffer, boolean currentOnly);

    @Override
    public abstract void setInt(String name, int i, boolean currentOnly);

    @Override
    public abstract void setBoolean(String name, boolean value, boolean currentOnly);

    @Override
    public abstract void setMatrix3(String name, FloatBuffer buffer, boolean currentOnly);

    @Override
    public abstract void setMatrix4(String name, FloatBuffer buffer, boolean currentOnly);

    @Override
    public abstract void setTexture(String name, Texture texture);

    @Override
    public abstract boolean supportsFeature(ShaderProgramFeature feature);

    @Override
    public abstract void activateFeature(ShaderProgramFeature feature);

    @Override
    public abstract void deactivateFeature(ShaderProgramFeature feature);

    @Override
    public abstract void deactivateFeatures(ShaderProgramFeature... features);

    @Override
    public abstract void bindTextures();

    @Override
    public void setFloat(String name, float f) {
        setFloat(name, f, false);
    }

    @Override
    public void setFloat1(String name, FloatBuffer buffer) {
        setFloat1(name, buffer, false);
    }

    @Override
    public void setFloat2(String name, float f1, float f2) {
        setFloat2(name, f1, f2, false);
    }

    @Override
    public void setFloat2(String name, Vector2fc value) {
        setFloat2(name, value.x(), value.y());
    }

    @Override
    public void setFloat2(String name, Vector2fc value, boolean currentOnly) {
        setFloat2(name, value.x(), value.y(), currentOnly);
    }

    @Override
    public void setFloat2(String name, FloatBuffer buffer) {
        setFloat2(name, buffer, false);
    }

    @Override
    public void setFloat3(String name, float f1, float f2, float f3) {
        setFloat3(name, f1, f2, f3, false);
    }

    @Override
    public void setFloat3(String name, Vector3fc value) {
        setFloat3(name, value.x(), value.y(), value.z());
    }

    @Override
    public void setFloat3(String name, Vector3fc value, boolean currentOnly) {
        setFloat3(name, value.x(), value.y(), value.z(), currentOnly);
    }

    @Override
    public void setFloat3(String name, FloatBuffer buffer) {
        setFloat3(name, buffer, false);
    }

    @Override
    public void setFloat4(String name, float f1, float f2, float f3, float f4) {
        setFloat4(name, f1, f2, f3, f4, false);
    }

    @Override
    public void setFloat4(String name, Vector4fc value) {
        setFloat4(name, value.x(), value.y(), value.z(), value.w());
    }

    @Override
    public void setFloat4(String name, Vector4fc value, boolean currentOnly) {
        setFloat4(name, value.x(), value.y(), value.z(), value.w(), currentOnly);
    }

    @Override
    public void setFloat4(String name, FloatBuffer buffer) {
        setFloat4(name, buffer, false);
    }

    @Override
    public void setInt(String name, int i) {
        setInt(name, i, false);
    }

    @Override
    public void setBoolean(String name, boolean value) {
        setBoolean(name, value, false);
    }

    @Override
    public void setMatrix3(String name, Matrix3fc matrix) {
        setMatrix3(name, matrix, false);
    }

    @Override
    public void setMatrix3(String name, FloatBuffer buffer) {
        setMatrix3(name, buffer, false);
    }

    @Override
    public void setMatrix4(String name, Matrix4fc matrix) {
        setMatrix4(name, matrix, false);
    }

    @Override
    public void setMatrix4(String name, FloatBuffer buffer) {
        setMatrix4(name, buffer, false);
    }

    /**
     * writes camera into the local material and they include the uniforms listed below:
     * <pre>{@code
     * uniform mat4 viewMatrix;
     * uniform mat4 projMatrix;
     * uniform mat4 viewProjMatrix;
     * uniform mat4 invProjMatrix;
     * }</pre>
     * @param camera camera to write into material
     */
    @Override
    public void setCamera(Camera camera) {
        setMatrix4("viewMatrix", camera.getViewMatrix(), true);
        setMatrix4("projMatrix", camera.getProjectionMatrix(), true);
        setMatrix4("viewProjMatrix", camera.getViewProjectionMatrix(), true);
        setMatrix4("invProjMatrix", camera.getInverseProjectionMatrix(), true);
    }
}
