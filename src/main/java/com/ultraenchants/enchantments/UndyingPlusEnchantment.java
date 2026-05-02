package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class UndyingPlusEnchantment extends BaseEnchantment {
    public UndyingPlusEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, 3, EquipmentSlot.CHEST); }
    @Override public boolean isTreasure() { return true; }
}
