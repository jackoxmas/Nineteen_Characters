package src;
import src.controller.Avatar;
import src.controller.Item;
import src.controller.Terrain;
import src.model.MapDisplay_Relation;
import src.model.MapMain_Relation;
import src.view.Display;
import src.view.MapView;
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
/**
* Initializes, opens the program.
*
* @author JohnReedLOL.
*/
public class JohnsTest {
MapMain_Relation m = new MapMain_Relation();
/**
* @param args the command line arguments
*/
public static void main(String[] args) {
testEverything();
initializeEverything();
}
public static void testEverything() {
MapMain_Relation map_main = new MapMain_Relation();
map_main.bindToNewMapOfSize(3, 3);
Avatar a = new Avatar("a", '~', 0, 0);
a.setMap(map_main);
Terrain obstacle = new Terrain("boulder", 'O', true, false);
MapView map_view = new MapView();
map_main.addViewToMap(map_view);
map_main.initializeTerrain(obstacle, 2, 0);
System.out.println("Adding avatar. Error code: " + map_main.addAvatar(a, 0, 0));

Item equipable = new Item("i", 'i', true, true, false);
map_main.addItem(equipable, 0, 0);
int error_code1 = a.equipInventoryItem();

System.out.println("top: " + map_main.getTile(0, 0).getTopCharacter() + error_code1);
a.getMapRelation().pickUpItemInDirection(0, 0);
int error_code2 = a.equipInventoryItem();
System.out.println("top: " + map_main.getTile(0, 0).getTopCharacter()+ error_code2);

System.out.println(map_main.getTile(2, 0).isPassable());
System.out.println(map_main.getTile(0, 2).isPassable());
System.out.println(map_main.getTile(0, 0).isPassable());
         

char out = map_view.getMapRelation().getTileRepresentation(0, 0);
System.out.println("Mapview works: " + out);
//Example of mapview in use
Display _d = new Display(map_view);
_d.printView();
MapDisplay_Relation map_display = new MapDisplay_Relation(null);
map_display.associateWithMap(map_main.getMyMap());
System.out.println( "representation of avatar: " + map_display.getTileRepresentation(0, 0) );
System.out.println( "representation of empty space: " + map_display.getTileRepresentation(1, 0) );
//a.getMapRelation().getMapTile().getTopCharacter();
System.out.println("x cordinate: " + a.getMapRelation().getMyXCordinate());
System.out.println("y cordinate: " + a.getMapRelation().getMyYCordinate());
testMoveAvatar(a, 1, 0);
testMoveAvatar(a, 1, 0);
try {
testMoveAvatar(a, 1, 0);
} catch(Exception e) {
System.out.println("success - avatar walked off map");
}
//a.getMapRelation().addStatsPack(stats_pack);
System.out.println(a == a.getMapRelation().getAvatar());
System.out.println(
map_main.getTile(a.getMapRelation().getMyXCordinate(),
a.getMapRelation().getMyYCordinate())
== a.getMapRelation().getMapTile()
);
System.out.println(a.getStatsPack().toString());

a.getMapRelation().hurtWithinRadius(10, 5);
System.out.println(a.getStatsPack().toString());

a.getMapRelation().healWithinRadius(10, 1);
System.out.println(a.getStatsPack().toString());

a.getMapRelation().killWithinRadius(1);
System.out.println(a.getStatsPack().toString());

a.getMapRelation().levelUpWithinRadius(1);
System.out.println(a.getStatsPack().toString());

a.getMapRelation().pickUpItemInDirection(0, 0);
System.out.println(a.getStatsPack().toString());

a.addItemToInventory(null);
a.getMyView();
}
public static void testMoveAvatar(Avatar a, int x, int y) {
System.out.println("Moving avatar. Error code: " + a.getMapRelation().moveInDirection(x, y));
System.out.println("x cordinate: " + a.getMapRelation().getMyXCordinate());
System.out.println("y cordinate: " + a.getMapRelation().getMyYCordinate());
}
static void initializeEverything() {
// currently there is only one avatar
//Avatar avatar = new Avatar("avatar", 'x', 0, 0);
}
static void saveGameToDisk() {
}
static void resumeGameFromDisk() {
}
}