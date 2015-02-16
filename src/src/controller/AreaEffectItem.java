/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.view.Display;

/**
 * Item that has an area of effect.
 * @author JohnMichaelReed
 */
public class AreaEffectItem extends Item {

    // Alex probably didn't make serialization code for this 
    transient /* TEMPORARY */ private Effect effect_;

    private boolean hasBeenActivated;
    
    public enum Effect {

        HEAL, HURT, LEVEL, KILL
    }

    transient private int power_ = 10;

    /**
     * Constructor: Contains extra parameter, power, which gives the item a strength.
     * @param name
     * @param representation
     * @param is_passable
     * @param goes_in_inventory
     * @param is_one_shot
     * @param effect
     * @param power
     */
    public AreaEffectItem(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot, Effect effect, int power) {
        super(name, representation, is_passable, goes_in_inventory, is_one_shot);
        effect_ = effect;
        power_ = power;
        hasBeenActivated = false;
    }

    /**
     * Constructor: Does not contain power parameter.
     * @param name
     * @param representation
     * @param is_passable
     * @param goes_in_inventory
     * @param is_one_shot
     * @param effect
     */
    public AreaEffectItem(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot, Effect effect) {
        super(name, representation, is_passable, goes_in_inventory, is_one_shot);
        effect_ = effect;
    }
    
    /**
     * Item performs action (HURT, HEAL, LEVEL, KILL)
     */
    @Override
    public void onWalkOver() {
    	
    	hasBeenActivated = true;

        System.out.println("Item: " + this.toString() + " is being walked on.");
        if (this.isOneShot() && !this.goesInInventory()) {
            this.getMapRelation().getMapTile().removeTopItem();
        }
        Display.setMessage("Walked on Item: " + this.toString(), 3);
        switch (effect_) {
            case HURT:
                this.getMapRelation().hurtWithinRadius(power_, 2);
                break;
            case HEAL:
                this.getMapRelation().healWithinRadius(power_, 2);
                break;
            case LEVEL:
                this.getMapRelation().levelUpWithinRadius(2);
                break;
            case KILL:
                this.getMapRelation().killWithinRadius(2);
                break;
        }
    }
    
    public boolean hasBeenActivated(){
    	return hasBeenActivated;
    }
    
    public int getPower(){
    	return power_;
    }
    
    public String getEffect(){
    	String s;
    	switch(effect_) {
    		case HURT:
    			s = "HURT";
    			break;
    		case HEAL:
    			s = "HEAL";
    			break;
    		case LEVEL:
    			s = "LEVEL";
    			break;
    		case KILL:
    			s = "KILL";
    			break;
    		default :
    			s = null;
    	}
    	return s;
    }
}