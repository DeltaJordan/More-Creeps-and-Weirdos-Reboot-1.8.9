package fr.elias.morecreeps.proxy;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.models.CREEPSModelArmyGuy;
import fr.elias.morecreeps.client.models.CREEPSModelArmyGuyArm;
import fr.elias.morecreeps.client.models.CREEPSModelAtom;
import fr.elias.morecreeps.client.models.CREEPSModelBigBaby;
import fr.elias.morecreeps.client.models.CREEPSModelBlorp;
import fr.elias.morecreeps.client.models.CREEPSModelBubbleScum;
import fr.elias.morecreeps.client.models.CREEPSModelBum;
import fr.elias.morecreeps.client.models.CREEPSModelCamel;
import fr.elias.morecreeps.client.models.CREEPSModelCamelJockey;
import fr.elias.morecreeps.client.models.CREEPSModelCastleCritter;
import fr.elias.morecreeps.client.models.CREEPSModelCastleKing;
import fr.elias.morecreeps.client.models.CREEPSModelCaveman;
import fr.elias.morecreeps.client.models.CREEPSModelDesertLizard;
import fr.elias.morecreeps.client.models.CREEPSModelDigBug;
import fr.elias.morecreeps.client.models.CREEPSModelDoghouse;
import fr.elias.morecreeps.client.models.CREEPSModelEvilChicken;
import fr.elias.morecreeps.client.models.CREEPSModelEvilCreature;
import fr.elias.morecreeps.client.models.CREEPSModelEvilLight;
import fr.elias.morecreeps.client.models.CREEPSModelEvilPig;
import fr.elias.morecreeps.client.models.CREEPSModelEvilScientist;
import fr.elias.morecreeps.client.models.CREEPSModelEvilSnowman;
import fr.elias.morecreeps.client.models.CREEPSModelFloob;
import fr.elias.morecreeps.client.models.CREEPSModelFloobShip;
import fr.elias.morecreeps.client.models.CREEPSModelG;
import fr.elias.morecreeps.client.models.CREEPSModelGPig;
import fr.elias.morecreeps.client.models.CREEPSModelGooGoat;
import fr.elias.morecreeps.client.models.CREEPSModelHippo;
import fr.elias.morecreeps.client.models.CREEPSModelHorseHead;
import fr.elias.morecreeps.client.models.CREEPSModelHotdog;
import fr.elias.morecreeps.client.models.CREEPSModelHunchback;
import fr.elias.morecreeps.client.models.CREEPSModelKid;
import fr.elias.morecreeps.client.models.CREEPSModelLawyerFromHell;
import fr.elias.morecreeps.client.models.CREEPSModelLolliman;
import fr.elias.morecreeps.client.models.CREEPSModelManDog;
import fr.elias.morecreeps.client.models.CREEPSModelNonSwimmer;
import fr.elias.morecreeps.client.models.CREEPSModelPreacher;
import fr.elias.morecreeps.client.models.CREEPSModelPyramidGuardian;
import fr.elias.morecreeps.client.models.CREEPSModelRatMan;
import fr.elias.morecreeps.client.models.CREEPSModelRobotTed;
import fr.elias.morecreeps.client.models.CREEPSModelRobotTodd;
import fr.elias.morecreeps.client.models.CREEPSModelRockMonster;
import fr.elias.morecreeps.client.models.CREEPSModelRocketGiraffe;
import fr.elias.morecreeps.client.models.CREEPSModelSchlump;
import fr.elias.morecreeps.client.models.CREEPSModelSneakySal;
import fr.elias.morecreeps.client.models.CREEPSModelSnowDevil;
import fr.elias.morecreeps.client.models.CREEPSModelSquimp;
import fr.elias.morecreeps.client.models.CREEPSModelTombstone;
import fr.elias.morecreeps.client.models.CREEPSModelTowel;
import fr.elias.morecreeps.client.models.CREEPSModelTrophy;
import fr.elias.morecreeps.client.models.CREEPSModelZebra;
import fr.elias.morecreeps.client.other.TickClientHandlerEvent;
import fr.elias.morecreeps.client.particles.CREEPSFxAtoms;
import fr.elias.morecreeps.client.particles.CREEPSFxBlood;
import fr.elias.morecreeps.client.particles.CREEPSFxBubbles;
import fr.elias.morecreeps.client.particles.CREEPSFxConfetti;
import fr.elias.morecreeps.client.particles.CREEPSFxDirt;
import fr.elias.morecreeps.client.particles.CREEPSFxFoam;
import fr.elias.morecreeps.client.particles.CREEPSFxPee;
import fr.elias.morecreeps.client.particles.CREEPSFxSmoke;
import fr.elias.morecreeps.client.render.CREEPSRenderArmyGuy;
import fr.elias.morecreeps.client.render.CREEPSRenderArmyGuyArm;
import fr.elias.morecreeps.client.render.CREEPSRenderAtom;
import fr.elias.morecreeps.client.render.CREEPSRenderBabyMummy;
import fr.elias.morecreeps.client.render.CREEPSRenderBigBaby;
import fr.elias.morecreeps.client.render.CREEPSRenderBlackSoul;
import fr.elias.morecreeps.client.render.CREEPSRenderBlorp;
import fr.elias.morecreeps.client.render.CREEPSRenderBubbleScum;
import fr.elias.morecreeps.client.render.CREEPSRenderBum;
import fr.elias.morecreeps.client.render.CREEPSRenderCamel;
import fr.elias.morecreeps.client.render.CREEPSRenderCamelJockey;
import fr.elias.morecreeps.client.render.CREEPSRenderCastleCritter;
import fr.elias.morecreeps.client.render.CREEPSRenderCastleKing;
import fr.elias.morecreeps.client.render.CREEPSRenderCaveman;
import fr.elias.morecreeps.client.render.CREEPSRenderDesertLizard;
import fr.elias.morecreeps.client.render.CREEPSRenderDigBug;
import fr.elias.morecreeps.client.render.CREEPSRenderDoghouse;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilChicken;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilCreature;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilLight;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilPig;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilScientist;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilSnowman;
import fr.elias.morecreeps.client.render.CREEPSRenderFloob;
import fr.elias.morecreeps.client.render.CREEPSRenderFloobShip;
import fr.elias.morecreeps.client.render.CREEPSRenderG;
import fr.elias.morecreeps.client.render.CREEPSRenderGooGoat;
import fr.elias.morecreeps.client.render.CREEPSRenderGuineaPig;
import fr.elias.morecreeps.client.render.CREEPSRenderHippo;
import fr.elias.morecreeps.client.render.CREEPSRenderHorseHead;
import fr.elias.morecreeps.client.render.CREEPSRenderHotdog;
import fr.elias.morecreeps.client.render.CREEPSRenderHunchback;
import fr.elias.morecreeps.client.render.CREEPSRenderKid;
import fr.elias.morecreeps.client.render.CREEPSRenderLawyerFromHell;
import fr.elias.morecreeps.client.render.CREEPSRenderLolliman;
import fr.elias.morecreeps.client.render.CREEPSRenderManDog;
import fr.elias.morecreeps.client.render.CREEPSRenderNonSwimmer;
import fr.elias.morecreeps.client.render.CREEPSRenderPreacher;
import fr.elias.morecreeps.client.render.CREEPSRenderPyramidGuardian;
import fr.elias.morecreeps.client.render.CREEPSRenderRatMan;
import fr.elias.morecreeps.client.render.CREEPSRenderRobotTed;
import fr.elias.morecreeps.client.render.CREEPSRenderRobotTodd;
import fr.elias.morecreeps.client.render.CREEPSRenderRockMonster;
import fr.elias.morecreeps.client.render.CREEPSRenderRocketGiraffe;
import fr.elias.morecreeps.client.render.CREEPSRenderSchlump;
import fr.elias.morecreeps.client.render.CREEPSRenderSneakySal;
import fr.elias.morecreeps.client.render.CREEPSRenderSnowDevil;
import fr.elias.morecreeps.client.render.CREEPSRenderSquimp;
import fr.elias.morecreeps.client.render.CREEPSRenderThief;
import fr.elias.morecreeps.client.render.CREEPSRenderTombstone;
import fr.elias.morecreeps.client.render.CREEPSRenderTowel;
import fr.elias.morecreeps.client.render.CREEPSRenderTrophy;
import fr.elias.morecreeps.client.render.CREEPSRenderZebra;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuyArm;
import fr.elias.morecreeps.common.entity.CREEPSEntityAtom;
import fr.elias.morecreeps.common.entity.CREEPSEntityBabyMummy;
import fr.elias.morecreeps.common.entity.CREEPSEntityBigBaby;
import fr.elias.morecreeps.common.entity.CREEPSEntityBlackSoul;
import fr.elias.morecreeps.common.entity.CREEPSEntityBlorp;
import fr.elias.morecreeps.common.entity.CREEPSEntityBubbleScum;
import fr.elias.morecreeps.common.entity.CREEPSEntityBum;
import fr.elias.morecreeps.common.entity.CREEPSEntityCamel;
import fr.elias.morecreeps.common.entity.CREEPSEntityCamelJockey;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleCritter;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleKing;
import fr.elias.morecreeps.common.entity.CREEPSEntityCaveman;
import fr.elias.morecreeps.common.entity.CREEPSEntityDesertLizard;
import fr.elias.morecreeps.common.entity.CREEPSEntityDigBug;
import fr.elias.morecreeps.common.entity.CREEPSEntityDoghouse;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilChicken;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilCreature;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilLight;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilScientist;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilSnowman;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloob;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloobShip;
import fr.elias.morecreeps.common.entity.CREEPSEntityFrisbee;
import fr.elias.morecreeps.common.entity.CREEPSEntityG;
import fr.elias.morecreeps.common.entity.CREEPSEntityGooDonut;
import fr.elias.morecreeps.common.entity.CREEPSEntityGooGoat;
import fr.elias.morecreeps.common.entity.CREEPSEntityGrow;
import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityHippo;
import fr.elias.morecreeps.common.entity.CREEPSEntityHorseHead;
import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;
import fr.elias.morecreeps.common.entity.CREEPSEntityHunchback;
import fr.elias.morecreeps.common.entity.CREEPSEntityKid;
import fr.elias.morecreeps.common.entity.CREEPSEntityLawyerFromHell;
import fr.elias.morecreeps.common.entity.CREEPSEntityLolliman;
import fr.elias.morecreeps.common.entity.CREEPSEntityManDog;
import fr.elias.morecreeps.common.entity.CREEPSEntityMoney;
import fr.elias.morecreeps.common.entity.CREEPSEntityNonSwimmer;
import fr.elias.morecreeps.common.entity.CREEPSEntityPreacher;
import fr.elias.morecreeps.common.entity.CREEPSEntityPyramidGuardian;
import fr.elias.morecreeps.common.entity.CREEPSEntityRatMan;
import fr.elias.morecreeps.common.entity.CREEPSEntityRay;
import fr.elias.morecreeps.common.entity.CREEPSEntityRobotTed;
import fr.elias.morecreeps.common.entity.CREEPSEntityRobotTodd;
import fr.elias.morecreeps.common.entity.CREEPSEntityRockMonster;
import fr.elias.morecreeps.common.entity.CREEPSEntityRocketGiraffe;
import fr.elias.morecreeps.common.entity.CREEPSEntitySchlump;
import fr.elias.morecreeps.common.entity.CREEPSEntityShrink;
import fr.elias.morecreeps.common.entity.CREEPSEntitySneakySal;
import fr.elias.morecreeps.common.entity.CREEPSEntitySnowDevil;
import fr.elias.morecreeps.common.entity.CREEPSEntitySquimp;
import fr.elias.morecreeps.common.entity.CREEPSEntityThief;
import fr.elias.morecreeps.common.entity.CREEPSEntityTombstone;
import fr.elias.morecreeps.common.entity.CREEPSEntityTowel;
import fr.elias.morecreeps.common.entity.CREEPSEntityTrophy;
import fr.elias.morecreeps.common.entity.CREEPSEntityZebra;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public void render()
	{
		MinecraftForge.EVENT_BUS.register(new TickClientHandlerEvent());
		FMLCommonHandler.instance().bus().register(new TickClientHandlerEvent());
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityArmyGuy.class, new CREEPSRenderArmyGuy(new CREEPSModelArmyGuy(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityArmyGuyArm.class, new CREEPSRenderArmyGuyArm(new CREEPSModelArmyGuyArm(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityAtom.class, new CREEPSRenderAtom(new CREEPSModelAtom(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityBabyMummy.class, new CREEPSRenderBabyMummy(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityBigBaby.class, new CREEPSRenderBigBaby(new CREEPSModelBigBaby(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityBlackSoul.class, new CREEPSRenderBlackSoul(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityBlorp.class, new CREEPSRenderBlorp(new CREEPSModelBlorp(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityBubbleScum.class, new CREEPSRenderBubbleScum(new CREEPSModelBubbleScum(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityBum.class, new CREEPSRenderBum(new CREEPSModelBum(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityCamel.class, new CREEPSRenderCamel(new CREEPSModelCamel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityCamelJockey.class, new CREEPSRenderCamelJockey(new CREEPSModelCamelJockey(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityCastleCritter.class, new CREEPSRenderCastleCritter(new CREEPSModelCastleCritter(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityCastleKing.class, new CREEPSRenderCastleKing(new CREEPSModelCastleKing(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityCaveman.class, new CREEPSRenderCaveman(new CREEPSModelCaveman(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityDesertLizard.class, new CREEPSRenderDesertLizard(new CREEPSModelDesertLizard(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityDigBug.class, new CREEPSRenderDigBug(new CREEPSModelDigBug(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityDoghouse.class, new CREEPSRenderDoghouse(new CREEPSModelDoghouse(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityEvilChicken.class, new CREEPSRenderEvilChicken(new CREEPSModelEvilChicken(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityEvilCreature.class, new CREEPSRenderEvilCreature(new CREEPSModelEvilCreature(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityEvilLight.class, new CREEPSRenderEvilLight(new CREEPSModelEvilLight(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityEvilPig.class, new CREEPSRenderEvilPig(new CREEPSModelEvilPig(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityEvilScientist.class, new CREEPSRenderEvilScientist(new CREEPSModelEvilScientist(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityEvilSnowman.class, new CREEPSRenderEvilSnowman(new CREEPSModelEvilSnowman(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityFloob.class, new CREEPSRenderFloob(new CREEPSModelFloob(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityFloobShip.class, new CREEPSRenderFloobShip(new CREEPSModelFloobShip(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityG.class, new CREEPSRenderG(new CREEPSModelG(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityGooGoat.class, new CREEPSRenderGooGoat(new CREEPSModelGooGoat(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityGuineaPig.class, new CREEPSRenderGuineaPig(new CREEPSModelGPig(), new CREEPSModelGPig(0.5F), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityHippo.class, new CREEPSRenderHippo(new CREEPSModelHippo(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityHorseHead.class, new CREEPSRenderHorseHead(new CREEPSModelHorseHead(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityHotdog.class, new CREEPSRenderHotdog(new CREEPSModelHotdog(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityHunchback.class, new CREEPSRenderHunchback(new CREEPSModelHunchback(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityKid.class, new CREEPSRenderKid(new CREEPSModelKid(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityLawyerFromHell.class, new CREEPSRenderLawyerFromHell(new CREEPSModelLawyerFromHell(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityLolliman.class, new CREEPSRenderLolliman(new CREEPSModelLolliman(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityManDog.class, new CREEPSRenderManDog(new CREEPSModelManDog(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityNonSwimmer.class, new CREEPSRenderNonSwimmer(new CREEPSModelNonSwimmer(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityPreacher.class, new CREEPSRenderPreacher(new CREEPSModelPreacher(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityPyramidGuardian.class, new CREEPSRenderPyramidGuardian(new CREEPSModelPyramidGuardian(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityRatMan.class, new CREEPSRenderRatMan(new CREEPSModelRatMan(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityRobotTed.class, new CREEPSRenderRobotTed(new CREEPSModelRobotTed(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityRobotTodd.class, new CREEPSRenderRobotTodd(new CREEPSModelRobotTodd(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityRocketGiraffe.class, new CREEPSRenderRocketGiraffe(new CREEPSModelRocketGiraffe(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityRockMonster.class, new CREEPSRenderRockMonster(new CREEPSModelRockMonster(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntitySchlump.class, new CREEPSRenderSchlump(new CREEPSModelSchlump(), 0.5F));
		
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityShrink.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), MoreCreepsAndWeirdos.shrinkshrink, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityRay.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), MoreCreepsAndWeirdos.rayray, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityGrow.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), MoreCreepsAndWeirdos.shrinkshrink, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityMoney.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), MoreCreepsAndWeirdos.money, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityGooDonut.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), MoreCreepsAndWeirdos.goodonut, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityFrisbee.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), MoreCreepsAndWeirdos.frisbee, Minecraft.getMinecraft().getRenderItem()));
		
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntitySneakySal.class, new CREEPSRenderSneakySal(new CREEPSModelSneakySal(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntitySnowDevil.class, new CREEPSRenderSnowDevil(new CREEPSModelSnowDevil(), new CREEPSModelSnowDevil(0.5F), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntitySquimp.class, new CREEPSRenderSquimp(new CREEPSModelSquimp(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityThief.class, new CREEPSRenderThief(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityTombstone.class, new CREEPSRenderTombstone(new CREEPSModelTombstone(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityTowel.class, new CREEPSRenderTowel(new CREEPSModelTowel(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityTrophy.class, new CREEPSRenderTrophy(new CREEPSModelTrophy(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(CREEPSEntityZebra.class, new CREEPSRenderZebra(new CREEPSModelZebra(), 0.5F));
	}
	
	public void renderModelItem()
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partBubble, 0, new ModelResourceLocation("morecreeps:partBubble", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partWhite, 0, new ModelResourceLocation("morecreeps:partWhite", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partRed, 0, new ModelResourceLocation("morecreeps:partRed", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partBlack, 0, new ModelResourceLocation("morecreeps:partBlack", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partYellow, 0, new ModelResourceLocation("morecreeps:partYellow", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partBlue, 0, new ModelResourceLocation("morecreeps:partBlue", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partShrink, 0, new ModelResourceLocation("morecreeps:partShrink", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.partBarf, 0, new ModelResourceLocation("morecreeps:partBarf", "inventory"));

		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_hell, 0, new ModelResourceLocation("morecreeps:a_hell", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_pig, 0, new ModelResourceLocation("morecreeps:a_pig", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_pyramid, 0, new ModelResourceLocation("morecreeps:a_pyramid", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_floob, 0, new ModelResourceLocation("morecreeps:a_floob", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_rockmonster, 0, new ModelResourceLocation("morecreeps:a_rockmonster", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_bubble, 0, new ModelResourceLocation("morecreeps:a_bubble", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_hotdog, 0, new ModelResourceLocation("morecreeps:a_hotdog", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_camel, 0, new ModelResourceLocation("morecreeps:a_camel", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_zebra, 0, new ModelResourceLocation("morecreeps:a_zebra", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_nonswimmer, 0, new ModelResourceLocation("morecreeps:a_nonswimmer", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.a_caveman, 0, new ModelResourceLocation("morecreeps:a_caveman", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.zebrahelmet, 0, new ModelResourceLocation("morecreeps:zebraHelmet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.zebrabody, 0, new ModelResourceLocation("morecreeps:zebraBody", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.zebralegs, 0, new ModelResourceLocation("morecreeps:zebraLegs", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.zebraboots, 0, new ModelResourceLocation("morecreeps:zebraBoots", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.blorpcola, 0, new ModelResourceLocation("morecreeps:blorpCola", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.bandaid, 0, new ModelResourceLocation("morecreeps:bandAid", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.goodonut, 0, new ModelResourceLocation("morecreeps:gooDonut", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.money, 0, new ModelResourceLocation("morecreeps:money", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.raygun, 0, new ModelResourceLocation("morecreeps:raygun", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.shrinkray, 0, new ModelResourceLocation("morecreeps:shrinkray", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.shrinkshrink, 0, new ModelResourceLocation("morecreeps:shrinkshrink", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.limbs, 0, new ModelResourceLocation("morecreeps:limbs", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.babyjarempty, 0, new ModelResourceLocation("morecreeps:babyJarEmpty", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.babyjarfull, 0, new ModelResourceLocation("morecreeps:babyJarFull", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.mobilephone, 0, new ModelResourceLocation("morecreeps:mobilephone", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.growray, 0, new ModelResourceLocation("morecreeps:growray", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.frisbee, 0, new ModelResourceLocation("morecreeps:frisbee", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.rayray, 0, new ModelResourceLocation("morecreeps:rayray", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.guineapigradio, 0, new ModelResourceLocation("morecreeps:guineapigRadio", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.evilegg, 0, new ModelResourceLocation("morecreeps:evilEgg", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.rocket, 0, new ModelResourceLocation("morecreeps:rocket", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.atompacket, 0, new ModelResourceLocation("morecreeps:atomPacket", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.ram16k, 0, new ModelResourceLocation("morecreeps:ram16k", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.battery, 0, new ModelResourceLocation("morecreeps:battery", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.horseheadgem, 0, new ModelResourceLocation("morecreeps:horseHeadGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.armygem, 0, new ModelResourceLocation("morecreeps:armyGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.gun, 0, new ModelResourceLocation("morecreeps:gun", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.bullet, 0, new ModelResourceLocation("morecreeps:bullet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.lifegem, 0, new ModelResourceLocation("morecreeps:lifeGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.lolly, 0, new ModelResourceLocation("morecreeps:lolly", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.armsword, 0, new ModelResourceLocation("morecreeps:armSword", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.donut, 0, new ModelResourceLocation("morecreeps:donut", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.extinguisher, 0, new ModelResourceLocation("morecreeps:extinguisher", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.zebrahide, 0, new ModelResourceLocation("morecreeps:zebrahide", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.firegem, 0, new ModelResourceLocation("morecreeps:fireGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.earthgem, 0, new ModelResourceLocation("morecreeps:earthGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.mininggem, 0, new ModelResourceLocation("morecreeps:miningGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.healinggem, 0, new ModelResourceLocation("morecreeps:healingGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.skygem, 0, new ModelResourceLocation("morecreeps:skyGem", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.gemsword, 0, new ModelResourceLocation("morecreeps:gemSword", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.moopsworm, 0, new ModelResourceLocation("morecreeps:moopsWorm", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.cavemanclub, 0, new ModelResourceLocation("morecreeps:cavemanClub", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MoreCreepsAndWeirdos.popsicle, 0, new ModelResourceLocation("morecreeps:popsicle", "inventory"));
	}
	
	public void shrinkBlast(World world, Entity entity, Random rand)
	{
        for (int i = 0; i < 8; i++)
        {
            byte byte0 = 7;

            if (rand.nextInt(4) == 0)
            {
                byte0 = 11;
            }
            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, MoreCreepsAndWeirdos.partShrink, 0.5F, 0);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	public void shrinkSmoke(World world, Entity entity)
	{
        for (int k = 0; k < 8; k++)
        {
            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, MoreCreepsAndWeirdos.partShrink, 0.25F, 0);
            creepsfxsmoke.renderDistanceWeight = 30D;
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	
	public void rocketGoBoom(World world, Entity entity, Random rand)
	{
        for (int i = 0; i < 20; i++)
        {
            Item j = MoreCreepsAndWeirdos.partYellow;

            if (rand.nextInt(3) == 0)
            {
                j = MoreCreepsAndWeirdos.partRed;
            }

            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, j, 1.0F, 0F);
            creepsfxsmoke.renderDistanceWeight = 30D;
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	
	public void rocketSmoke(World world, Entity entity, Item item)
	{
        CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, item, 13, 3F);
        creepsfxsmoke.renderDistanceWeight = 15D;
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        CREEPSFxSmoke creepsfxsmoke1 = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, item, 14, 3F);
        creepsfxsmoke1.renderDistanceWeight = 15D;
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke1);
        CREEPSFxSmoke creepsfxsmoke2 = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, item, 24, 3F);
        creepsfxsmoke2.renderDistanceWeight = 15D;
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke2);
	}
	
	public void robotTedSmoke(World world, double posX, double posY, double posZ, int floattimer, float modelspeed)
	{
        for (int i = 0; i < 15; i++)
        {
            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, posX - 0.40000000000000002D, (posY - 0.5D) + (double)(floattimer / 100), posZ, MoreCreepsAndWeirdos.partWhite, 13, 0.4F - (0.51F - modelspeed));
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }

        for (int j = 0; j < 15; j++)
        {
            CREEPSFxSmoke creepsfxsmoke1 = new CREEPSFxSmoke(world, posX + 0.40000000000000002D, (posY - 0.5D) + (double)(floattimer / 100), posZ, MoreCreepsAndWeirdos.partWhite, 13, 0.4F - (0.51F - modelspeed));
            creepsfxsmoke1.renderDistanceWeight = 15D;
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke1);
        }
	}
	
	public void barf(World world, EntityPlayer player)
	{
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                CREEPSFxBlood creepsfxblood = new CREEPSFxBlood(world, player.posX, player.posY + 0.60000000298023224D, player.posZ, MoreCreepsAndWeirdos.partBarf, 0.85F);
                creepsfxblood.motionX += d * 0.25D;
                creepsfxblood.motionZ += d1 * 0.25D;
                Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxblood);
            }
        }
	}
	public void blood(World world, double posx, double posy, double posz, boolean realBlood)
	{
        if (CREEPSConfig.Blood)
        {
            for (int i = 0; i < 30; i++)
            {
                CREEPSFxBlood creepsfxblood = new CREEPSFxBlood(world, posx, posy, posz, realBlood ? MoreCreepsAndWeirdos.partRed : MoreCreepsAndWeirdos.partWhite, 0.255F);
                Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxblood);
            }
        }
	}
	public void confettiB(World world, CREEPSEntityTrophy trophy)
	{
		if(world.isRemote)
		{
	        for (int i = 1; i < 10; i++)
	        {
	            for (int j = 0; j < 10; j++)
	            {
	                CREEPSFxConfetti creepsfxconfetti = new CREEPSFxConfetti(world, trophy.posX + (double)(world.rand.nextFloat() * 8F - world.rand.nextFloat() * 8F), trophy.posY + (double)world.rand.nextInt(4) + 4D, trophy.posZ + (double)(world.rand.nextFloat() * 8F - world.rand.nextFloat() * 8F), Item.getItemById(world.rand.nextInt(99)));
	                Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxconfetti);
	            }
	        }
		}
	}
	
	public void dirt(World world, EntityPlayer player, Random random, int l, int i1, int k)
	{
        for (int j1 = 0; j1 < 15; j1++)
        {
            CREEPSFxDirt creepsfxdirt = new CREEPSFxDirt(world, (int)(player.posX + (double)l + random.nextGaussian() * 0.02D), (int)(player.posY + (double)k), (int)(player.posZ + (double)i1 + random.nextGaussian() * 0.02D), Item.getItemFromBlock(Blocks.dirt));
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxdirt);
        }
	}
	public void dirtDigBug(World world, CREEPSEntityDigBug dbug, Random random, int k2)
	{
		CREEPSFxDirt creepsfxdirt2 = new CREEPSFxDirt(world, dbug.posX, dbug.posY + (double)k2, dbug.posZ, Item.getItemFromBlock(Blocks.dirt));
		Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxdirt2);
	}
	public void foam(World world, EntityPlayer player)
	{
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        for (int i = 0; i < 25; i++)
        {
            CREEPSFxFoam creepsfxfoam = new CREEPSFxFoam(world, player.posX + d * 0.20000000000000001D, player.posY * 0.80000001192092896D, player.posZ + d1 * 0.5D, MoreCreepsAndWeirdos.partWhite);
            creepsfxfoam.motionX += d * 1.3999999761581421D;
            creepsfxfoam.motionZ += d1 * 1.3999999761581421D;
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxfoam);
        }
	}
	public void foam2(World world, CREEPSEntityAtom atom)
	{
        for (int i1 = 0; (float)i1 < atom.atomsize; i1++)
        {
            CREEPSFxAtoms creepsfxatoms = new CREEPSFxAtoms(world, atom.posX, atom.posY + (double)(int)(atom.atomsize / 3F), atom.posZ, Item.getItemById(atom.rand.nextInt(99) + 1), 0.3F);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxatoms);
        }
	}
	public void foam3(World world, CREEPSEntityCaveman player, int i, int j, int k)
	{
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        for (int i1 = 0; i1 < 25; i1++)
        {
        	CREEPSFxFoam creepsfxfoam = new CREEPSFxFoam(world, i, j, k, MoreCreepsAndWeirdos.partWhite);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxfoam);
        }
	}
	public void smoke(World world, EntityPlayer player, Random random)
	{
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, ((player.posX + random.nextGaussian() * 0.25D) - random.nextGaussian() * 0.25D) + d * 1.0D, ((player.posY - 0.5D) + random.nextGaussian() * 0.5D) - random.nextGaussian() * 0.5D, ((player.posZ + random.nextGaussian() * 0.25D) - random.nextGaussian() * 0.25D) + d1 * 1.0D, MoreCreepsAndWeirdos.partBubble, 0.05F, 0.0F);
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
	}
	public void smokeHorseHead(World world, CREEPSEntityHorseHead horsehead, Random rand)
	{
		CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, horsehead.posX, (horsehead.posY - 0.5D) + rand.nextGaussian() * 0.20000000000000001D, horsehead.posZ, MoreCreepsAndWeirdos.instance.partWhite, 0.25F, 0.0F);
		Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
	}
	public void smoke2(World world, Entity entity, Random random)
	{
        for (int i = 0; i < 8; i++)
        {
            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, MoreCreepsAndWeirdos.partBlack, 0.2F, 0.5F);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	public void smoke3(World world, Entity entity, Random random)
	{
        for (int i = 0; i < 5; i++)
        {
            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, (entity.posY - 0.5D) + random.nextGaussian() * 0.20000000000000001D, entity.posZ, MoreCreepsAndWeirdos.partWhite, 0.65F, 0.0F);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	public void growParticle(World world, Entity entity)
	{
        for (int k = 0; k < 8; k++)
        {
            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, MoreCreepsAndWeirdos.instance.partShrink, 0.25F, 0.0F);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	public void smokeRay(World world, Entity entity, byte b0)
	{
		CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, Items.egg, b0, 0.5F);
		 Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
	}
	public void shrinkParticle(World world, Entity entity)
	{
        for (int i = 0; i < 8; i++)
        {
            byte byte0 = 7;

            if (world.rand.nextInt(4) == 0)
            {
                byte0 = 11;
            }

            CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(world, entity.posX, entity.posY, entity.posZ, MoreCreepsAndWeirdos.instance.partShrink, 0.5F, 0.0F);
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);
        }
	}
	public void addChatMessage(String s)
	{
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
	}
	public void playSoundEffectAtPlayer(World world, String s, float volume, float pitch)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		world.playSoundEffect(player.posX, player.posY, player.posZ, s, volume, pitch);
	}
	public void bubble(World world, EntityLivingBase entity)
	{
        double d = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);
        CREEPSFxBubbles creepsfxbubbles = new CREEPSFxBubbles(world, entity.posX + d * 0.5D, entity.posY + 0.75D, entity.posZ + d1 * 0.5D, MoreCreepsAndWeirdos.partBubble, 0.7F);
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxbubbles);
	}
	public void bubbleDoghouse(World world, EntityLivingBase entity)
	{
        double d = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);
        CREEPSFxBubbles creepsfxbubbles = new CREEPSFxBubbles(world, entity.posX + d * 0.10000000000000001D, entity.posY + 2D, (entity.posZ - 0.75D) + d1 * 0.5D, MoreCreepsAndWeirdos.partWhite, 1.2F);
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxbubbles);
	}
	public void pee(World world, double posX, double posY, double posZ, float rotationYaw, float modelsize)
	{
        double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);

        for (int i = 0; i < 25; i++)
        {
            CREEPSFxPee creepsfxpee = new CREEPSFxPee(world, posX + d * 0.20000000000000001D, (posY + 0.75D) - (double)((1.0F - modelsize) * 0.8F), posZ + d1 * 0.20000000000000001D, Item.getItemFromBlock(Blocks.cobblestone));
            creepsfxpee.motionX += d * 0.23999999463558197D;
            creepsfxpee.motionZ += d1 * 0.23999999463558197D;
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxpee);
        }
	}
	public boolean isJumpKeyDown()
	{
		return Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode());
	}
}
