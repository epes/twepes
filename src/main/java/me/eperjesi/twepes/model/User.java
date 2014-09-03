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
package me.eperjesi.twepes.model;

import me.eperjesi.twepes.enums.UserRankEnum;

import java.util.ArrayList;

/**
 * Author: Robert Eperjesi
 * Date: 8/10/2014
 * Project: twepes
 * Location: me.eperjesi.twepes.model.User
 * Description: Holds the characteristics of the User who has triggered
 *      some sort of an Event.
 */
public class User {

    private String username;
    private ArrayList<UserRankEnum> ranks;
    private String color;
    private String emoteset;

    public User(String username_){
        this.username = username_;
        this.ranks = new ArrayList<UserRankEnum>();
        this.color = "";
        this.emoteset = "";
    }

    /**
     * Internally used by a publisher, should not be used publicly.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Internally used by a publisher, should not be used publicly.
     */
    public void setEmoteset(String emoteset) {
        this.emoteset = emoteset;
    }

    /**
     * @return (String) username of the user
     */
    public String getUsername(){
        return username;
    }

    /**
     * @return (String) color of the users chat name
     */
    public String getColor() {
        return color;
    }

    /**
     * @return (String) emote set that the user is currently using
     */
    public String getEmoteset() {
        return emoteset;
    }

    /**
     * Adds a rank to the user. Ranks are automatically added by the publishers.
     * Should not be used publicly unless handling custom ranks.
     * @param userRankEnum to be added to the user
     */
    public void addRank(UserRankEnum userRankEnum){
        if(!this.hasRank(userRankEnum)){
            ranks.add(userRankEnum);
        }
    }

    /**
     * Removes a rank from the user. Ranks are automatically removed by the publishers.
     * Should not be used publicly unless handling custom ranks.
     * @param userRankEnum to be removed from the user
     */
    public void removeRank(UserRankEnum userRankEnum){
        if(this.hasRank(userRankEnum)){
            ranks.remove(userRankEnum);
        }
    }

    /**
     * Checks whether the user possesses the given rank.
     * @param userRankEnum rank to check for
     * @return (boolean) whether the user posses the rank given
     */
    public boolean hasRank(UserRankEnum userRankEnum){
        return ranks.contains(userRankEnum);
    }

    /**
     * @return (ArrayList) list of all the ranks the user possesses
     */
    public ArrayList<UserRankEnum> getAllRanks(){
        return ranks;
    }

    /**
     * Mostly for debugging.
     * [O] = channel owner
     * [*] = Twitch staff
     * [A] = Twitch admin
     * [M] = channel mod
     * [S] = channel subscriber
     * [T] = Turbo
     *
     * Ex: [M][S][T] would mean that the user is a mod, subscriber, and has Turbo
     *
     * @return (String) concatenated string of all the ranks the user possesses
     */
    public String getRankString(){

        String rankString = "";

        if(ranks.contains(UserRankEnum.OWNER))
            rankString += "[O]";
        if(ranks.contains(UserRankEnum.STAFF))
            rankString += "[*]";
        if(ranks.contains(UserRankEnum.ADMIN))
            rankString += "[A]";
        if(ranks.contains(UserRankEnum.MOD))
            rankString += "[M]";
        if(ranks.contains(UserRankEnum.SUBSCRIBER))
            rankString += "[S]";
        if(ranks.contains(UserRankEnum.TURBO))
            rankString += "[T]";
        if(ranks.contains(UserRankEnum.GUEST))
            rankString += "";

        return rankString;
    }
}
