package src.userIO;

import java.util.Scanner;


import src.entityThings.AreaEffectItem;
import src.entityThings.Avatar;
import src.entityThings.AvatarController;
import src.entityThings.Item;
import src.entityThings.Terrain;
import src.model.Map;
import src.model.MapAvatar_Relation;
/*
 * A simple testing method used early on. Should get deleted at some point, but might be useful at some point.
 */
public class TestingMain {

    public TestingMain() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void printArray(Viewport view) {
        char[][] in = view.getContents();
        for (int j = 0; j != view.height_; ++j) {
            for (int i = 0; i != view.width_; ++i) {
                {
                    System.out.print(in[i][j]);
                }
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
    	Map map = new Map(Viewport.width_/2, Viewport.height_);
		Avatar avatar = new Avatar("avatar", '☃');
		avatar.generateMapView(map);
		MapAvatar_Relation mavatar = new MapAvatar_Relation(map,avatar,0,0);
		avatar.setMapRelation(mavatar);
        Item blue = new Item("umbrella_1", '☂', true);
        Item red = new Item("umbrella_2", '☂', true);
        Item green = new Item("umbrella_3", '☂', true);
        Item brown = new Item("umbrella_4", '☂', true);
        Item seven = new Item("umbrella_5", '☂', true);
        seven.getStatsPack().offensive_rating_ = 17;
        
        map.addItem(blue, 6, 6,true,true); // ▨
        map.addItem(red, 7, 7,true,true); // ▨
        map.addItem(green, 8, 8,true,true); // ▨
        map.addItem(brown, 9, 9,true,true); // ▨
        map.addItem(seven, 5, 5,true,true); // ▨
        for (int y = 0; y < Viewport.height_; ++y) {
            for (int x = 0; x < Viewport.width_ / 2; ++x) {
                Terrain obstacle = new Terrain("land", '▨', false, false);
                if (y == 4) {
                    if (x == 2) {
                        obstacle.addDecal('☠');
                    } else if (x == 6) {
                        obstacle.addDecal('★');
                    } else if (x == 9) {
                        obstacle.addDecal('✚');
                    }
                }
                map.addTerrain(obstacle, x, y);
            }
        }
       
        
        Display _display = new Display(avatar.getMyView());
        _display.printView();
        Terrain obstacle = new Terrain("boulder", '■', true, false);
        map.addTerrain(obstacle, 2, 2);
        System.out.println("☠ and ★ and ✚");
        Display.setMessage("test",3);
        AvatarController AC = new AvatarController(avatar);
		AC.runTheGame();

    }

}
