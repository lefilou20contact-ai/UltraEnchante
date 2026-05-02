package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class ExplosiveMineEnchantment extends BaseEnchantment {
    public ExplosiveMineEnchantment() { super(Rarity.VERY_RARE, EnchantmentTarget.DIGGER, 3, EquipmentSlot.MAINHAND); }
    @Override public boolean isTreasure() { return true; }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof VeinminerEnchantment) return false;
        if (other instanceof net.minecraft.enchantment.SilkTouchEnchantment) return false;
        return super.canAccept(other);
    }
}
