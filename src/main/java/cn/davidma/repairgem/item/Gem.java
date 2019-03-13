package cn.davidma.repairgem.item;

import java.util.List;

import cn.davidma.repairgem.reference.GemConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Gem extends Item {
	
	private static String COOLDOWN_TAG = "repairCooldown";
	
	public Gem() {
		this.setRegistryName("gem");
		this.setUnlocalizedName(this.getRegistryName().toString());
		this.setCreativeTab(CreativeTabs.MISC);
		this.setMaxStackSize(1);
		this.setMaxDamage(GemConfig.durability);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		// Dynamic tooltip.
		if (GuiScreen.isShiftKeyDown()) {
			tooltip.add(I18n.format("tooltip.repairgem.expanded_tooltip"));
		} else {
			tooltip.add(TextFormatting.ITALIC + I18n.format("tooltip.repairgem.hold_shift"));
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (world.isRemote) return;
		if (!(entity instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) entity;
		NBTTagCompound nbt = stack.getTagCompound();
		
		int cooldown = nbt.getInteger(COOLDOWN_TAG);
		if (cooldown <= 0) {
			if (repair(player)) {
				stack.damageItem(1, player); 
			}
			nbt.setInteger(COOLDOWN_TAG, GemConfig.cooldown);
		} else {
			nbt.setInteger(COOLDOWN_TAG, cooldown - 1);
		}
		
	}
	
	private static boolean repair(EntityPlayer player) {
		IItemHandler inv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		boolean flag = false;
		
		// Scan the player's inventory.
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getItem() instanceof Gem) continue;
			if (!stack.isEmpty() && stack.getItem().isRepairable()) {
				
				// Do not repair if holding & using.
				if (!(player.isSwingInProgress && stack == player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND))) {
					
					// Repair.
					if (stack.isItemDamaged()) {
						stack.setItemDamage(stack.getItemDamage() - 1);
						flag = true;
						if (GemConfig.singleItem) return flag; // Only repair one item at a time.
					}
				}
			}
		}
		return flag;
	}
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
