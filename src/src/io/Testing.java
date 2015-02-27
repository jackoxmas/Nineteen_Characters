package src.io;


import src.Effect;
import src.FacingDirection;
import src.model.map.Map;
import src.model.map.constructs.Avatar;
import src.model.map.constructs.Item;
import src.model.map.constructs.Terrain;
import src.io.controller.AvatarController;
import src.io.view.Display;
import src.io.view.Viewport;
import src.model.*;

/*
 * A simple testing method used early on. Should get deleted at some point, but might be useful at some point.
 */
public class Testing {

    public Testing() {
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
		map.addAvatar(avatar, 0, 0);
        Item blue = new Item("umbrella_1", '☂', true);
        Item red = new Item("umbrella_2", '☂', true);
        Item green = new Item("umbrella_3", '☂', true);
        Item brown = new Item("umbrella_4", '☂', true);
        Item seven = new Item("umbrella_5", '☂', true);
        //seven.getStatsPack().offensive_rating_ = 17; //Can no longer do this.
        
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
        Terrain obstacle = new Terrain("boulder_terrain", 'T', true, false);
        map.addTerrain(obstacle, 20, 2);
        Item obstacle2 = new Item("boulder_item", '■', false);
        map.addItem(obstacle2, 2, 2, false, false);
        System.out.println("☠ and ★ and ✚");
        Display.setMessage("test",3);
        avatar.getMapRelation().moveInDirection(18, 0);
        avatar.getMapRelation().moveInDirection(0, 12);
        avatar.getMapRelation().areaEffectFunctor.effectAreaWithinLine(5, 20, Effect.HEAL);
        avatar.getMapRelation().moveInDirection(0, 1);
        avatar.setFacingDirection(FacingDirection.RIGHT);
        avatar.getMapRelation().areaEffectFunctor.effectAreaWithinArc(3, 20, Effect.HURT);
        avatar.setFacingDirection(FacingDirection.DOWN_LEFT);
        avatar.getMapRelation().areaEffectFunctor.effectAreaWithinArc(3, 1, Effect.KILL);
        //avatar.getMapRelation().areaEffectFunctor.effectAreaWithinArc(10, 20, Effect.HURT);
        AvatarController AC = new AvatarController(avatar);
		AC.runTheGame();

    }

}
