package me.onenrico.commanddelay.utils;

import java.util.HashMap;

public class PlaceholderUT {
	private HashMap<String, String> acuan;

	public PlaceholderUT(HashMap<String, String> acuan) {
		this.acuan = acuan;
	}

	public String t(String data) {
		for (String a : acuan.keySet()) {
			if (data.contains("{" + a + "}")) {
				data = data.replace("{" + a + "}", acuan.get(a));
			}
		}
		return MessageUT.t(data);
	}
}
