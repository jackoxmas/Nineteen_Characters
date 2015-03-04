/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import src.SkillEnum;

/**
 * Interface for Occupations (Smasher, Sneak, Summoner). Different Occupations
 * have different advantages.
 *
 * @author JohnReedLOL
 */
public abstract class Occupation {
    
    protected final Entity occupation_holder_;
    private Occupation(){occupation_holder_ = null;}
    public Occupation(Entity occupation_holder){
        occupation_holder_ = occupation_holder;
    }
    
    private int skill_1_ = 1;
    private int skill_2_ = 1;
    private int skill_3_ = 1;
    private int skill_4_ = 1;

    public int getSkill_1_() {
        return skill_1_;
    }

    public int getSkill_2_() {
        return skill_2_;
    }

    public int getSkill_3_() {
        return skill_3_;
    }

    public int getSkill_4_() {
        return skill_4_;
    }

    public int incrementSkill_1_() {
        return ++skill_1_;
    }

    public int incrementSkill_2_() {
        return ++skill_2_;
    }

    public int incrementSkill_3_() {
        return ++skill_3_;
    }

    public int incrementSkill_4_() {
        return ++skill_4_;
    }

    public abstract void change_stats(EntityStatsPack current_stats);

    public abstract int incrementSkill(SkillEnum skill);

    public abstract int getOccNumber();
}
