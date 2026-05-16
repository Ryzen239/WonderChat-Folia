package me.ryzen23.wonderlandschat.command;

import me.ryzen23.wonderlandschat.WonderlandsChatPlugin;
import me.ryzen23.wonderlandschat.data.PlayerData;
import me.ryzen23.wonderlandschat.utils.command.SimpleCommand;
import me.ryzen23.wonderlandschat.utils.storage.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatColorResetCommand
implements SimpleCommand {
    private final WonderlandsChatPlugin plugin;
    private final MessagesFile messages;

    public ChatColorResetCommand(WonderlandsChatPlugin plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Override
    public String getIdentifier() {
        return "chat.reset.*";
    }

    @Override
    public String getPermission() {
        return "wonderlandschat.reset";
    }

    @Override
    public void execute(CommandSender sender, String ... args) {
        Player player;
        if (args[0].isEmpty()) {
            this.messages.sendMessage(sender, "specify-player");
        }
        if ((player = Bukkit.getPlayer((String)args[0])) == null) {
            this.messages.sendMessage(sender, "player-not-found", message -> message.replace("%player%", args[0]));
        }
        PlayerData data = this.plugin.getPlayerStorage().getPlayerData(player.getUniqueId());
        data.reset();
        this.plugin.getMessages().sendMessage((CommandSender)player, "reset-player", message -> message.replace("%player%", player.getName()));
    }
}
