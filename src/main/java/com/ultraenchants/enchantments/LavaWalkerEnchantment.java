package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class LavaWalkerEnchantment extends BaseEnchantment {
    public LavaWalkerEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, 2, EquipmentSlot.FEET); }
    @Override public boolean isTreasure() { return true; }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.FrostWalkerEnchantment) return false;
        return super.canAccept(other);
    }
}
