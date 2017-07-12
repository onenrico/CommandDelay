package me.onenrico.commanddelay.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.utils.DelayUT;

public class Interactevent implements Listener {
	Core instance;

	public Interactevent() {
		instance = Core.getThis();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void interaction(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		if (item.getType().equals(Material.WATCH)) {
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					Material mat = event.getClickedBlock().getType();
					if (mat.equals(Material.ANVIL) || mat.equals(Material.CHEST) || mat.equals(Material.ACACIA_DOOR)
							|| mat.equals(Material.BIRCH_DOOR) || mat.equals(Material.DARK_OAK_DOOR)
							|| mat.equals(Material.WOODEN_DOOR) || mat.equals(Material.WOOD_DOOR)
							|| mat.equals(Material.TRAP_DOOR) || mat.equals(Material.WALL_SIGN)
							|| mat.equals(Material.ITEM_FRAME) || mat.equals(Material.SIGN_POST)
							|| mat.equals(Material.HOPPER) || mat.equals(Material.DROPPER)
							|| mat.equals(Material.DISPENSER) || mat.equals(Material.TRAPPED_CHEST)) {
						return;
					}
				}
			}
		}
		if (ConfigPlugin.getCancel("interact")) {
			DelayUT.cancelTeleport(player);
		}
	}

}
