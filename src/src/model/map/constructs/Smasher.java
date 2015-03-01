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
public final class Smasher implements Occupation {

    public void change_stats(EntityStatsPack current_stats) {
        //for smasher
        current_stats.increaseStrengthLevelByOne();

    }
    @Override
    public int incrementSkill(SkillEnum skill) {
        if(skill == SkillEnum.OCCUPATION_SKILL_1) {
            
        } else if(skill == SkillEnum.OCCUPATION_SKILL_2) {
            
        } else if(skill == SkillEnum.OCCUPATION_SKILL_3) {
            
        } else if(skill == SkillEnum.OCCUPATION_SKILL_4) {
            
        }
    }

    @Override
    public String toString() {
    	return "Smasher";
    }
}
