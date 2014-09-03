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
package me.eperjesi.twepes.listener;

import me.eperjesi.twepes.model.User;

import java.util.Date;

/**
 * Author: Robert Eperjesi
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.listener.Listener
 * Description: Abstract Listener class that serves as a base for all other Listeners.
 */
public abstract class Listener {

    /**
     * Triggered corresponding to a ChatMessageEvent
     * @param user that sent the message
     * @param message that was sent by the user
     * @param timestamp of the event as a Date object
     */
    public void onChatMessage(final User user, final String message, final Date timestamp){

    }

    /**
     * Triggered corresponding to a SubscriberEvent
     * @param user that subscribed to the channel
     * @param channel that the user subscribed to
     * @param timestamp of the event as a Date object
     */
    public void onSubscriber(final User user, final String channel, final Date timestamp){

    }

    /**
     * Triggered corresponding to a JoinEvent
     * @param user that joined the channel
     * @param channel that the user has joined
     * @param timestamp of the event as a Date object
     */
    public void onJoin(final User user, final String channel, final Date timestamp){

    }

    /**
     * Triggered corresponding to a PartEvent
     * @param user that left the channel
     * @param channel that the user has left
     * @param timestamp of the event as a Date object
     */
    public void onPart(final User user, final String channel, final Date timestamp){

    }

    /**
     * Triggered corresponding to a UnsuccessfulLoginEvent
     * @param nick of the user trying to log in
     * @param timestamp of the event as a Date object
     */
    public void onUnsuccessfulLogin(final String nick, final Date timestamp){

    }

    /**
     * Triggered corresponding to a WriteMessageEvent
     * @param user that wrote the message
     * @param message that was written by the user
     * @param timestamp of the event as a Date object
     */
    public void onWriteMessage(final User user, final String message, final Date timestamp){

    }

}
