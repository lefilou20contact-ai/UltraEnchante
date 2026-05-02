package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class HomingEnchantment extends BaseEnchantment {
    public HomingEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.BOW, 2, EquipmentSlot.MAINHAND); }
    @Override public boolean isTreasure() { return true; }
}
