/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import src.SkillEnum;

/**
 * Sneak Occupation, agility +1.
 */
public final class Sneak extends Occupation {

    public Sneak(Entity e) {
        super(e);
    }
    private Bow bow_ = null;

    @Override
    public void change_stats(EntityStatsPack current_stats) {
        //for sneak
        current_stats.increaseAgilityLevelByOne();

    }

    @Override
    public int equipOneHandWeapon(OneHandedWeapon weapon) {
        return -1;
    }

    @Override
    public int unEquipEverything() {
        bow_ = null;
        return 0;
    }

    @Override
    public int equipTwoHandWeapon(TwoHandedWeapon weapon) {
        try {
            bow_ = (Bow) weapon;
            return 0;
        } catch (ClassCastException e) {
            return -1;
        }
    }

    /**
     * Increments an occupation specific skill. Caller should decrement number
     * of skillpoints.
     *
     * @author John-Michael Reed
     * @param skill Which skill to increment
     * @return 0 on success, -1 if this occupation cannot increment this skill.
     */
    @Override
    public int incrementSkill(SkillEnum skill) {
        if (skill == SkillEnum.OCCUPATION_SKILL_1) {
            super.incrementSkill_1_();
            return 0;
        } else if (skill == SkillEnum.OCCUPATION_SKILL_2) {
            super.incrementSkill_2_();
            return 0;
        } else if (skill == SkillEnum.OCCUPATION_SKILL_3) {
            super.incrementSkill_3_();
            return 0;
        } else if (skill == SkillEnum.OCCUPATION_SKILL_4) {
            super.incrementSkill_4_();
            return 0;
        } else {
            System.exit(-1);
            return -99999; // F***ing impossible
        }
    }

    @Override
    public String toString() {
        return "Sneak";
    }
}
