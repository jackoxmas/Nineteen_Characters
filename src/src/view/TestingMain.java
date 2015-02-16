package src.view;

import java.util.Scanner;
import src.controller.AreaEffectItem;

import src.controller.Avatar;
import src.controller.AvatarController;
import src.controller.Item;
import src.controller.Terrain;
import src.model.MapMain_Relation;

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
        Item equipable = new Item("☂", '☂', true, true, false);
        mmr_.addItem(equipable, 5, 5); // ▨
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
        mmr_.addItem(inflict_pain, 8, 3);
        
        AreaEffectItem area_heal = new AreaEffectItem("area_heal", '♥', true, false,
                false, AreaEffectItem.Effect.HURT, 10);
        mmr_.addItem(area_heal, 9, 9);
        
        AreaEffectItem area_kill = new AreaEffectItem("area_kill", '☣', true, false,
                true, AreaEffectItem.Effect.HURT, 10);
        mmr_.addItem(area_kill, 2, 4);
        
        AreaEffectItem area_level = new AreaEffectItem("area_level", '↑', true, false,
                true, AreaEffectItem.Effect.HURT, 10);
        mmr_.addItem(area_level, 7, 1);
        
        Display _display = new Display(avatar.getMyView());
        _display.printView();
        Terrain obstacle = new Terrain("boulder", '■', true, false);
        mmr_.addTerrain(obstacle, 2, 2);
        System.out.println("☠ and ★ and ✚");
        Display.setMessage("test",3);
        AvatarController AC = new AvatarController(avatar);
        try {
			AC.runTheGame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
