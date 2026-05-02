package com.ultraenchants.mixin;

import com.ultraenchants.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin {

    /**
     * Gestion des effets à l'impact d'une flèche.
     * - EXPLOSIVE_ARROW : explosion
     * - WITHER_ARROW : effet wither
     * - ICESHOT : gel de la cible
     * - CHAIN_LIGHTNING : foudre en chaîne
     * - SNIPER : dégâts de distance
     */
    @Inject(method = "onEntityHit", at = @At("TAIL"))
    private void onEntityHit(EntityHitResult hitResult, CallbackInfo ci) {
        ArrowEntity arrow = (ArrowEntity) (Object) this;
        if (!(arrow.getOwner() instanceof PlayerEntity shooter)) return;
        if (!(hitResult.getEntity() instanceof LivingEntity target)) return;

        ItemStack bow = shooter.getMainHandStack();
        // Essaie aussi l'offhand (arbalète)
        if (!bow.getItem().toString().contains("bow") && !bow.getItem().toString().contains("crossbow")) {
            bow = shooter.getOffHandStack();
        }

        // === EXPLOSIVE_ARROW ===
        int explosiveLvl = EnchantmentHelper.getLevel(ModEnchantments.EXPLOSIVE_ARROW, bow);
        if (explosiveLvl > 0) {
            float radius = 1.5f + 0.5f * explosiveLvl;
            arrow.getWorld().createExplosion(
                    arrow, target.getX(), target.getY(), target.getZ(),
                    radius, false, Explosion.DestructionType.KEEP
            );
        }

        // === WITHER_ARROW ===
        int witherLvl = EnchantmentHelper.getLevel(ModEnchantments.WITHER_ARROW, bow);
        if (witherLvl > 0) {
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.WITHER, 100 * witherLvl, 1));
        }

        // === ICESHOT ===
        int iceLvl = EnchantmentHelper.getLevel(ModEnchantments.ICESHOT, bow);
        if (iceLvl > 0) {
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SLOWNESS, 60 * iceLvl, 2 + iceLvl));
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.MINING_FATIGUE, 60 * iceLvl, 0));
            if (arrow.getWorld() instanceof ServerWorld sw) {
                sw.spawnParticles(ParticleTypes.SNOWFLAKE,
                        target.getX(), target.getY() + 1, target.getZ(),
                        20, 0.5, 0.5, 0.5, 0.1);
            }
        }

        // === CHAIN_LIGHTNING (arc) ===
        int chainLvl = EnchantmentHelper.getLevel(ModEnchantments.CHAIN_LIGHTNING, bow);
        if (chainLvl > 0 && arrow.getWorld() instanceof ServerWorld sw) {
            var nearby = arrow.getWorld().getEntitiesByClass(
                    LivingEntity.class,
                    target.getBoundingBox().expand(6 + 2.0 * chainLvl),
                    e -> e != target && e != shooter
            );
            int hit = 0;
            for (LivingEntity e : nearby) {
                if (hit >= 3 * chainLvl) break;
                LightningEntity bolt = EntityType.LIGHTNING_BOLT.create(arrow.getWorld());
                if (bolt != null) {
                    bolt.refreshPositionAfterTeleport(e.getX(), e.getY(), e.getZ());
                    bolt.setCosmetic(true); // visuellement
                    sw.spawnEntity(bolt);
                    e.damage(arrow.getWorld().getDamageSources().generic(), 4 * chainLvl);
                    hit++;
                }
            }
        }

        // === SNIPER : bonus dégâts selon distance ===
        int sniperLvl = EnchantmentHelper.getLevel(ModEnchantments.SNIPER, bow);
        if (sniperLvl > 0) {
            double distance = shooter.getPos().distanceTo(target.getPos());
            if (distance > 10) {
                float bonusDmg = (float)(distance * 0.05f * sniperLvl);
                target.damage(arrow.getWorld().getDamageSources().generic(), bonusDmg);
                if (arrow.getWorld() instanceof ServerWorld sw) {
                    sw.spawnParticles(ParticleTypes.CRIT,
                            target.getX(), target.getY() + 1, target.getZ(),
                            8, 0.3, 0.3, 0.3, 0.3);
                }
            }
        }
    }

    /**
     * HOMING : la flèche suit la cible la plus proche
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ArrowEntity arrow = (ArrowEntity) (Object) this;
        if (arrow.getWorld().isClient()) return;
        if (!(arrow.getOwner() instanceof PlayerEntity shooter)) return;

        ItemStack bow = shooter.getMainHandStack();
        int homingLvl = EnchantmentHelper.getLevel(ModEnchantments.HOMING, bow);
        if (homingLvl == 0) return;

        // Trouve la cible la plus proche dans un rayon
        double searchRadius = 8.0 * homingLvl;
        var nearest = arrow.getWorld().getEntitiesByClass(
                LivingEntity.class,
                arrow.getBoundingBox().expand(searchRadius),
                e -> e != shooter && e.isAlive()
        ).stream()
                .min((a, b) -> Double.compare(
                        a.squaredDistanceTo(arrow),
                        b.squaredDistanceTo(arrow)
                ));

        nearest.ifPresent(target -> {
            var dir = target.getPos().add(0, target.getHeight() / 2, 0)
                    .subtract(arrow.getPos()).normalize();
            var vel = arrow.getVelocity();
            var newVel = vel.add(dir.multiply(0.05 * homingLvl)).normalize()
                    .multiply(vel.length());
            arrow.setVelocity(newVel);
        });
    }
}
