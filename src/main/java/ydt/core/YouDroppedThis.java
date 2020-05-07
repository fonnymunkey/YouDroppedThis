package ydt.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ydt.core.proxies.*;

import org.apache.logging.log4j.Logger;

@Mod(modid = YouDroppedThis.MODID, name = YouDroppedThis.NAME, version = YouDroppedThis.VERSION)
public class YouDroppedThis
{
    public static final String MODID = "ydt";
    public static final String NAME = "You Dropped This";
    public static final String VERSION = "1.1.0";

    public static Logger logger;
    
    @SidedProxy(clientSide = "ydt.core.proxies.ClientProxy", serverSide = "ydt.core.proxies.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();

    	proxy.registerConfig(event);
    	proxy.initLists();
    	proxy.registerHandlers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
