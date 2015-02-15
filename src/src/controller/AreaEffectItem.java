/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.view.Display;

/**
 *
 * @author JohnMichaelReed
 */
public class AreaEffectItem extends Item {

    // Alex probably didn't make serialization code for this 
    transient private Effect effect_;

    public enum Effect {

        HEAL, HURT, LEVEL, KILL
    }

    transient private int power_ = 10;

    AreaEffectItem(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot, Effect effect, int power) {
        super(name, representation, is_passable, goes_in_inventory, is_one_shot);
        effect_ = effect;
        power_ = power;
    }

    AreaEffectItem(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot, Effect effect) {
        super(name, representation, is_passable, goes_in_inventory, is_one_shot);
        effect_ = effect;
    }

    @Override
    public void onWalkOver() {
        System.out.println("Item: " + this.toString() + " is being walked on.");
        if (this.isOneShot() && !this.goesInInventory()) {
            this.getMapRelation().getMapTile().removeTopItem();
        }
        Display.setMessage("Walked on Item: " + this.toString(), 3);
        switch (effect_) {
            case HEAL:
                this.getMapRelation().hurtWithinRadius(power_, 2);
                break;
            case HURT:
                this.getMapRelation().hurtWithinRadius(power_, 2);
                break;
            case LEVEL:
                this.getMapRelation().levelUpWithinRadius(2);
                break;
            case KILL:
                this.getMapRelation().killWithinRadius(2);
                break;
        }
    }
}
