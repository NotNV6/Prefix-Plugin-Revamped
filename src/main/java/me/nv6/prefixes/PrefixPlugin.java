package me.nv6.prefixes;

import lombok.Getter;
import me.nv6.prefixes.commands.PrefixCommand;
import me.nv6.prefixes.listener.PlayerListeners;
import me.nv6.prefixes.prefix.Prefix;
import me.nv6.prefixes.profile.Profile;
import me.nv6.prefixes.util.Config;
import me.nv6.prefixes.util.Database;
import me.nv6.prefixes.util.menu.ButtonListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Prefixes, Last updated 1:35 AM, 4/28/2019
 * This prefix plugin was made by NV6, with some menu API I found.
 * I revamped my old plugin because the code was plain out just awful, so I wanted to improve it.
 * I do not know whether the code is good now or not, but it definitely is a lot better.
 *
 * Also put my config api in util, but I don't really use it in this project, just for the default config.yml.
 * You can use it in any of your other projects if you want to.
 *
 * SNAPSHOT-1.0.3
 */
public class PrefixPlugin extends JavaPlugin {


    private Config c;

    @Getter private static PrefixPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        this.c = new Config("config");
        new Database(c.getString("database.user"), c.getString("database.password"), c.getString("database.database"), c.getBoolean("database.auth"), c.getString("database.host"), c.getInteger("database.port"));

        this.getCommand("prefix").setExecutor(new PrefixCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ButtonListener(),this);


        Prefix.loadPrefixes();
    }

    @Override
    public void onDisable() {

        Prefix.getPrefixes().forEach(Prefix::save);
        Profile.getProfiles().forEach(Profile::save);
    }

}
