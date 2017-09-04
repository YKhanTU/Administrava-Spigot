package administrava.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AGui {
	
	private Inventory gui;
	
	public AGui(String title, InventoryType type) {
		gui = Bukkit.createInventory(null, type, title);
	}
	
	public abstract void onClickEvent(InventoryClickEvent event);
	
	public void open(Player player) {
		player.openInventory(gui);
	}
	
	public void setButton(int index, ItemStack type, String name, String... description) {
		ItemMeta meta = type.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(description));
		type.setItemMeta(meta);
		
		gui.setItem(index, type);
	}
}
