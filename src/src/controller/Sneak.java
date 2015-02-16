/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.controller;

/**
 * Sneak Occupation, agility +1.
 */
public final class Sneak  implements Occupation
{
    public void change_stats(EntityStatsPack current_stats) {
        //for sneak
        current_stats.agility_level_ += 1;

    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("OCSNEAK", 35);
    // </editor-fold>
    
    @Override
    public String toString() {
    	return "Sneak";
    }
}
