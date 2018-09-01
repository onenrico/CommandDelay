package me.onenrico.commanddelay.hooker;

import org.bukkit.plugin.RegisteredServiceProvider;

import me.onenrico.commanddelay.main.Core;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class vaultHook {

	static Core instance;

	public vaultHook() {
		instance = Core.getThis();
	}

	public boolean setupEconomy() {
		if (instance.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = instance.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		Core.v_economy = rsp.getProvider();
		return Core.v_economy != null;
	}

	public boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = instance.getServer().getServicesManager()
				.getRegistration(Permission.class);
		Core.v_permission = rsp.getProvider();
		return Core.v_permission != null;
	}

	public boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = instance.getServer().getServicesManager().getRegistration(Chat.class);
		Core.v_chat = rsp.getProvider();
		return Core.v_chat != null;
	}
}
