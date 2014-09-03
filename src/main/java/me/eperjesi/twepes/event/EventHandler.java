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

import me.eperjesi.twepes.listener.Listener;
import me.eperjesi.twepes.publisher.Publisher;
import me.eperjesi.twepes.publisher.TwitchOnePublisher;
import me.eperjesi.twepes.publisher.TwitchThreePublisher;
import me.eperjesi.twepes.tools.Config;
import me.eperjesi.twepes.tools.Log;

import java.util.ArrayList;

/**
 * Author: Robert Eperjesi
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.event.EventHandler
 * Description: Responsible for setting up and starting the application.
 *      Distributes all incoming Event notifications.
 *      Temporarily used to handle message queuing.
 */
public class EventHandler {
    private static EventHandler ourInstance = new EventHandler();

    public static EventHandler getInstance() {
        return ourInstance;
    }

    private ArrayList<Listener> listeners;
    private ArrayList<Publisher> publishers;
    private boolean started;
    private Config config;

    private EventHandler() {
        listeners = new ArrayList<Listener>();
        publishers = new ArrayList<Publisher>();
        started = false;
    }

    /**
     * Starts the EventHandler. Adds and starts up the publisher threads.
     */
    public void start(){
        if(!started && config != null){
            Log.i("Starting all publishers in EventHandler.");
            started = true;
            publishers.add(new TwitchOnePublisher(config));
            publishers.add(new TwitchThreePublisher(config));

            for(Publisher p : publishers){
                new Thread(p).start();
            }
        }
    }

    /**
     * Stops the EventHandler. Stops all the publisher threads.
     */
    public void stop(){
        if(started) {
            Log.i("Stopping all publishers in EventHandler.");
            started = false;
            for (Publisher p : publishers) {
                p.stop();
            }
        }
    }

    /**
     * Restarts the EventHandler. Stops all the publisher threads then starts them again.
     */
    public void restart(){
        stop();
        start();
    }

    /**
     * Sets the EventHandler config object.
     * @param config_ (Config)
     * @return (EventHandler) self object to allow for method chaining
     */
    public EventHandler setConfig(Config config_){
        this.config = config_;
        return this;
    }

    /**
     * Adds a listener to the list of listeners.
     * @param toAdd_ (Listener)
     * @return (EventHandler) self object to allow for method chaining
     */
    public EventHandler addListener(Listener toAdd_){
        listeners.add(toAdd_);
        return this;
    }

    /**
     * Queues a given message up for writing in the IRC chat.
     * @param message (String) message to be written
     */
    public void writeChatMessage(String message){
        if(started){
            ((TwitchThreePublisher) publishers.get(1)).queueMessage(message);
        }
    }

    /**
     * Notifies all registered listeners of the given event.
     * @param event (Event)
     */
    public void notify(Event event){
        if(event == null){
            System.out.println("EventHandler :: notify(event) :: NULL");
            return;
        }

        switch(event.getEventEnum()){
            case CHAT_MESSAGE:
                notifyChatMessage((ChatMessageEvent) event);
                break;
            case SUBSCRIBER:
                notifySubscriber((SubscriberEvent) event);
                break;
            case JOIN:
                notifyJoin((JoinEvent) event);
                break;
            case PART:
                notifyPart((PartEvent) event);
                break;
            case UNSUCCESSFUL_LOGIN:
                notifyUnsuccessfulLogin((UnsuccessfulLoginEvent) event);
                break;
            case WRITE_MESSAGE:
                notifyWriteMessage((WriteMessageEvent) event);
                break;
            default:
                System.out.println("EventHandler :: switch(event.getEventEnum) :: default - not found");
        }
    }

    private void notifyChatMessage(ChatMessageEvent event){
        for(Listener l : listeners){
            l.onChatMessage(event.getUser(), event.getMessage(), event.getTimestamp());
        }
    }

    private void notifySubscriber(SubscriberEvent event){
        for(Listener l : listeners){
            l.onSubscriber(event.getUser(), event.getChannel(), event.getTimestamp());
        }
    }

    private void notifyJoin(JoinEvent event){
        for(Listener l : listeners){
            l.onJoin(event.getUser(), event.getChannel(), event.getTimestamp());
        }
    }

    private void notifyPart(PartEvent event){
        for(Listener l : listeners){
            l.onPart(event.getUser(), event.getChannel(), event.getTimestamp());
        }
    }

    private void notifyUnsuccessfulLogin(UnsuccessfulLoginEvent event){
        for(Listener l : listeners){
            l.onUnsuccessfulLogin(event.getNick(), event.getTimestamp());
        }
    }

    private void notifyWriteMessage(WriteMessageEvent event){
        for(Listener l : listeners){
            l.onWriteMessage(event.getUser(), event.getMessage(), event.getTimestamp());
        }
    }
}
