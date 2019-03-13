package cn.davidma.repairgem.handler;

import cn.davidma.repairgem.Main;
import cn.davidma.repairgem.item.Gem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
		Main.proxy.registerItemRenderer(Main.gem, 0, "inventory");
	}
}
