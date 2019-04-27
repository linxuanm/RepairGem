package cn.davidma.repairgem.handler;

import cn.davidma.repairgem.Main;
import cn.davidma.repairgem.item.Gem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(Main.gem = new Gem());
	}
	
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Main.gem, 0, new ModelResourceLocation(Main.gem.getRegistryName(), "inventory"));
	}
}
