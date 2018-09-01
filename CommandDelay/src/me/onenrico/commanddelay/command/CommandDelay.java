package me.onenrico.commanddelay.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.locale.Locales;
import me.onenrico.commanddelay.utils.MessageUT;
import me.onenrico.commanddelay.utils.PermissionUT;
import me.onenrico.commanddelay.utils.PlayerUT;

public class CommandDelay implements CommandExecutor {
	public boolean hasLength(int a) {
		if (tempargs.length < a) {
			return false;
		}
		return true;
	}

	public boolean cekArg(int index, String cek) {
		if (hasLength(index)) {
			if (tempargs[index - 1].equalsIgnoreCase(cek)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {
			command(PlayerUT.getPlayer(sender), args);
		} else {
			command(sender, args);
		}
		return true;
	}

	private String[] tempargs;
	public HashMap<Player, String> data = new HashMap<>();

	String hl = "&8&l[&e&m----------------&f CommandDelay Command &e&m----------------&8&l]";

	public void help(Player player) {
		MessageUT.pmessage(player, hl);
		MessageUT.pmessage(player, "");
		MessageUT.plmessage(player, "&f/commanddelay reload");
		MessageUT.pmessage(player, "");
		MessageUT.pmessage(player, hl);
	}

	public void command(Player player, String[] args) {
		tempargs = args;
		if (cekArg(1, "reload")) {
			if (PermissionUT.check(player, "commanddelay.reload")) {
				MessageUT.plmessage(player, Locales.message_command_reload);
				ConfigPlugin.reloadSetting();
				return;
			}
			return;
		}
		help(player);
	}

	public void command(CommandSender console, String[] args) {
		tempargs = args;
		if (cekArg(1, "reload")) {
			MessageUT.cmessage(Locales.message_command_reload);
			ConfigPlugin.reloadSetting();
			return;
		}
	}
}
