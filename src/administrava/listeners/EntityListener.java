package administrava.listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import administrava.Administrava;
import administrava.util.RandomUtil;
import net.md_5.bungee.api.ChatColor;

public class EntityListener implements Listener {

	private static EntityType[] mobTypes = {
			EntityType.BLAZE,
			EntityType.CAVE_SPIDER,
			EntityType.CREEPER,
			EntityType.ELDER_GUARDIAN,
			EntityType.ENDER_DRAGON,
			EntityType.ENDERMAN,
			EntityType.ENDERMITE,
			EntityType.EVOKER,
			EntityType.GHAST,
			EntityType.GUARDIAN,
			EntityType.ILLUSIONER,
			EntityType.MAGMA_CUBE,
			EntityType.SHULKER,
			EntityType.SILVERFISH,
			EntityType.SKELETON,
			EntityType.SKELETON_HORSE,
			EntityType.SLIME,
			EntityType.SPIDER,
			EntityType.VEX,
			EntityType.VINDICATOR,
			EntityType.WITCH,
			EntityType.WITHER,
			EntityType.WITHER_SKELETON,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE_HORSE,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.PIG_ZOMBIE
	};
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity e = event.getEntity();
		
		EntityDamageEvent.DamageCause cause = e.getLastDamageCause().getCause();
		
		if(cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || cause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK ||
				cause == EntityDamageEvent.DamageCause.PROJECTILE || cause == EntityDamageEvent.DamageCause.THORNS ||
				cause == EntityDamageEvent.DamageCause.MAGIC) {
			
			// Player gets loot box off of luck
			// Loot box rises up, then plays some cute little tunes
			// Drops Shulker "Loot Box"
			// Player right-clicks it to open what is inside (change name to 'Opened Loot Box'), then it disappears.
			
				for(EntityType type : mobTypes) {
					if(e.getType() == type/*& canDrop*/) {
						
						double chance = RandomUtil.getRandom().nextDouble();
						
						if(chance <= .1D) {		
							ArmorStand as = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().add(new Vector(0, .5D, 0)), EntityType.ARMOR_STAND);
							
							as.setCustomName(ChatColor.GOLD + "Loot Box" + ChatColor.GRAY + " Level 1" + ChatColor.GREEN + "Found!");
							as.setCustomNameVisible(true);
							as.setHelmet(new ItemStack(Material.WHITE_SHULKER_BOX));
							as.setGravity(true);
							as.setVisible(false);
							as.setCollidable(false);
							as.setInvulnerable(true);
							as.setCanPickupItems(false);
							
							as.getWorld().playSound(as.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2f, 1f);
							
							BukkitRunnable runnable = new BukkitRunnable() {
								
								long startTime = System.currentTimeMillis();
								
	//							double startHover = as.getLocation().getY();
								double maxHover = as.getLocation().getY() + .15D;
								double minHover = as.getLocation().getY() - .15D;
								
								boolean up = true;
								
								@Override
								public void run() {
									
									long elapsed = System.currentTimeMillis() - startTime;
									
									Location l = as.getLocation();
									
									if(elapsed < 10001) {
										
										if(up) {
											
											if(l.getY() < maxHover) {
												
												as.setVelocity(new Vector(0, .0125D, 0));
											}else{
												
												up = false;
											}
											
										}else{
											
											if(l.getY() > minHover) {
												
												as.setVelocity(new Vector(0, -.0125D, 0));
											}else{
												
												up = true;
											}
										}
										
										as.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, l.getX(), l.getY() + 1.5D, l.getZ(), 1, 0.5, 0.4, 0.5);
										
										as.setCustomName(ChatColor.GOLD + "Loot Chest" + ChatColor.GRAY + " Level 1 - " + up + ", " + elapsed);
										
									}else{
										
										as.getWorld().playSound(l, Sound.ENTITY_ITEM_PICKUP, 5f, 1f);
										as.getWorld().spawnParticle(Particle.SMOKE_LARGE, l.getX(), l.getY() + 1.5D, l.getZ(), 6, .1, .2, .1);
										
										ItemStack itemShulkerBox = new ItemStack(Material.WHITE_SHULKER_BOX);
										ItemMeta meta = itemShulkerBox.getItemMeta();
										meta.setDisplayName(ChatColor.YELLOW + "Level 1 Loot Box");
										meta.setLore(Arrays.asList(new String[] {ChatColor.GRAY + "Common", ChatColor.AQUA + "(Right-click) Open to see what's inside!", ChatColor.DARK_GRAY + "Destroys on use."}));
										meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
										itemShulkerBox.setItemMeta(meta);
										
										
										as.getWorld().dropItemNaturally(as.getLocation().add(new Vector(0, 1.5D, 0)), itemShulkerBox);
										
										as.remove();
										this.cancel();
									}
								}
							};
							
							runnable.runTaskTimer(Administrava.getInstance(), 0, 1L);
							
							return;
						}
					}
				}
//			}
		}
		
//		Chunk c = e.getLocation().getChunk();
		
//		boolean canDrop = false;
//		
//		if(c.isLoaded()) {
//			for(Entity entity : c.getEntities()) {
//				if(entity instanceof Player) {
//					Player p = (Player) entity;
//					
//					if(!p.isDead()) {
//						
//						canDrop = true;
//						return;
//					}
//				}
//			}
//		}
		
	}
}
