package me.onenrico.commanddelay.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.utils.DelayUT;

public class Interactevent implements Listener {
	Core instance;

	public Interactevent() {
		instance = Core.getThis();
	}

	@EventHandler
	public void interaction(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (ConfigPlugin.getCancel("interact")) {
			DelayUT.cancelCommand(player);
		}
	}

}
