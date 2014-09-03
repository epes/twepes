/*************************************************************************
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
 *************************************************************************/
package me.eperjesi.twepes.enums;

/**
 * Author: Robert Eperjesi
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.enums.EventEnum
 * Description: Enum to describe different types of events that can be published/handled.
 */
public enum EventEnum {
    CHAT_MESSAGE,
    SUBSCRIBER,
    DONATION,
    JOIN,
    PART,
    UNSUCCESSFUL_LOGIN,
    WRITE_MESSAGE
}
