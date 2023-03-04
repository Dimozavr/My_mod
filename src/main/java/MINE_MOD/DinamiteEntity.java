package MINE_MOD;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DinamiteEntity extends SnowballEntity {

    public DinamiteEntity(EntityType<? extends SnowballEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }
    public DinamiteEntity(World p_i1774_1_, LivingEntity p_i1774_2_) {
        super(p_i1774_1_, p_i1774_2_);
    }
    public DinamiteEntity(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    int livetime = 0;

    @Override
    public void tick() {
        this.livetime ++;
        if (this.livetime > 20 && this.isInWater()){
            TNTEntity Dinamite_Entity = new TNTEntity(this.getCommandSenderWorld(), this.getX(), this.getY(), this.getZ(), (LivingEntity) this.getOwner());
            Dinamite_Entity.level.explode(Dinamite_Entity, Dinamite_Entity.getX(), Dinamite_Entity.getY(0.0625D), Dinamite_Entity.getZ(), 0F, Explosion.Mode.BREAK);
            this.remove();
            return;
        }
        else if (this.livetime > 20){
            TNTEntity Dinamite_Entity = new TNTEntity(this.getCommandSenderWorld(), this.getX(), this.getY(), this.getZ(), (LivingEntity) this.getOwner());
            Dinamite_Entity.level.explode(Dinamite_Entity, Dinamite_Entity.getX(), Dinamite_Entity.getY(0.0625D), Dinamite_Entity.getZ(), 4.0F, Explosion.Mode.BREAK);
            this.remove();
            return;
        }
        super.tick();
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                TileEntity tileentity = this.level.getBlockEntity(blockpos);
                if (tileentity instanceof EndGatewayTileEntity && EndGatewayTileEntity.canEntityTeleport(this)) {
                    ((EndGatewayTileEntity)tileentity).teleportEntity(this);
                }

                flag = true;
            }
        }

        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }

        this.checkInsideBlocks();
        Vector3d vector3d = this.getDeltaMovement();
        double d2 = this.getX() + vector3d.x;
        double d0 = this.getY() + vector3d.y;
        double d1 = this.getZ() + vector3d.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d2 - vector3d.x * 0.25D, d0 - vector3d.y * 0.25D, d1 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        this.setDeltaMovement(vector3d.scale((double)f));
        if (!this.isNoGravity()) {
            Vector3d vector3d1 = this.getDeltaMovement();
            this.setDeltaMovement(vector3d1.x, vector3d1.y - (double)this.getGravity(), vector3d1.z);
        }

        this.setPos(d2, d0, d1);
    }

    @Override
    protected void onHit(RayTraceResult traceResult) {
        super.onHit(traceResult);
        if (!this.level.isClientSide && this.isInWater()) {
            this.level.broadcastEntityEvent(this, (byte)3);
            TNTEntity Dinamite_Entity = new TNTEntity(this.getCommandSenderWorld(), traceResult.getLocation().x, traceResult.getLocation().y, traceResult.getLocation().z, (LivingEntity) this.getOwner());
            Dinamite_Entity.level.explode(Dinamite_Entity, Dinamite_Entity.getX(), Dinamite_Entity.getY(0.0625D), Dinamite_Entity.getZ(), 0F, Explosion.Mode.BREAK);
            this.remove();
        } else if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            TNTEntity Dinamite_Entity = new TNTEntity(this.getCommandSenderWorld(), traceResult.getLocation().x, traceResult.getLocation().y, traceResult.getLocation().z, (LivingEntity) this.getOwner());
            Dinamite_Entity.level.explode(Dinamite_Entity, Dinamite_Entity.getX(), Dinamite_Entity.getY(0.0625D), Dinamite_Entity.getZ(), 4.0F, Explosion.Mode.BREAK);
            this.remove();
        }
    }

}