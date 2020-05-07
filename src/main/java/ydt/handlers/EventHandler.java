package ydt.handlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ydt.core.YouDroppedThis;
import ydt.handlers.ConfigHandler;

public class EventHandler
{
	boolean dropsFromInventoryMakeNoise = ConfigHandler.dropsFromInventoryMakeNoise;
	boolean dropsFromMobsMakeNoise = ConfigHandler.dropsFromMobsMakeNoise;
	boolean dropsImpactingGroundMakeNoise = ConfigHandler.dropsImpactingGroundMakeNoise;
	
	boolean dropsFromInventoryMakeNoiseByDefault = ConfigHandler.dropsFromInventoryMakeNoiseByDefault;
	boolean dropsFromMobsMakeNoiseByDefault = ConfigHandler.dropsFromMobsMakeNoiseByDefault;
	boolean dropsImpactingGroundMakeNoiseByDefault = ConfigHandler.dropsImpactingGroundMakeNoiseByDefault;
	
	boolean specificDroppedItemsMakeNoise = ConfigHandler.specificDroppedItemsMakeNoise;
	boolean specificDropsImpactingGroundMakeNoise = ConfigHandler.specificDropsImpactingGroundMakeNoise;
	
	String dropsFromInventoryDefaultSound = ConfigHandler.dropsFromInventoryDefaultSound;
	String dropsFromMobsDefaultSound = ConfigHandler.dropsFromMobsDefaultSound;
	String dropsImpactingGroundDefaultSound = ConfigHandler.dropsImpactingGroundDefaultSound;
	
	//Items being dropped by players
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onInventoryItemDrop(ItemTossEvent event)
	{
		if(event.getPlayer() == null || event.getEntityItem() == null || event.getPlayer().world.isRemote || event.isCanceled()) return;
		
		World world = event.getPlayer().world;
		EntityPlayer player = event.getPlayer();

		if(dropsFromInventoryMakeNoise)
		{
			if(!specificDroppedItemsMakeNoise && dropsFromInventoryMakeNoiseByDefault)
					world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(dropsFromInventoryDefaultSound), SoundCategory.PLAYERS, 0.4F, world.rand.nextFloat() * 0.1F + 0.9F);		
			else if(specificDroppedItemsMakeNoise && dropsFromInventoryMakeNoiseByDefault)
			{
				for(String item : ConfigHandler.specificDroppedItemsAndSoundsList.keySet()) {
					if(item.contentEquals(event.getEntityItem().getItem().getItem().getRegistryName().toString()))
						world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(ConfigHandler.specificDroppedItemsAndSoundsList.get(item)), SoundCategory.PLAYERS, 0.4F, world.rand.nextFloat() * 0.1F + 0.9F);
				}
				world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(dropsFromInventoryDefaultSound), SoundCategory.PLAYERS, 0.4F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
			else if(specificDroppedItemsMakeNoise && !dropsFromInventoryMakeNoiseByDefault)
			{
				for(String item : ConfigHandler.specificDroppedItemsAndSoundsList.keySet()) {
					if(item.contentEquals(event.getEntityItem().getItem().getItem().getRegistryName().toString()))
						world.playSound(null, player.posX, player.posY + 0.5, player.posZ, getRegisteredSoundEvent(ConfigHandler.specificDroppedItemsAndSoundsList.get(item)), SoundCategory.PLAYERS, 0.4F, world.rand.nextFloat() * 0.1F + 0.9F);
				}
			}
		}
	}
	
	//Dropping items from death
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onDeathItemDrop(LivingDropsEvent event)
	{
		if(event.getEntity() == null || event.getDrops() == null || event.getDrops().isEmpty() || event.getEntity().world.isRemote || event.isCanceled()) return;
		
		World world = event.getEntity().world;
		Entity entity = event.getEntity();
		
		if(dropsFromMobsMakeNoise)
		{
			if(!specificDroppedItemsMakeNoise && dropsFromMobsMakeNoiseByDefault)
				world.playSound(null, entity.posX, entity.posY + 0.5, entity.posZ, getRegisteredSoundEvent(dropsFromMobsDefaultSound), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);		
			else if(specificDroppedItemsMakeNoise && dropsFromMobsMakeNoiseByDefault)
			{
				for(EntityItem itemDropped : event.getDrops())
				{
					for(String itemDesired : ConfigHandler.specificDroppedItemsAndSoundsList.keySet())
					{
						if(itemDesired.contentEquals(itemDropped.getItem().getItem().getRegistryName().toString()))
							world.playSound(null, entity.posX, entity.posY + 0.5, entity.posZ, getRegisteredSoundEvent(ConfigHandler.specificDroppedItemsAndSoundsList.get(itemDesired)), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
					}
				}
				world.playSound(null, entity.posX, entity.posY + 0.5, entity.posZ, getRegisteredSoundEvent(dropsFromMobsDefaultSound), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
			else if(specificDroppedItemsMakeNoise && !dropsFromMobsMakeNoiseByDefault)
			{
				for(EntityItem itemDropped : event.getDrops())
				{
					for(String itemDesired : ConfigHandler.specificDroppedItemsAndSoundsList.keySet())
					{
						if(itemDesired.contentEquals(itemDropped.getItem().getItem().getRegistryName().toString()))
							world.playSound(null, entity.posX, entity.posY + 0.5, entity.posZ, getRegisteredSoundEvent(ConfigHandler.specificDroppedItemsAndSoundsList.get(itemDesired)), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
					}
				}
			}
		}
	}
	
	//Item Impacting
	@SubscribeEvent//(priority = EventPriority.LOW)
	public void onWorldTick(TickEvent.PlayerTickEvent event)
	{
		if(event.player == null || event.player.world.isRemote || event.phase == TickEvent.Phase.START) return;
		
		if(dropsImpactingGroundMakeNoise)
		{
			World world = event.player.world;
			EntityPlayer player = event.player;
			AxisAlignedBB playerBB = new AxisAlignedBB(player.posX-8,player.posY-8,player.posZ-8,player.posX+8,player.posY+8,player.posZ+8);
			
			List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, playerBB);
					
			for(EntityItem item : entities)
			{
				if(item.prevPosY>item.posY && item.onGround)// && playerDistanceUnder(world,item,8)) //Not needed due to AABB, only here for reference
				{
					if(!specificDropsImpactingGroundMakeNoise && dropsImpactingGroundMakeNoiseByDefault)
						world.playSound(null, item.posX, item.posY, item.posZ, getRegisteredSoundEvent(dropsImpactingGroundDefaultSound), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);		
					else if(specificDropsImpactingGroundMakeNoise && dropsImpactingGroundMakeNoiseByDefault)
					{
						for(String desiredItem : ConfigHandler.specificImpactingGroundItemsAndSoundsList.keySet()) {
							if(desiredItem.contentEquals(item.getItem().getItem().getRegistryName().toString()))
								world.playSound(null, item.posX, item.posY, item.posZ, getRegisteredSoundEvent(ConfigHandler.specificImpactingGroundItemsAndSoundsList.get(desiredItem)), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
						}
						world.playSound(null, item.posX, item.posY, item.posZ, getRegisteredSoundEvent(dropsImpactingGroundDefaultSound), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
					}
					else if(specificDropsImpactingGroundMakeNoise && !dropsImpactingGroundMakeNoiseByDefault)
					{
						for(String desiredItem : ConfigHandler.specificImpactingGroundItemsAndSoundsList.keySet()) {
							if(desiredItem.contentEquals(item.getItem().getItem().getRegistryName().toString()))
								world.playSound(null, item.posX, item.posY, item.posZ, getRegisteredSoundEvent(ConfigHandler.specificImpactingGroundItemsAndSoundsList.get(desiredItem)), SoundCategory.NEUTRAL, 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
						}
					}
				}
			}
		}
	}
	//Not needed due to AABB, just here for reference
	/*private static boolean playerDistanceUnder(World world, EntityItem item, int distance)
	{
		System.out.println("Starting distance check");
		EntityPlayer closestPlayer = world.getClosestPlayerToEntity(item, distance);
		
		if(closestPlayer == null)
		{
			System.out.println("Player not detected");
			return false;
		}
		else
		{
			System.out.println("Player detected: " + closestPlayer.getName() + " distance: " + item.getDistanceSq(closestPlayer.getPosition()));
			return true;
		}
	}*/
	
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
