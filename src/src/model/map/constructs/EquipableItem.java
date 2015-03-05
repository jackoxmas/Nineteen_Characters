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
public abstract class EquipableItem extends PickupableItem{
    public EquipableItem(String name, char representation) {
        super(name, representation);
    }
    public abstract int equipMyselfTo(Entity to_equip);
}
