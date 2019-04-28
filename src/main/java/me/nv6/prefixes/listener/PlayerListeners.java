package me.nv6.prefixes.listener;

import me.nv6.prefixes.profile.Profile;
import me.nv6.prefixes.util.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) { new Profile(event.getPlayer().getUniqueId()); }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) { Profile.getProfile(event.getPlayer()).destroy(); }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Profile profile = Profile.getProfile(event.getPlayer());
        event.setMessage(event.getMessage().replace("%", "%%")); // if you do not know why I am doing this, if you type % in chat while using event#setFormat, the format will fuck up.
        if(profile.getCurrentPrefix() == null) return; else event.setFormat(CC.translate(profile.getCurrentPrefix().getPrefix().replace("_", " ")) + event.getFormat());
    }
}
