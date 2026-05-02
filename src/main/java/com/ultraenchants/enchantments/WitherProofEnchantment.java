package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class WitherProofEnchantment extends BaseEnchantment {
    public WitherProofEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, 1, EquipmentSlot.CHEST); }
    @Override public boolean isTreasure() { return true; }
}
