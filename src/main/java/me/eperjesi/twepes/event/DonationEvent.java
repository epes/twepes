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
 * Date: 8/5/14
 * Project: twepes
 * Location: me.eperjesi.twepes.event.DonationEvent
 * Description: TODO: NOT YET IMPLEMENTED
 */
public class DonationEvent extends Event {
    //user message amount timestamp

    private User user;
    private String message;
    private double amount;

    public DonationEvent(User user_, String message_, double amount_, Date timestamp_){
        super();
        this.user = user_;
        this.message = message_;
        this.amount = amount_;
        this.timestamp = timestamp_;
    }

    /**
     * @return (User) user that donated
     */
    public User getUser() {
        return user;
    }

    /**
     * @return (String) message that was provided with the donation
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return (double) amount that was donated
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public EventEnum getEventEnum() {
        return EventEnum.DONATION;
    }
}
