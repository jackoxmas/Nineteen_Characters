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

    private final Entity occupation_holder_;
    protected Entity getEntity() {
        return occupation_holder_;
    }

    private Occupation() {
        occupation_holder_ = null;
    }

    public Occupation(Entity occupation_holder) {
        occupation_holder_ = occupation_holder;
    }

    private int[] skills_levels_ = {1,1,1,1};
    
    /**
     * Goes from one to four
     * @param number
     * @return 
     */
    abstract public int performOccupationSkill(int number);

    public int getSkill_1_() {
        return skills_levels_[0];
    }

    public int getSkill_2_() {
        return skills_levels_[1];
    }

    public int getSkill_3_() {
        return skills_levels_[2];
    }

    public int getSkill_4_() {
        return skills_levels_[3];
    }

    public int incrementSkill_1_() {
        return ++skills_levels_[0];
    }

    public int incrementSkill_2_() {
        return ++skills_levels_[1];
    }

    public int incrementSkill_3_() {
        return ++skills_levels_[2];
    }

    public int incrementSkill_4_() {
        return ++skills_levels_[3];
    }

    /**
     * @author John-Michael Reed
     * @param weapon weapon to be equipped
     * @return 0 on success, -1 on failure
     */
    
     public abstract int equipOneHandWeapon(OneHandedWeapon weapon);
     public abstract int equipTwoHandWeapon(TwoHandedWeapon weapon);
     public abstract int unEquipEverything();
     public abstract void change_stats(EntityStatsPack current_stats);

     public abstract int incrementSkill(SkillEnum skill);
     public String getSkillsName(int i){
    	 return "";
     }
     public int getSkillLevel(int i){
    	 if(i > 0 && i < skills_levels_.length){
    		 return skills_levels_[i];
    	 }
    	 return -1;
     }
}
