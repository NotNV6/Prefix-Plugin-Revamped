# Prefix-Plugin-Revamped
An updated version of my old prefix plugin, saves into MongoDB now.

# API
You can set the current prefix of a player by using profiles:
Profile.getPlayer(player).setCurrentPrefix(prefix);

You can add/remove a prefix to/from a player by using profiles:
Profile.getPlayer(player).getPrefixes().remove/add(prefix);

The format is supported by any plugin as this plugin uses event#setFormat

# Notes
- Only supports MongoDB for now, no flatfile or anything.
- There might be bugs, if you do find some please report them to me (NV6#9199 on discord)
- This is an opensource project, I have not put a lot of effort in this plugin.
- I am not a professional developer by any means, I am not responsible for any issues.  
