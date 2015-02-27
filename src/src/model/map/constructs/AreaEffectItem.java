/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import src.Effect;

/**
 * Item that has an area of effect.
 *
 * @author JohnMichaelReed
 */
public class AreaEffectItem extends Item {

    private Effect effect_;

    private boolean hasBeenActivated;

    transient private int power_ = 10;

    /**
     * Constructor: Contains extra parameter, power, which gives the item a
     * strength.
     *
     * @param name
     * @param representation
     * @param is_passable
     * @param goes_in_inventory
     * @param is_one_shot
     * @param effect
     * @param power
     */
    public AreaEffectItem(String name, char representation,
            boolean goes_in_inventory, Effect effect, int power) {
        super(name, representation, goes_in_inventory);
        effect_ = effect;
        power_ = power;
        hasBeenActivated = false;
    }

    /**
     * Constructor: Does not contain power parameter.
     *
     * @param name
     * @param representation
     * @param is_passable
     * @param goes_in_inventory
     * @param is_one_shot
     * @param effect
     */
    public AreaEffectItem(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot, Effect effect) {
        super(name, representation, goes_in_inventory);
        effect_ = effect;
    }

    public int getPower() {
        return power_;
    }

    public String getEffect() {
        String s;
        switch (effect_) {
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
            default:
                s = "null";
        }
        return s;
    }

    public boolean hasBeenActivated() {
        return hasBeenActivated;
    }

    /**
     * Item performs action (HURT, HEAL, LEVEL, KILL)
     */
    @Override
    public void onWalkOver() {

        hasBeenActivated = true;

        // System.out.println("Item: " + this.toString() + " is being walked on.");
        if (this.isOneShot() && !this.goesInInventory()) {
            this.getMapRelation().getMapTile().removeTopItem();
        }
        this.getMapRelation().areaEffectFunctor.
                effectAreaWithinRadius(2, power_, effect_);
    }
}
