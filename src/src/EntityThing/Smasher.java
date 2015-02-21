/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.EntityThing;

/**
 * Smasher Occupation, Strength +1.
 */
public final class Smasher implements Occupation {

    public void change_stats(EntityStatsPack current_stats) {
        //for smasher
        current_stats.strength_level_ += 1;

    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("OCSMASHER", 35);
    // </editor-fold>
    
    @Override
    public String toString() {
    	return "Smasher";
    }
}
