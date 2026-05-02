package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class AngelEnchantment extends BaseEnchantment {
    public AngelEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, 3, EquipmentSlot.CHEST); }
    @Override public boolean isTreasure() { return true; }
}
