package cn.davidma.repairgem.handler;

import cn.davidma.repairgem.Main;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(Main.gem);
	}
	
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		Main.gem.registerModels();
	}
}
