/**********************************************************************************
 *
 *                    Copyright 2014 Robert Eperjesi
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 **********************************************************************************/
package me.eperjesi.twepes.event;

import me.eperjesi.twepes.enums.EventEnum;

import java.util.Date;

/**
 * Author: Robert Eperjesi
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.event.Event
 * Description: Abstract Event class that serves as a base for all other events.
 */
public abstract class Event {

    protected Date timestamp;

    /**
     * @return (Date) date object at the instance the event was created
     */
    public Date getTimestamp(){
        return timestamp;
    }

    /**
     * @return (EventEnum) type of event used internally, not for public use
     */
    public abstract EventEnum getEventEnum();
}
