package cn.davidma.repairgem;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TickHandler {
	
	int DELAY = 40;
	
	Item gem;
	int time;
	
	public TickHandler(Item inGem, int inDelay) {
		gem = inGem;
		DELAY = inDelay;
		time = DELAY;
		
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event){
		EntityPlayer player=event.player;
		IItemHandler inv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		// Scan the player's inventory.
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack target = inv.getStackInSlot(i);
			
			// Gem in inventory.
			if (target.getItem() == gem) {
				time--;
				if (time <= 0) {
					time = DELAY;
					repair(player);
				}
			}
		}			
	}
	
	private void repair(EntityPlayer player) {
		IItemHandler inv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		// Scan the player's inventory.
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack target = inv.getStackInSlot(i);
			if (!target.isEmpty() && target.getItem().isRepairable()) {
				
				// Do not repair if holding & using.
				if (!(player.isSwingInProgress && target == player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND))) {
					
					// Repair.
					if (target.isItemDamaged()) {
						target.setItemDamage(target.getItemDamage() - 1);
						return; // Only repair one item at a time.
					}
				}
			}
		}
	}
}
