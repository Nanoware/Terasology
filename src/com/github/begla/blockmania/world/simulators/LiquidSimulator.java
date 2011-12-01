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
package com.github.begla.blockmania.world.simulators;

import com.github.begla.blockmania.datastructures.BlockPosition;
import com.github.begla.blockmania.world.main.WorldProvider;

import javax.vecmath.Vector3f;

/**
 * TODO
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class LiquidSimulator extends Simulator {

    private static final Vector3f[] NEIGHBORS = {new Vector3f(0, -1, 0), new Vector3f(-1, 0, 0), new Vector3f(1, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 0, -1)};

    public LiquidSimulator(WorldProvider parent) {
        super(parent);
    }

    public void simulate() {
        for (int i = _activeBlocks.size() - 1; i >= 0; i--) {
            BlockPosition bp = _activeBlocks.get(i);
            byte state = _parent.getState(bp.x, bp.y, bp.z);
            byte type = _parent.getBlock(bp.x, bp.y, bp.z);
            byte stateAbove = _parent.getState(bp.x, bp.y + 1, bp.z);
            byte typeAbove = _parent.getBlock(bp.x, bp.y + 1, bp.z);

            if (state == 7) {


            }
        }
    }
}
