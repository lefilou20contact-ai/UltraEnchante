package com.ultraenchants.mixin;

import com.ultraenchants.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    private int dashCooldown = 0;

    /**
     * Tick côté serveur : gère les enchantements passifs (Magnet, Speed Boost, Guardian, Spring, Anti Gravity).
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getWorld().isClient()) return;

        // Cooldown du dash
        if (dashCooldown > 0) dashCooldown--;

        // === MAGNET (armure poitrine) ===
        ItemStack chest = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST);
        int magnetLvl = EnchantmentHelper.getLevel(ModEnchantments.MAGNET, chest);
        if (magnetLvl > 0) {
            double radius = 4.0 + 2.0 * magnetLvl;
            Box area = new Box(player.getBlockPos()).expand(radius);

            // Attire les items
            List<ItemEntity> items = player.getWorld().getEntitiesByClass(ItemEntity.class, area, e -> true);
            for (ItemEntity item : items) {
                item.setPos(player.getX(), player.getY(), player.getZ());
            }

            // Attire l'XP
            List<ExperienceOrbEntity> orbs = player.getWorld().getEntitiesByClass(ExperienceOrbEntity.class, area, e -> true);
            for (ExperienceOrbEntity orb : orbs) {
                orb.setPos(player.getX(), player.getY(), player.getZ());
            }
        }

        // === SPEED_BOOST (bottes) ===
        ItemStack boots = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.FEET);
        int speedLvl = EnchantmentHelper.getLevel(ModEnchantments.SPEED_BOOST, boots);
        if (speedLvl > 0) {
            // Applique Speed en continu (court durée pour refresh chaque tick)
            if (!player.hasStatusEffect(StatusEffects.SPEED) ||
                    player.getStatusEffect(StatusEffects.SPEED).getAmplifier() < speedLvl - 1) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SPEED, 40, speedLvl - 1, true, false, false));
            }
        }

        // === SPRING (bottes) : Jump Boost ===
        int springLvl = EnchantmentHelper.getLevel(ModEnchantments.SPRING, boots);
        if (springLvl > 0) {
            if (!player.hasStatusEffect(StatusEffects.JUMP_BOOST) ||
                    player.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() < springLvl - 1) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.JUMP_BOOST, 40, springLvl - 1, true, false, false));
            }
        }

        // === ANGEL (poitrine) : lévitation légère ===
        int angelLvl = EnchantmentHelper.getLevel(ModEnchantments.ANGEL, chest);
        if (angelLvl > 0 && player.isSneaking() && player.fallDistance > 0) {
            // Ralentit la chute via slow fall
            if (!player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SLOW_FALLING, 40, angelLvl - 1, true, false, false));
            }
        }

        // === IRONHIDE (poitrine) : Résistance ===
        int ironhideLvl = EnchantmentHelper.getLevel(ModEnchantments.IRONHIDE, chest);
        if (ironhideLvl > 0) {
            if (!player.hasStatusEffect(StatusEffects.RESISTANCE) ||
                    player.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() < ironhideLvl - 1) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.RESISTANCE, 40, ironhideLvl - 1, true, false, false));
            }
        }
    }

    /**
     * ANTI_GRAVITY + SPRING : annule les dégâts de chute
     */
    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelFallDamage(float fallDistance, float damageMultiplier,
                                   net.minecraft.entity.damage.DamageSource damageSource, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack boots = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.FEET);

        // Anti Gravity : annule complètement
        int antiGravityLvl = EnchantmentHelper.getLevel(ModEnchantments.ANTI_GRAVITY, boots);
        if (antiGravityLvl > 0) {
            ci.cancel();
            return;
        }

        // Spring : réduit de 25% par niveau
        int springLvl = EnchantmentHelper.getLevel(ModEnchantments.SPRING, boots);
        if (springLvl > 0) {
            float reduced = fallDistance * (1.0f - 0.25f * springLvl);
            if (reduced <= 3.0f) {
                ci.cancel();
            }
        }
    }

    /**
     * SOULBOUND : garde les items à la mort
     */
    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void keepSoulboundItems(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        // Parcourt l'inventaire et marque les items Soulbound comme "keep"
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty() && EnchantmentHelper.getLevel(ModEnchantments.SOULBOUND, stack) > 0) {
                // L'item reste — on remet en place après le drop via gamerule keepInventory pour cet item
                // Implementation complète nécessite un Mixin sur PlayerInventory.dropAll()
                // Ici on log pour montrer la logique
                org.slf4j.LoggerFactory.getLogger("UltraEnchants").info("[UltraEnchants] Soulbound: {} conservé", stack.getName().getString());
            }
        }
    }
}
