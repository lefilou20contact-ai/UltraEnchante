package com.ultraenchants.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

// ═══════════════════════════════════════════════════════════════
//  ENCHANTEMENTS D'ARMURE
// ═══════════════════════════════════════════════════════════════

/**
 * THORNS_PLUS (Niv. 1-5)
 * Renvoie 10% * niveau des dégâts reçus à l'attaquant.
 * Version bien plus puissante que le Thorns vanilla.
 */
public class ThornsPlusEnchantment extends BaseEnchantment {
    public ThornsPlusEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, 5,
                EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.ThornsEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * GUARDIAN (Niv. 1-3)
 * Réduit tous les dégâts de 5% * niveau (pas limité à un type de dégât).
 * Géré via LivingEntityMixin.
 */
public class GuardianEnchantment extends BaseEnchantment {
    public GuardianEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR, 3,
                EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD);
    }
}

/**
 * ANGEL (Niv. 1-3)
 * Ralentit chute, permet de planer brièvement en maintenant espace.
 * Niv 1 = chute lente | Niv 3 = quasi lévitation
 */
public class AngelEnchantment extends BaseEnchantment {
    public AngelEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, 3, EquipmentSlot.CHEST);
    }
    @Override public boolean isTreasure() { return true; }
}

/**
 * MAGNET (Niv. 1-3)
 * Attire automatiquement les items et l'XP dans un rayon.
 * Rayon : 4 + 2 * niveau blocs
 * Géré via PlayerEntityMixin (tick).
 */
public class MagnetEnchantment extends BaseEnchantment {
    public MagnetEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, 3, EquipmentSlot.CHEST);
    }
}

/**
 * SPEED_BOOST (Niv. 1-3)
 * Donne Speed I/II/III permanent tant que l'armure est portée.
 * Géré via PlayerEntityMixin (tick).
 */
public class SpeedBoostEnchantment extends BaseEnchantment {
    public SpeedBoostEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, 3, EquipmentSlot.FEET);
    }
}

/**
 * IRONHIDE (Niv. 1-4)
 * Augmente la résistance physique selon le niveau.
 * Niv 1 = Résistance I | Niv 4 = Résistance IV
 */
public class IronhideEnchantment extends BaseEnchantment {
    public IronhideEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_CHEST, 4, EquipmentSlot.CHEST);
    }
}

/**
 * REFLECT (Niv. 1-3)
 * Chance de renvoyer une flèche à l'attaquant.
 * Niv 1=15% | Niv 2=30% | Niv 3=50%
 */
public class ReflectEnchantment extends BaseEnchantment {
    public ReflectEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_CHEST, 3, EquipmentSlot.CHEST);
    }
}

/**
 * WITHER_PROOF (Niv. 1)
 * Immunité complète contre l'effet Wither.
 */
public class WitherProofEnchantment extends BaseEnchantment {
    public WitherProofEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, 1, EquipmentSlot.CHEST);
    }
    @Override public boolean isTreasure() { return true; }
}

/**
 * UNDYING_PLUS (Niv. 1-3)
 * Version améliorée de Totem of Undying.
 * Peut sauver la vie plusieurs fois par jour (cooldown : 20min / niveau).
 * Niv 1 = soigne à 2HP | Niv 3 = soigne à 10HP + effets positifs
 */
public class UndyingPlusEnchantment extends BaseEnchantment {
    public UndyingPlusEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, 3, EquipmentSlot.CHEST);
    }
    @Override public boolean isTreasure() { return true; }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        return super.canAccept(other);
    }
}
