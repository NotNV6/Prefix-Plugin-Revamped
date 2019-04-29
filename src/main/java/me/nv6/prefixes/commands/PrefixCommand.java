package me.nv6.prefixes.commands;

import me.nv6.prefixes.menu.DeletePrefixMenu;
import me.nv6.prefixes.menu.GrantMenu;
import me.nv6.prefixes.menu.PrefixMenu;
import me.nv6.prefixes.prefix.Prefix;
import me.nv6.prefixes.profile.Profile;
import me.nv6.prefixes.util.CC;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;


public class PrefixCommand implements CommandExecutor {


    @SuppressWarnings("all") // There are no actual warnings, just duplicates.
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cYou must be a player to execute this command."));
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("prefixes.admin")) new PrefixMenu().openMenu(player); else {

            if(args.length < 1) {
                String[] strings = {"&7&m------------------------------------------",
                        "&a&lPrefix Help &7&o(<> = required, [] = optional)",
                        " &e/prefix menu &7(Opens the prefix menu)",
                        " &e/prefix delete &7(Opens the prefix deletion menu)",
                        " &e/prefix grant <player> [prefix] &7(Opens the prefix grant menu)",
                        " &e/prefix create <name> &7(Creates a prefix)",
                        " &e/prefix prefix <name> <prefix> &7(Sets the prefix of a prefix)",
                        " &e/prefix description <name> <description> &7(Sets the prefix of a prefix)",
                        "&7&m------------------------------------------"};
                Arrays.stream(strings).forEach(string -> player.sendMessage(CC.translate(string)));
                return true;
            }

            switch(args[0]) {
                case "menu": new PrefixMenu().openMenu(player); break;
                case "delete": new DeletePrefixMenu().openMenu(player); break;
                case "grant": {
                    if(args.length < 3) {
                        if (args.length < 2) {
                            player.sendMessage(CC.translate("&cYou must provide at least 2 arguments."));
                            return true;
                        }

                        if (Bukkit.getPlayer(args[1]) == null || !Bukkit.getPlayer(args[1]).isOnline()) {
                            player.sendMessage(CC.translate("&cYou must provide a valid player."));
                            return true;
                        }

                        Profile target = Profile.getProfile(Bukkit.getPlayer(args[1]));

                        new GrantMenu(target).openMenu(player);

                        return true;
                    }

                    if (Bukkit.getPlayer(args[1]) == null || !Bukkit.getPlayer(args[1]).isOnline()) {
                        player.sendMessage(CC.translate("&cYou must provide a valid player."));
                        return true;
                    }

                    Prefix prefix = Prefix.getByName(args[2]);

                    if(prefix == null) {
                        player.sendMessage(CC.translate("&cThat prefix does not exist."));
                    }

                    Profile target = Profile.getProfile(Bukkit.getPlayer(args[1]));

                    target.getPrefixes().add(prefix);

                    player.sendMessage(CC.translate("&aYou have added " + prefix.getName() + " to " + target.getPlayer().getName()));
                    target.getPlayer().sendMessage(CC.translate("&aYou have received the " + prefix.getName() + " prefix"));
                } break;

                case "create": {
                    if(args.length < 2) {
                        player.sendMessage(CC.translate("&cYou must provide at least 2 arguments."));
                        return true;
                    }

                    if(Prefix.getByName(args[1]) != null) {
                        player.sendMessage(CC.translate("&cThat prefix already exists."));
                        return true;
                    }

                    player.sendMessage(CC.translate("&aYou have created the prefix " + args[1]));
                    new Prefix(args[1], "Default description", "");
                } break;

                case "description": {
                    if(args.length < 3) {
                        player.sendMessage(CC.translate("&cYou must provide at least 3 arguments."));
                        return true;
                    }

                    if(Prefix.getByName(args[1]) == null) {
                        player.sendMessage(CC.translate("&cThat prefix does not exist!"));
                        return true;
                    }

                    String description = StringUtils.join(args, ' ', 2, args.length);

                    Prefix.getByName(args[1]).setDescription(description);

                    player.sendMessage(CC.translate("&aYou have set the description of " + args[1] + " to " + description));
                } break;

                case "prefix": {
                    if(args.length < 3) {
                        player.sendMessage(CC.translate("&cYou must provide at least 3 arguments."));
                        return true;
                    }

                    if(Prefix.getByName(args[1]) == null) {
                        player.sendMessage(CC.translate("&cThat prefix does not exist!"));
                        return true;
                    }

                    String prefix = args[2];

                    Prefix.getByName(args[1]).setPrefix(prefix);
                    player.sendMessage(CC.translate("&aYou have set the prefix of " + args[1] + " to " + prefix));
                } break;

            }
        }


        return false;
    }
}
