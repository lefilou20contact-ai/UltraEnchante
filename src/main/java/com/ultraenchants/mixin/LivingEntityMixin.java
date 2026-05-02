package com.ultraenchants.mixin;

import com.ultraenchants.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    /**
     * Modifie les dégâts selon les enchantements d'épée actifs.
     * - LIFESTEAL : vol de vie
     * - BERSERKER : bonus si PV bas
     * - EXECUTE : bonus si cible < 25% vie
     * - GUARDIAN : réduction des dégâts
     * - UNDYING_PLUS : survie à mort
     */
    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float modifyIncomingDamage(float amount, DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;

        // === GUARDIAN : réduit les dégâts entrants ===
        for (ItemStack armorPiece : self.getArmorItems()) {
            int guardianLvl = EnchantmentHelper.getLevel(ModEnchantments.GUARDIAN, armorPiece);
            if (guardianLvl > 0) {
                amount *= (1.0f - 0.05f * guardianLvl);
            }
        }

        // === REFLECT : chance de renvoyer la flèche ===
        if (source.getAttacker() instanceof LivingEntity attacker) {
            for (ItemStack armorPiece : self.getArmorItems()) {
                int reflectLvl = EnchantmentHelper.getLevel(ModEnchantments.REFLECT, armorPiece);
                if (reflectLvl > 0) {
                    float reflectChance = 0.15f * reflectLvl;
                    if (self.getWorld().getRandom().nextFloat() < reflectChance) {
                        attacker.damage(self.getWorld().getDamageSources().generic(), amount * 0.5f);
                        amount = 0;
                    }
                }
            }
        }

        // === IRONHIDE : résistance physique via armure poitrine ===
        ItemStack chest = self.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST);
        int ironhideLvl = EnchantmentHelper.getLevel(ModEnchantments.IRONHIDE, chest);
        if (ironhideLvl > 0 && source.getAttacker() instanceof LivingEntity) {
            amount *= (1.0f - 0.08f * ironhideLvl);
        }

        return amount;
    }

    /**
     * Après avoir infligé des dégâts — logique de l'attaquant.
     */
    @Inject(method = "damage", at = @At("TAIL"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return;
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(source.getAttacker() instanceof PlayerEntity attacker)) return;

        ItemStack weapon = attacker.getMainHandStack();

        // === LIFESTEAL ===
        int lifestealLvl = EnchantmentHelper.getLevel(ModEnchantments.LIFESTEAL, weapon);
        if (lifestealLvl > 0) {
            float heal = amount * 0.05f * lifestealLvl;
            attacker.heal(heal);
            if (attacker.getWorld() instanceof ServerWorld sw) {
                sw.spawnParticles(ParticleTypes.HEART,
                        attacker.getX(), attacker.getY() + 1.5, attacker.getZ(),
                        3, 0.5, 0.5, 0.5, 0.1);
            }
        }

        // === VAMPIRISM : regen au combat ===
        int vampLvl = EnchantmentHelper.getLevel(ModEnchantments.VAMPIRISM, weapon);
        if (vampLvl > 0) {
            attacker.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.REGENERATION,
                    60 + 40 * vampLvl,
                    vampLvl - 1,
                    true, false
            ));
        }

        // === BERSERKER : plus de dégâts quand PV bas ===
        int berserkerLvl = EnchantmentHelper.getLevel(ModEnchantments.BERSERKER, weapon);
        if (berserkerLvl > 0) {
            float healthPercent = attacker.getHealth() / attacker.getMaxHealth();
            if (healthPercent < 0.5f) {
                float bonus = 1.0f + (0.5f - healthPercent) * 0.4f * berserkerLvl;
                self.damage(attacker.getWorld().getDamageSources().generic(), amount * (bonus - 1));
            }
        }

        // === EXECUTE : bonus dégâts < 25% vie cible ===
        int executeLvl = EnchantmentHelper.getLevel(ModEnchantments.EXECUTE, weapon);
        if (executeLvl > 0) {
            float targetHealth = self.getHealth() / self.getMaxHealth();
            if (targetHealth < 0.25f) {
                float bonusDmg = amount * 0.5f * executeLvl;
                self.damage(attacker.getWorld().getDamageSources().generic(), bonusDmg);
                if (attacker.getWorld() instanceof ServerWorld sw) {
                    sw.spawnParticles(ParticleTypes.CRIT,
                            self.getX(), self.getY() + 1, self.getZ(),
                            10, 0.5, 0.5, 0.5, 0.5);
                }
            }
        }

        // === SOULREND : dégâts bonus vs undead & joueurs ===
        int soulrendLvl = EnchantmentHelper.getLevel(ModEnchantments.SOULREND, weapon);
        if (soulrendLvl > 0) {
            boolean isUndead = self.getType().isIn(net.minecraft.registry.tag.EntityTypeTags.UNDEAD);
            boolean isPlayer = self instanceof PlayerEntity;
            if (isUndead || isPlayer) {
                self.damage(attacker.getWorld().getDamageSources().generic(), 1.5f * soulrendLvl);
            }
        }

        // === CHAIN_LIGHTNING : arc sur les mobs proches ===
        int chainLvl = EnchantmentHelper.getLevel(ModEnchantments.CHAIN_LIGHTNING, weapon);
        if (chainLvl > 0 && attacker.getWorld() instanceof ServerWorld sw) {
            int chainCount = 3 * chainLvl;
            var nearby = attacker.getWorld().getEntitiesByClass(
                    LivingEntity.class,
                    new Box(self.getPos(), self.getPos()).expand(8),
                    e -> e != self && e != attacker
            );
            int hit = 0;
            for (LivingEntity nearby_entity : nearby) {
                if (hit >= chainCount) break;
                nearby_entity.damage(attacker.getWorld().getDamageSources().generic(), 3 * chainLvl);
                sw.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                        nearby_entity.getX(), nearby_entity.getY() + 1, nearby_entity.getZ(),
                        15, 0.3, 0.5, 0.3, 0.2);
                hit++;
            }
        }
    }

    /**
     * WITHER_PROOF : immunité à l'effet wither
     */
    @Inject(method = "canHaveStatusEffect", at = @At("RETURN"), cancellable = true)
    private void cancelWitherEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (effect.getEffectType() == StatusEffects.WITHER) {
            for (ItemStack armor : self.getArmorItems()) {
                if (EnchantmentHelper.getLevel(ModEnchantments.WITHER_PROOF, armor) > 0) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }
    }

    /**
     * UNDYING_PLUS : survie à la mort
     */
    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void undyingPlus(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        ItemStack chest = self.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST);
        int undyingLvl = EnchantmentHelper.getLevel(ModEnchantments.UNDYING_PLUS, chest);

        if (undyingLvl > 0) {
            float healAmount = 2.0f + 4.0f * undyingLvl;
            self.setHealth(healAmount);
            self.clearStatusEffects();
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, undyingLvl));
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, undyingLvl));
            if (undyingLvl >= 3) {
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0));
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 1));
            }
            cir.setReturnValue(true);
        }
    }
}
