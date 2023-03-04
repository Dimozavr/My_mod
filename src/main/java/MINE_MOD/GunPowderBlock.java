package MINE_MOD;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GunPowderBlock extends SnowBlock {
    public GunPowderBlock(){
        super(Properties.of(Material.TOP_SNOW).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW));
    }
    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        if (p_225542_2_.getBrightness(LightType.BLOCK, p_225542_3_) > 11) {
            dropResources(p_225542_1_, p_225542_2_, p_225542_3_);
            p_225542_2_.removeBlock(p_225542_3_, false);
        }

    }
    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = p_225533_4_.getItemInHand(p_225533_5_);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
        } else {
            catchFire(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_6_.getDirection(), p_225533_4_);
            p_225533_2_.setBlock(p_225533_3_, Blocks.AIR.defaultBlockState(), 11);
            TNTEntity Explode_Entity = new TNTEntity(p_225533_2_, p_225533_3_.getX(), p_225533_3_.getY(), p_225533_3_.getZ(), p_225533_4_);
            Explode_Entity.level.explode(Explode_Entity, Explode_Entity.getX(), Explode_Entity.getY(0.0625D), Explode_Entity.getZ(), 2.0F, Explosion.Mode.BREAK);
            if (!p_225533_4_.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, p_225533_4_, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(p_225533_5_);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return ActionResultType.sidedSuccess(p_225533_2_.isClientSide);
        }
    }
}