/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.Random;
import src.Effect;
import src.SkillEnum;

/**
 * Summoner Occupation, intellect +1.
 */
public final class Summoner extends Occupation {

    public Summoner(Entity e) {
        super(e);
    }
    private Staff staff_ = null;

    public void change_stats(EntityStatsPack current_stats) {
        //for sneak
        current_stats.increaseIntellectLevelByOne();
    }

    @Override
    public int equipOneHandWeapon(OneHandedWeapon weapon) {
        try {
            staff_ = (Staff) weapon;
            return 0;
        } catch (ClassCastException e) {
            return -1;
        }
    }

    @Override
    public int unEquipEverything() {
        staff_ = null;
        return 0;
    }

    @Override
    public int equipTwoHandWeapon(TwoHandedWeapon weapon) {
        return -1;
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
    public int performOccupationSkill(int number) {
        if (number <= 0 || number > 4) {
            System.err.println("Error in Summoner.performOccupationSkill()");
            System.exit(-109);
        }
        final int cost = 1;
        int has_run_out_of_mana = getEntity().getStatsPack().deductCurrentManaBy(cost);
        if (has_run_out_of_mana == 0) {
            if (number == 1) {
                // influencing another's behavior [Confusion spell]
                Random randomGenerator = new Random();
                Boolean isConfused = randomGenerator.nextBoolean();
                if (isConfused) {
                    super.getEntity().getMapRelation().sendAttackInFacingDirection();
                    super.getEntity().receiveAttack(10, null); // hurt myself
                } else {
                    super.getEntity().getMapRelation().getEntityInFacingDirection().
                            receiveAttack(getSkill_1_() * 5, null); // hurt enemy [no attack-back]
                }
            } else if (number == 2) {
                // boon - magic that heals
                super.getEntity().getMapRelation().areaEffectFunctor.effectAreaWithinRadius(getSkill_2_() * 2, getSkill_2_(), Effect.HEAL);
                super.getEntity().getMapRelation().areaEffectFunctor.effectAreaWithinLine(getSkill_2_() * 3, getSkill_2_(), Effect.HURT);
            } else if (number == 3) {
                // bane - magic that does damage or harm.
                super.getEntity().getMapRelation().areaEffectFunctor.effectAreaWithinLine(getSkill_3_() * 4, 2 * getSkill_3_(), Effect.HURT);
            } else if (number == 4) {
                // Staff attack
                if (staff_ != null) {
                    for (int num_attacks = 0; num_attacks <= super.getSkill_4_(); ++num_attacks) {
                        getEntity().getMapRelation().sendAttackInFacingDirection();
                    }
                }
            }
        } else {
            // you don't have enought manna and wasted it on trying to cast a spell
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Summoner";
    }
}
