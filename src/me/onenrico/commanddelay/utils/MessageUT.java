package me.onenrico.commanddelay.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.locale.Locales;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.nms.actionbar.ActionBar;
import me.onenrico.commanddelay.nms.titlebar.TitleBar;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUT {

	public static String t(String colorize) {
		return ChatColor.translateAlternateColorCodes('&', colorize);
	}

	public static String u(String decolorize) {
		return decolorize.replace('§', '&');
	}

	public static String d(String remove) {
		remove = MessageUT.t(remove);
		for (ChatColor color : ChatColor.values()) {
			remove = remove.replaceAll(color.toString(), "");
		}
		return remove;
	}

	public static List<HashMap<String, String>> generateJson(String text) {
		List<HashMap<String, String>> end = new ArrayList<>();
		String[] texts = text.split(" ");
		for (String c : texts) {
			String[] splitter = c.split(jsonsplit);
			if (splitter.length > 1) {
				end.add(generateJson(splitter[0], splitter[1]));
			} else {
				end.add(generateJson(splitter[0], ""));
			}
		}
		return end;
	}

	public static String jsonsplit = ("#%#");

	public static HashMap<String, String> generateJson(String text, String cmd) {
		HashMap<String, String> end = new HashMap<>();
		end.put(text, cmd);
		return end;
	}

	public static void jmessage(Player player, List<HashMap<String, String>> data) {
		List<TextComponent> textlist = new ArrayList<>();
		for (HashMap<String, String> map : data) {
			Set<String> key = map.keySet();
			for (String text : key) {
				String cache = map.get(text);
				text = MessageUT.t(text);
				TextComponent single = new TextComponent(text);
				String[] temp = cache.split("<>");
				for (String eventstr : temp) {
					eventstr = eventstr.replace("_", " ");
					if (eventstr.contains("#C:")) {
						eventstr = eventstr.replace("#C:", "");
						if (eventstr.contains("$RUN:")) {
							eventstr = eventstr.replace("$RUN:", "");
							String cmd = eventstr;
							single.setClickEvent(new ClickEvent(Action.RUN_COMMAND, cmd));
						}
						if (eventstr.contains("$SUGGEST:")) {
							eventstr = eventstr.replace("$SUGGEST:", "");
							String cmd = eventstr;
							single.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, cmd));
						}
						if (eventstr.contains("$URL:")) {
							eventstr = eventstr.replace("$URL:", "");
							String cmd = eventstr;
							single.setClickEvent(new ClickEvent(Action.OPEN_URL, cmd));
						}
					}
					if (eventstr.contains("#H:")) {
						eventstr = eventstr.replace("#H:", "");
						if (eventstr.contains("$TEXT:")) {
							eventstr = eventstr.replace("$TEXT:", "");
							String msg = MessageUT.t(eventstr);
							single.setHoverEvent(
									new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msg).create()));
						}
					}
				}
				textlist.add(single);
			}
		}
		TextComponent rs = new TextComponent("");
		for (TextComponent tc : textlist) {
			tc.setText(tc.getText() + " ");
			rs.addExtra(tc);
		}
		player.spigot().sendMessage(rs);
	}

	public static void cmessage(String teks) {
		Core.getThis().getServer().getConsoleSender().sendMessage(t(teks));
	}

	public static void debug(String o) {
		Core.getThis().getServer().getConsoleSender().sendMessage(t("&8[&dDebug&8] &f" + o));
	}

	public static void debug(Player player, String o) {
		pmessage(player, "&8[&dDebug&8] &f" + o);
	}

	// PLAYER MESSAGE
	public static void pmessage(Player player, String teks) {
		pmessage(player, teks, false);
	}

	public static void pmessage(Player player, String teks, Boolean Action) {
		player.sendMessage(t(teks));
		;
		if (Action) {
			acmessage(player, teks);
		}
	}

	public static void plmessage(Player player, String teks) {
		plmessage(player, teks, false);
	}

	public static void plmessage(Player player, String teks, Boolean warning) {
		plmessage(player, teks, warning, false);
	}

	public static void plmessage(Player player, String teks, Boolean warning, Boolean Action) {
		if (warning) {
			pmessage(player, MessageUT.pluginPrefix + "&c" + teks, Action);
		} else {
			pmessage(player, MessageUT.pluginPrefix + "&b" + teks, Action);
		}
	}
	// PLAYER MESSAGE

	// ACTIONBAR MESSAGE
	public static void acplmessage(Player player, String teks, Boolean warning) {
		if (warning) {
			acmessage(player, MessageUT.pluginPrefix + "&c" + teks);
		} else {
			acmessage(player, MessageUT.pluginPrefix + "&b" + teks);
		}
	}

	public static void acplmessage(Player player, String teks) {
		acplmessage(player, teks, false);
	}

	public static void acmessage(Player player, String teks) {
		teks = MessageUT.t(teks);
		if (!ConfigPlugin.getConfig().getBoolean("message_actionbar_enabled")) {
			return;
		}
		ActionBar.sendActionBar(player, teks);
	}
	// ACTIONBAR MESSAGE

	// TITLEBAR MESSAGE
	public static void tfullmessage(Player player, String title, String subtitle, int fadein, int stay, int fadeout) {
		TitleBar.sendTitle(player, fadein, stay, fadeout, t(title), t(subtitle));
	}

	public static void tfullmessage(Player player, String title, String subtitle) {
		tfullmessage(player, title, subtitle, 20, 60, 20);
	}

	public static void tsubmessage(Player player, String subtitle, int fadein, int stay, int fadeout) {
		tfullmessage(player, null, subtitle, fadein, stay, fadeout);
	}

	public static void tsubmessage(Player player, String subtitle) {
		tfullmessage(player, null, subtitle, 20, 60, 20);
	}

	public static void ttmessage(Player player, String title, int fadein, int stay, int fadeout) {
		tfullmessage(player, title, null, fadein, stay, fadeout);
	}

	public static void ttmessage(Player player, String title) {
		tfullmessage(player, null, title, 20, 60, 20);
	}
	// TITLEBAR MESSAGE

	public static final String pluginPrefix = Locales.get("message_prefix", "&8[&dCommandDelay&8]");
}
