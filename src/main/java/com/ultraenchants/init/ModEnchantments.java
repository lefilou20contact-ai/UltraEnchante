package com.ultraenchants.init;

import com.ultraenchants.UltraEnchantsMain;
import com.ultraenchants.enchantments.*;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class ModEnchantments {

    // ═══════════════════════════════════════════
    //  ÉPÉE
    // ═══════════════════════════════════════════
    public static final Enchantment LIFESTEAL        = new LifestealEnchantment();
    public static final Enchantment THUNDERSTRIKE    = new ThunderstrikeEnchantment();
    public static final Enchantment SOULREND         = new SoulrendEnchantment();
    public static final Enchantment BERSERKER        = new BerserkerEnchantment();
    public static final Enchantment VENOM_BLADE      = new VenomBladeEnchantment();
    public static final Enchantment EXECUTE          = new ExecuteEnchantment();
    public static final Enchantment VAMPIRISM        = new VampirismEnchantment();
    public static final Enchantment BLEED            = new BleedEnchantment();
    public static final Enchantment GRAVITON         = new GravitonEnchantment();
    public static final Enchantment INFERNO          = new InfernoEnchantment();

    // ═══════════════════════════════════════════
    //  ARC / ARBALÈTE
    // ═══════════════════════════════════════════
    public static final Enchantment SNIPER           = new SniperEnchantment();
    public static final Enchantment EXPLOSIVE_ARROW  = new ExplosiveArrowEnchantment();
    public static final Enchantment CHAIN_LIGHTNING  = new ChainLightningEnchantment();
    public static final Enchantment HOMING           = new HomingEnchantment();
    public static final Enchantment WITHER_ARROW     = new WitherArrowEnchantment();
    public static final Enchantment ICESHOT          = new IceshotEnchantment();
    public static final Enchantment MULTISHOT_PLUS   = new MultishotPlusEnchantment();
    public static final Enchantment BOUNCE           = new BounceEnchantment();

    // ═══════════════════════════════════════════
    //  ARMURE
    // ═══════════════════════════════════════════
    public static final Enchantment THORNS_PLUS      = new ThornsPlusEnchantment();
    public static final Enchantment GUARDIAN         = new GuardianEnchantment();
    public static final Enchantment ANGEL            = new AngelEnchantment();
    public static final Enchantment MAGNET           = new MagnetEnchantment();
    public static final Enchantment SPEED_BOOST      = new SpeedBoostEnchantment();
    public static final Enchantment IRONHIDE         = new IronhideEnchantment();
    public static final Enchantment REFLECT          = new ReflectEnchantment();
    public static final Enchantment WITHER_PROOF     = new WitherProofEnchantment();
    public static final Enchantment UNDYING_PLUS     = new UndyingPlusEnchantment();

    // ═══════════════════════════════════════════
    //  PIOCHE / OUTILS
    // ═══════════════════════════════════════════
    public static final Enchantment VEINMINER        = new VeinminerEnchantment();
    public static final Enchantment SMELTING         = new SmeltingEnchantment();
    public static final Enchantment MAGNETIC_PICK    = new MagneticPickEnchantment();
    public static final Enchantment EXPLOSIVE_MINE   = new ExplosiveMineEnchantment();
    public static final Enchantment LUMBER           = new LumberEnchantment();
    public static final Enchantment REPLANT          = new ReplantEnchantment();
    public static final Enchantment TITAN_PICK       = new TitanPickEnchantment();

    // ═══════════════════════════════════════════
    //  BOTTES
    // ═══════════════════════════════════════════
    public static final Enchantment SPRING           = new SpringEnchantment();
    public static final Enchantment LAVA_WALKER      = new LavaWalkerEnchantment();
    public static final Enchantment DASH             = new DashEnchantment();
    public static final Enchantment ANTI_GRAVITY     = new AntiGravityEnchantment();

    // ═══════════════════════════════════════════
    //  TOUT ITEM
    // ═══════════════════════════════════════════
    public static final Enchantment CURSE_DECAY      = new CurseDecayEnchantment();
    public static final Enchantment SOULBOUND        = new SoulboundEnchantment();

    public static void registerAll() {
        // Épée
        register("lifesteal",        LIFESTEAL);
        register("thunderstrike",    THUNDERSTRIKE);
        register("soulrend",         SOULREND);
        register("berserker",        BERSERKER);
        register("venom_blade",      VENOM_BLADE);
        register("execute",          EXECUTE);
        register("vampirism",        VAMPIRISM);
        register("bleed",            BLEED);
        register("graviton",         GRAVITON);
        register("inferno",          INFERNO);

        // Arc / Arbalète
        register("sniper",           SNIPER);
        register("explosive_arrow",  EXPLOSIVE_ARROW);
        register("chain_lightning",  CHAIN_LIGHTNING);
        register("homing",           HOMING);
        register("wither_arrow",     WITHER_ARROW);
        register("iceshot",          ICESHOT);
        register("multishot_plus",   MULTISHOT_PLUS);
        register("bounce",           BOUNCE);

        // Armure
        register("thorns_plus",      THORNS_PLUS);
        register("guardian",         GUARDIAN);
        register("angel",            ANGEL);
        register("magnet",           MAGNET);
        register("speed_boost",      SPEED_BOOST);
        register("ironhide",         IRONHIDE);
        register("reflect",          REFLECT);
        register("wither_proof",     WITHER_PROOF);
        register("undying_plus",     UNDYING_PLUS);

        // Outils
        register("veinminer",        VEINMINER);
        register("smelting",         SMELTING);
        register("magnetic_pick",    MAGNETIC_PICK);
        register("explosive_mine",   EXPLOSIVE_MINE);
        register("lumber",           LUMBER);
        register("replant",          REPLANT);
        register("titan_pick",       TITAN_PICK);

        // Bottes
        register("spring",           SPRING);
        register("lava_walker",      LAVA_WALKER);
        register("dash",             DASH);
        register("anti_gravity",     ANTI_GRAVITY);

        // Spéciaux
        register("curse_decay",      CURSE_DECAY);
        register("soulbound",        SOULBOUND);
    }

    private static void register(String name, Enchantment enchantment) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(UltraEnchantsMain.MOD_ID, name), enchantment);
    }

    // ═══════════════════════════════════════════
    //  EVENTS FABRIC — logique des enchantements
    // ═══════════════════════════════════════════
    public static void registerEvents() {

        // --- Attaque d'entité (épée) ---
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack weapon = player.getMainHandStack();
            if (!(entity instanceof LivingEntity target)) return ActionResult.PASS;

            // LIFESTEAL : vol de vie
            int lifestealLvl = weapon.getEnchantments().getInt(
                    new net.minecraft.nbt.NbtString() {{ /* placeholder */ }});
            // NOTE : voir LifestealEnchantment pour l'implémentation via Mixin

            // THUNDERSTRIKE : frappe de foudre (niveau 1-3, chance croissante)
            int thunderLvl = getEnchantLevel(weapon, THUNDERSTRIKE);
            if (thunderLvl > 0 && world instanceof ServerWorld sw) {
                float chance = 0.10f * thunderLvl;
                if (world.getRandom().nextFloat() < chance) {
                    LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                    if (lightning != null) {
                        lightning.refreshPositionAfterTeleport(entity.getX(), entity.getY(), entity.getZ());
                        lightning.setCosmetic(false);
                        sw.spawnEntity(lightning);
                    }
                }
            }

            // GRAVITON : repousse l'ennemi
            int gravitonLvl = getEnchantLevel(weapon, GRAVITON);
            if (gravitonLvl > 0) {
                double force = 1.5 * gravitonLvl;
                entity.setVelocity(entity.getVelocity().add(0, 0.4 * gravitonLvl, 0)
                        .add(player.getRotationVector().multiply(force * 0.3)));
            }

            // BLEED : applique slowness + saignement (particules + dégâts sur temps)
            int bleedLvl = getEnchantLevel(weapon, BLEED);
            if (bleedLvl > 0 && target instanceof LivingEntity le) {
                le.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60 * bleedLvl, bleedLvl - 1));
                if (world instanceof ServerWorld sw2) {
                    for (int i = 0; i < 8; i++) {
                        sw2.spawnParticles(ParticleTypes.DAMAGE_INDICATOR,
                                target.getX(), target.getY() + 1, target.getZ(),
                                3, 0.3, 0.3, 0.3, 0.1);
                    }
                }
            }

            // INFERNO : met le feu (durée selon niveau)
            int infernoLvl = getEnchantLevel(weapon, INFERNO);
            if (infernoLvl > 0) {
                entity.setOnFireFor(4 * infernoLvl);
            }

            // VENOM_BLADE : poison puissant
            int venomLvl = getEnchantLevel(weapon, VENOM_BLADE);
            if (venomLvl > 0 && target instanceof LivingEntity le) {
                le.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100 * venomLvl, venomLvl));
            }

            // EXECUTE : bonus dégâts si cible < 25% vie
            // (géré via Mixin LivingEntityMixin)

            return ActionResult.PASS;
        });

        // --- Cassage de bloc (outils) ---
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ItemStack tool = player.getMainHandStack();

            // MAGNETIC_PICK : attire les drops vers le joueur
            int magnetLvl = getEnchantLevel(tool, MAGNETIC_PICK);
            if (magnetLvl > 0 && world instanceof ServerWorld sw) {
                double radius = 4.0 * magnetLvl;
                List<net.minecraft.entity.ItemEntity> items = world.getEntitiesByType(
                        EntityType.ITEM,
                        new Box(pos).expand(radius),
                        e -> true
                );
                for (net.minecraft.entity.ItemEntity item : items) {
                    item.setPos(player.getX(), player.getY(), player.getZ());
                }
            }

            // REPLANT : replante automatiquement les cultures
            int replantLvl = getEnchantLevel(tool, REPLANT);
            if (replantLvl > 0) {
                // La logique complète est dans ReplantEnchantment
            }
        });
    }

    // Utilitaire pour récupérer le niveau d'enchantement sur un ItemStack
    public static int getEnchantLevel(ItemStack stack, Enchantment enchantment) {
        return stack.getEnchantments().getKeys().stream()
                .filter(key -> Registries.ENCHANTMENT.get(key) == enchantment)
                .mapToInt(key -> stack.getEnchantments().getInt(key))
                .findFirst()
                .orElse(0);
    }

    // Version via ItemStack directement
    public static int getLevel(ItemStack stack, Enchantment ench) {
        return net.minecraft.enchantment.EnchantmentHelper.getLevel(ench, stack);
    }
}
