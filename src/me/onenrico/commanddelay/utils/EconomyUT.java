package me.onenrico.commanddelay.utils;

import org.bukkit.entity.Player;

import me.onenrico.commanddelay.main.Core;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyUT {
	Core instance;

	public EconomyUT() {
		instance = Core.getThis();
	}

	public static double getRawBal(Player player) {
		double bal = Core.v_economy.getBalance(player);
		return bal;
	}

	public static Boolean has(Player player, double amount) {
		return Core.v_economy.has(player, amount);
	}

	public static String format(double amount) {
		return Core.v_economy.format(amount);
	}

	public static String getBal(Player player) {
		String bal = format(getRawBal(player));
		return bal;
	}

	public static EconomyResponse addBal(Player player, double amount) {
		EconomyResponse trans = Core.v_economy.depositPlayer(player, amount);
		return trans;
	}

	public static EconomyResponse subtractBal(Player player, double amount) {
		EconomyResponse trans = Core.v_economy.withdrawPlayer(player, amount);
		return trans;
	}
}
