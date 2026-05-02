package com.ultraenchants.mixin;

import com.ultraenchants.init.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    /**
     * VEINMINER + SMELTING + TITAN_PICK + LUMBER : logique après le minage
     */
    @Inject(method = "postMine", at = @At("TAIL"))
    private void onPostMine(World world, BlockState state, BlockPos pos,
                             PlayerEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (world.isClient() || !(miner instanceof ServerPlayerEntity player)) return;
        ItemStack tool = player.getMainHandStack();

        // === VEINMINER ===
        int veinLvl = EnchantmentHelper.getLevel(ModEnchantments.VEINMINER, tool);
        if (veinLvl > 0) {
            int maxBlocks = 8 * veinLvl;
            Set<BlockPos> visited = new HashSet<>();
            Queue<BlockPos> queue = new LinkedList<>();
            queue.add(pos);
            int count = 0;

            while (!queue.isEmpty() && count < maxBlocks) {
                BlockPos current = queue.poll();
                if (visited.contains(current)) continue;
                visited.add(current);

                for (BlockPos neighbor : getNeighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        BlockState neighborState = world.getBlockState(neighbor);
                        if (neighborState.getBlock() == state.getBlock()) {
                            Block.dropStacks(neighborState, (ServerWorld) world, neighbor, null, player, tool);
                            world.removeBlock(neighbor, false);
                            queue.add(neighbor);
                            count++;
                        }
                    }
                }
            }
        }

        // === LUMBER : coupe tout l'arbre ===
        int lumberLvl = EnchantmentHelper.getLevel(ModEnchantments.LUMBER, tool);
        if (lumberLvl > 0) {
            String blockName = Registries.BLOCK.getId(state.getBlock()).getPath();
            if (blockName.contains("_log") || blockName.contains("_stem")) {
                int maxLogs = 32 * lumberLvl;
                Set<BlockPos> visited = new HashSet<>();
                Queue<BlockPos> queue = new LinkedList<>();
                queue.add(pos);
                int count = 0;

                while (!queue.isEmpty() && count < maxLogs) {
                    BlockPos current = queue.poll();
                    if (visited.contains(current)) continue;
                    visited.add(current);

                    // Cherche en haut et côtés
                    for (BlockPos neighbor : getNeighborsAndUp(current)) {
                        if (!visited.contains(neighbor)) {
                            BlockState ns = world.getBlockState(neighbor);
                            String neighborName = Registries.BLOCK.getId(ns.getBlock()).getPath();
                            if (neighborName.contains("_log") || neighborName.contains("_stem")
                                    || ns.getBlock() instanceof LeavesBlock) {
                                Block.dropStacks(ns, (ServerWorld) world, neighbor, null, player, tool);
                                world.removeBlock(neighbor, false);
                                queue.add(neighbor);
                                count++;
                            }
                        }
                    }
                }
            }
        }

        // === SMELTING : fond les drops automatiquement ===
        int smeltLvl = EnchantmentHelper.getLevel(ModEnchantments.SMELTING, tool);
        if (smeltLvl > 0 && world instanceof ServerWorld sw) {
            // Les drops ont déjà été placés par minecraft — on cherche les ItemEntity proches
            // et on essaie de les fondre selon les recettes de furnace
            var drops = world.getEntitiesByClass(
                    net.minecraft.entity.ItemEntity.class,
                    new net.minecraft.util.math.Box(pos).expand(2),
                    e -> true
            );
            for (var drop : drops) {
                ItemStack dropStack = drop.getStack();
                // Cherche la recette de fonte
                Optional<net.minecraft.recipe.SmeltingRecipe> recipe = sw.getRecipeManager()
                        .getFirstMatch(RecipeType.SMELTING,
                                new net.minecraft.inventory.SimpleInventory(dropStack),
                                world);
                recipe.ifPresent(r -> {
                    ItemStack result = r.getOutput(world.getRegistryManager()).copy();
                    result.setCount(dropStack.getCount());
                    drop.setStack(result);
                });
            }
        }

        // === REPLANT : replante les cultures ===
        int replantLvl = EnchantmentHelper.getLevel(ModEnchantments.REPLANT, tool);
        if (replantLvl > 0) {
            if (state.getBlock() instanceof CropBlock crop) {
                if (crop.isMature(state)) {
                    world.setBlockState(pos, crop.withAge(0));
                }
            }
        }
    }

    private List<BlockPos> getNeighbors(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>();
        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++)
                for (int z = -1; z <= 1; z++)
                    if (x != 0 || y != 0 || z != 0)
                        neighbors.add(pos.add(x, y, z));
        return neighbors;
    }

    private List<BlockPos> getNeighborsAndUp(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>();
        // 6 directions + haut prioritaire
        neighbors.add(pos.up());
        neighbors.add(pos.north());
        neighbors.add(pos.south());
        neighbors.add(pos.east());
        neighbors.add(pos.west());
        neighbors.add(pos.north().up());
        neighbors.add(pos.south().up());
        neighbors.add(pos.east().up());
        neighbors.add(pos.west().up());
        return neighbors;
    }
}
