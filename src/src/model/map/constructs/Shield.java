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
public class Shield extends EquipableItem implements SecondaryHandHoldable {

    public Shield(String name, char representation) {
        super(name, representation);
        this.getStatsPack().addOn(new DrawableThingStatsPack(0,10));
    }
    public int equipMyselfTo(Entity to_equip) {
        return to_equip.equipShield(this);
    }
}
