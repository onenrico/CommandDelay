package me.onenrico.commanddelay.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUT {

	public static ItemStack createItem(Material material) {
		return createItem(material, 1);
	}

	public static ItemStack createItem(Material material, int amount) {
		return createItem(material, amount, (short) 0);
	}

	public static ItemStack createItem(Material material, short data) {
		return createItem(material, 1, data);
	}

	public static ItemStack createItem(Material material, int amount, short data) {
		return createItem(material, null, null, amount, data);
	}

	public static ItemStack createItem(Material material, List<String> lore) {
		ItemStack item = createItem(material);
		return changeLore(item, lore);
	}

	public static ItemStack createItem(Material material, String displayname) {
		ItemStack item = createItem(material);
		return changeDisplayName(item, displayname);
	}

	public static ItemStack createItem(Material material, String displayname, short data) {
		return createItem(material, displayname, null, 1, data);
	}

	public static ItemStack createItem(Material material, String displayname, List<String> lore, short data) {
		return createItem(material, displayname, lore, 1, data);
	}

	public static ItemStack createItem(Material material, String displayname, List<String> lore, int amount) {
		return createItem(material, displayname, lore, amount, (short) 0);
	}

	public static ItemStack createItem(Material material, String displayname, List<String> lore) {
		return createItem(material, displayname, lore, 1);
	}

	public static ItemStack createItem(Material material, String displayname, List<String> lore, int amount,
			short data) {
		ItemStack item = new ItemStack(material, amount);
		if (data >= 0) {
			item = changeData(item, data);
		}
		if (displayname != null) {
			item = changeDisplayName(item, displayname);
		}
		if (lore != null) {
			item = changeLore(item, lore);
		}
		return item;
	}

	public static List<String> getLore(ItemStack item) {
		if (item.hasItemMeta()) {
			return item.getItemMeta().getLore();
		} else {
			return null;
		}
	}

	public static String getName(ItemStack item) {
		if (item.hasItemMeta()) {
			return item.getItemMeta().getDisplayName();
		} else {
			return null;
		}
	}

	public static ItemStack changeData(ItemStack item, short data) {
		ItemStack newitem = new ItemStack(item.getType(), item.getAmount(), data);
		if (item.hasItemMeta()) {
			newitem.setItemMeta(item.getItemMeta());
		}
		return newitem;
	}

	public static ItemStack changeDisplayName(ItemStack item, String displayname) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUT.t(displayname));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack changeLore(ItemStack item, List<String> Lore) {
		ItemMeta meta = item.getItemMeta();
		List<String> theLore = new ArrayList<String>();
		for (String l : Lore) {
			theLore.add(MessageUT.t(l));
		}
		meta.setLore(theLore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack changeLore(ItemStack item, String Lore, int line) {
		ItemMeta meta = item.getItemMeta();
		List<String> theLore = new ArrayList<String>();
		if (meta.hasLore()) {
			theLore = getLore(item);
			theLore.set(line - 1, MessageUT.t(Lore));
		} else {
			theLore.set(line - 1, MessageUT.t(Lore));
		}
		meta.setLore(theLore);
		item.setItemMeta(meta);
		return item;
	}

	public static List<String> createLore(String lores, String splitter) {
		return createLore(lores, splitter, "", "");
	}

	public static List<String> createLore(String lores, String splitter, String prefix) {
		return createLore(lores, splitter, prefix, "");
	}

	public static List<String> createLore(String lores) {
		return createLore(lores, "%n%");
	}

	public static List<String> createLore(String lores, String splitter, String prefix, String suffix) {
		List<String> Lore = new ArrayList<String>();
		prefix = MessageUT.t(prefix);
		suffix = MessageUT.t(suffix);
		String[] loreArray = MessageUT.t(lores).split(splitter);
		for (String l : loreArray) {
			Lore.add(prefix + l + suffix);
		}
		return Lore;
	}

	public static boolean cekItem(Player player, Material material, int amount) {
		return removeItem(player, material, amount, false);
	}

	public static boolean removeItem(Player player, ItemStack item, int amount, Boolean remove) {
		if (amount < 0) {
			return true;
		}
		ItemStack[] contents = player.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				ItemStack content = contents[i];
				if (content.getType().equals(item.getType())) {
					if (ItemUT.getName(item).equals(ItemUT.getName(content))
							&& ItemUT.getLore(item).equals(ItemUT.getLore(content))) {
						int contentamount = content.getAmount();
						if (amount >= contentamount) {
							amount -= contentamount;
							if (remove) {
								player.getInventory().setItem(i, null);
							}
						} else {
							contentamount -= amount;
							if (remove) {
								content.setAmount(contentamount);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean removeItem(Player player, Material material, int amount, Boolean remove) {
		return removeItem(player.getInventory(), material, amount, remove);
	}

	public static boolean removeItem(Inventory inv, Material material, int amount, Boolean remove) {
		if (amount < 0) {
			return true;
		}
		ItemStack[] contents = inv.getContents();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				ItemStack content = contents[i];
				if (content.getType().equals(material)) {
					int contentamount = content.getAmount();
					if (amount >= contentamount) {
						amount -= contentamount;
						if (remove) {
							inv.setItem(i, null);
						}
					} else {
						contentamount -= amount;
						if (remove) {
							content.setAmount(contentamount);
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	public static Boolean hasLore(ItemStack item, String check) {
		return hasLore(item, check, false);
	}

	public static Boolean hasLore(ItemStack item, String check, Boolean contains) {
		List<String> lores = getLore(item);
		if (lores == null) {
			return false;
		}
		for (String lore : lores) {
			lore = MessageUT.u(lore);
			check = MessageUT.u(check);
			if (contains) {
				if (lore.contains(check)) {
					return true;
				}
			} else {
				if (lore.equals(check)) {
					return true;
				}
			}
		}
		return false;
	}

	public static ItemStack setGlowing(ItemStack item, Boolean glow) {
		ItemMeta meta = item.getItemMeta();
		if (glow) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
		} else {
			meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.removeEnchant(Enchantment.ARROW_INFINITE);
		}
		item.setItemMeta(meta);
		return item;
	}
}
