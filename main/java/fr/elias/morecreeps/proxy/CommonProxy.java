package fr.elias.morecreeps.proxy;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import fr.elias.morecreeps.client.gui.handler.CREEPSGuiHandler;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityAtom;
import fr.elias.morecreeps.common.entity.CREEPSEntityCaveman;
import fr.elias.morecreeps.common.entity.CREEPSEntityDigBug;
import fr.elias.morecreeps.common.entity.CREEPSEntityHorseHead;
import fr.elias.morecreeps.common.entity.CREEPSEntityRay;
import fr.elias.morecreeps.common.entity.CREEPSEntityTrophy;

public class CommonProxy
{
	public void render(){}
	public void renderModelItem(){}
	public void smoke(World world, EntityPlayer player, Random random){}
	public void smoke2(World world, Entity entity, Random random){}
	public void smoke3(World world, Entity entity, Random random){}
	public void blood(World world, double posx, double posy, double posz, boolean realBlood){}
	public void dirt(World world, EntityPlayer player, Random random, int l, int i1, int k){}
	public void foam(World world, EntityPlayer player){}
	public void foam2(World world, CREEPSEntityAtom atom){}
	public void confettiA(EntityLivingBase player, World world)
	{
		if(!world.isRemote)
		{
	        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
	        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
	        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(world);
	        creepsentitytrophy.setLocationAndAngles(player.posX + d * 3D, player.posY - 2D, player.posZ + d1 * 3D, player.rotationYaw, 0.0F);
	        world.spawnEntityInWorld(creepsentitytrophy);
		}
	}
	public void shrinkBlast(World world, Entity entity, Random rand){}
	public void shrinkSmoke(World world, Entity entity){}
	public void rocketGoBoom(World world, Entity entity, Random rand) {}
	public void rocketSmoke(World world, Entity entity, Item item){}
	public void robotTedSmoke(World world, double posX, double posY, double posZ, int floattimer, float modelspeed){}
	public void confettiB(World world, CREEPSEntityTrophy trophy){} // for the confetti particles
	public void barf(World world, EntityPlayer player){}
	public void bubble(World world, EntityLivingBase entity){}
	public void addChatMessage(String s){}
	public void playSoundEffectAtPlayer(World world, String s, float volume, float pitch){}
	public void pee(World world, double posX, double posY, double posZ, float rotationYaw, float modelsize){}
	public void foam3(World world, CREEPSEntityCaveman player, int i, int j, int k){}
	public void dirtDigBug(World world, CREEPSEntityDigBug dbug, Random random, int k2){}
	public void bubbleDoghouse(World world, EntityLivingBase entity){}
	public void growParticle(World world, Entity entity){}
	public void shrinkParticle(World world, Entity entity){}
	public void smokeHorseHead(World world, CREEPSEntityHorseHead horsehead, Random rand) {}
	public boolean isJumpKeyDown(){return false;}
	public void smokeRay(World worldObj, CREEPSEntityRay creepsEntityRay, byte byte0) {}
}
