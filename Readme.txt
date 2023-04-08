# CS611-Assignment 4
## Monsters and Heroes Legends!
---------------------------------------------------------------------------
Kevin Lin
lin2391@bu.edu
U05482650

Anand Shetler
anashe@bu.edu

## Files
---------------------------------------------------------------------------

GameSelector.java: Class that allows user to select a game to play.

GridSquareLegends.java: Subclass of GridSquare that represents a tile in the world. Allows new terrain and a hero and monster on tile

GridSquareNexus.java: Subclass of GridSquareLegend that represents a nexus tile. Spawns monsters or heroes and determines when game extends

GridSquareNexusHero.java: Subclass of GridSquareNexus that represents a nexus tile that spawns heroes

GridSquareNexusMonster.java: Subclass of GridSquareNexus that represents a nexus tile that spawns monsters

HeroTeamLegends.java: Subclass of HeroTeam that represents a team of heroes. Each team has 1 hero and a spawn point. Ability to recall and respawn

MonsterController.java: Class that controls the monsters. Spawns monsters and controls their movements and actions

MonstersAndHeroesLegends.java: Class that runs the game. Constructor and game loop.

WorldLegends.java: Class that represents the world. Has a grid of GridSquareLegends. ArrayList of Heroes

- Default
   -Animal.txt: A type of monster good at dodging
   -Armor.txt: A type of item that can be equipped by a hero
   -Beast.txt: A type of monster good at defense
   -Charms.txt: A type of item that can be used (spell)
   -Egyptian.txt: A type of hero. Good at dexterity and agility
   -Greek.txt: A type of hero. Good at strength and agility
   -Hybrid.txt: A type of monster good at damage
   -Norse.txt: A type of hero. Good at strength and dexterity
   -Potions.txt: A type of item that can be used
   -Weapons.txt: A type of item that can be equipped by a hero

Attribute.java: Class that represents attributes such as health, mana etc

AttributeWeight.java: Subclass of Attribute that represents the weight carried by hero

Battleground.java: Used to represent the battleground where the hero fights monsters.

Color.java: Class used to change way things are printed

Equiptable.java: Interface that represents behaviors of items that can be equipped by a hero

FactoryHero.java: Class that reads in data and allows user to select Heroes

FactoryMarket.java: Class that reads in data and is used to generate markets with random items

FactoryMonster.java: Class that reads in data and generates varying number of Monsters

FileParser.java: Class that reads in data from files

GridSquare.java: Abstract class that represents a tile in the world. Heroes occupy a gridsquare

GridSquareCommon.java: Subclass of GridSquare that represents a common tile. Battles can happen here

GridSquareMarket.java: Subclass of GridSquare that represents a market tile. Trading can happen here

GridSquareRestricted.java: Subclass of GridSquare that represents a restricted tile. Heroes cannot enter

GridSquareSeverlyRestricted.java: Subclass of GridSquare that represents a severly restricted tile. Heroes cannot enter. Used for border

Hero.java: Represents a hero. Has attributes and inventory. They can use items

HeroTeam.java: A team of heroes. Is used to display in the world. Teams fight monsters

Input.java: Class that handles user input

Item.java: Abstract class that represents an item. Items can be used or equipped or repaired

ItemArmor.java: Subclass of Item that represents an armor item. Implements Equiptable and Repairable

ItemCharm.java: Subclass of Item that represents a charm item (spell). Implements Usable and Repairable

ItemPotion.java: Subclass of Item that represents a potion item. Implements Usable and Repairable

ItemWeapon.java: Subclass of Item that represents a weapon item. Implements Equiptable and Repairable and Usable

Level.java: Class that represents level of hero/monster. This is a subject for the observers (attributes)

Main.java: Main class that runs the game

Marketplace.java: Class that represents a marketplace. Heroes can trade or repair items here. Has unique items

Monster.java: Represents a monster. Has attributes. They attack heroes

MonstersAndHeroes.java: Class that runs the game. Has a world and the main loop

Randomgen.java: Class that generates random numbers

Repairable.java: Interface that represents behaviors of items that can be repaired

Terrain.java: Class that represents the terrain of each grid square. Is the object that gets printed for display

Useable.java: Interface that represents behaviors of items that can be used

World.java: Class that represents the world. Has a grid of gridsquares. Heroes can move around


## Notes
---------------------------------------------------------------------------
1. Allows selection of game 1 or game 2. (Original or new)
2. If new game, allows user to select size of world (How many tiles between nexus)
3. If new game, allows user to select number of heroes which determines number of lanes and width of world
4. Files to be parsed are in the default folder
5. I added a few additional fields. This includes a weight for items and a max weight a hero can carry
6. Additionally, all items have a durability. This is used to determine if an item can be used or repaired
7. All things that are parsed in have a description
8. Armor and weapons have slots to go in (Armor: head, body, legs, feet, shield. Weapon: left hand, right hand, both)
    If shield is used, both hands can't have weapon and vice versa. 
9. Items can be repaired in markets
10. Some heroes have special abilities. These are listed in description.
    Notably, Geb can manipulate the terrain to his advantage (change any non border tile to common)
    Isis will heal all heroes in her team each km traveled
    Heimdallr allows his teams visible range to be increased by 1
11. Monsters and Heroes fight by Each hero selecting a monster and attacking, then each monster selecting a hero and attacking
12. When a hero dies, they are removed from the world and respawn at their nexus as a new hero

## How to compile and run
---------------------------------------------------------------------------
1. Navigate to the directory "MonstersAndHeroes" after unzipping the files
2. Run the following instructions:
javac *.java -d bin
java -cp ./bin Main

## Input/Output Example
---------------------------------------------------------------------------
Output:
[+] Welcome to Monsters and Heroes!
[+] Would you like to modify the size of the world? (y/n):

Input:
n

Output:
[+] Will additional heroes be joining you? (y/n):

Input:
y

Ouput:
[+] How many heroes will embark on this quest? (1-3):

Input:
2

Output:
[+] 1. Egyptian - Favored for their dexterity and agility (magic and dodging)
[+] 2. Norse - Favored for their strength and dexterity (attack and magic)
[+] 3. Greek - Favorite for their strength and agility (attack and dodging)
[+] Player 1, select a culture to add a hero to your party: (1 - 3):

Input:
1

Output:
1. [i] Name: Isis
        [i] Description: Goddess of life, she healed her husband Osiris, and has the ability to heal heroes over km walked.
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 10 / 10
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

2. [i] Name: Geb
        [i] Description: The earth god, has the ability to change restricted terrain to common terrain
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 10 / 10
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 15 / 15
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

3. [i] Name: Ra
        [i] Description: The king of the gods has the highest mana
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 200 / 200
        [i] Current Strength: 10 / 10
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 15 / 15
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

[+] Player 1, select a hero to add to your party: (1 - 3):

Input:
2

Output:
[+] 1. Norse - Favored for their dexterity and agility (magic and dodging)
[+] 2. Greek - Favored for their strength and dexterity (attack and magic)
[+] Player 2, select a culture to add a hero to your party: (1 - 2):

Input:
2

Output:
1. [i] Name: Achilles
        [i] Description: This warrior was known in battle for being untouchable in combat. He has good agility
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 20 / 20
        [i] Current Agility: 20 / 20
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

2. [i] Name: Hercules
        [i] Description: This demigod wears wears the hide of the Nemean Lion that increases his health.
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 200 / 200
        [i] Current Mana: 100 / 100
        [i] Current Strength: 20 / 20
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

3. [i] Name: Midas
        [i] Description: A cautionary tale against greed is benefitial to this king. He has a lot of gold.
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 15 / 15
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 10000 coins

[+] Player 2, select a hero to add to your party: (1 - 3):

Input:
1

Output:
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |     |     |  /  |  M  |     |     |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  M  |     |  /  |     |  /  |     |     |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  /  |  M  |     |     |     |     |     |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |     |  M  |  H  |     |     |  M  |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |  M  |     |  /  |     |     |     |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |  /  |  M  |     |  /  |     |     |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |  M  |  M  |     |  /  |  /  |  /  |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |     |     |     |  /  |     |     |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+

+-----+-----+-----+
|     |     |     |
+-----+-----+-----+
|  M  |  H  |     |
+-----+-----+-----+
|     |  /  |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input: 
I

Output:
[+] Which player's inventory would you like to view?1. Geb 2. Achilles :

Input:
1

Output:
Armor:
Weapons:
 Name: Wooden_Stick
        [i] Description: Good for hitting things
        [i] Value: 0 coins
        [i] Weight: 1 lbs
        [i] Required Level: 1
        [i] Damage: 5
        [i] Hands Required: 1
        [i] Uses Remaining: 100.0 / 100.0

Rucksack:
 Name: Shiny_Rock Charm
        [i] Description: Why do I have this???
        [i] Value: 5 coins
        [i] Weight: 1 lbs
        [i] Required Level: 1
        [i] Effect: 0
        [i] Target Attribute: 0
        [i] Damage: 0
        [i] Mana Cost: 0
        [i] Uses Remaining: 0.0 / 0.0

+-----+-----+-----+
|     |     |     |
+-----+-----+-----+
|  M  |  H  |     |
+-----+-----+-----+
|     |  /  |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input:
M

Output:
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |     |     |  /  |  M  |     |     |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  M  |     |  /  |     |  /  |     |     |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  /  |  M  |     |     |     |     |     |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |     |  M  |  H  |     |     |  M  |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |  M  |     |  /  |     |     |     |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |  /  |  M  |     |  /  |     |     |  /  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |  M  |  M  |     |  /  |  /  |  /  |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |     |     |     |     |  /  |     |     |     |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
|  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+

+-----+-----+-----+
|     |     |     |
+-----+-----+-----+
|  M  |  H  |     |
+-----+-----+-----+
|     |  /  |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input:
h

Ouput:
[i] Name: Geb
        [i] Description: The earth god, has the ability to change restricted terrain to common terrain
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 10 / 10
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 15 / 15
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

[i] Name: Achilles
        [i] Description: This warrior was known in battle for being untouchable in combat. He has good agility
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 20 / 20
        [i] Current Agility: 20 / 20
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

+-----+-----+-----+
|     |     |     |
+-----+-----+-----+
|  M  |  H  |     |
+-----+-----+-----+
|     |  /  |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input:
t

Ouput:
There is no market in this area.
+-----+-----+-----+
|     |     |     |
+-----+-----+-----+
|  M  |  H  |     |
+-----+-----+-----+
|     |  /  |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input:
g

Output:
[+] Which direction would you like to manipulate terrain? (1. North, 2. East, 3. South, 4. West):

Input:
3

Output:
+-----+-----+-----+
|     |     |     |
+-----+-----+-----+
|  M  |  H  |     |
+-----+-----+-----+
|     |     |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input: 
d

Ouput:
+-----+-----+-----+
|  M  |     |     |
+-----+-----+-----+
|     |  H  |     |
+-----+-----+-----+
|  M  |     |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input:
t

Output:
Hello there Geb!
[+] What can I do for you? Would you like to buy or sell goods? (Buy: 1, Sell: 2, Repair: 3, Just lookin: 4):

Input: 
1

Output:
[1] Name: Vulnerability_I Charm
        [i] Description: A spell that weakens enemy defense
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 10
        [i] Target Attribute: Defense
        [i] Damage: 20
        [i] Mana Cost: 30
        [i] Uses Remaining: 3.0 / 3.0
[2] Name: Leather_Shield
        [i] Description: A shield made of leather
        [i] Value: 10 coins
        [i] Weight: 5 lbs
        [i] Required Level: 1
        [i] Protection: 10
        [i] Body Part: Hand
        [i] Uses Remaining: 20.0 / 20.0
[3] Name: Health_I
        [i] Description:  Bottoms up
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 30
        [i] Target Attribute: Health
        [i] Uses Remaining: 1.0 / 1.0
[4] Name: Sword
        [i] Description: Stabby Boi
        [i] Value: 100 coins
        [i] Weight: 10 lbs
        [i] Required Level: 1
        [i] Damage: 55
        [i] Hands Required: 1
        [i] Uses Remaining: 100.0 / 100.0
[5] Name: Agility_I
        [i] Description:  No answers at the bottom of the bottle
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 30
        [i] Target Attribute: Agility
        [i] Uses Remaining: 1.0 / 1.0
[6] Name: Agility_I
        [i] Description:  No answers at the bottom of the bottle
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 30
        [i] Target Attribute: Agility
        [i] Uses Remaining: 1.0 / 1.0

[7] Leave Shop
[+] Which item would you like to purchase?:

Input:
4

You have bought a Sword for 100 coins.
[+] What can I do for you? Would you like to buy or sell goods? (Buy: 1, Sell: 2, Repair: 3, Just lookin: 4):

Input:
4

Hello there Achilles!
[+] What can I do for you? Would you like to buy or sell goods? (Buy: 1, Sell: 2, Repair: 3, Just lookin: 4):

Input:
1

Output:
[1] Name: Vulnerability_I Charm
        [i] Description: A spell that weakens enemy defense
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 10
        [i] Target Attribute: Defense
        [i] Damage: 20
        [i] Mana Cost: 30
        [i] Uses Remaining: 3.0 / 3.0
[2] Name: Leather_Shield
        [i] Description: A shield made of leather
        [i] Value: 10 coins
        [i] Weight: 5 lbs
        [i] Required Level: 1
        [i] Protection: 10
        [i] Body Part: Hand
        [i] Uses Remaining: 20.0 / 20.0
[3] Name: Health_I
        [i] Description:  Bottoms up
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 30
        [i] Target Attribute: Health
        [i] Uses Remaining: 1.0 / 1.0
[4] Name: Agility_I
        [i] Description:  No answers at the bottom of the bottle
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 30
        [i] Target Attribute: Agility
        [i] Uses Remaining: 1.0 / 1.0
[5] Name: Agility_I
        [i] Description:  No answers at the bottom of the bottle
        [i] Value: 10 coins
        [i] Weight: 2 lbs
        [i] Required Level: 1
        [i] Effect: 30
        [i] Target Attribute: Agility
        [i] Uses Remaining: 1.0 / 1.0
[6] Leave Shop
[+] Which item would you like to purchase?:

Input:
6

Ouput:
Very well, come back when you have something to buy!
[+] What can I do for you? Would you like to buy or sell goods? (Buy: 1, Sell: 2, Repair: 3, Just lookin: 4):

Input:
4

Thats too bad. Just remember, you break it, you buy it!
Pleasure doing buisness with you!
+-----+-----+-----+
|  M  |     |     |
+-----+-----+-----+
|     |  H  |     |
+-----+-----+-----+
|  M  |     |     |
+-----+-----+-----+

[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit

Input:
d

Output:
You have encountered a monster!
1:[i] Name: Anubis
        [i] Description: Half man, half jackal
        [i] Level: 1
        [i] Current Health: 100 / 100
        [i] Current Damage: 45 / 45
        [i] Current Agility: 10 / 10
        [i] Current Defense: 20 / 20

2:[i] Name: Minotaur
        [i] Description: Half man, half bull
        [i] Level: 1
        [i] Current Health: 100 / 100
        [i] Current Damage: 55 / 55
        [i] Current Agility: 5 / 5
        [i] Current Defense: 20 / 20

Geb it is your turn!
[i] Name: Geb
        [i] Description: The earth god, has the ability to change restricted terrain to common terrain
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 10 / 10
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 15 / 15
        [i] Current Weight: 10 / 100
        [i] Gold: 900 coins

[+] What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory:
4

Output:
[+] What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory:
1: Name: Sword
        [i] Description: Stabby Boi
        [i] Value: 50 coins
        [i] Weight: 10 lbs
        [i] Required Level: 1
        [i] Damage: 55
        [i] Hands Required: 1
        [i] Uses Remaining: 100.0 / 100.0

[+] Which equiptable do you want to use?:

Input:
1

Output:
[+] Which weapon slot do you want to use? 0: Left Hand, 1: Right Hand, 2: Double:

Input:
2

Output:
Achilles it is your turn!
[i] Name: Achilles
        [i] Description: This warrior was known in battle for being untouchable in combat. He has good agility
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 20 / 20
        [i] Current Agility: 20 / 20
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

[+] What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory:

Input:
1

Output:
1: [i] Name: Anubis
        [i] Description: Half man, half jackal
        [i] Level: 1
        [i] Current Health: 100 / 100
        [i] Current Damage: 45 / 45
        [i] Current Agility: 10 / 10
        [i] Current Defense: 20 / 20

2: [i] Name: Minotaur
        [i] Description: Half man, half bull
        [i] Level: 1
        [i] Current Health: 100 / 100
        [i] Current Damage: 55 / 55
        [i] Current Agility: 5 / 5
        [i] Current Defense: 20 / 20

[+] Which monster do you want to attack?:

Input:
1

Output:
INEFFECTIVE! Achilles hit Anubis for 0 damage!
Anubis attacks!
Anubis missed!
Minotaur attacks!
Minotaur attacked Geb for 55 damage!
Healed Geb for 4.95 health!
Healed Achilles for 10.0 health!
Geb it is your turn!
[i] Name: Geb
        [i] Description: The earth god, has the ability to change restricted terrain to common terrain
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 49 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 10 / 10
        [i] Current Agility: 15 / 15
        [i] Current Dexterity: 15 / 15
        [i] Current Weight: 10 / 100
        [i] Gold: 900 coins

[+] What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory:

Input:
1

Output:
1: [i] Name: Anubis
        [i] Description: Half man, half jackal
        [i] Level: 1
        [i] Current Health: 100 / 100
        [i] Current Damage: 45 / 45
        [i] Current Agility: 10 / 10
        [i] Current Defense: 20 / 20

2: [i] Name: Minotaur
        [i] Description: Half man, half bull
        [i] Level: 1
        [i] Current Health: 100 / 100
        [i] Current Damage: 55 / 55
        [i] Current Agility: 5 / 5
        [i] Current Defense: 20 / 20

[+] Which monster do you want to attack?:

Input:
1

Output:
EFFECTIVE! Geb hit Anubis for 42 damage!
Achilles it is your turn!
[i] Name: Achilles
        [i] Description: This warrior was known in battle for being untouchable in combat. He has good agility
        [i] Level: 1
        [i] Experience to next level: 10
        [i] Current Health: 100 / 100
        [i] Current Mana: 100 / 100
        [i] Current Strength: 20 / 20
        [i] Current Agility: 20 / 20
        [i] Current Dexterity: 10 / 10
        [i] Current Weight: 0 / 100
        [i] Gold: 1000 coins

[+] What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory:


etc. etc. etc.