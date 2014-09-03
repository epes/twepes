twepes
===
Multithreaded Java framework built on top of Twitch IRC to capture and publish various IRC events.

## Why do I want it?
Have you ever wanted to make a Twitch IRC bot but didn't want to deal with sockets, IRC parsing, threading, etc..? I'm glad you said yes because that's exactly what this framework takes care of. This way you can focus on handling events and not creating them.

## What does it do?
twepes sends out certain event notifications and allows you to intercept and handle them within your own registered listeners. Currently there are six available events to listen for and I plan on adding more. 

  *  [Chat Message](src/main/java/me/eperjesi/twepes/event/ChatMessageEvent.java)
  *  [New Subscriber](src/main/java/me/eperjesi/twepes/event/SubscriberEvent.java)
  *  [User Join](src/main/java/me/eperjesi/twepes/event/JoinEvent.java)
  *  [User Leave](src/main/java/me/eperjesi/twepes/event/PartEvent.java)
  *  [Write Message](src/main/java/me/eperjesi/twepes/event/WriteMessageEvent.java)
  *  [Unsuccessful Login](src/main/java/me/eperjesi/twepes/event/UnsuccessfulLoginEvent.java)

## How do I use it?

#### [Config](src/main/java/me/eperjesi/twepes/tools/Config.java)
In order to connect to the Twitch IRC server you will need to get an oauth to act as a password. (More on connecting to Twitch IRC can be found [HERE](http://help.twitch.tv/customer/portal/articles/1302780-twitch-irc) and info on how to get your oauth can be found [HERE](http://www.twitchapps.com/tmi/) ). You will then need to instantiate a [Config](src/main/java/me/eperjesi/twepes/tools/Config.java) object with your username, oauth, and the channel name you wish to join. 

###### Example:
```
Config config = new Config(
  "put-your-username-here",
  "oauth:put-your-oauth-here",
  "put-channel-name-here"
);
```

#### [Listener](src/main/java/me/eperjesi/twepes/listener/Listener.java)
Listeners are used to listen in on all the events that twepes is sending out and respond to them accordingly. To create your own listener you will need to extend the [Listener](src/main/java/me/eperjesi/twepes/listener/Listener.java) class and override any events you wish to handle. The list of all overridable methods can be found within the [Listener](src/main/java/me/eperjesi/twepes/listener/Listener.java) class. You can create multiple listeners but remember to then register them all.

###### Example:
```
import me.eperjesi.twepes.event.EventHandler;
import me.eperjesi.twepes.model.User;
import java.util.Date;

public class ChatListener extends Listener{
  @Override
  public void onChatMessage(final User user, final String message, final Date timestamp){
    //This will print to the console: "##> {user-rank-here}{username-here}: {message-here}"
    System.out.println("##> " + user.getRankString() + user.getUsername() + ": " + message);
    //This will write a message in IRC: "{username-here} said {message-here}
    EventHandler.getInstance().writeChatMessage(user.getUsername() + " said " + message);
  }
  
  @Override
  public void onSubscriber(final User user, final String channel, final Date timestamp){
    //This will print to the console: "##> {username-here} SUBSCRIBED!"
    System.out.println("##> " + user.getUsername() + " SUBSCRIBED!");
  }
}
```

#### [EventHandler](src/main/java/me/eperjesi/twepes/event/EventHandler.java)
The [EventHandler](src/main/java/me/eperjesi/twepes/event/EventHandler.java) ties together the Config and the Listeners to launch the application. It requires that a Config be set before starting. Once a config is set you can then register your listeners and start the application. 

###### Example:
```
EventHandler.getInstance()
  // sets the config we instantiated above
  .setConfig(config)
  //registers the listener we instantiated above
  .addListener(new ChatListener())
  //starts the application
  .start();
```
Aaaaand you're up and running! It's that easy!

## Extra Information

#### Writing Messages
Writing messages across threads in twepes was sort of tricky to figure out. Currently it is a bit messy and the location of the method is unsettlingly coupled; however, it works. In order to write a message from a listener you can simply put: 
```
EventHandler.getInstance().writeChatMessage("put-your-message-here");
```

This will queue your message inside of a ConcurrentQueue which is based in its own "writing" thread.

#### User Object
Most of the events contain a User object as a parameter. [User](src/main/java/me/eperjesi/twepes/model/User.java) is a twepes class that maintains the characteristics of the user who triggered the event. The object is mainly for keeping track of the username, name color in chat, emote set, and ranks. There are multiple methods that deal with adding, removing, and displaying ranks. If you need to check whether a certain user possesses a rank (ex: Turbo), you can do so with the **hasRank(UserRankEnum userRankEnum)** method with a parameter of the rank (ex: [UserRankEnum](src/main/java/me/eperjesi/twepes/enums/UserRankEnum.java).TURBO). There is another method that I mainly used for debugging but **getRankString()** will print out all the ranks that the user has. You can find more on that in that particular method in [User](src/main/java/me/eperjesi/twepes/model/User.java).

#### Logging
Currently there is no logging framework in place. I have made a wrapper for it but did not want to add dependencies onto the framework. Everyone has their favorite loggers so I have provided comments on how to implement those easier in [Log](src/main/java/me/eperjesi/twepes/tools/Log.java).