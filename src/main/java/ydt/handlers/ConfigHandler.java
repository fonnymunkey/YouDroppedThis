package ydt.handlers;

import java.io.File;
import java.util.HashMap;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static Configuration config;
	
	public static boolean useImpactSounds = false;
	public static boolean useDropSounds = true;
	
	public static boolean useDefaultImpactSounds = false;
	public static boolean useItemSpecificImpactSounds = false;
	public static String defaultImpactSound = "block.cloth.fall";
	
	public static boolean useDefaultDropSounds = true;
	public static boolean useItemSpecificDropSounds = false;
	public static String defaultDropSound = "block.dispenser.launch";
	
	public static String[] specificItemDropSounds = {"minecraft:stone;block.dispenser.launch"};
	public static String[] specificItemImpactSounds = {""};
	
	public static HashMap<String, String> itemDropList = new HashMap<>();
	public static HashMap<String, String> itemImpactList = new HashMap<>();
	
	public static void initConfig(File configFile)
	{
		config = new Configuration(configFile);
		
		useImpactSounds = config.getBoolean("useImpactSounds", CATEGORY_GENERAL, useImpactSounds, "Whether you want items impacting the ground to make a noise.");
		useDropSounds = config.getBoolean("useDropSounds", CATEGORY_GENERAL, useDropSounds, "Whether you want items being dropped by players to make a noise.");
		
		useDefaultImpactSounds = config.getBoolean("useDefaultImpactSounds", "Ground Impact", useDefaultImpactSounds, "Whether you want a sound to play by default for when unspecified items hit the ground.");
		useItemSpecificImpactSounds = config.getBoolean("useItemSpecificImpactSounds", "Ground Impact", useItemSpecificImpactSounds, "Whether you want to have sounds play for specified items when they hit the ground.");
		defaultImpactSound = config.getString("defaultImpactSound", "Ground Impact", defaultImpactSound, "The default noise you want to use for when an unspecified item hits the ground");
		
		useDefaultDropSounds = config.getBoolean("useDefaultDropSounds", "Item Dropped", useDefaultDropSounds, "Whether you want a sound to play by default for when unspecified items hit the ground.");
		useItemSpecificDropSounds = config.getBoolean("useItemSpecificDropSounds", "Item Dropped", useItemSpecificDropSounds, "Whether you want to have sounds play for specified items when they are dropped.");
		defaultDropSound = config.getString("defaultDropSound", "Item Dropped", defaultDropSound, "The default noise you want to use for when an unspecified item is dropped.");

		specificItemDropSounds = config.getStringList("specificItemDropSounds", "Item Dropped", specificItemDropSounds, "List of items and the corresponding sounds you want them to make when they are dropped");
		specificItemImpactSounds = config.getStringList("specificItemImpactSounds", "Ground Impact", specificItemImpactSounds, "List of items and the corresponding sounds you want them to make when they hit the ground");
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
	
	public static void buildItemDropList()
	{
		if(specificItemDropSounds.length>0)
		{
			for(String input : specificItemDropSounds)
			{
				String[] list = input.split(";");
				itemDropList.put(list[0].trim(), list[1].trim());
			}
		}
	}
	
	public static void buildItemImpactList()
	{
		if(specificItemImpactSounds.length>0);
		{
			for(String input : specificItemImpactSounds)
			{
				String[] list = input.split(";");
				itemImpactList.put(list[0].trim(), list[1].trim());
			}
		}
	}
}
