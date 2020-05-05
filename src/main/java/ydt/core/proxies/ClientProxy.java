package ydt.core.proxies;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
	}
	
	@Override
	public void registerConfig(FMLPreInitializationEvent event)
	{
		super.registerConfig(event);
	}
	
	@Override
	public void initLists()
	{
		super.initLists();
	}
}
