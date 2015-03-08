/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.Random;
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
    public int performOccupationSkill(int number) {
        if (number <= 0 || number > 4) {
            System.err.println("Error in Sneak.performOccupationSkill()");
            System.exit(-68);
        }
        final int cost = 1;
        int has_run_out_of_mana = getEntity().getStatsPack().deductCurrentManaBy(cost);
        if (has_run_out_of_mana == 0) {
            if (number == 1) {
                //...
            } else if (number == 2) {
                // detect & remove trap
                Item potential_trap = getEntity().getMapRelation().getTopmostItemInFacingDirection();
                Trap trap = null;
                try {
                    trap = (Trap) potential_trap;
                    // it is actually a trap
                    Random randomGenerator = new Random();
                    final int chance_of_detection = randomGenerator.nextInt(10) + super.getSkill_2_();
                    if (chance_of_detection < 5) {
                        // failed to detect the trap
                        System.out.println("Failed to detect trap.");
                    } else {
                        // detected the trap
                        trap.setViewable(true);
                        System.out.println("Trap detected.");
                        trap.removeMyselfFromMap();
                    }
                } catch (ClassCastException e) {
                    // it is not a trap. 
                    // Take back your manna
                    getEntity().getStatsPack().increaseCurrentManaBy(cost);
                    return 0;
                }
            } else if (number == 3) {
                // become invisible [or visible]
                boolean is_visible = getEntity().isVisible();
                getEntity().setViewable(!is_visible);
            } else if (number == 4) {
                // Bow attack
                if (bow_ != null) {
                    // Case that you have enough mana:
                    if (has_run_out_of_mana == 0) {
                        for (int num_attacks = 0; num_attacks <= super.getSkill_4_(); ++num_attacks) {
                            getEntity().getMapRelation().sendAttackInFacingDirection();
                        }
                    } else {
                        // Not enough mana to case spell. Do nothing.
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
        return "Sneak";
    }
}
