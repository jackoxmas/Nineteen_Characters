package src;

import src.controller.Avatar;
import src.model.MapMain_Relation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** 
 * Initializes, opens the program.
 * @author JohnReedLOL.
 */
public class Main
{
    MapMain_Relation m = new MapMain_Relation();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
    	System.out.println("Ran");
        initializeEverything();
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
