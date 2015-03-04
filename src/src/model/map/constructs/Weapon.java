/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

/**
 *
 * @author JohnReedLOL
 */
public abstract class Weapon extends EquipableItem {

    public Weapon(String name, char representation, String eBy_) {
        super(name, representation);
        equippableBy_ = eBy_;
    }
    private String equippableBy_;
    public boolean isEquppable(Occupation occ){
    	if(occ instanceof Sneak){
    		if(equippableBy_.equals("Sneak")){return true;}
    		return false;
    	}
    	if(occ instanceof Smasher){
    		if(equippableBy_.equals("Summoner")){return true;}
    		return false;
    	}
    	if(occ instanceof Summoner){
    		if(equippableBy_.equals("Summoner")){return true;}
    		return false;
    	}
    	return false;//Void occupation can't equip anything!
    }
}
