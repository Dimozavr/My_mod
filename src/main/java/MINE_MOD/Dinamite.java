package MINE_MOD;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Dinamite extends Item{
    public Dinamite(){
        super(new Item.Properties().tab(ItemGroup.TAB_COMBAT));
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        p_77659_1_.playSound((PlayerEntity)null, p_77659_2_.getX(), p_77659_2_.getY(), p_77659_2_.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!p_77659_1_.isClientSide) {
            DinamiteEntity snowballentity = new DinamiteEntity(p_77659_1_, p_77659_2_);
            snowballentity.setItem(itemstack);
            snowballentity.shootFromRotation(p_77659_2_, p_77659_2_.xRot, p_77659_2_.yRot, 0.0F, 1.5F, 1.0F);
            p_77659_1_.addFreshEntity(snowballentity);
        }

        p_77659_2_.awardStat(Stats.ITEM_USED.get(this));
        if (!p_77659_2_.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
    }
}
