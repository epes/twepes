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

import me.eperjesi.twepes.event.EventHandler;
import me.eperjesi.twepes.event.JoinEvent;
import me.eperjesi.twepes.event.PartEvent;
import me.eperjesi.twepes.event.UnsuccessfulLoginEvent;
import me.eperjesi.twepes.model.User;
import me.eperjesi.twepes.tools.Config;
import me.eperjesi.twepes.tools.Log;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Robert Eperjesi
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.publisher.ChatPublisher
 * Description: Abstract Publisher class used as a base for all chat-based publishers.
 */
public abstract class ChatPublisher extends Publisher {

    //TWITCHCLIENT1 - JOIN, PART, UNSUCCESSFULLOGIN
    protected static int TWITCHCLIENT1 = 1;
    //TWITCHCLIENT3 - SUBSCRIBER, CHATMSG, CHATRANK
    protected static int TWITCHCLIENT3 = 3;
    //WRITE

    protected String nick;
    protected String oauth;
    protected String channel;
    private int clientType;
    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;

    private final static ConcurrentHashMap<String, User> viewers = new ConcurrentHashMap<String, User>();

    public ChatPublisher(Config config_, int clientType_){

        super();

        this.nick = config_.getNick();
        this.oauth = config_.getOAuth();
        this.channel = config_.getChannel();

        this.clientType = clientType_;

        this.socket = openSocket(config_.getServer(), config_.getPort());
        this.writer = getBufferedWriter(socket);
        this.reader = getBufferedReader(socket);
    }

    /**
     * Starts the ChatPublisher.
     */
    @Override
    public void run(){

        Log.i("Running ChatPublisher Thread");


        login(nick, oauth);

        if(running) {
            onRunStartup(channel);
        }

        String line;
        while ( running && ( (line = read()) != null )) {
            parseCommand(line);
        }

        Log.i("Exiting ChatPublisher Thread " + clientType);
    }

    /**
     * Closes all streams and stops the ChatPublisher.
     */
    @Override
    public void stop(){
        super.stop();
        try {
            reader.close();
            writer.close();
            socket.close();
        }catch(IOException e){
            Log.e(this.getClass().toString(), e);
        }
    }

    protected void parseCommand(String line){
        if(line.startsWith("PING ")){
            pong(line);
            Log.i("Just got pinged! I ponged!");
        }
    }

    private void login(String nick, String oauth) {
        write("PASS " + oauth);
        write("NICK " + nick);

        Log.i("Logging in as " + nick + " (" + oauth + ")");
        String line;
        while ( running && ( (line = read()) != null ) ){

            //System.out.println(line);

            if(line.contains(" 004 ")){
                Log.i("Successful login!");
                break;
            }

            if(line.contains("Login unsuccessful")){
                Log.i("Login unsuccessful");
                stop();

                // TODO: grossly dependent, will need to refactor so event only pops once
                if(clientType == TWITCHCLIENT1){
                    EventHandler.getInstance().notify(new UnsuccessfulLoginEvent(nick, Calendar.getInstance().getTime()));
                }
                break;
            }
        }
    }

    protected abstract void onRunStartup(String channel);

    private void pong(String line) {
        write("PONG " + line.substring(5));
    }

    protected User getViewer(String username_){
        if(!viewers.containsKey(username_)){
            addViewer(username_);
        }
        return viewers.get(username_);
    }

    protected void addViewer(String username_){
        if(!viewers.containsKey(username_)){
            User user = new User(username_);
            viewers.put(username_, user);
            EventHandler.getInstance().notify(new JoinEvent(user, channel, Calendar.getInstance().getTime()));
        }else{
            Log.i("Trying to addViewer that exists [" + username_ + "]");
        }
    }

    protected void removeViewer(String username_){
        if(viewers.containsKey(username_)){
            User user = viewers.remove(username_);
            EventHandler.getInstance().notify(new PartEvent(user, channel, Calendar.getInstance().getTime()));
        }else{
            Log.i("Trying to removeViewer that doesn't exists");
        }
    }

    private Socket openSocket(String server, int port){
        Log.i("Opening socket");
        try{
            return new Socket(server, port);
        }catch(IOException e){
            Log.e(this.getClass().toString(), e);
            return null;
        }
    }

    private BufferedWriter getBufferedWriter(Socket socket){
        Log.i("Opening BufferedWriter");
        try{
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            Log.e(this.getClass().toString(), e);
            return null;
        }
    }

    private BufferedReader getBufferedReader(Socket socket){
        Log.i("Opening BufferedReader");
        try{
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            Log.e(this.getClass().toString(), e);
            return null;
        }
    }

    protected void write(String toWrite) {
        try {
            Log.i("##> WRITE: " + toWrite);
            writer.write(toWrite + "\n");
            writer.flush();
        }catch(IOException e){
            Log.e(this.getClass().toString(), e);
        }
    }

    protected String read(){
        try{
            return reader.readLine();
        }catch(IOException e){
            Log.e(this.getClass().toString(), e);
            return null;
        }
    }
}
