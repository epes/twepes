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
package me.eperjesi.twepes.publisher;

import me.eperjesi.twepes.enums.UserRankEnum;
import me.eperjesi.twepes.event.ChatMessageEvent;
import me.eperjesi.twepes.event.EventHandler;
import me.eperjesi.twepes.event.SubscriberEvent;
import me.eperjesi.twepes.event.WriteMessageEvent;
import me.eperjesi.twepes.model.User;
import me.eperjesi.twepes.tools.Config;
import me.eperjesi.twepes.tools.Log;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author: Robert Eperjesi
 * Date: 8/25/2014
 * Project: twepes
 * Location: me.eperjesi.twepes.publisher.TwitchThreePublisher
 * Description: Extension of ChatPublisher that handles SubscriberEvent, ChatMessageEvent, and User ranking.
 */
public class TwitchThreePublisher extends ChatPublisher {

    private ConcurrentLinkedQueue<String> queuedMessages;

    public TwitchThreePublisher(Config config_){
        super(config_, ChatPublisher.TWITCHCLIENT3);
        queuedMessages = new ConcurrentLinkedQueue<String>();
    }

    /**
     * Queues message up for writing in the writer thread.
     * @param message to be written to the IRC
     */
    public void queueMessage(String message){
        queuedMessages.add(message);
    }

    @Override
    protected void parseCommand(String line) {
        super.parseCommand(line);

        if(line.startsWith(":jtv MODE ")){

            //:jtv MODE {channel} +o/-o {username}
            String[] arr = line.split(" ");

            if(arr[3].equals("+o")){
                getViewer(arr[4]).addRank(UserRankEnum.MOD);
            }else if(arr[3].equals("-o")){
                getViewer(arr[4]).removeRank(UserRankEnum.MOD);
            }
        }else if(line.indexOf('!') > 0 && line.indexOf('!') < 30){

            //:{nick}!{nick}@{nick}.tmi.twitch.tv_
            //18 extra characters other than NICK
            String username = line.substring(1, line.indexOf('!'));

            //substring of line starting with the command
            String tempLine = line.substring(18 + (username.length() * 3));

            if(tempLine.startsWith("PRIVMSG " + channel)){

                //PRIVMSG {channel} :
                String message = tempLine.substring(10 + channel.length());
                switch(username){
                    case "jtv":
                        //Update USER ranks
                        buildUserRank(message);
                        break;
                    case "twitchnotify":
                        //Post Subscriber Event
                        String subscriber = message.split(" ")[0];
                        EventHandler.getInstance().notify(new SubscriberEvent(getViewer(subscriber), channel, Calendar.getInstance().getTime()));
                        break;
                    default:
                        //if(!username.equals(nick)) {
                            //Post Chat Message Event
                            EventHandler.getInstance().notify(new ChatMessageEvent(getViewer(username), message, Calendar.getInstance().getTime()));
                        //}
                }
            }
        }
    }

    private void buildUserRank(String message){
        String[] arr = message.split(" ");
        User user = getViewer(arr[1]);
        if(message.startsWith("SPECIALUSER ")) {
            switch(arr[2]){
                case "staff":
                    user.addRank(UserRankEnum.STAFF);
                    break;
                case "admin":
                    user.addRank(UserRankEnum.ADMIN);
                    break;
                case "subscriber":
                    user.addRank(UserRankEnum.SUBSCRIBER);
                    break;
                case "turbo":
                    user.addRank(UserRankEnum.TURBO);
                    break;
            }
        }else if(message.startsWith("USERCOLOR ")){
            user.setColor(arr[2]);
        }else if(message.startsWith("EMOTESET ")){
            user.setEmoteset(arr[2]);
        }
    }

    @Override
    protected void onRunStartup(String channel){
        write("JOIN " + channel);
        Log.i("Joining " + channel);

        write("TWITCHCLIENT 3");

        if (!getNames(nick, channel)) {
            stop();
        }

        startWritingQueue();
    }

    private void startWritingQueue(){
        new Thread(){
            public void run(){
                while(running){
                    while(!queuedMessages.isEmpty()){
                        writePrivMsg(queuedMessages.poll());
                    }
                }
            }
        }.start();
    }

    private void writePrivMsg(String message){
        write("PRIVMSG " + channel + " :" + message);
        EventHandler.getInstance().notify(new WriteMessageEvent(getViewer(nick), message, Calendar.getInstance().getTime()));
    }

    private boolean getNames(String nick, String channel){
        Log.i("Reading names");

        String line;
        while ( running && ( (line = read()) != null ) ){

            if(line.contains(" 353 ")){
                // :{nick}.tmi.twitch.tv 353 {nick} = {channel} :
                // 25 extra characters besides channel and nick
                int subIndex = (2 * nick.length()) + (channel.length()) + 25;
                String[] namesArr = line.substring(subIndex).split(" ");
                for(String username : namesArr){
                    addViewer(username);
                }
            }else if(line.contains(" 366 ")){
                Log.i("Finished getting all names");
                break;
            }
        }

        return true;
    }
}
