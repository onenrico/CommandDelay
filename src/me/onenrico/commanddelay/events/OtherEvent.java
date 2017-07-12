package me.onenrico.commanddelay.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.object.Category;
import me.onenrico.commanddelay.utils.DelayUT;
import me.onenrico.commanddelay.utils.PermissionUT;

public class OtherEvent implements Listener {
	public static List<Player> delayed = new ArrayList<>();

	@EventHandler
	public void playerCmd(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (delayed.contains(player)) {
			delayed.remove(player);
			return;
		}
		String ce = event.getMessage().replace("/", "");
		String cworld = player.getWorld().getName();
		if (!PermissionUT.has(player, "commanddelay.bypass")) {
			for (Category ca : ConfigPlugin.getCategory()) {
				String blacklist = ca.getBlacklist();
				if (blacklist == null) {
					List<String> commands = ca.getCommands();
					for (String c : commands) {
						Boolean pass = false;
						String ceb = ce.toLowerCase().split(" ")[0];
						if (!c.contains(" ")) {
							if (ceb.equals(c)) {
								pass = true;
							}
						} else {
							if(ce.startsWith(c)) {
								pass = true;
							}
						}
						if (pass) {
							DelayUT.Delayed(player, ca.getDelay(), ce, ca.getCost());
							event.setCancelled(true);
							return;
						}

					}
				} else if (blacklist.equalsIgnoreCase("true")) {
					for (String world : ca.getWorlds()) {
						if (cworld.equals(world)) {
							return;
						}
						List<String> commands = ca.getCommands();
						for (String c : commands) {
							Boolean pass = false;
							String ceb = ce.toLowerCase().split(" ")[0];
							if (!c.contains(" ")) {
								if (ceb.equals(c)) {
									pass = true;
								}
							} else {
								if(ce.startsWith(c)) {
									pass = true;
								}
							}
							if (pass) {
								DelayUT.Delayed(player, ca.getDelay(), ce, ca.getCost());
								event.setCancelled(true);
								return;
							}
						}

					}
				} else if (blacklist.equalsIgnoreCase("false")) {
					for (String world : ca.getWorlds()) {
						if (cworld.equals(world)) {
							List<String> commands = ca.getCommands();
							for (String c : commands) {
								Boolean pass = false;
								String ceb = ce.toLowerCase().split(" ")[0];
								if (!c.contains(" ")) {
									if (ceb.equals(c)) {
										pass = true;
									}
								} else {
									if(ce.startsWith(c)) {
										pass = true;
									}
								}
								if (pass) {
									DelayUT.Delayed(player, ca.getDelay(), ce, ca.getCost());
									event.setCancelled(true);
									return;
								}
							}
							return;
						}
					}
				}
			}
		}
		if (ConfigPlugin.getCancel("command")) {
			DelayUT.cancelTeleport(event.getPlayer());
		}

	}

	@EventHandler
	public void itemDrop(final PlayerDropItemEvent event) {
		if (ConfigPlugin.getCancel("drop")) {
			DelayUT.cancelTeleport(event.getPlayer());
		}
	}

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event) {
		if (ConfigPlugin.getCancel("chat")) {
			DelayUT.cancelTeleport(event.getPlayer());
		}
	}

	@EventHandler
	public void playerBreak(BlockBreakEvent event) {
		if (ConfigPlugin.getCancel("break")) {
			DelayUT.cancelTeleport(event.getPlayer());
		}
	}

	@EventHandler
	public void playerPlace(BlockPlaceEvent event) {
		if (ConfigPlugin.getCancel("place")) {
			DelayUT.cancelTeleport(event.getPlayer());
		}
	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {
		if (ConfigPlugin.getCancel("death")) {
			DelayUT.cancelTeleport(event.getEntity());
		}
	}

	@EventHandler
	public void playerDamage(EntityDamageByEntityEvent event) {
		if (event.getEntityType().equals(EntityType.PLAYER)) {
			Player player = (Player) event.getEntity();
			if (ConfigPlugin.getCancel("damaged")) {
				DelayUT.cancelTeleport(player);
			}
		}
		if (event.getDamager().equals(EntityType.PLAYER)) {

			Player player = (Player) event.getDamager();
			if (ConfigPlugin.getCancel("damage")) {
				DelayUT.cancelTeleport(player);
			}
		}
	}

}
