package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class SmeltingEnchantment extends BaseEnchantment {
    public SmeltingEnchantment() { super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, 1, EquipmentSlot.MAINHAND); }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.SilkTouchEnchantment) return false;
        return super.canAccept(other);
    }
}
