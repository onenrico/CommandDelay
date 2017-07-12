package me.onenrico.commanddelay.utils;

import org.bukkit.entity.Player;

import me.onenrico.commanddelay.locale.Locales;
import me.onenrico.commanddelay.main.Core;

public class PermissionUT {
	public static boolean check(Player player, String perm) {
		if (has(player, perm)) {
			return true;
		} else {
			String msg = Locales.message_command_nopermission.replace("{perm}", perm);
			MessageUT.plmessage(player, msg, true);
			return false;
		}
	}

	public static boolean has(Player player, String perm) {
		Boolean op = false;
		if (player.isOp()) {
			op = true;
			player.setOp(false);
		}
		if (Core.v_permission == null) {
			if (player.hasPermission(perm)) {
				player.setOp(op);
				op = false;
				return true;
			}
			player.setOp(op);
			op = false;
			return false;
		}
		if (Core.v_permission.has(player, perm)) {
			player.setOp(op);
			op = false;
			return true;
		}
		player.setOp(op);
		op = false;
		return false;
	}
}
