package cn.davidma.repairgem;

import cn.davidma.repairgem.config.GemConfig;
import cn.davidma.repairgem.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION)
public class Main {
	
	public static Item gem;
	
	// Custom crafting recipe.
	public static Object corner;
	public static Object middle;
	public static Object side;
	public static Object edge;
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide=Reference.CLIENT_PROXY, serverSide=Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		gem = new Gem("gem");
		if (GemConfig.hardRecipe) {
			corner = Items.END_CRYSTAL;
			middle = Items.NETHER_STAR;
			side = Blocks.DIAMOND_BLOCK;
			edge = Blocks.EMERALD_BLOCK;
		} else {
			corner = Blocks.REDSTONE_BLOCK;
			middle = Items.EMERALD;
			side = Items.DIAMOND;
			edge = Items.ENDER_PEARL;
		}
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new TickHandler(gem, GemConfig.cooldown));
		addRecipe("gem", new ItemStack(gem), new Object[] {"CSC", "EME", "CSC", 'C', corner, 'S', side, 'E', edge, 'M', middle});
		addRecipe("rotated_gem", new ItemStack(gem), new Object[] {"CEC", "SMS", "CEC", 'C', corner, 'S', side, 'E', edge, 'M', middle});
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		
	}
	
	private static void addRecipe (String inName, ItemStack output, Object... input) {
		// Right the new recipe loader is ridiculous.
		ResourceLocation name = new ResourceLocation(Reference.MOD_ID, inName);
		GameRegistry.addShapedRecipe(name, null, output, input);
	}
}
