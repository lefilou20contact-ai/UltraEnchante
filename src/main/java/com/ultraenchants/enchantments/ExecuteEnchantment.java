package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class ExecuteEnchantment extends BaseEnchantment {
    public ExecuteEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, 4, EquipmentSlot.MAINHAND); }
    @Override public boolean isTreasure() { return true; }
}
