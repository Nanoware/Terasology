/*
 * Copyright 2017 MovingBlocks
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
package org.terasology.world.zones;

import org.terasology.math.geom.BaseVector3i;
import org.terasology.module.sandbox.API;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

import java.util.List;
import java.util.function.BiFunction;

@API
public class LayeredZoneRegionFunction implements BiFunction<BaseVector3i, Region, Boolean> {

    public static final class LayeredZoneOrdering {
        public static final int HIGH_SKY = 300;
        public static final int MEDIUM_SKY = 200;
        public static final int LOW_SKY = 100;
        public static final int SURFACE = 0;
        public static final int SHALLOW_UNDERGROUND = -100;
        public static final int MEDIUM_UNDERGROUND = -200;
        public static final int DEEP_UNDERGROUND = -300;

    }

    private final LayeredZoneManager manager;

    private final int minWidth;
    private final int maxWidth;
    private final int ordering;

    public LayeredZoneRegionFunction(int minWidth, int maxWidth, int ordering, LayeredZoneManager manager) {
        this.manager = manager;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.ordering = ordering;

        if (ordering < 0) {
            manager.addUndergroundLayer(this);
        } else {
            manager.addAbovegroundLayer(this);
        }
    }

    @Override
    public Boolean apply(BaseVector3i pos, Region region) {
        int surfaceHeight = (int) Math.floor(region.getFacet(SurfaceHeightFacet.class).getWorld(pos.x(), pos.z()));
        boolean underground = pos.y() < surfaceHeight;
        int cumulativeDistance = 0;
        List<LayeredZoneRegionFunction> applicableLayers =
                underground ? manager.getUndergroundLayers() : manager.getAbovegroundLayers();
        for (LayeredZoneRegionFunction layer : applicableLayers) {
            //TODO: allow variable-width layers
            cumulativeDistance += layer.getMinWidth();
            if ((!underground && surfaceHeight + cumulativeDistance > pos.y())
                    || (underground && surfaceHeight - cumulativeDistance - 1 < pos.y())) {
                //Position is within the layer currently being tested
                return this.equals(layer);
            }
        }
        int lastIndex = applicableLayers.size() - 1;
        if (lastIndex < 0) {
            return false;
        }
        LayeredZoneRegionFunction lastLayer = applicableLayers.get(lastIndex);
        return this.equals(lastLayer) && underground == (lastLayer.ordering < 0);
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getOrdering() {
        return ordering;
    }

}
