package ydt.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ydt.handlers.ConfigHandler;
import ydt.handlers.EventHandler;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	public void registerConfig(FMLPreInitializationEvent event)
	{
		ConfigHandler.initConfig(event.getSuggestedConfigurationFile());
	}
	
	public void initLists()
	{
		if(ConfigHandler.useItemSpecificDropSounds) ConfigHandler.buildItemDropList();
		if(ConfigHandler.useItemSpecificImpactSounds) ConfigHandler.buildItemImpactList();
	}
}
