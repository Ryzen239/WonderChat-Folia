package me.ryzen23.wonderlandschat.listeners;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import me.ryzen23.wonderlandschat.WonderlandsChatPlugin;
import me.ryzen23.wonderlandschat.data.PlayerData;
import me.ryzen23.wonderlandschat.data.sql.SQLDatabase;
import me.ryzen23.wonderlandschat.data.sql.objects.SQLTable;
import me.ryzen23.wonderlandschat.storage.PlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener
implements Listener {
    private final SQLDatabase database;
    private final PlayerStorage playerStorage;

    public PlayerJoinListener(WonderlandsChatPlugin plugin) {
        this.database = plugin.getSqlDatabase();
        this.playerStorage = plugin.getPlayerStorage();
        this.reload();
    }

    public void reload() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.addPlayer(player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.addPlayer(player);
    }

    public void addPlayer(Player player) {
        UUID playerUUID = player.getUniqueId();
        SQLTable colorTable = this.database.getOrCreateTable("chatcolor");
        String query = "SELECT * FROM `chatcolor` WHERE player_id = '" + playerUUID.toString() + "';";
        PlayerData data = new PlayerData(playerUUID);
        ((CompletableFuture)colorTable.executeQuery(query, new Object[0]).thenAccept(resultSet -> {
            try {
                if (resultSet.next()) {
                    String color = resultSet.getString("color");
                    String format = resultSet.getString("format");
                    if (color != null) {
                        if (color.equals("random")) {
                            data.setRandomColor(true);
                        } else {
                            data.setColor(color);
                        }
                    }
                    if (format != null) {
                        data.setFormat(format);
                    }
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).thenRun(() -> this.playerStorage.add(playerUUID, data));
    }
}
