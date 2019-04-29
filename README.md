# Prefix-Plugin-Revamped
An updated version of my old prefix plugin, saves into MongoDB now.

# API
You can set the current prefix of a player by using profiles:
```java
Profile.getPlayer(player).setCurrentPrefix(prefix);
```

You can add/remove a prefix to/from a player by using profiles:
```java
Profile.getPlayer(player).getPrefixes().remove/add(prefix);
```

The format is supported by any plugin as this plugin uses 
```java
event#setFormat
```

# Notes
- Only supports MongoDB for now, no flatfile or anything.
- There might be bugs, if you do find some please report them to me (NV6#9199 on discord)
- This is an opensource project, I have not put a lot of effort in this plugin.
- I am not a professional developer by any means, I am not responsible for any issues.  
- You can NOT claim this code to be yours.
- This plugin does not work out of the box, you must create a folder in your plugins folder called "Prefixes", and inside of that folder you must create a yml file called "config.yml", and in that folder you must put the default config.yml provided in src/resources/config.yml.

# Donate?
- Feel free to donate for my work if you are happy with my product, as this is an opensource and free project after all. You can donate using my paypal: paypal.me/donatenv6
