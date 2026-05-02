package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class LifestealEnchantment extends BaseEnchantment {
    public LifestealEnchantment() { super(Rarity.RARE, EnchantmentTarget.WEAPON, 5, EquipmentSlot.MAINHAND); }
    @Override public boolean isTreasure() { return false; }
}
