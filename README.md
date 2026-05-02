# ⚔️ UltraEnchants — Mod Fabric 1.20.4

> **42 enchantements custom** pour transformer ton Minecraft en RPG !

---

## 📦 Installation rapide

### Prérequis
- Java **17** ou supérieur
- Minecraft **1.20.4**
- [Fabric Loader](https://fabricmc.net/use/installer/) **≥ 0.15.9**
- [Fabric API](https://modrinth.com/mod/fabric-api) **0.96.11+1.20.4**

### Compiler le mod toi-même

```bash
# 1. Clone ou télécharge ce dossier
cd ultraenchants

# 2. Télécharge le Gradle wrapper
gradle wrapper   # ou utilise le wrapper inclus

# 3. Compile
./gradlew build

# 4. Le .jar se trouve dans :
#    build/libs/ultraenchants-1.0.0.jar

# 5. Copie ce fichier dans ton dossier mods Minecraft :
#    Windows : %APPDATA%\.minecraft\mods\
#    Linux   : ~/.minecraft/mods/
#    macOS   : ~/Library/Application Support/minecraft/mods/
```

---

## ⚔️ Liste complète des enchantements

### 🗡️ ÉPÉE (10 enchantements)

| Enchantement | Niveaux | Effet |
|---|---|---|
| **Vol de Vie** (Lifesteal) | I-V | Vole 5% × niveau de la vie infligée |
| **Foudre Céleste** (Thunderstrike) | I-III | 10%/20%/30% de chance de foudre sur la cible |
| **Déchirement d'Âme** (Soul Rend) | I-IV | +1.5×niveau dégâts vs morts-vivants et joueurs |
| **Berserker** | I-III | Bonus dégâts quand < 50% PV (jusqu'à +60%) |
| **Lame Venimeuse** (Venom Blade) | I-III | Poison amplifié (5s × niveau) |
| **Exécution** (Execute) | I-IV | ×1.5/2/2.5/3 dégâts si cible < 25% PV |
| **Vampirisme** | I-III | Régénération Regen I/II/III pendant le combat |
| **Saignement** (Bleed) | I-III | Lenteur + particules de dégâts |
| **Graviton** | I-III | Propulse l'ennemi dans les airs |
| **Inferno** | I-IV | Enflamme l'ennemi (4s × niveau) |

### 🏹 ARC / ARBALÈTE (8 enchantements)

| Enchantement | Niveaux | Effet |
|---|---|---|
| **Sniper** | I-III | +5%/bloc de distance parcouru par niveau |
| **Flèche Explosive** | I-III | Explosion à l'impact (rayon 2/2.5/3 blocs) |
| **Foudre en Chaîne** | I-III | Arc de foudre sur 3/6/9 mobs proches |
| **Guidage** (Homing) | I-II | Les flèches suivent les mobs *(Trésor)* |
| **Flèche de Wither** | I-II | Applique Wither II pendant 5/10s |
| **Tir Glacé** (Iceshot) | I-III | Gèle la cible (lenteur + fatigue de minage) |
| **Multitir+** | I-III | Tire 2/3/4 flèches en éventail (Arbalète) |
| **Ricochet** (Bounce) | I-III | Les flèches rebondissent sur les murs |

### 🛡️ ARMURE (9 enchantements)

| Enchantement | Niveaux | Effet |
|---|---|---|
| **Épines Acérées** (Thorns+) | I-V | Renvoie 10%×niveau des dégâts reçus |
| **Gardien** (Guardian) | I-III | -5%×niveau tous les dégâts |
| **Ailes d'Ange** (Angel) | I-III | Slow Fall en maintenant Shift + vol réduit |
| **Magnétisme** | I-III | Attire items & XP dans 6/8/10 blocs |
| **Vitesse Accrue** | I-III | Speed I/II/III permanent (bottes) |
| **Peau de Fer** (Ironhide) | I-IV | Résistance I/II/III/IV permanent |
| **Réflexion** (Reflect) | I-III | 15/30/50% chance de renvoyer une flèche |
| **Résistance au Wither** | I | Immunité complète au Wither *(Trésor)* |
| **Immortalité** (Undying+) | I-III | Survie à la mort avec soin automatique *(Trésor)* |

### ⛏️ OUTILS (7 enchantements)

| Enchantement | Niveaux | Effet |
|---|---|---|
| **Extraction Minière** (Veinminer) | I-III | Casse 8/16/32 blocs identiques connectés |
| **Fusion Automatique** (Smelting) | I | Fond automatiquement les minerais |
| **Pioche Magnétique** | I-III | Attire les drops dans 4/8/12 blocs |
| **Mine Explosive** | I-III | Explosion de minage (rayon 2/3/4 blocs) *(Trésor)* |
| **Bûcheron** (Lumber) | I-II | Coupe tout l'arbre d'un coup (hache) |
| **Replantation** | I | Replante les cultures automatiquement (houe) |
| **Pioche Titan** | I-V | Vitesse de minage ×2/4/6/8/10 |

### 👟 BOTTES (4 enchantements)

| Enchantement | Niveaux | Effet |
|---|---|---|
| **Ressort** (Spring) | I-IV | Jump Boost I-IV + réduction des dégâts de chute |
| **Marcheur de Lave** | I-II | Marche sur la lave *(Trésor)* |
| **Dash** | I-III | Sprint rapide (double-tap Shift, cooldown 5/3/2s) |
| **Anti-Gravité** | I-II | Annule les dégâts de chute (niv.2 : aussi ender pearl) |

### ✨ SPÉCIAUX (2 enchantements)

| Enchantement | Type | Effet |
|---|---|---|
| **Malédiction de Dégradation** | Malédiction | Durabilité -3× (s'obtient normalement) |
| **Lié à l'Âme** (Soulbound) | Spécial | L'item reste à la mort *(Trésor exclusif)* |

---

## 🔧 Structure du projet

```
ultraenchants/
├── build.gradle
├── gradle.properties
└── src/main/
    ├── java/com/ultraenchants/
    │   ├── UltraEnchantsMain.java          ← Point d'entrée
    │   ├── UltraEnchantsDatagen.java
    │   ├── init/
    │   │   └── ModEnchantments.java        ← Registre de tous les enchantements
    │   ├── enchantments/
    │   │   ├── BaseEnchantment.java        ← Classe abstraite
    │   │   ├── SwordEnchantments.java      ← 10 enchantements épée
    │   │   ├── BowEnchantments.java        ← 8 enchantements arc
    │   │   ├── ArmorEnchantments.java      ← 9 enchantements armure
    │   │   ├── ToolEnchantments.java       ← 7 enchantements outils
    │   │   └── BootAndSpecialEnchantments.java ← 6+2 enchantements
    │   └── mixin/
    │       ├── LivingEntityMixin.java      ← Dégâts & effets de combat
    │       ├── PlayerEntityMixin.java      ← Effets passifs (tick)
    │       ├── ArrowEntityMixin.java       ← Impact des flèches & homing
    │       └── ItemStackMixin.java         ← Minage & outils
    └── resources/
        ├── fabric.mod.json
        ├── ultraenchants.mixins.json
        └── assets/ultraenchants/lang/
            ├── fr_fr.json                  ← Traduction française
            └── en_us.json                  ← Traduction anglaise
```

---

## 🛠️ Compatibilité & Notes

- ✅ Compatible Fabric **1.20.4** (à jour fin 2024)
- ✅ Compatible avec la majorité des mods Fabric (OptiFine non compatible, utilise Sodium)
- ✅ Fonctionne en **Singleplayer** et **Multiplayer** (serveur + client)
- ⚡ Nécessite **Fabric API**
- 🔧 Port vers **1.21** : mettre à jour `gradle.properties` (minecraft_version, yarn_mappings, fabric_version)

---

## 📝 Licence

MIT — libre d'utilisation, modification et redistribution.
