<br>
<h3 align="center">BattleAsya BedWars Extension</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Version-1.0.3-green"> <img src="https://img.shields.io/badge/Spigot-1.8+-lightgrey"> <img src="https://img.shields.io/badge/License-MIT-blue"> <img src="https://img.shields.io/badge/Language-Java-yellow">
</p>

<p align="center">
    A hybrid extension to <a href="https://www.spigotmc.org/resources/51340/">BedWars Minigame</a> and <a href="https://www.spigotmc.org/resources/83380/">AlonsoLevels</a>.<br>
    <a href="https://github.com/denniemok/battleasya-bedwars-extension/releases">Latest Release</a> •
    <a href="https://github.com/denniemok/battleasya-bedwars-extension/wiki">User Guide</a> •
    <a href="https://github.com/denniemok/battleasya-bedwars-extension/issues">Issue Tracker</a>
</p>
<br>

<hr>

### Introduction
This simple extension introduces several EULA-friendly enhancements to the 2 installed plugins (i.e., BedWars Minigame, and AlonosoLevels) on BattleAsya. Since the extension was initially made for exclusive use on BattleAsya, most of the messages and parameters were hard-coded (i.e., non-configurable).<p>

BattleAsya has been using this extension on all its BedWars instances ever since 2021. Until today, the functions it offers still constitute  a large part of BattleAsya's VIP benefits. <p>

<hr>

### Components
- Experience Booster Extension for [AlonsoLevels](https://www.spigotmc.org/resources/83380/)
- In-Chat Statistics Extension for [BedWars Minigame](https://www.spigotmc.org/resources/51340/) <br>

<hr>

### Main Features
- Fetch raw statistics from BedWars's remote database 
- Compute extra statistics and perform data category grouping
- Compute the individual rankings for each data category (intensive)
- Compute the leaderboard for each data category
- Apply a cooldown to resource-intensive commands 
- Asynchronously perform database-related tasks and print the result
- Intercept AlonsoLevels experience change event
- Compute the new experience gain with respect to the global modifier
- Apply the changes in experience with avoidance to duplication <br>

<hr>

### Runtime Requirements
- Java 8 or above
- Spigot 1.8 or above, or equivalent forks
- Permission plugin, preferably LuckPerms or PermissionsEX
- [AlonsoLevels](https://www.spigotmc.org/resources/83380/) 2.2.1-BETA or above
- [BedWars Minigame](https://www.spigotmc.org/resources/51340/) 1.16-Legacy or above, with MySQL option enabled in config.yml
- MySQL (com.mysql.jdbc) drivers <br>

<hr>

### Build Dependencies
- Java 8
- Spigot API 1.8.8 R0.1
- AlonsoLevels 2.2.1-BETA (Free) <br>

<hr>

This project is released under the [MIT License](https://opensource.org/license/mit/).
