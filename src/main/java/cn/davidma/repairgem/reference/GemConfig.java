package cn.davidma.repairgem.reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid=Reference.MOD_ID, name="repairgem")
public class GemConfig {
	
	@Comment({
		"The cooldown (in ticks) of each repair; smaller value means faster repair."
	})
	@Name("Cooldown ticks")
	@RangeInt(min=1)
	public static int cooldown = 40;
	
	@Comment({
		"Whether to enable a harder recipe for the gem (for balancing the mod)."
	})
	@Name("Harder Recipe")
	public static boolean hardRecipe = false;
	
	@Comment({
		"Whether to repair a single item at a time or everything at once."
	})
	@Name("Single item mode")
	public static boolean singleItem = true;
}
