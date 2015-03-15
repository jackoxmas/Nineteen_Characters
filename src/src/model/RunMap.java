/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.awt.Color;
import src.Effect;
import src.SavedGame;
import src.io.controller.GameController;
import src.io.controller.MapEditorController;
import src.model.map.Map;
import src.model.map.constructs.Avatar;
import src.model.map.constructs.Item;
import src.model.map.constructs.Merchant;
import src.model.map.constructs.Monster;
import src.model.map.constructs.ObstacleRemovingItem;
import src.model.map.constructs.OneHandedSword;
import src.model.map.constructs.OneShotAreaEffectItem;
import src.model.map.constructs.OneWayTeleportItem;
import src.model.map.constructs.PermanentObstacleItem;
import src.model.map.constructs.Sheild;
import src.model.map.constructs.TemporaryObstacleItem;
import src.model.map.constructs.Terrain;
import src.model.map.constructs.Trap;
import src.model.map.constructs.TwoHandedSword;
import src.model.map.constructs.Villager;

/**
 *
 * @author JohnReedLOL
 */
public class RunMap {

    private static Avatar avatar_;
    private static Map map_;
    private static int mapHeight_ = 40;
    private static int mapWidth_ = 40;
    // private static boolean map_editor_mode_ = false;

    public static void main(String[] args) {
        //if (map_editor_mode_) {
        //    startMapEditor();
        //} 
        //else {
        startNewGame();
        //}
    }

    private static int startNewGame() {
        initialize(); // Initialize any data we need to before loading
        populateMap();//Add stuff into the map
        //startGame(); // Begin the avatarcontroller loop
        return 0;
    }

    private static int startMapEditor() {
        initialize(); // Initialize any data we need to before loading
        MapEditorController me_ = new MapEditorController(map_); // Begin the avatarcontroller loop
        return 0;
    }

    private static void initialize() {
        //if (saveGame_ == null) {
        //    saveGame_ = SavedGame.newSavedGame();
        //}
        map_ = new Map(mapWidth_, mapHeight_);

    }

    public static void saveGameToDisk() {
        // if (saveGame_ == null) {
        //    saveGame_ = SavedGame.newSavedGame();
        //}
        //saveGame_.saveGame(map_, uc_);
    }

    private static void populateMap() {
        avatar_ = new Avatar("avatar", '☃');
        // map_.addAsAvatar(avatar_, 0, 0);
        map_.addAsAvatar(avatar_, 0, 0);

        Avatar buddy = new Avatar("buddy", '웃');
        // map_.addAsAvatar(buddy, 3, 0);
        map_.addAsEntity(buddy, 3, 0); // buddy doesn't have the auto-talk ability.

        Villager villager1 = new Villager("villager1", '웃');
        villager1.getStatsPack().increaseQuantityOfExperienceBy(200);
        map_.addAsEntity(villager1, 3, 13);

        Monster monster = new Monster("monster1", '웃');
        monster.getStatsPack().increaseQuantityOfExperienceBy(300);
        map_.addAsEntity(monster, 13, 3);

        Merchant merchant = new Merchant("merchant1", '웃');
        merchant.getStatsPack().increaseQuantityOfExperienceBy(1000);
        map_.addAsEntity(merchant, 1, 1);
        Item teleport = new OneWayTeleportItem("tele", 'T', 0, 0);
        Item onehandedsword = new OneHandedSword("Excalibur", '|');
        Item twohandedsword = new TwoHandedSword("Two_hander", '|');
        Item sheild = new Sheild("Sheildy", 'O');
        OneShotAreaEffectItem heal = new OneShotAreaEffectItem("healer", 'h', Effect.HEAL, 10);
        OneShotAreaEffectItem hurt = new OneShotAreaEffectItem("hurter", 'u', Effect.HURT, 10);
        OneShotAreaEffectItem kill = new OneShotAreaEffectItem("killer", 'k', Effect.KILL, 10);
        OneShotAreaEffectItem level = new OneShotAreaEffectItem("leveler", 'l', Effect.LEVEL, 10);

        ObstacleRemovingItem key = new ObstacleRemovingItem("Key", 'K');
        TemporaryObstacleItem door = new TemporaryObstacleItem("Door", 'D', key);
        map_.addItem(key, 11, 0);
        map_.addItem(door, 13, 0);

        map_.addItem(heal, 3, 2);
        map_.addItem(hurt, 6, 2);
        map_.addItem(kill, 9, 2);
        map_.addItem(level, 12, 2);

        Villager villager = new Villager("Tom", 'V');
        map_.addAsEntity(villager, 0, 5);

        //Add some traps
        Trap trap1 = new Trap("trap1", 'b', Effect.HURT, 2);
        map_.addItem(trap1, 1, 0);

        //seven.getStatsPack().offensive_rating_ = 17; //Can no longer do this.
        map_.addItem(teleport, 2, 4);
        map_.addItem(twohandedsword, 1, 1);
        map_.addItem(sheild, 10, 10);
        map_.addItem(onehandedsword, 5, 5);
        for (int y = 0; y < mapHeight_; ++y) {
            for (int x = 0; x < mapWidth_; ++x) {
                Terrain obstacle = new Terrain("land", '▨', false, false);
                if (y == 4) {
                    if (x == 2) {
                        obstacle.addDecal('☠', Color.black);
                    } else if (x == 6) {
                        obstacle.addDecal('★', Color.yellow);
                    } else if (x == 9) {
                        obstacle.addDecal('✚', Color.red);
                    }
                }
                map_.addTerrain(obstacle, x, y);
            }
        }

        //Terrain obstacle = new Terrain("boulder", '■', true, false);
        //map_.addTerrain(obstacle, 2, 2);
        PermanentObstacleItem obstacle = new PermanentObstacleItem("boulder", '■');
        map_.addItem(obstacle, 2, 2);

    }
}
