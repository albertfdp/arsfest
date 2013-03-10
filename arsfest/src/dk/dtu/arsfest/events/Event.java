/*******************************************************************************
 * Copyright 2013 Albert Fern�ndez de la Pe�a
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dk.dtu.arsfest.events;

import dk.dtu.arsfest.events.EventType;
import dk.dtu.arsfest.model.Location;

public abstract class Event {
	
	protected float startTime;
	protected float endTime;
	protected Location location;
	protected String name;
	protected EventType type;
	protected float id;

}
