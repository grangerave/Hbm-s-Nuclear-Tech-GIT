package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileBunkerBuster extends EntityMissileBaseAdvanced {

	public EntityMissileBunkerBuster(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileBunkerBuster(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		for(int i = 0; i < 15; i++)
		{
			this.worldObj.createExplosion(this, this.posX, this.posY - i, this.posZ, 5F, true);
			if(ExplosionNukeGeneric.destruction(worldObj, (int)this.posX, (int)(this.posY-i), (int)this.posZ)>3){
				System.out.println(ExplosionNukeGeneric.destruction(worldObj, (int)this.posX, (int)(this.posY-i), (int)this.posZ));
				return;
			}
			this.worldObj.setBlockToAir((int)this.posX, (int)(this.posY-i), (int)this.posZ);
			this.worldObj.setBlockToAir((int)this.posX+1, (int)(this.posY-i), (int)this.posZ);
			this.worldObj.setBlockToAir((int)this.posX-1, (int)(this.posY-i), (int)this.posZ);
			this.worldObj.setBlockToAir((int)this.posX, (int)(this.posY-i), (int)this.posZ+1);
			this.worldObj.setBlockToAir((int)this.posX, (int)(this.posY-i), (int)this.posZ-1);
		}
		
		ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 5);
		ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 5);
		ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 5);
	}

}
