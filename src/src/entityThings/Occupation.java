/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.entityThings;

/**
 * Interface for Occupations (Smasher, Sneak, Summoner). Different Occupations have different advantages.
 * @author JohnReedLOL
 */
public interface Occupation {
    public void change_stats(EntityStatsPack current_stats);
}
