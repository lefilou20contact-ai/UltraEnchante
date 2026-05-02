package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class VampirismEnchantment extends BaseEnchantment {
    public VampirismEnchantment() { super(Rarity.RARE, EnchantmentTarget.WEAPON, 3, EquipmentSlot.MAINHAND); }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof LifestealEnchantment) return false;
        return super.canAccept(other);
    }
}
