package src;

import src.controller.Avatar;
import src.controller.Entity;
import src.model.MapMain_Relation;

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
public class Main {

    MapMain_Relation m = new MapMain_Relation();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testEverything();
        initializeEverything();
    }

    public static void testEverything() {
        MapMain_Relation m = new MapMain_Relation();
        Avatar a = new Avatar("a", 'x', 0, 0);
        System.out.println("Adding avatar. Error code: " + m.addAvatar(a, 0, 0));
        System.out.println("x cordinate: " + a.getMapRelation().getMyXCordinate());
        System.out.println("y cordinate: " + a.getMapRelation().getMyYCordinate());

        testMoveAvatar(a, 1, 0);
        testMoveAvatar(a, 1, 0);
        try {
            testMoveAvatar(a, 1, 0);
        } catch(Exception e) {
            System.out.println("success - avatar walked off map");
        }
    }

    public static void testMoveAvatar(Avatar a, int x, int y) {
        System.out.println("Moving avatar. Error code: " + a.getMapRelation().moveInDirection(x, y));
        System.out.println("x cordinate: " + a.getMapRelation().getMyXCordinate());
        System.out.println("y cordinate: " + a.getMapRelation().getMyYCordinate());
    }

    static void initializeEverything() {
        // currently there is only one avatar
        Avatar avatar = new Avatar("avatar", 'x', 0, 0);
    }

    static void saveGameToDisk() {

    }

    static void resumeGameFromDisk() {

    }

}