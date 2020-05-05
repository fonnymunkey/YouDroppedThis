package ydt.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ydt.core.YouDroppedThis;
import ydt.handlers.ConfigHandler;

public class EventHandler
{
	boolean useDropSounds = ConfigHandler.useDropSounds;
	boolean useImpactSounds = ConfigHandler.useImpactSounds;
	
	boolean useDefaultImpactSounds = ConfigHandler.useDefaultImpactSounds;
	boolean useItemSpecificImpactSounds = ConfigHandler.useItemSpecificImpactSounds;
	String defaultImpactSound = ConfigHandler.defaultImpactSound;
	
	boolean useDefaultDropSounds = ConfigHandler.useDefaultDropSounds;
	boolean useItemSpecificDropSounds = ConfigHandler.useItemSpecificDropSounds;
	String defaultDropSound = ConfigHandler.defaultDropSound;
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemDropped(ItemTossEvent event)
	{
		if(event.getPlayer() == null || event.getEntityItem() == null || event.getPlayer().world.isRemote || event.isCanceled()) return;
		
		World world = event.getPlayer().world;
		EntityPlayer player = event.getPlayer();
		
		if(useDropSounds)
		{
			if(!useItemSpecificDropSounds)
					world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(defaultDropSound), SoundCategory.PLAYERS, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);		
			else if(useItemSpecificDropSounds)
			{
				for(String item : ConfigHandler.itemDropList.keySet()) {
					if(item.contentEquals(event.getEntityItem().getItem().getItem().getRegistryName().toString()))
						world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(ConfigHandler.itemDropList.get(item)), SoundCategory.PLAYERS, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
				}
				world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(defaultDropSound), SoundCategory.PLAYERS, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
			else YouDroppedThis.logger.warn("Malformed config at useItemSpecificDropSounds");
		}
		
		if(useImpactSounds)
		{
			//do impact black magic
		}
	}
	
    private static SoundEvent getRegisteredSoundEvent(String id)
    {
        SoundEvent soundevent = SoundEvent.REGISTRY.getObject(new ResourceLocation(id));

        if (soundevent == null)
        {
            YouDroppedThis.logger.warn("Invalid sound requested: " + id + ", returning dispenser.");
            return SoundEvents.BLOCK_DISPENSER_LAUNCH;
        }
        else
        {
            return soundevent;
        }
    }
}
