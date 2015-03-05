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
    private TwoHandedSword two_handed_sword_ = null;
    private OneHandedSword one_handed_sword_ = null;
    private OneHandedWeapon bullshit_weapon_one_ = null;
    private TwoHandedWeapon bullshit_weapon_two_ = null;

    public enum ActiveWeapon {

        FISTS, TWO_HANDED_SWORD, ONE_HANDED_SWORD, TWO_HANDED_BULLSHIT, ONE_HANDED_BULLSHIT
    }
    private ActiveWeapon current_weapon = ActiveWeapon.FISTS;

    public void change_stats(EntityStatsPack current_stats) {
        //for smasher
        current_stats.increaseStrengthLevelByOne();
    }

    @Override
    public int equipOneHandWeapon(OneHandedWeapon weapon) {
        try {
            one_handed_sword_ = (OneHandedSword) weapon;
            bullshit_weapon_one_ = null;
            current_weapon = ActiveWeapon.ONE_HANDED_SWORD;
            return 0;
        } catch (ClassCastException e1) {
            bullshit_weapon_one_ = weapon;
            one_handed_sword_ = null;
            current_weapon = ActiveWeapon.ONE_HANDED_BULLSHIT;
            return 0;
        }
    }

    @Override
    public int unEquipEverything() {
        two_handed_sword_ = null;
        one_handed_sword_ = null;
        bullshit_weapon_one_ = null;
        bullshit_weapon_two_ = null;
        current_weapon = ActiveWeapon.FISTS;
        return 0;
    }

    /**
     * Equips a two handed weapon to an occupation for occupation specific bonuses.
     * @author John-Michael Reed
     * @param weapon
     * @return 
     */
    @Override
    public int equipTwoHandWeapon(TwoHandedWeapon weapon) {
        try {
            two_handed_sword_ = (TwoHandedSword) weapon;
            bullshit_weapon_two_ = null;
            current_weapon = ActiveWeapon.TWO_HANDED_SWORD;
            return 0;
        } catch (ClassCastException e1) {
            bullshit_weapon_two_ = weapon;
            one_handed_sword_ = null;
            current_weapon = ActiveWeapon.TWO_HANDED_BULLSHIT;
            return 0;
        }
    }

    /**
     * Increments an occupation specific skill. Caller should decrement number
     * of skillpoints.
     *
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
