package com.ultraenchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

// ═══════════════════════════════════════════════════════════════
//  ENCHANTEMENTS D'ÉPÉE
// ═══════════════════════════════════════════════════════════════

/**
 * LIFESTEAL (Niv. 1-5)
 * Vole un % de vie par coup. Géré via LivingEntityMixin.
 * Niv 1 = 5% | Niv 5 = 25%
 */
public class LifestealEnchantment extends BaseEnchantment {
    public LifestealEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, 5, EquipmentSlot.MAINHAND);
    }
    @Override public boolean isTreasure() { return false; }
}

/**
 * THUNDERSTRIKE (Niv. 1-3)
 * Chance de faire tomber la foudre sur la cible.
 * Niv 1=10% | Niv 2=20% | Niv 3=30%
 */
public class ThunderstrikeEnchantment extends BaseEnchantment {
    public ThunderstrikeEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * SOULREND (Niv. 1-4)
 * Inflige des dégâts supplémentaires aux mobs non-morts et aux joueurs.
 * Dégâts : 1.5 * niveau
 */
public class SoulrendEnchantment extends BaseEnchantment {
    public SoulrendEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, 4, EquipmentSlot.MAINHAND);
    }
    // Compatible avec Sharpness mais pas Smite/Bane
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.DamageEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * BERSERKER (Niv. 1-3)
 * Plus vous avez perdu de vie, plus vos dégâts augmentent.
 * À 50% de vie : +20% dégâts par niveau
 * Géré via LivingEntityMixin.
 */
public class BerserkerEnchantment extends BaseEnchantment {
    public BerserkerEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * VENOM_BLADE (Niv. 1-3)
 * Applique du poison amplified à chaque coup.
 * Durée : 5s * niveau, Amplificateur : niveau
 */
public class VenomBladeEnchantment extends BaseEnchantment {
    public VenomBladeEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * EXECUTE (Niv. 1-4)
 * Si la cible a moins de 25% de vie : dégâts * (1 + 0.5 * niveau).
 * Puissant contre les boss !
 */
public class ExecuteEnchantment extends BaseEnchantment {
    public ExecuteEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, 4, EquipmentSlot.MAINHAND);
    }
    @Override public boolean isTreasure() { return true; }
}

/**
 * VAMPIRISM (Niv. 1-3)
 * Soigne le porteur de regeneration pendant le combat.
 * Niv 1 = Regen I 3s | Niv 3 = Regen III 5s
 */
public class VampirismEnchantment extends BaseEnchantment {
    public VampirismEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof LifestealEnchantment) return false; // Incompatible avec Lifesteal
        return super.canAccept(other);
    }
}

/**
 * BLEED (Niv. 1-3)
 * Applique un effet de saignement : ralentissement + particules de dégâts.
 */
public class BleedEnchantment extends BaseEnchantment {
    public BleedEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * GRAVITON (Niv. 1-3)
 * Repousse violemment l'ennemi dans les airs à chaque coup.
 */
public class GravitonEnchantment extends BaseEnchantment {
    public GravitonEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * INFERNO (Niv. 1-4)
 * Embrase l'ennemi plus longtemps que Fire Aspect.
 * Durée : 4s * niveau
 */
public class InfernoEnchantment extends BaseEnchantment {
    public InfernoEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, 4, EquipmentSlot.MAINHAND);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.FireAspectEnchantment) return false;
        return super.canAccept(other);
    }
}
