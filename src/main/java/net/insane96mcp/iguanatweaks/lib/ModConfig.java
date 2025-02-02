package net.insane96mcp.iguanatweaks.lib;

import net.insane96mcp.iguanatweaks.IguanaTweaks;
import net.insane96mcp.iguanatweaks.modules.ModuleHardness;
import net.insane96mcp.iguanatweaks.modules.ModuleMovementRestriction;
import net.insane96mcp.iguanatweaks.modules.ModuleStackSizes;
import net.insane96mcp.iguanatweaks.network.ConfigSync;
import net.insane96mcp.iguanatweaks.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Config(modid = IguanaTweaks.MOD_ID, category = "", name = "IguanaTweaksReborn")
public class ModConfig {
	
	@Name("config")
	public static ConfigOptions config = new ConfigOptions();
	private final static ConfigOptions localConfig = new ConfigOptions();
	
	public static class ConfigOptions {
		@RequiresMcRestart
		public Global global = new Global();
		
		public static class Global {
			@Name("Drops")
			@Comment("Set to false to disable everything the Drops module does")
			public boolean drops = true;
			@Name("Experience")
			@Comment("Set to false to disable everything the Experience module does")
			public boolean experience = true;
			@Name("Hardness")
			@Comment("Set to false to disable everything the Hardness module does")
			public boolean hardness = true;
			@Name("Hud")
			@Comment("Set to false to disable everything the Hud module does")
			public boolean hud = true;
			@Name("Movement Restriction")
			@Comment("Set to false to disable everything the Movement Restriction module does")
			public boolean movementRestriction = true;
			@Name("Sleep & Respawn")
			@Comment("Set to false to disable everything the Sleep & Respawn module does")
			public boolean sleepRespawn = true;
			@Name("Stack Size")
			@Comment("Set to false to disable everything the Stack Size module does")
			public boolean stackSize = true;
			@Name("Farming")
			@Comment("Set to false to disable everything the Farming module does")
			public boolean farming = true;
		}
	
		
		public Farming farming = new Farming();
		
		public static class Farming {			
			public enum NerfedBonemeal {
				DISABLED,
				SLIGHT,
				NERFED
			}
			
			@Name("Nerfed Bone Meal")
			@Comment("Makes more Bone Meal required for Crops. Valid Values are\nDISABLED: No Bone Meal changes\nSLIGHT: Makes Bone Meal grow 1-2 crop stages\nNERFED: Makes Bone Meal grow only 1 Stage")
			public NerfedBonemeal nerfedBonemeal = NerfedBonemeal.NERFED;

			@Name("Crop Require Water")
			@Comment("Crops will no longer grow if Farmland is not Wet.")
			public boolean cropRequireWater = true;

			@Name("Crop Growth Speed Multiplier")
			@Comment("Increases the time required for a crop to grow (e.g. at 2.0 the plant will take twice to grow).\nSetting this to 0 will prevent crops from growing naturally.\n1.0 will make crops grow like normal.")
			public float cropGrowthMultiplier = 2.5f;

			@Name("No Sunlight Growth Multiplier")
			@Comment("Increases the time required for a crop to grow when it's sky light level is below \"Min Sunlight\", (e.g. at 2.0 when the crop has a skylight below \"Min Sunlight\" will take twice to grow).\nSetting this to 0 will prevent crops from growing when sky light level is below \"Min Sunlight\".\n1.0 will make crops growth not affected by skylight.")
			public float noSunlightGrowthMultiplier = 2.0f;

			@Name("Min Sunlight")
			@Comment("Minimum Sky Light level required for crops to not be affected by \"No Sunlight Growth Multiplier\".")
			public int minSunlight = 10;

			@Name("Sugar Canes Growth Speed Multiplier")
			@Comment("Increases the time required for Sugar Canes to grow (e.g. at 2.0 Sugar Canes will take twice to grow).\nSetting this to 0 will prevent Sugar Canes from growing naturally.\n1.0 will make Sugar Canes grow like normal.")
			public float reedsGrowthMultiplier = 2.0f;

			@Name("Cactus Growth Speed Multiplier")
			@Comment("Increases the time required for Cactuses to grow (e.g. at 2.0 Cactuses will take twice to grow).\nSetting this to 0 will prevent Cactuses from growing naturally.\n1.0 will make Cactuses grow like normal.")
			public float cactusGrowthMultiplier = 2.0f;
		}
		
		
		public Misc misc = new Misc();
		
		public static class Misc {
			@Name("Less Obivious Silverfish")
			@Comment("If true, silverfish blocks will be almost like stone")
			@RequiresMcRestart
			public boolean lessObviousSilverfish = true;
			@Name("Alter Poison")
			@Comment("The poison effect will be changed to be deadly and drain hunger, but will damage the player 3 times slower")
			@RequiresMcRestart
			public boolean alterPoison = true;
			@Name("Tick Rate Player Update")
			@Comment("How often the speed of players are calculated (in ticks). Higher values might increase performance but may increase the chance of odd behavior")
			@RangeInt(min = 1, max = Integer.MAX_VALUE)
			public int tickRatePlayerUpdate = 2;
			@Name("Tick Rate Entity Update")
			@Comment("How often the speed of entities (not player) are calculated (in ticks). Higher values might increase performance but may increase the chance of odd behavior. Set to 0 to disable Movement Restriction for mobs.")
			@RangeInt(min = 0, max = Integer.MAX_VALUE)
			public int tickRateEntityUpdate = 7;
			@Name("Disable FoV on Speed Modified")
			@Comment("Disables FoV changes when you get slowed down or sped up. Highly recommended if you have 'movement_restrictions' active.")
			public boolean disableFovOnSpeedModified = true;
			@Name("Exaustion Block Break Hardness Based")
			@Comment("Minecraft normally adds 0.005 exaustion for block broken. With this at true, exhaustion will be added based on block hardness (hardness / 100). ELI5 when you break a block you lose more hunger the harder is a block to break.")
			public boolean exhaustionOnBlockBreak = true;
			@Name("Exaustion Multiplier on Block Break")
			@Comment("Multiply the exhaustion given to the player when breaking blocks by this value")
			@RangeDouble(min = 0, max = Float.MAX_VALUE)
			public float exhaustionMultiplier = 1.0f;
			@Name("No Item No Knockback")
			@Comment("If the player attacks an entity without a tool / weapon, the attacked mob will take almost no damage (1/20 of a heart) and no knockback. The no knockback applies even if the player attack speed cooldown is below 75% with a tool / weapon, but in this case the damage is applied normally.\nThis feature is disabled by default since is a little bit buggy, like no experience is dropped from the mob if it gets attacked only by No Knockbacks attacks.")
			public boolean noItemNoKnockback = false;
		}

		
		public Hardness hardness = new Hardness();
		
		public static class Hardness {
			@Name("Punish Wrong Tool")
			@Comment("True if the tool should break when mining the wrong block (e.g. mining Wood with a Pickaxe or mining Obsidian with an Iron Pickaxe) or if no tool in hand damage the player based on the block hardness (multiplied by the hardness multiplier).")
			public boolean punishWrongTool = false;
			@Name("Dimension Multiplier")
			@Comment("List of dimensions to have a custom hardness multiplier. If a dimension is listed here that dimension will ignore the Global Multiplier.\nThe format is dimension_id,hardness_multiplier.\nE.g. -1,2.0 will make blocks in the Nether twice as hard to mine.")
			@RangeDouble(min = 0, max = Float.MAX_VALUE)
			public String[] dimensionMultiplier = new String[] {};
			@Name("Multiplier")
			@Comment("Multiplier applied to the hardness of blocks (set to 1 to disable)")
			@RangeDouble(min = 0, max = Float.MAX_VALUE)
			public float multiplier = 4.0f;
			@Name("Block List as Whitelist")
			@Comment("True if hardness multiplier should only affect blocks on the list, false if all blocks are affected except those on the list")
			public boolean blockListIsWhitelist = false;
			@Name("Block Black/Whitelist")
			@Comment("Block ids (one per line) for the hardness whitelist/blacklist.\nFormat is modid:blockid:meta\nE.g. 'minecraft:stone:1' will target granite")
			public String[] blockList = new String[] {};
			@Name("Custom Block Hardness")
			@Comment("Define for each line a custom block hardness for every block. Those blocks are not affected by the global block hardness multiplier\nThe format is modid:blockid:meta,hardness.\nE.g. 'minecraft:stone:1,5.0' will make granite have 5 hardness. If no meta is specified, this will affect every block meta.\nBy default this is set to make ores harder to mine the better they are (accounting 4x global hardness too)")
			public String[] blockHardness = new String[] {
				"minecraft:coal_ore,12.0",
		        "minecraft:iron_ore,15",
		        "minecraft:gold_ore,20",
		        "minecraft:diamond_ore,25",
		        "minecraft:redstone_ore,12.0",
		        "minecraft:lapis_ore,15",
		        "minecraft:emerald_ore,30",
		        "minecraft:nether_quartz_ore,12.0"
			};
		}
	
		
		@Name("Stack Sizes")
		@RequiresMcRestart
		public StackSizes stackSizes = new StackSizes();
		
		public static class StackSizes {
			@Name("Block Stack Size Divider Min")
			@Comment("Min stack size divider for blocks")
			@RangeInt(min = 1, max = 64)
			public int blockDividerMin = 2;
			@Name("Block Stack Size Divider Max")
			@Comment("Mia stack size divider for blocks")
			@RangeInt(min = 1, max = 64)
			public int blockDividerMax = 4;
			@Name("Item Stack Size Divider")
			@Comment("Stack size divider for items")
			@RangeInt(min = 1, max = 64)
			public int itemDivider = 2;
			@Name("Custom Stack List")
			@Comment("List of all the custom stacks for blocks and items. The format is 'modid:name,max_stack_size'.\nMeta items can't be changed (e.g. setting stone max stack to 16 will make even granite, diorite, etc. as 16).\nGoing over 64 doesn't work.\nBy default, some items are set so that villagers can trade them.")
			@RangeInt(min = 1, max = 64)
			public String[] customStackList = new String[] {
					"minecraft:emerald,64",
					"minecraft:paper,36",
					"minecraft:rotten_flesh,40"
			};
		}
	
		
		@Name("Sleep & Respawn")
		public SleepRespawn sleepRespawn = new SleepRespawn();
		
		public static class SleepRespawn {
			@Name("Disable Sleeping")
			@Comment("Prevents players from sleeping")
			public boolean disableSleeping = true;
			@Name("Destroy Bed on Respawn")
			@Comment("As the player respawns the bed will be destroyed. This makes bed one time respawn only")
			public boolean destroyBedOnRespawn = false;
			@Name("Disable Set Respawn Point")
			@Comment("If active using a bed will not set your spawn point (requires disable_sleeping to be true)")
			public boolean disableSetRespawnPoint = false;
			@Name("Random Spawn Location Radius Min")
			@Comment("Upon entering the world your spawn will be randomised around the spawn point, at least at this minimum distance (set to 0 to disable)")
			@RangeInt(min = 0)
			public int spawnLocationRandomMin = 0;
			@Name("Random Spawn Location Radius Max")
			@Comment("Upon entering the world your spawn will be randomised around the spawn point, at most at this maximum distance (set to 0 to disable)")
			@RangeInt(min = 0)
			public int spawnLocationRandomMax = 0;
			@Name("Random Respawn Location Radius Min")
			@Comment("Upon respawning your location will be randomised around your respawn point, at least at this minimum distance (set to 0 to disable)")
			@RangeInt(min = 0)
			public int respawnLocationRandomMin = 0;
			@Name("Random Respawn Location Radius Max")
			@Comment("Upon respawning your location will be randomised around your respawn point, at most at this maximum distance (set to 0 to disable)")
			@RangeInt(min = 0)
			public int respawnLocationRandomMax = 0;
			@Name("Respawn Health")
			@Comment("Amount of health you respawn with (with 'respawnHealthDifficultyScaling' this will be modified by difficulty)")
			@RangeInt(min = 1)
			public int respawnHealth = 10;
			@Name("Respawn Health Difficulty Based")
			@Comment("If true, the amount of health you respawn with is dependant on difficulty. (Easy x2, Normal x1, Hard x0.5)")
			public boolean respawnHealthDifficultyScaling = true;
		}

	
		public Hud hud = new Hud();
		
		public static class Hud {
			@Name("Hide Hotbar")
			@Comment("If true, the hotbar will be hidden until the mouse wheel is used or an item is selected with numbers")
			public boolean hideHotbar = false;
			@Name("Hide Hotbar Delay")
			@Comment("Delay (in seconds) before hiding the hotbar")
			@RangeInt(min = 0)
			public int hideHotbarDelay = 4;
			@Name("Hide Health bar")
			@Comment("If true, the health bar will be hidden when above a certain threshold (the bar will always be shown if absorpion hearts are present)")
			public boolean hideHealthBar = true;
			@Name("Hide Health Bar Threshold")
			@Comment("Health needs to be equal to or above this before the bar will hide")
			@RangeInt(min = 1)
			public int hideHealthBarThreshold = 20;
			@Name("Hide Health Bar Delay")
			@Comment("Delay (in seconds) before hiding the hunger bar")
			@RangeInt(min = 0)
			public int hideHealthBarDelay = 4;
			@Name("Hide Hunger Bar")
			@Comment("If true, the hunger bar will be hidden when above a certain threshold")
			public boolean hideHungerBar = true;
			@Name("Hide Hunger Bar Threshold")
			@Comment("Hunger needs to be equal to or above this before the bar will hide")
			@RangeInt(min = 1)
			public int hideHungerBarThreshold = 20;
			@Name("Hide Hunger Bar Delay")
			@Comment("Delay (in seconds) before hiding the hunger bar")
			@RangeInt(min = 0)
			public int hideHungerBarDelay = 4;
			@Name("Hide Experience Bar")
			@Comment("If true, the experience bar will be hidden unless there are xp orbs in a small radius around the player or a gui is open")
			public boolean hideExperienceBar = true;
			@Name("Hide Experience Bar Delay")
			@Comment("Delay (in seconds) before hiding the experience bar")
			@RangeInt(min = 0)
			public int hideExperienceDelay = 4;
			@Name("Hide Armor Bar")
			@Comment("If true, the armor bar will be hidden unless the player takes damage")
			public boolean hideArmorBar = true;
			@Name("Hide Armor Bar Delay")
			@Comment("Delay (in seconds) before hiding the armor bar")
			@RangeInt(min = 0)
			public int hideArmorDelay = 4;
			@Name("Show Creative Text")
			@Comment("If true, a 'Creative mode' text will show up when in creative mode")
			public boolean showCreativeText = true;
		}
		
		
		public Drops drops = new Drops();
		
		public static class Drops {
			@Name("Restricted Drops")
			@Comment("List of items/blocks to restrict from mob drops (separated by new line, format modid:itemid:meta)")
			public String[] restrictedDrops = new String[] {};
			@Name("Item Lifespan")
			@Comment("Lifespan (in ticks) of items on the ground")
			public int itemLifespan = 6000;
			@Name("Item Lifespan Mob Death")
			@Comment("Lifespan (in ticks) of items dropped when a mob dies")
			public int itemLifespanMobDeath = 6000;
			@Name("Item Lifespan Player Death")
			@Comment("Lifespan (in ticks) of items dropped on player death")
			public int itemLifespanPlayerDeath = Integer.MAX_VALUE;
			@Name("Item Lifespan Tossed")
			@Comment("Lifespan (in ticks) of items tossed on the ground")
			public int itemLifespanTossed = 6000;
		}
	
	
		@Name("Movement Restriction")
		public MovementRestriction movementRestriction = new MovementRestriction();
		
		public static class MovementRestriction {
			@Name("Encumbrance Debug")
			@Comment("Shows weight text in the debug (F3) details")
		    public boolean addEncumbranceDebugText = true;
			@Name("Encumbrance Hud")
			@Comment("Shows weight text on the HUD when carrying too much")
		    public boolean addEncumbranceHudText = true;
			@Name("Encumbrance Top-Left")
			@Comment("Show Encumbrance Hud on Top-Left instead of Top-Right")
			public boolean encumbranceTopLeft = false;
			@Name("Detailed Encumbrance Hud")
			@Comment("Weight text on the HUD will be more detailed, showing numbers")
		    public boolean detailedEncumbranceHudText = false;
			@Name("Max Carry Weight")
			@Comment("Maximum carry weight (set to 0 to disable)")
			@RangeInt(min = 0)
		    public int maxCarryWeight = 640;
			@Name("Rock Weight")
			@Comment("Weight of one rock block, used as a base to calculate weight of other blocks")
			@RangeDouble(min = 0f, max = Float.MAX_VALUE)
			public float rockWeight = 1;
			@Name("Custom Weights")
			@Comment("Set here (one per line) block weight for each block or item. Format is 'modid:blockid:meta,weight', meta is not needed, setting no meta, means all the blocks sub-types of that block.")
			public String[] customWeight = new String[] {};
			@Name("Armor Weight")
			@Comment("Weight added by each point of armor (set to 0 to disable)")
			@RangeDouble(min = 0f, max = Float.MAX_VALUE)
			public float armorWeight = 8f;
			@Name("Armor Weight Mobs")
			@Comment("Percentage Slowdown for each armor point for Mobs (set to zero to prevent mobs from slowing down when wearing armor)")
			@RangeDouble(min = 0f, max = 5f)
			public float armorWeightMobs = 0.5f;
			@Name("Damage Slowdown Duration")
			@Comment("Number of ticks each heart of damage slows you down for (set to 0 to disable)")
			@RangeInt(min = 0)
			public int damageSlowdownDuration = 5;
			@Name("Damage Slowdown Effectiviness")
			@Comment("When player's damaged, how much is slowed down?")
			@RangeDouble(min = 0f, max = 100f)
			public float damageSlowdownEffectiveness = 20f;
			@Name("Damage Slowdown Difficulty Scaling")
			@Comment("Is the duration of the slowdown dependant on difficulty?")
			public boolean damageSlowdownDifficultyScaling = true;
			@Name("Terrain Slowdown")
			@Comment("Set this to false to disable terrain slowdown feature")
			public boolean terrainSlowdown = true;
			@Name("Terrain Slowdown on Dirt")
			@Comment("Percentage of slowdown when walking on dirt or grass")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownOnDirt = 5f;
			@Name("Terrain Slowdown on Ice")
			@Comment("Percentage of slowdown when walking on ice")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownOnIce = 50f;
			@Name("Terrain Slowdown on Plants")
			@Comment("Percentage of slowdown when walking on plants")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownOnPlant = 20f;
			@Name("Terrain Slowdown on sand")
			@Comment("Percentage of slowdown when walking on sand")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownOnSand = 20f;
			@Name("Terrain Slowdown on snow")
			@Comment("Percentage of slowdown when walking on snow")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownOnSnow = 20f;
			@Name("Terrain Slowdown in Snow")
			@Comment("Percentage of slowdown when walking in snow, this is added to the ground slowdown")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownInSnow = 15f;
			@Name("Terrain Slowdown in Plants")
			@Comment("Percentage of slowdown when walking in plants, this is added to the ground slowdown")
			@RangeDouble(min = 0f, max = 100f)
			public float terrainSlowdownInPlant = 5f;
			@Name("Custom Terrain Slowdown")
			@Comment("Custom list for each block that slows you down when you walk on it. Format is 'modid:blockid:meta,slowness', meta is not needed, setting no meta, means all the blocks. E.g. 'minecraft:diamond_block,75' will slowdown the player by 75% when walks on diamond block.")
			public String[] terrainSlowdownCustom = new String[] {};
			@Name("Slowdown Walking Backwards")
			@Comment("Set to false to disable the slowdown when walking backwards")
			public boolean slowdownWhenWalkingBackwards = true;
			@Name("Shulker Weight Reduction")
			@Comment("Multiplier for items weight in shulker boxes. Set this to 0 to make items in shulker boxes not count towards weight. Set this to 1 to make items in shulker boxes weight the same as they were out of the box.")
			@RangeDouble(min = 0f, max = 1f)
			public float shulkerWeightReduction = 0.75f;
			@Name("Encubrance Exhaustion per Second")
			@Comment("How much exhaustion is given to the player each second while exaustion is 100% (e.g. at 5% encumbrance the exhaustion applied to the player will be 5% of this value)")
			@RangeDouble(min = 0f, max = 1f)
			public float encumbranceExhaustionPerSecond = 0.05f;
		}
	
	
		public Experience experience = new Experience();
		
		public static class Experience {
			@Name("Percentage Ore")
			@Comment("Percentage of experience dropped by blocks. Experience dropped by blocks are still affected by 'Percentage All'; so if you have e.g. 'Percentage All' at 50, this needs to be set to 200 to make blocks drop normal experience")
			@RangeDouble(min = 0f, max = Float.MAX_VALUE)
			public float percentageOre = 100f;
			@Name("Percentage All")
			@Comment("Percentage of experience given by everything (0 to disable all xp orbs from being created) (100 to disable)")
			@RangeDouble(min = 0f, max = Float.MAX_VALUE)
			public float percentageAll = 100f;
			@Name("Percentage Mobs From Spawner")
			@Comment("Percentage of experience dropped from mobs spawned from Spawners.")
			@RangeDouble(min = 0f, max = Float.MAX_VALUE)
			public float percentageFromSpawner = 75f;
			@Name("Lifespan")
			@Comment("Lifespan (in ticks) of xp orbs (If set to -1 the orbs will never despawn. If set to 6000 orbs will not be modified)")
			@RangeInt(min = -1, max = 38767)
			public int lifespan = 6000;
		}
	}
	
	public static void parseConfig() {

        IguanaTweaks.logger.info("Parsing Config");
        
        ModuleHardness.loadBlockHardnesses();
        ModuleHardness.loadDimensionMultipliers();
        ModuleHardness.loadBlockList();
        ModuleStackSizes.loadCustomStackSizes();
        ModuleMovementRestriction.loadCustomWeights();
        ModuleMovementRestriction.loadCustomTerrainSlowdown();
        
        IguanaTweaks.logger.info("Config Parsed");
	}

	@Mod.EventBusSubscriber(modid = IguanaTweaks.MOD_ID)
	private static class EventHandler{
		@SubscribeEvent
	    public static void EventOnConfigChanged(OnConfigChangedEvent event)
	    {
	        if (event.getModID().equals(IguanaTweaks.MOD_ID))
	        {
	            ConfigManager.sync(IguanaTweaks.MOD_ID, Type.INSTANCE);
	            
	            parseConfig();
	        }
	    }
	    
		@SubscribeEvent
	    public static void EventPlayerLoggedIn(PlayerLoggedInEvent event) {
	    	if (event.player.world.isRemote)
	    		return;
	    	
	    	ConfigSync message = new ConfigSync();
	    	message.lessObiviousSilverfish = ModConfig.config.misc.lessObviousSilverfish;
	    	message.multiplier = ModConfig.config.hardness.multiplier;
	    	message.blockListIsWhitelist = ModConfig.config.hardness.blockListIsWhitelist;
	    	message.blockList = String.join("\r\n", ModConfig.config.hardness.blockList);
	    	message.blockHardness = String.join("\r\n", ModConfig.config.hardness.blockHardness);
	    	
	    	parseConfig();
	    	
	    	PacketHandler.SendToClient(message, (EntityPlayerMP) event.player);
	    }

	}
}