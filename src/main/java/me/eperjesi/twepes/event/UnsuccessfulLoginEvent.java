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
 * Date: 8/14/14
 * Project: twepes
 * Location: me.eperjesi.twepes.event.UnsuccessfulLoginEvent
 * Description: UnsuccessfulLoginEvent is triggered when the application could
 *      not connect to the Twitch IRC server.
 */
public class UnsuccessfulLoginEvent extends Event {

    private String nick;

    public UnsuccessfulLoginEvent(String nick_, Date timestamp_){
        super();
        this.nick = nick_;
        this.timestamp = timestamp_;
    }

    /**
     * @return (String) name of the user trying to connect
     */
    public String getNick(){
        return nick;
    }

    @Override
    public EventEnum getEventEnum(){
        return EventEnum.UNSUCCESSFUL_LOGIN;
    }
}
