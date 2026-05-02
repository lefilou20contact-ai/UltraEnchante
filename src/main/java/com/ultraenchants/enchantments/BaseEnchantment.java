package com.ultraenchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Classe de base pour tous les enchantements UltraEnchants.
 * Simplifie la création de nouveaux enchantements.
 */
public abstract class BaseEnchantment extends Enchantment {

    protected final int maxLevel;

    protected BaseEnchantment(Rarity rarity, EnchantmentTarget target, int maxLevel, EquipmentSlot... slots) {
        super(rarity, target, slots);
        this.maxLevel = maxLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }

    /**
     * Par défaut, les enchantements UltraEnchants sont compatibles entre eux,
     * sauf si on surcharge cette méthode.
     */
    @Override
    protected boolean canAccept(Enchantment other) {
        if (other instanceof BaseEnchantment) return true;
        return super.canAccept(other);
    }

    // Utilitaire : récupère le niveau sur un stack
    public int getLevel(ItemStack stack) {
        return net.minecraft.enchantment.EnchantmentHelper.getLevel(this, stack);
    }
}
