package cn.davidma.repairgem.handler;

import java.util.ArrayList;

import cn.davidma.repairgem.reference.GemConfig;
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
	
	int DELAY;
	
	Item gem;
	int time;
	
	public TickHandler(Item inGem, int inDelay) {
		gem = inGem;
		DELAY = inDelay;
		time = DELAY;
		
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event){
		EntityPlayer player = event.player;
		IItemHandler inv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		// Gem in inventory.
		if (player.inventory.hasItemStack(new ItemStack(gem))) {
			System.out.println("hello");
			time--;
			if (time <= 0) {
				time = DELAY;
				repair(player);
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
						if (GemConfig.singleItem) return; // Only repair one item at a time.
					}
				}
			}
		}
	}
}
