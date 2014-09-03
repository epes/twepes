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

import java.util.logging.Logger;

/**
 * Author: Robert Eperjesi
 * Date: 8/12/14
 * Project: twepes
 * Location: me.eperjesi.twepes.tools.Log
 * Description: //TODO: Write description
 */
public class Log {
    public static Logger log = Logger.getLogger("me.eperjesi.twepes");

    /**
     * TODO: Desc
     * @param toLog
     * @param e
     */
    public static void e(String toLog, Exception e){
        //log.log(Level.SEVERE, toLog, e);
        System.out.println("## ERROR ##> " + toLog);
        e.printStackTrace();
    }

    /**
     * TODO: Desc
     * @param toLog
     */
    public static void e(String toLog){
        //log.severe(toLog);
        System.out.println("## ERROR ##> " + toLog);
    }

    /**
     * TODO: Desc
     * @param toLog
     */
    public static void i(String toLog){
        //log.warning(toLog);
        System.out.println("## INFO ##> " + toLog);
    }

    /**
     * TODO: Desc
     * @param toLog
     */
    public static void d(String toLog){
        //log.info(toLog);
        System.out.println("## DEBUG ##> " + toLog);
    }


}
