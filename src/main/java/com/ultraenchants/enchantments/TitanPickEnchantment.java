package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class TitanPickEnchantment extends BaseEnchantment {
    public TitanPickEnchantment() { super(Rarity.RARE, EnchantmentTarget.DIGGER, 5, EquipmentSlot.MAINHAND); }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.EfficiencyEnchantment) return false;
        return super.canAccept(other);
    }
}
