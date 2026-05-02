package com.ultraenchants.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

// ═══════════════════════════════════════════════════════════════
//  ENCHANTEMENTS DE BOTTES
// ═══════════════════════════════════════════════════════════════

/**
 * SPRING (Niv. 1-4)
 * Augmente la hauteur du saut.
 * Niv 1 = Jump Boost I | Niv 4 = Jump Boost IV
 * Aussi réduit les dégâts de chute de 25% par niveau.
 */
public class SpringEnchantment extends BaseEnchantment {
    public SpringEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, 4, EquipmentSlot.FEET);
    }
}

/**
 * LAVA_WALKER (Niv. 1-2)
 * Permet de marcher sur la lave (transforme en obsidienne ou croûte).
 * Niv 1 = obsidienne temporaire | Niv 2 = croûte solide durable
 * Incompatible avec Frost Walker.
 */
public class LavaWalkerEnchantment extends BaseEnchantment {
    public LavaWalkerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, 2, EquipmentSlot.FEET);
    }
    @Override public boolean isTreasure() { return true; }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.FrostWalkerEnchantment) return false;
        return super.canAccept(other);
    }
}

/**
 * DASH (Niv. 1-3)
 * Double-tap shift pour effectuer un dash rapide dans la direction du regard.
 * Cooldown : 5s / niveau
 * Géré via PlayerEntityMixin (key input).
 */
public class DashEnchantment extends BaseEnchantment {
    public DashEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, 3, EquipmentSlot.FEET);
    }
}

/**
 * ANTI_GRAVITY (Niv. 1-2)
 * Annule complètement les dégâts de chute.
 * Niv 2 = aussi immunité aux dégâts d'ender pearl.
 */
public class AntiGravityEnchantment extends BaseEnchantment {
    public AntiGravityEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, 2, EquipmentSlot.FEET);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.ProtectionEnchantment) return false;
        return super.canAccept(other);
    }
}

// ═══════════════════════════════════════════════════════════════
//  ENCHANTEMENTS SPÉCIAUX (tous items)
// ═══════════════════════════════════════════════════════════════

/**
 * CURSE_DECAY (Niv. 1)
 * MALÉDICTION : L'item se dégrade 3x plus vite.
 * Peut s'obtenir en tableaux d'enchantement comme toute malédiction.
 */
public class CurseDecayEnchantment extends BaseEnchantment {
    public CurseDecayEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, 1,
                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET);
    }
    @Override public boolean isCursed() { return true; }
    @Override public boolean isTreasure() { return false; }
}

/**
 * SOULBOUND (Niv. 1)
 * L'item reste dans l'inventaire à la mort (ne drop pas).
 * Extrêmement rare, uniquement via commerce ou loot spécial.
 */
public class SoulboundEnchantment extends BaseEnchantment {
    public SoulboundEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, 1,
                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET);
    }
    @Override public boolean isTreasure() { return true; }
    @Override public boolean isAvailableForEnchantedBookOffer() { return false; }
    @Override public boolean isAvailableForRandomSelection() { return false; }
}
