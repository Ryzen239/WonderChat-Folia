package me.ryzen23.wonderlandschat.utils.menu.element;

import java.util.Collection;
import java.util.Collections;
import me.ryzen23.wonderlandschat.utils.text.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface MenuElement {
    public ItemStack getDisplayItem();

    public void handle(InventoryClickEvent var1);

    public MenuElement copy();

    default public Collection<Placeholder<Player>> getItemPlaceholders() {
        return Collections.emptyList();
    }

    default public MenuElement setItemPlaceholders(Collection<Placeholder<Player>> placeholders) {
        return this;
    }
}
