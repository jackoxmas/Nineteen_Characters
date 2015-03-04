/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.ArrayList;

/**
 *
 * @author JohnReedLOL
 */
public abstract class Weapon extends EquipableItem {

    public Weapon(String name, char representation, String eBy_) {
        super(name, representation);
        equippableBy_ = new ArrayList<String>();
        equippableBy_.add(eBy_);
    }
    public Weapon(String name, char representation, ArrayList<String> eBy_) {
        super(name, representation);
        equippableBy_ =eBy_;
    }
    private ArrayList<String> equippableBy_;
    public boolean isEquppable(Occupation occ){
    	if(occ instanceof Sneak){
    		for(String equipBy_ : equippableBy_){
    			if(equipBy_.equals("Sneak")){return true;}
    		}
    		return false;
    	}
    	if(occ instanceof Smasher){
    		return true;
    	}
    	if(occ instanceof Summoner){
    		for(String equipBy_ : equippableBy_){
    			if(equipBy_.equals("Summoner")){return true;}
    		}
    		return false;
    	}
    	return false;//Void occupation can't equip anything!
    }
}
