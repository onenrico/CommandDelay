package me.onenrico.commanddelay.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.utils.DelayUT;

public class MoveEvent implements Listener {

	public MoveEvent() {
	}

	@EventHandler
	public void playerMove(final PlayerMoveEvent event) {
		double tox = event.getTo().getBlockX();
		double toz = event.getTo().getBlockZ();
		double toy = event.getTo().getBlockY();
		double fromy = event.getFrom().getBlockY();
		double fromx = event.getFrom().getBlockX();
		double fromz = event.getFrom().getBlockZ();
		if (ConfigPlugin.getCancel("jump")) {
			if (toy != fromy) {
				DelayUT.cancelTeleport(event.getPlayer());
			}
		}
		if (ConfigPlugin.getCancel("move")) {
			if (tox != fromx || toz != fromz) {
				DelayUT.cancelTeleport(event.getPlayer());
			}
		}
	}
}
