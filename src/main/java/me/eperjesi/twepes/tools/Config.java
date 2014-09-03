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
package me.eperjesi.twepes.tools;

/**
 * Author: Robert Eperjesi
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.tools.Config
 * Description: //TODO: Write description
 */
public class Config {

    private String server;
    private int port;
    private String nick;
    private String oauth;
    private String channel;

    private Config(String server_, int port_, String nick_, String oauth_, String channel_){
        this.server = server_;
        this.port = port_;
        this.nick = nick_;
        this.oauth = fixOAuth(oauth_);
        this.channel = fixChannel(channel_);
    }

    public Config(String nick_, String oauth_, String channel_){
        this("irc.twitch.tv", 80, nick_, oauth_, channel_);
    }

    private String fixOAuth(String oauth_){
        if(oauth_.startsWith("oauth:")){
            return oauth_;
        }else{
            return "oauth:" + oauth_;
        }
    }

    private String fixChannel(String channel_){
        if(channel_.startsWith("#")){
            return channel_;
        }else{
            return "#" + channel_;
        }
    }

    /**
     * TODO: Desc
     * @return
     */
    public String getServer() {
        return server;
    }

    /**
     * TODO: Desc
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * TODO: Desc
     * @return
     */
    public String getNick() {
        return nick;
    }

    /**
     * TODO: Desc
     * @return
     */
    public String getOAuth() {
        return oauth;
    }

    /**
     * TODO: Desc
     * @return
     */
    public String getChannel() {
        return channel;
    }
}
