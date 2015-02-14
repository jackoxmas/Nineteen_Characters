/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.controller;

/**
 *
 * @author JohnReedLOL
 */
public final class Sneak  implements Occupation
{
    public StatsPack change_stats(StatsPack current_stats){
        //for sneak
        StatsPack level_up_stats = new StatsPack(0,1,2,1,1,0,1,0,1);
        return current_stats.add(level_up_stats);
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("OCSNEAK", 35);
    // </editor-fold>
}
