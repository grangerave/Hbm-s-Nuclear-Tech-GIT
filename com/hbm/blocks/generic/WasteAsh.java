package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class WasteAsh extends BlockSnow {

    public WasteAsh(Material m){
        this.setStepSound(soundTypeSnow);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setTickRandomly(false);
        this.setAshHeight(1);
    }
	
    protected void setAshHeight(int m)
    {
        int j = m & 7;
        float f = (float)(2 * (1 + j)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
	@Override
    public void updateTick(World world, int x, int y, int z, Random r){
		if(MainRegistry.enableAutoCleanup)
    			if(!world.isRemote)
    				world.setBlock(x, y, z, Blocks.dirt);
    }

	@Override
	public void onEntityCollidedWithBlock(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity){
		super.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, entity);
	    	if (entity instanceof EntityLivingBase)
	    	{
	    		if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
	        	{
	        		/*Library.damageSuit(((EntityPlayer)entity), 0);
	        		Library.damageSuit(((EntityPlayer)entity), 1);
	        		Library.damageSuit(((EntityPlayer)entity), 2);
	        		Library.damageSuit(((EntityPlayer)entity), 3);*/
	        		
	        	} else if(entity instanceof EntityCreeper) {
	        		EntityNuclearCreeper creep = new EntityNuclearCreeper(p_149724_1_);
	        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
	        		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
	        		if(!entity.isDead)
	        			if(!p_149724_1_.isRemote)
	        					p_149724_1_.spawnEntityInWorld(creep);
	        		entity.setDead();
	        	} else if(entity instanceof EntityVillager) {
	        		EntityZombie creep = new EntityZombie(p_149724_1_);
	        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
	        		entity.setDead();
	        		if(!p_149724_1_.isRemote)
	        		p_149724_1_.spawnEntityInWorld(creep);
	        	} else if(!(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie)) {
	        		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 2 * 60 * 20, 2));
	        	}
	    		
	    	}
    }
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int x1, float x2, float x3, float x4)
    {
		int l = world.getBlockMetadata(x, y, z) + 1;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
		//this.setAshHeight(l);
        return true;
    }
	
	@Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
		if(super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_)) {
			Block block = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
			return block != ModBlocks.waste_earth && !(block instanceof BlockLeaves) && block != ModBlocks.waste_log && block != ModBlocks.waste_trinitite && block != ModBlocks.waste_trinitite_red  && block != ModBlocks.waste_mycelium;
		}
		return false;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
        int l = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_) + 1;
        p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 0.125F*l, p_149734_4_ + p_149734_5_.nextFloat(), 0.0D, 0.0D, 0.0D);

    }
    
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return ModBlocks.waste_earth.getIcon(1,1);
	}
}
