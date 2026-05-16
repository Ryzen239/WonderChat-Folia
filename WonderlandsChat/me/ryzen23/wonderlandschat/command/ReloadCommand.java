package me.ryzen23.wonderlandschat.command;

import me.ryzen23.wonderlandschat.WonderlandsChatPlugin;
import me.ryzen23.wonderlandschat.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand
implements SimpleCommand {
    private final WonderlandsChatPlugin plugin;

    public ReloadCommand(WonderlandsChatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "chat.reload";
    }

    @Override
    public String getPermission() {
        return "wonderlandschat.reload";
    }

    @Override
    public void execute(CommandSender sender, String ... args) {
        this.plugin.onDisable();
        this.plugin.onEnable();
        this.plugin.getMessages().sendMessage(sender, "admin.reload");
    }
}
