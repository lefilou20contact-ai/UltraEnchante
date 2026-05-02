package com.ultraenchants.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

// ═══════════════════════════════════════════════════════════════
//  ENCHANTEMENTS D'ARC / ARBALÈTE
// ═══════════════════════════════════════════════════════════════

/**
 * SNIPER (Niv. 1-3)
 * Les flèches ignorent les variations de trajectoire et font plus de dégâts
 * selon la distance parcourue. +5% dégâts par bloc parcouru par niveau.
 */
public class SniperEnchantment extends BaseEnchantment {
    public SniperEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BOW, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * EXPLOSIVE_ARROW (Niv. 1-3)
 * Les flèches explosent à l'impact.
 * Rayon : 1.5 + 0.5 * niveau blocs.
 */
public class ExplosiveArrowEnchantment extends BaseEnchantment {
    public ExplosiveArrowEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BOW, 3, EquipmentSlot.MAINHAND);
    }
    @Override public boolean isTreasure() { return true; }
}

/**
 * CHAIN_LIGHTNING (Niv. 1-3)
 * La flèche génère de la foudre en chaîne sur jusqu'à 3 * niveau cibles proches.
 */
public class ChainLightningEnchantment extends BaseEnchantment {
    public ChainLightningEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BOW, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * HOMING (Niv. 1-2)
 * Les flèches suivent légèrement la trajectoire vers les mobs proches.
 * Géré via ArrowEntityMixin.
 */
public class HomingEnchantment extends BaseEnchantment {
    public HomingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BOW, 2, EquipmentSlot.MAINHAND);
    }
    @Override public boolean isTreasure() { return true; }
}

/**
 * WITHER_ARROW (Niv. 1-2)
 * Les flèches appliquent l'effet Wither II pendant 5s * niveau.
 */
public class WitherArrowEnchantment extends BaseEnchantment {
    public WitherArrowEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BOW, 2, EquipmentSlot.MAINHAND);
    }
}

/**
 * ICESHOT (Niv. 1-3)
 * Les flèches gèlent la cible (slowness + mining fatigue).
 * Durée : 3s * niveau
 */
public class IceshotEnchantment extends BaseEnchantment {
    public IceshotEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.BOW, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * MULTISHOT_PLUS (Niv. 1-3)
 * Tire N flèches supplémentaires en éventail (N = niveau).
 * Incompatible avec le Multishot vanilla.
 */
public class MultishotPlusEnchantment extends BaseEnchantment {
    public MultishotPlusEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.CROSSBOW, 3, EquipmentSlot.MAINHAND);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.MultishotEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * BOUNCE (Niv. 1-3)
 * Les flèches rebondissent sur les murs et touchent plusieurs cibles.
 * Nombre de rebonds : niveau
 */
public class BounceEnchantment extends BaseEnchantment {
    public BounceEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.BOW, 3, EquipmentSlot.MAINHAND);
    }
}
