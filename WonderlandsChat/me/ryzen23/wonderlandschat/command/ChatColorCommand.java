package me.ryzen23.wonderlandschat.command;

import me.ryzen23.wonderlandschat.WonderlandsChatPlugin;
import me.ryzen23.wonderlandschat.gui.ChatColorMenu;
import me.ryzen23.wonderlandschat.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatColorCommand
implements SimpleCommand {
    private final WonderlandsChatPlugin plugin;

    public ChatColorCommand(WonderlandsChatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"cc"};
    }

    @Override
    public String getIdentifier() {
        return "chatcolor";
    }

    @Override
    public void execute(CommandSender sender, String ... args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return;
        }
        new ChatColorMenu(this.plugin).open((Player)sender);
    }
}
