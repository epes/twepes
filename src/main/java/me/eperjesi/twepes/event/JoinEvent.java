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
import me.eperjesi.twepes.model.User;

import java.util.Date;

/**
 * Author: Robert Eperjesi
 * Date: 8/9/14
 * Project: twepes
 * Location: me.eperjesi.twepes.event.JoinEvent
 * Description: JoinEvent is triggered when a User enters the Twitch channel.
 */
public class JoinEvent extends Event {

    private User user;
    private String channel;

    public JoinEvent(User user_, String channel_, Date timestamp_){
        super();
        this.user = user_;
        this.channel = channel_;
        this.timestamp = timestamp_;
    }

    /**
     * @return (User) user that entered the channel
     */
    public User getUser() {
        return user;
    }

    /**
     * @return (String) channel that the user has entered
     */
    public String getChannel() {
        return channel;
    }

    @Override
    public EventEnum getEventEnum(){
        return EventEnum.JOIN;
    }
}
