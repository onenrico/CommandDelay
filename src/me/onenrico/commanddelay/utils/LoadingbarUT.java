package me.onenrico.commanddelay.utils;

import me.onenrico.commanddelay.config.ConfigPlugin;

public class LoadingbarUT {

	public static String getBar(int totalBar, float value, float maxvalue, barType bar, Boolean reverse,
			String filledColor, String unfilledColor) {
		String hasil = "";
		String barSymbol = barCheck(bar);
		int persentase = (int) MathUT.getPersentase(value, maxvalue);
		int barFill = (int) MathUT.getRealvalue(totalBar, persentase);
		String unfilled = unfilledColor;
		String filled = filledColor;
		for (int x = 0; x < totalBar; x++) {
			if (reverse) {
				if (x < barFill) {
					hasil += unfilled + barSymbol;
				} else {
					hasil += filled + barSymbol;
				}
			} else {
				if (x < barFill) {
					hasil += filled + barSymbol;
				} else {
					hasil += unfilled + barSymbol;
				}
			}
		}
		return hasil;
	}

	public static String getBar(int totalBar, int value, int maxvalue, barType bar) {
		return getBar(totalBar, value, maxvalue, bar, false);
	}

	public static String getBar(int totalBar, int value, int maxvalue, barType bar, Boolean reverse) {
		return getBar(totalBar, value, maxvalue, bar, reverse, "&f", "&8");
	}

	public static String barCheck(barType bar) {
		switch (bar) {
		case FULL_HIGH:
			return ConfigPlugin.getStr("message_actionbar_symbol", "█");
		default:
			return "NO";
		}
	}

	public enum barType {
		VERY_LOW, LOW, MEDIUM, FULL_HIGH,
	}
}
