// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.engine.rendering.primitives;

import com.google.common.base.Preconditions;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.engine.rendering.assets.mesh.StandardMeshData;
import org.terasology.gestalt.module.sandbox.API;
import org.terasology.engine.rendering.assets.mesh.Mesh;
import org.terasology.engine.rendering.assets.mesh.MeshData;
import org.terasology.engine.utilities.Assets;
import org.terasology.engine.world.block.shapes.BlockMeshPart;

@API
public class Tessellator {

    private StandardMeshData meshData = new StandardMeshData();

    private int nextIndex;

    private Vector4f activeColor = new Vector4f();
    private Vector3f activeNormal = new Vector3f();
    private Vector2f activeTex = new Vector2f();
    private Vector3f lighting = new Vector3f();

    private boolean useLighting = true;
    private boolean useNormals = true;


    public Tessellator() {
        resetParams();
    }

    public void setUseLighting(boolean enable) {
        this.useLighting = enable;
    }

    public void setUseNormals(boolean enable) {
        this.useNormals = enable;
    }

    public void resetParams() {
        activeColor.set(1, 1, 1, 1);
        activeTex.set(0, 0);
        lighting.set(1, 1, 1);
        activeNormal.set(0, 1, 0);
    }

    public void addPoly(Vector3f[] vertices, Vector2f[] texCoords) {
        if (vertices.length != texCoords.length || vertices.length < 3) {
            throw new IllegalArgumentException("addPoly expected vertices.length == texCoords.length > 2");
        }
        for (int i = 0; i < vertices.length; ++i) {
            meshData.getVertices().add(vertices[i].x);
            meshData.getVertices().add(vertices[i].y);
            meshData.getVertices().add(vertices[i].z);

            meshData.color0.add(activeColor.x);
            meshData.color0.add(activeColor.y);
            meshData.color0.add(activeColor.z);
            meshData.color0.add(activeColor.w);

            if (useNormals) {
                meshData.normals.add(activeNormal.x);
                meshData.normals.add(activeNormal.y);
                meshData.normals.add(activeNormal.z);
            }

            meshData.uv0.add(texCoords[i].x);
            meshData.uv0.add(texCoords[i].y);

            if (useLighting) {
                meshData.uv1.add(lighting.x);
                meshData.uv1.add(lighting.y);
                meshData.uv1.add(lighting.z);
            }
        }

        // Standard fan
        for (int i = 0; i < vertices.length - 2; i++) {
            meshData.getIndices().add(nextIndex);
            meshData.getIndices().add(nextIndex + i + 1);
            meshData.getIndices().add(nextIndex + i + 2);
        }
        nextIndex += vertices.length;
    }

    public void addMeshPartDoubleSided(BlockMeshPart part) {
        addMeshPart(part, true);
    }

    public void addMeshPart(BlockMeshPart part) {
        addMeshPart(part, false);
    }

    private void addMeshPart(BlockMeshPart part, boolean doubleSided) {
        for (int i = 0; i < part.size(); ++i) {
            Vector3fc vertex = part.getVertex(i);
            meshData.getVertices().add(vertex.x());
            meshData.getVertices().add(vertex.y());
            meshData.getVertices().add(vertex.z());

            meshData.color0.add(activeColor.x);
            meshData.color0.add(activeColor.y);
            meshData.color0.add(activeColor.z);
            meshData.color0.add(activeColor.w);

            Vector3fc normal = part.getNormal(i);
            meshData.normals.add(normal.x());
            meshData.normals.add(normal.y());
            meshData.normals.add(normal.z());

            Vector2fc uv = part.getTexCoord(i);
            meshData.uv0.add(uv.x());
            meshData.uv0.add(uv.y());

            meshData.light0.add(lighting.x);
            meshData.light0.add(lighting.y);
            meshData.light0.add(lighting.z);
        }

        for (int i = 0; i < part.indicesSize(); ++i) {
            meshData.getIndices().add(nextIndex + part.getIndex(i));
        }
        if (doubleSided) {
            for (int i = 0; i < part.indicesSize(); i += 3) {
                int i1 = nextIndex + part.getIndex(i);
                int i2 = nextIndex + part.getIndex(i + 1);
                int i3 = nextIndex + part.getIndex(i + 2);
                meshData.getIndices().add(i1);
                meshData.getIndices().add(i3);
                meshData.getIndices().add(i2);
            }
        }

        nextIndex += part.size();
    }

    public void setColor(Vector4f v) {
        activeColor.set(v);
    }

    public void setNormal(Vector3f v) {
        activeNormal.set(v);
    }

    public void setTex(Vector2f v) {
        activeTex.set(v);
    }

    public void setLighting(Vector3f v) {
        lighting.set(v);
    }

    public MeshData generateMeshData() {
        MeshData result = meshData;
        meshData = new StandardMeshData();
        return result;
    }

    public Mesh generateMesh(ResourceUrn urn) {
        Preconditions.checkNotNull(urn);
        Mesh result = Assets.generateAsset(urn, meshData, Mesh.class);
        meshData = new StandardMeshData();
        return result;
    }

    public Mesh generateMesh() {
        Mesh result = Assets.generateAsset(meshData, Mesh.class);
        meshData = new StandardMeshData();
        return result;
    }
}
