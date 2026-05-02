package com.ultraenchants.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

// ═══════════════════════════════════════════════════════════════
//  ENCHANTEMENTS D'OUTILS
// ═══════════════════════════════════════════════════════════════

/**
 * VEINMINER (Niv. 1-3)
 * Casse tous les blocs du même type connectés en une fois.
 * Niv 1=8 blocs | Niv 2=16 blocs | Niv 3=32 blocs
 * Géré via PlayerBlockBreakEvents + récursion BFS.
 */
public class VeinminerEnchantment extends BaseEnchantment {
    public VeinminerEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, 3, EquipmentSlot.MAINHAND);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof ExplosiveMineEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * SMELTING (Niv. 1)
 * Les blocs cassés sont automatiquement fondus (comme Fortune + Fourneau).
 * Ex: Minerai de fer → lingot de fer directement.
 */
public class SmeltingEnchantment extends BaseEnchantment {
    public SmeltingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, 1, EquipmentSlot.MAINHAND);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.SilkTouchEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * MAGNETIC_PICK (Niv. 1-3)
 * Les drops et l'XP sont attirés vers le joueur dans un rayon croissant.
 * Rayon : 4 * niveau blocs
 */
public class MagneticPickEnchantment extends BaseEnchantment {
    public MagneticPickEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, 3, EquipmentSlot.MAINHAND);
    }
}

/**
 * EXPLOSIVE_MINE (Niv. 1-3)
 * Crée une explosion à l'impact qui casse un cube de blocs.
 * Rayon : 1 + niveau blocs (sans dégâts au joueur).
 */
public class ExplosiveMineEnchantment extends BaseEnchantment {
    public ExplosiveMineEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.DIGGER, 3, EquipmentSlot.MAINHAND);
    }
    @Override public boolean isTreasure() { return true; }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof VeinminerEnchantment) return false;
        if (other instanceof net.minecraft.enchantment.SilkTouchEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * LUMBER (Niv. 1-2)
 * Coupe tout l'arbre en un coup (tous les bûches connectées au-dessus).
 * Niv 1=32 blocs | Niv 2=64 blocs
 */
public class LumberEnchantment extends BaseEnchantment {
    public LumberEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, 2, EquipmentSlot.MAINHAND);
    }
}

/**
 * REPLANT (Niv. 1)
 * Replante automatiquement les graines après avoir cassé une culture.
 * Compatible avec Fortune.
 */
public class ReplantEnchantment extends BaseEnchantment {
    public ReplantEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.DIGGER, 1, EquipmentSlot.MAINHAND);
    }
}

/**
 * TITAN_PICK (Niv. 1-5)
 * Incrémente la vitesse de minage massivement.
 * Niv 5 = vitesse de minage ×10
 * Géré via Mining Speed Mixin.
 */
public class TitanPickEnchantment extends BaseEnchantment {
    public TitanPickEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, 5, EquipmentSlot.MAINHAND);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.EfficiencyEnchantment) return false;
        return super.canAccept(other);
    }
}
