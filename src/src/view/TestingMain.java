package src.view;

import java.util.Scanner;
import src.controller.AreaEffectItem;

import src.controller.Avatar;
import src.controller.AvatarController;
import src.controller.Item;
import src.controller.Terrain;
import src.model.MapMain_Relation;
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

    public static void oldtest() {
        MapMain_Relation mmr_ = new MapMain_Relation();
        mmr_.bindToNewMapOfSize(Viewport.width_, Viewport.height_); //Can change these later if we so desire. 
		Avatar avatar = new Avatar("avatar", '@', 0, 0);
		avatar.setMap(mmr_);
		Display _display = new Display(avatar.getMyView());
		_display.printView();
		Terrain obstacle = new Terrain("boulder", 'O', true, false);
		mmr_.addTerrain(obstacle, 2, 2);
		System.out.println("☠ and ★ and ✚");
		Scanner sc = new Scanner(System.in);
		while(true){
			char c;
			c = sc.next().charAt(0);
			if(c == '5'){return;}
			avatar.sendInput(c);
			_display.setView(avatar.getMyView());
			_display.printView();
		}
    }

    public static void main(String[] args) {
        MapMain_Relation mmr_ = new MapMain_Relation();
        mmr_.bindToNewMapOfSize(Viewport.width_ / 2, Viewport.height_); //Can change these later if we so desire. 
        Avatar avatar = new Avatar("avatar", '☃', 0, 0);
        avatar.setMap(mmr_);
        Item equipable = new Item("umbrella_0", '☂', true, true, false);
        Item blue = new Item("umbrella_1", '☂', true, true, false);
        Item red = new Item("umbrella_2", '☂', true, true, false);
        Item green = new Item("umbrella_3", '☂', true, true, false);
        Item brown = new Item("umbrella_4", '☂', true, true, false);
        Item seven = new Item("umbrella_5", '☂', true, true, false);
        
        mmr_.addItem(equipable, 5, 5); // ▨
        mmr_.addItem(blue, 6, 6); // ▨
        mmr_.addItem(red, 7, 7); // ▨
        mmr_.addItem(green, 8, 8); // ▨
        mmr_.addItem(brown, 9, 9); // ▨
        mmr_.addItem(seven, 5, 5); // ▨
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
                mmr_.addTerrain(obstacle, x, y);
            }
        }
            // String name, char representation, boolean is_passable,
        // boolean goes_in_inventory, boolean is_one_shot, Effect effect, int power
        AreaEffectItem inflict_pain = new AreaEffectItem("inflict_pain", '♨', true, false,
                true, AreaEffectItem.Effect.HURT, 10);
        mmr_.addItem(inflict_pain, 16, 7);
        
        AreaEffectItem area_heal = new AreaEffectItem("area_heal", '♥', true, false,
                false, AreaEffectItem.Effect.HEAL, 10);
        mmr_.addItem(area_heal, 12, 12);
        
        AreaEffectItem area_kill = new AreaEffectItem("area_kill", '☣', true, false,
                true, AreaEffectItem.Effect.KILL, 10);
        mmr_.addItem(area_kill, 3, 11);
        
        AreaEffectItem area_level = new AreaEffectItem("area_level", '↑', true, false,
                true, AreaEffectItem.Effect.LEVEL, 10);
        mmr_.addItem(area_level, 11, 5);
        
        Display _display = new Display(avatar.getMyView());
        _display.printView();
        Terrain obstacle = new Terrain("boulder", '■', true, false);
        mmr_.addTerrain(obstacle, 2, 2);
        System.out.println("☠ and ★ and ✚");
        Display.setMessage("test",3);
        AvatarController AC = new AvatarController(avatar);
		AC.runTheGame();

    }

}
