/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import src.SkillEnum;

/**
 * Smasher Occupation, Strength +1.
 */
public final class Smasher extends Occupation {
    
    public Smasher(Entity e) {
        super(e);
    }

    public void change_stats(EntityStatsPack current_stats) {
        //for smasher
        current_stats.increaseStrengthLevelByOne();

    }
    /**
     * Increments an occupation specific skill. Caller should decrement number of skillpoints.
     * @param skill Which skill to increment
     * @return 0 on success, -1 if this occupation cannot increment this skill.
     */
    @Override
    public int incrementSkill(SkillEnum skill) {
        if(skill == SkillEnum.OCCUPATION_SKILL_1) {
            super.incrementSkill_1_();
            return 0;
        } else if(skill == SkillEnum.OCCUPATION_SKILL_2) {
            super.incrementSkill_2_();
            return 0;
        } else if(skill == SkillEnum.OCCUPATION_SKILL_3) {
            super.incrementSkill_3_();
            return 0;
        } else if(skill == SkillEnum.OCCUPATION_SKILL_4) {
            // Smasher does not have four skills.
            return -1; // error cannot increment skill.
        } else {
            System.exit(-1);
            return -99999; // F***ing impossible
        }
    }

    @Override
    public String toString() {
    	return "Smasher";
    }
}
