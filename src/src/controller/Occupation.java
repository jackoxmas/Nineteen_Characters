/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

/**
 * Represents the eight directions plus one direction for staying in place
 * @author JohnReedLOL
 */
public interface Occupation {
    public StatsPack change_stats(StatsPack current_stats);

}
