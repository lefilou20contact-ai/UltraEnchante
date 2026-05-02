package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class ExplosiveArrowEnchantment extends BaseEnchantment {
    public ExplosiveArrowEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.BOW, 3, EquipmentSlot.MAINHAND); }
    @Override public boolean isTreasure() { return true; }
}
