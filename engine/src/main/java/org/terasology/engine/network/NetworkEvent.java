/*
 * Copyright 2013 MovingBlocks
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

package org.terasology.engine.network;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.Event;

/**
 * Optional parent for events that are replicated over the network. Inherit this to make use of advanced features
 *
 */
public abstract class NetworkEvent implements Event {

    private EntityRef instigator = EntityRef.NULL;

    protected NetworkEvent() {
    }

    /**
     * @param instigator The instigator of this event. Can be used with BroadcastEvent's skipInstigator option
     *                   to avoid sending the event to the client that caused the event (who will have simulated it
     *                   client-side).
     */
    protected NetworkEvent(EntityRef instigator) {
        this.instigator = instigator;
    }

    public EntityRef getInstigator() {
        return instigator;
    }
}
