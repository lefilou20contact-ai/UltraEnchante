package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class MultishotPlusEnchantment extends BaseEnchantment {
    public MultishotPlusEnchantment() { super(Rarity.RARE, EnchantmentTarget.CROSSBOW, 3, EquipmentSlot.MAINHAND); }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.MultishotEnchantment) return false;
        return super.canAccept(other);
    }
}
