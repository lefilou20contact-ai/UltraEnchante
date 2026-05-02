package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class InfernoEnchantment extends BaseEnchantment {
    public InfernoEnchantment() { super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, 4, EquipmentSlot.MAINHAND); }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.FireAspectEnchantment) return false;
        return super.canAccept(other);
    }
}
