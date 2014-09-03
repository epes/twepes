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
 * Date: 8/27/2014
 * Project: twepes
 * Location: me.eperjesi.twepes.event.WriteMessageEvent
 * Description: WriteMessageEvent is triggered when the application writes
 *      a message to the IRC server.
 */
public class WriteMessageEvent extends Event{

    private User user;
    private String message;

    public WriteMessageEvent(User user_, String message_, Date timestamp_){
        super();
        this.user = user_;
        this.message = message_;
        this.timestamp = timestamp_;
    }

    /**
     * @return (User) user that sent the message, most likely the currently signed in user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return (String) message that was written by the user
     */
    public String getMessage(){
        return message;
    }

    @Override
    public EventEnum getEventEnum(){
        return EventEnum.WRITE_MESSAGE;
    }
}
