package administrava.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import administrava.Administrava;
import administrava.util.ChatHelper;
import net.md_5.bungee.api.ChatColor;


public class ItemListener implements Listener {

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Item i = event.getItemDrop();
		
		if(i.getItemStack().getType() == Material.WHITE_SHULKER_BOX) {
			
			ItemMeta meta = i.getItemStack().getItemMeta();
					
			if(meta.getDisplayName().contains("Loot Box")) {
				
				Player player = event.getPlayer();
				
				ChatHelper.sendMessage(player, ChatColor.RED + "A mysterious force restricts you from dropping this item.");
				
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onItemUse(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		ItemStack itemStack = event.getItem();
		
		if(itemStack != null && itemStack.getType() == Material.WHITE_SHULKER_BOX 
				&& itemStack.getItemMeta().getDisplayName().contains("Loot Box")) {
		
			Action action = event.getAction();
			
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				Inventory shulkerInv = Bukkit.createInventory(null, 27, ChatColor.GRAY + "Level 1 Loot Box [Opened]");
				
				for(int i = 0; i < 27; i++) {
					shulkerInv.setItem(0, new ItemStack(Material.DIAMOND));
				}
						
//				shulkerBox.getInventory().setContents(shulkerInv.getContents());
						
				player.openInventory(shulkerInv);
						
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(event.getPlayer() instanceof Player) {
			
			Player player = (Player) event.getPlayer();
			
			Inventory i = event.getInventory();
			
			if(i.getName().contains("Loot Box")) {
				
				Inventory playerInventory = player.getInventory();
				if(playerInventory.contains(Material.WHITE_SHULKER_BOX)) {
					
					ItemStack[] contents = playerInventory.getContents();
					
					for(int j = 0; j < contents.length; j++) {
						
						ItemStack item = contents[j];
						
						if(item.getType() == Material.WHITE_SHULKER_BOX) {
							
							ItemMeta meta = item.getItemMeta();
							
							if(meta.getDisplayName().contains("Loot Box")) {
								
								final int remove = j;
								
								BukkitRunnable runnable = new BukkitRunnable() {
									
									@Override
									public void run() {
										
										ChatHelper.sendMessage(player, ChatColor.RED + "A mysterious force emerges and the Loot Box vanishes.");
										player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
										playerInventory.clear(remove);
									}
								};
								
								BukkitTask task = runnable.runTaskLater(Administrava.getInstance(), 1L);
								
								return;
							}
						}
					}
				}
			}
		}
	}
}