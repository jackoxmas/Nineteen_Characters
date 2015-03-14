/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.io.Serializable;

/**
 *
 * @author JohnReedLOL
 */
public class Bow extends TwoHandedWeapon implements Serializable {
    public Bow(String name, char representation) {
        super(name, representation);
    }
}
