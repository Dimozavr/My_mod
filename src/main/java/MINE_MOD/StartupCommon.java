package MINE_MOD;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {
    public static BlockSimple blockSimple;  // this holds the unique instance of your block
    public static BlockItem itemBlockSimple;  // this holds the unique instance of the ItemBlock corresponding to your block
    public static Dinamite dinamite;
    public static GunPowderBlock gunpowder;
    public static BlockItem itemGunpowder;

    @SubscribeEvent
    public static void onBlocksRegistration(final RegistryEvent.Register<Block> blockRegisterEvent) {
        blockSimple = (BlockSimple) (new BlockSimple().setRegistryName("minemod", "pig_block"));
        blockRegisterEvent.getRegistry().register(blockSimple);
        gunpowder = (GunPowderBlock) (new GunPowderBlock().setRegistryName("minemod", "gunpowder_block"));
        blockRegisterEvent.getRegistry().register(gunpowder);
    }

    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
        // We need to create a BlockItem so the player can carry this block in their hand and it can appear in the inventory
        final int MAXIMUM_STACK_SIZE = 64;  // player can only hold 20 of this block in their hand at once

        Item.Properties itemSimpleProperties = new Item.Properties()
                .stacksTo(MAXIMUM_STACK_SIZE)
                .tab(ItemGroup.TAB_BUILDING_BLOCKS);
        dinamite = new Dinamite();
        dinamite.setRegistryName("pig_dinamite");
        itemRegisterEvent.getRegistry().register(dinamite);

        itemBlockSimple = new BlockItem(blockSimple, itemSimpleProperties);
        itemBlockSimple.setRegistryName("pig_block");
        itemRegisterEvent.getRegistry().register(itemBlockSimple);

        itemGunpowder = new BlockItem(gunpowder, itemSimpleProperties);
        itemGunpowder.setRegistryName("gunpowder_block");
        itemRegisterEvent.getRegistry().register(itemGunpowder);
    }

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        // not actually required for this example....
    }

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> entityTypeRegisterEvent) {
        EntityType<DinamiteEntity> DinamiteEntityType = EntityType.Builder.<DinamiteEntity>of(
                        DinamiteEntity::new, EntityClassification.MISC)
                .build("minemod:dinamite_entity");
        DinamiteEntityType.setRegistryName("minemod:dinamite_entity");
        entityTypeRegisterEvent.getRegistry().register(DinamiteEntityType);
    }
}