package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class VeinminerEnchantment extends BaseEnchantment {
    public VeinminerEnchantment() { super(Rarity.RARE, EnchantmentTarget.DIGGER, 3, EquipmentSlot.MAINHAND); }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof ExplosiveMineEnchantment) return false;
        return super.canAccept(other);
    }
}
