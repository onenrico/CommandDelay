package me.onenrico.commanddelay.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.onenrico.commanddelay.command.CommandDelay;
import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.events.Interactevent;
import me.onenrico.commanddelay.events.MoveEvent;
import me.onenrico.commanddelay.events.OtherEvent;
import me.onenrico.commanddelay.hooker.vaultHook;
import me.onenrico.commanddelay.nms.actionbar.ActionBar;
import me.onenrico.commanddelay.nms.particle.ParticleManager;
import me.onenrico.commanddelay.utils.MessageUT;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Core extends JavaPlugin {
	public vaultHook v_hook;
	public ConfigPlugin settings;
	public ActionBar actionbar;
	public static Chat v_chat = null;
	public static Economy v_economy = null;
	public static Permission v_permission = null;
	public ParticleManager particleManager;

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		setupConstructor();
		setupEvent();
		setupDepedency();
		setupNMS();
		if (!v_hook.setupEconomy()) {
			MessageUT.cmessage(MessageUT.pluginPrefix + " &cPlease Install Vault and Economy Plugin !");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		File cekConfig = new File(getDataFolder(), "config.yml");
		if (!cekConfig.exists()) {
			MessageUT.cmessage(MessageUT.pluginPrefix + " &aGenerating Default Config...");
		}
		ConfigPlugin.setupSetting();
		getServer().getPluginCommand("commanddelay").setExecutor(new CommandDelay());
	}

	@Override
	public void onDisable() {
		MessageUT.cmessage(MessageUT.pluginPrefix + "&cPlugin Disabled !");
		v_hook = null;
		settings = null;
		actionbar = null;
		if (ConfigPlugin.changed) {
			saveConfig();
			MessageUT.cmessage(MessageUT.pluginPrefix + " &cConfig Saved !");
		} else {
			return;
		}
	}

	private static Core instance;

	public static Core getThis() {
		return instance;
	}

	private void setupConstructor() {
		settings = new ConfigPlugin();
		v_hook = new vaultHook();
	}

	private void setupEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(new Interactevent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new MoveEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new OtherEvent(), this);
	}

	public static String nmsver;

	private void setupNMS() {

		nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		actionbar = new ActionBar();
		ActionBar.setup();
		
		particleManager = new ParticleManager();

	}

	private void setupDepedency() {
		v_hook.setupPermissions();
		v_hook.setupChat();
	}
}
