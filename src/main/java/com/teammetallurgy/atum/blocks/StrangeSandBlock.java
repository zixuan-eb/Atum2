package com.teammetallurgy.atum.blocks;

import com.teammetallurgy.atum.init.AtumBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;

public class StrangeSandBlock extends FallingBlock {

    public StrangeSandBlock() {
        super(Block.Properties.create(Material.SAND).hardnessAndResistance(0.5F).sound(SoundType.SAND).tickRandomly().harvestTool(ToolType.SHOVEL).harvestLevel(0));
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull IBlockReader world, BlockPos pos, @Nonnull Direction direction, IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.offset(direction));
        PlantType plantType = plantable.getPlantType(world, pos.up());
        boolean hasWater = (world.getBlockState(pos.east()).getFluidState().isTagged(FluidTags.WATER) ||
                world.getBlockState(pos.west()).getFluidState().isTagged(FluidTags.WATER)||
                world.getBlockState(pos.north()).getFluidState().isTagged(FluidTags.WATER) ||
                world.getBlockState(pos.south()).getFluidState().isTagged(FluidTags.WATER));

        if (plant.getBlock() instanceof CactusBlock || plant.getBlock() == AtumBlocks.ANPUTS_FINGERS) {
            return true;
        }

        if (plantType.equals(PlantType.DESERT)) {
            return true;
        } else if (plantType.equals(PlantType.BEACH)) {
            return hasWater;
        } else {
            return super.canSustainPlant(state, world, pos, direction, plantable);
        }
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        return toolType == ToolType.SHOVEL ? AtumBlocks.STRANGE_SAND_PATH.getDefaultState() : super.getToolModifiedState(state, world, pos, player, stack, toolType);
    }
}