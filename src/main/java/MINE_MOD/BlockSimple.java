package MINE_MOD;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSimple extends Block
{
    public BlockSimple()
    {
        super(Block.Properties.of(Material.STONE)  // look at Block.Properties for further options
                // typically useful: hardnessAndResistance(), harvestLevel(), harvestTool()
        );
    }

    // render using a BakedModel (mbe01_block_simple.json --> mbe01_block_simple_model.json)
    // not strictly required because the default (super method) is MODEL.
}

