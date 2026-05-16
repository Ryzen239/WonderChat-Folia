package me.ryzen23.wonderlandschat.listeners;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import me.ryzen23.wonderlandschat.WonderlandsChatPlugin;
import me.ryzen23.wonderlandschat.data.PlayerData;
import me.ryzen23.wonderlandschat.data.sql.SQLDatabase;
import me.ryzen23.wonderlandschat.data.sql.objects.SQLTable;
import me.ryzen23.wonderlandschat.storage.PlayerStorage;
import me.ryzen23.wonderlandschat.utils.menu.base.BaseMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener
implements Listener {
    private final PlayerStorage playerStorage;
    private final SQLDatabase database;

    public InventoryCloseListener(WonderlandsChatPlugin plugin) {
        this.playerStorage = plugin.getPlayerStorage();
        this.database = plugin.getSqlDatabase();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        Inventory playerInv = player.getOpenInventory().getTopInventory();
        if (!(playerInv.getHolder() instanceof BaseMenu)) {
            return;
        }
        UUID playerId = player.getUniqueId();
        SQLTable colorTable = this.database.getOrCreateTable("chatcolor");
        PlayerData data = this.playerStorage.getPlayerData(playerId);
        String color = data.getColor();
        String format = data.getFormat();
        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("player_id", playerId.toString());
        dataMap.put("color", color);
        dataMap.put("format", format);
        String selectQuery = "SELECT * FROM `chatcolor` WHERE player_id = '" + playerId + "';";
        colorTable.executeQuery(selectQuery, new Object[0]).thenAccept(resultSet -> {
            try {
                if (resultSet.next()) {
                    String updateQuery = "UPDATE `chatcolor` SET color = '" + color + "', format = '" + format + "' WHERE player_id = '" + playerId + "';";
                    colorTable.executeQuery(updateQuery, new Object[0]);
                } else {
                    colorTable.insert(dataMap);
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
