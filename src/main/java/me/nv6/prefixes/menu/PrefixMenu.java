package me.nv6.prefixes.menu;

import me.nv6.prefixes.prefix.Prefix;
import me.nv6.prefixes.profile.Profile;
import me.nv6.prefixes.util.CC;
import me.nv6.prefixes.util.menu.Button;
import me.nv6.prefixes.util.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PrefixMenu extends PaginatedMenu implements Listener {

    public PrefixMenu() {
        this.setAutoUpdate(true);
        this.setUpdateAfterClick(true);
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&eChoose your Prefix";
    }

    @SuppressWarnings("all") // There are no actual warnings, just duplicates.
    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        int index = 0;
        for(Prefix prefix : Prefix.getPrefixes()) {
            buttons.put(index, new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    Profile profile = Profile.getProfile(player);
                    ItemStack wool = new ItemStack(Material.WOOL, 1, (profile.getPrefixes().contains(prefix) ? (byte) 13 : (byte) 14));
                    ItemStack item = new ItemStack(wool);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(CC.GOLD + prefix.getName());
                    itemMeta.setLore(Arrays.asList(
                       CC.translate("&7&o" + prefix.getDescription()),
                            "",
                            CC.translate("&ePrefix: " + prefix.getPrefix()),
                            "",
                            CC.translate(profile.getPrefixes().contains(prefix) ? "&aClick here to select this prefix." : "&cYou are not permitted to select this prefix")
                    ));
                    item.setItemMeta(itemMeta);
                    return item;
                }

                public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                    if(prefix == null) {
                        player.sendMessage(CC.translate("&cThat prefix is does not exist."));
                        return;
                    }
                    Profile profile = Profile.getProfile(player);
                    if(!profile.getPrefixes().contains(prefix)) {
                        player.sendMessage(CC.translate("&cYou do not have permissions to use this prefix."));
                        return;
                    }

                    profile.setCurrentPrefix(prefix);
                    player.sendMessage(CC.translate("&aYou have set your prefix to \"" + prefix.getPrefix() + "\""));
                    player.closeInventory();
                }
            });

            index++;
        }
        return buttons;
    }
}
