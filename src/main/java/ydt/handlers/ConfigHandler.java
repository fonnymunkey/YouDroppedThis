package ydt.handlers;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static Configuration config;
	
	public static boolean dropsFromInventoryMakeNoise = true;
	public static boolean dropsFromInventoryMakeNoiseByDefault = true;
	public static String dropsFromInventoryDefaultSound = "block.dispenser.launch";
	
	public static boolean dropsFromMobsMakeNoise = false;
	public static boolean dropsFromMobsMakeNoiseByDefault = false;
	public static String dropsFromMobsDefaultSound = "block.dispenser.launch";
	
	public static boolean dropsImpactingGroundMakeNoise = true;
	public static boolean dropsImpactingGroundMakeNoiseByDefault = true;
	public static String dropsImpactingGroundDefaultSound = "block.cloth.place";
	
	public static boolean specificDroppedItemsMakeNoise = false;
	public static String[] specificDroppedItemsAndSounds = {"minecraft:stone;block.dispenser.launch"};
	
	public static boolean specificDropsImpactingGroundMakeNoise = false;
	public static String[] specificImpactingGroundItemsAndSounds = {""};
	
	public static HashMap<String, String> specificDroppedItemsAndSoundsList = new HashMap<>();
	public static HashMap<String, String> specificImpactingGroundItemsAndSoundsList = new HashMap<>();
	
	public static void initConfig(File configFile)
	{
		config = new Configuration(configFile);
		
		dropsFromInventoryMakeNoise = config.getBoolean("dropsFromInventoryMakeNoise", "Drops From Inventory", dropsFromInventoryMakeNoise, "Any items dropped from a player can make noise.");
		dropsFromInventoryMakeNoiseByDefault = config.getBoolean("dropsFromInventoryMakeNoiseByDefault", "Drops From Inventory", dropsFromInventoryMakeNoiseByDefault, "Do all unspecified items make a noise when dropped from inventory?");
		dropsFromInventoryDefaultSound = config.getString("dropsFromInventoryDefaultSound", "Drops From Inventory", dropsFromInventoryDefaultSound, "The default noise you want to use for when an unspecified item is dropped from inventory.");
		
		dropsFromMobsMakeNoise = config.getBoolean("dropsFromMobsMakeNoise", "Drops From Mobs", dropsFromMobsMakeNoise, "Any items dropped from mobs can make noise.");
		dropsFromMobsMakeNoiseByDefault = config.getBoolean("dropsFromMobsMakeNoiseByDefault", "Drops From Mobs", dropsFromMobsMakeNoiseByDefault, "Do all unspecified items make a noise when dropped from mobs?");
		dropsFromMobsDefaultSound = config.getString("dropsFromMobsDefaultSound", "Drops From Mobs", dropsFromMobsDefaultSound, "The default noise you want to use for when an unspecified item is dropped from a mob.");
		
		dropsImpactingGroundMakeNoise = config.getBoolean("dropsImpactingGroundMakeNoise", "Drops Impacting Ground", dropsImpactingGroundMakeNoise, "Any items impacting the ground can make a noise.");		
		dropsImpactingGroundMakeNoiseByDefault = config.getBoolean("dropsImpactingGroundMakeNoiseByDefault", "Drops Impacting Ground", dropsImpactingGroundMakeNoiseByDefault, "Do all unspecified items make a noise when dropped from inventory?");
		dropsImpactingGroundDefaultSound = config.getString("dropsImpactingGroundDefaultSound", "Drops Impacting Ground", dropsImpactingGroundDefaultSound, "The default noise you want to use for when an unspecified item hits the ground");

		specificDroppedItemsMakeNoise = config.getBoolean("specificDroppedItemsMakeNoise", "Specific Items", specificDroppedItemsMakeNoise, "Do specific items make specific noises when dropped? (Applies to both mobs and players)");
		specificDroppedItemsAndSounds = config.getStringList("specificDroppedItemsAndSounds", "Specific Items", specificDroppedItemsAndSounds, "List of items and the corresponding sounds you want them to make when they are dropped.");
		
		specificDropsImpactingGroundMakeNoise = config.getBoolean("specificDropsImpactingGroundMakeNoise", "Specific Impacts", specificDropsImpactingGroundMakeNoise, "Do specific items make specific noises when they impact the ground? (Applies to both mobs and players)");
		specificImpactingGroundItemsAndSounds = config.getStringList("specificImpactingGroundItemsAndSounds", "Specific Impacts", specificImpactingGroundItemsAndSounds, "List of items and the corresponding sounds you want them to make when they hit the ground.");
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
	
	public static void buildItemDropList()
	{
		if(specificDroppedItemsAndSounds.length>0)
		{
			for(String input : specificDroppedItemsAndSounds)
			{
				String[] list = input.split(";");
				specificDroppedItemsAndSoundsList.put(list[0].trim(), list[1].trim());
			}
		}
	}
	
	public static void buildItemImpactList()
	{
		if(specificImpactingGroundItemsAndSounds.length>0);
		{
			for(String input : specificImpactingGroundItemsAndSounds)
			{
				String[] list = input.split(";");
				specificImpactingGroundItemsAndSoundsList.put(list[0].trim(), list[1].trim());
			}
		}
	}
}
