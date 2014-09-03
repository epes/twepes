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

import me.eperjesi.twepes.tools.Config;
import me.eperjesi.twepes.tools.Log;

/**
 * Author: Robert Eperjesi
 * Date: 8/25/2014
 * Project: twepes
 * Location: me.eperjesi.twepes.publisher.TwitchOnePublisher
 * Description: Extension of ChatPublisher that handles JoinEvent, PartEvent, and UnsuccessfulLoginEvent.
 */
public class TwitchOnePublisher extends ChatPublisher{

    public TwitchOnePublisher(Config config_){
        super(config_, ChatPublisher.TWITCHCLIENT1);
    }

    @Override
    protected void parseCommand(String line){
        super.parseCommand(line);

        if(line.indexOf('!') > 0 && line.indexOf('!') < 30) {

            //:{nick}!{nick}@{nick}.tmi.twitch.tv_
            //18 extra characters other than NICK
            String username = line.substring(1, line.indexOf('!'));

            //substring of line starting with the command
            String tempLine = line.substring(18 + (username.length() * 3));

            if (tempLine.startsWith("JOIN")) {
                //JOIN {channel}
                addViewer(username);
            } else if (tempLine.startsWith("PART")) {
                //PART {channel}
                removeViewer(username);
            }
        }
    }

    @Override
    protected void onRunStartup(String channel){
        write("JOIN " + channel);
        Log.i("Joining " + channel);
    }
}
