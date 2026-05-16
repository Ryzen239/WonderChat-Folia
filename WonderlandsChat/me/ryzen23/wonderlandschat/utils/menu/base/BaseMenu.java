package me.ryzen23.wonderlandschat.utils.menu.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.ryzen23.wonderlandschat.utils.item.ItemUtil;
import me.ryzen23.wonderlandschat.utils.menu.element.MenuElement;
import me.ryzen23.wonderlandschat.utils.menu.element.Renderable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseMenu
implements InventoryHolder {
    private final Inventory inventory;
    private final UUID destinationPlayerId;
    private final List<Renderable> renderables = new ArrayList<Renderable>();
    private final Map<Integer, MenuElement> elements = new HashMap<Integer, MenuElement>();
    private Runnable closeTask;
    private boolean allowRemoveItems = false;

    public BaseMenu(UUID uUID, String string, int n) {
        this.inventory = Bukkit.createInventory((InventoryHolder)this, (int)(n * 9), (String)string);
        this.destinationPlayerId = uUID;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void clearElements() {
        this.elements.clear();
        this.forceUpdate();
    }

    public void open() {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        JavaPlugin javaPlugin = JavaPlugin.getProvidingPlugin(BaseMenu.class);
        player.getScheduler().run((Plugin)javaPlugin, scheduledTask -> player.openInventory(this.inventory), null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer((UUID)this.destinationPlayerId);
    }

    public void addRenderable(Renderable ... renderableArray) {
        this.renderables.addAll(Arrays.asList(renderableArray));
    }

    public void forceUpdate() {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        for (Renderable object : this.renderables) {
            if (!object.isActive()) continue;
            object.forceUpdate();
        }
        for (Map.Entry entry : this.elements.entrySet()) {
            int n = (Integer)entry.getKey();
            MenuElement menuElement = (MenuElement)entry.getValue();
            ItemStack itemStack = menuElement.getDisplayItem().clone();
            ItemUtil.replacePlaceholder(itemStack, player, menuElement.getItemPlaceholders());
            this.inventory.setItem(n, itemStack);
        }
    }

    public void setElement(int n, MenuElement menuElement) {
        this.elements.put(n, menuElement);
    }

    public void setAllowRemoveItems(boolean bl) {
        this.allowRemoveItems = bl;
    }

    public void onClose(Runnable runnable) {
        this.closeTask = runnable;
    }

    public boolean isAllowRemoveItems() {
        return this.allowRemoveItems;
    }

    public void handleClick(InventoryClickEvent inventoryClickEvent) {
        int n = inventoryClickEvent.getRawSlot();
        if (n < 0 || n >= this.inventory.getSize()) {
            if (inventoryClickEvent.isShiftClick()) {
                inventoryClickEvent.setCancelled(true);
            }
            return;
        }
        MenuElement menuElement = this.elements.get(n);
        if (menuElement == null) {
            return;
        }
        menuElement.handle(inventoryClickEvent);
        if (!this.allowRemoveItems) {
            inventoryClickEvent.setCancelled(true);
        }
    }

    public void handleClose() {
        if (this.closeTask != null) {
            this.closeTask.run();
        }
    }
}
