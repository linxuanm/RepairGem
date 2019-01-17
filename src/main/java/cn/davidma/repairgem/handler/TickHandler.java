package cn.davidma.repairgem.handler;

import java.util.HashMap;
import java.util.Map;

import cn.davidma.repairgem.Main;
import cn.davidma.repairgem.reference.GemConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TickHandler {
	
	Item gem;
	Map<String, Integer> globalCooldown;
	
	public TickHandler() {
		gem = Main.gem;
		globalCooldown = new HashMap<String, Integer>();
		
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event){
		EntityPlayer player = event.player; 
		
		// A super important line which I forgot to add previously :(
		if (player.world.isRemote) return;
		
		IItemHandler inv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		// Scan the player's inventory.
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getItem().equals(gem)) {
				String name = player.getName();
				if (!globalCooldown.containsKey(name)) globalCooldown.put(name, GemConfig.cooldown);
				int currDelay = globalCooldown.get(name);
				globalCooldown.put(name, globalCooldown.get(name) - 1);
				if (currDelay <= 0) {
					globalCooldown.put(name, GemConfig.cooldown);
					if (repair(player)) {
						stack.damageItem(1, player);
					}
				}
				return;
			}
		}	
	}
	
	private boolean repair(EntityPlayer player) {
		IItemHandler inv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		boolean flag = false;
		
		// Scan the player's inventory.
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack target = inv.getStackInSlot(i);
			if (target.getItem().equals(gem)) continue;
			if (!target.isEmpty() && target.getItem().isRepairable()) {
				
				// Do not repair if holding & using.
				if (!(player.isSwingInProgress && target == player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND))) {
					
					// Repair.
					if (target.isItemDamaged()) {
						target.setItemDamage(target.getItemDamage() - 1);
						flag = true;
						if (GemConfig.singleItem) return flag; // Only repair one item at a time.
					}
				}
			}
		}
		return flag;
	}
}
