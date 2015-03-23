package src.model.constructs;

import java.util.Random;

import src.Effect;
import src.HardCodedStrings;
import src.io.view.display.Display;

public class SummonerUltimate extends Summoner {

    public int getID() { return 26; }

    public SummonerUltimate(Entity e) {
        super(e);
    }

    public SummonerUltimate(Occupation o) {
        super(o);
    }

    public SummonerUltimate getACopyOfMyself() {
        return new SummonerUltimate(this);
    }

    @Override
    public String getSkillNameFromNumber(int skill_number) {
        super.getSkillNameFromNumber(skill_number); // checks input
        switch (skill_number) {
            case 1:
                return "Fear";
            case 2:
                return "Tank";
            case 3:
                return "Explode";
            case 4:
                return "Staff";
            default:
                System.err.println("Error in Summoner");
                System.exit(-54);
                return "";
        }
    }

    @Override
    public int performOccupationSkill(int number) {
        if (number <= 0 || number > 4) {
            System.err.println("Error in Summoner.performOccupationSkill()");
            System.exit(-109);
        }
        final int cost = 3;
        System.out.println("Starting skill 2: DEBUG");
        int has_run_out_of_mana = getEntity().getStatsPack().deductCurrentManaBy(cost);
        Entity target = super.getEntity().getMapRelation().getEntityInFacingDirection(5+getEntity().getStatsPack().getIntellect_level_());
        if (has_run_out_of_mana == 0) {
            if (number == 1) {
                // enchantment, puts target to sleep (stops them from chasing until more damage is taken)
                Random randomGenerator = new Random();
                double failed = randomGenerator.nextDouble();
                if (failed + getSkill_1_() * .1 > .6) {
                    if (target != null) {
                        ((Monster) target).causeFear(super.getEntity(), (3 + getSkill_1_())*2);
                        Display.getDisplay().setMessage("Succesfully casted Fear!");
                    } else {
                        // get your mana back
                        getEntity().getStatsPack().increaseCurrentManaBy(cost);
                        Display.getDisplay().setMessage("Spell Succeeded, but not a monster! The entity was "
                        		+ "kind enough to return your mana to you");
                    }
                } else {
                	if(target ==null){
                		Display.getDisplay().setMessage("NO VALID TARGET");
                	}else{
                		Display.getDisplay().setMessage("SPELL FAILED");
                		target.receiveAttack(0, super.getEntity());
                		Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(1) + ".");
                	}
                }
            } else if (number == 2) {
                // boon - magic that heals
                Random randomGenerator = new Random();
                double failed = randomGenerator.nextDouble();
                if (failed + getSkill_2_() * .1 > .4) {
                    EntityStatsPack boon_stats = new EntityStatsPack();
                    for (int i = 0; i < 4 + getSkill_2_() * 2; i++) {
                        boon_stats.increaseDefenseLevelByOne();
                    }
                    for (int i = 0; i < 4 + getSkill_2_() * 2; i++) {
                        boon_stats.increaseStrengthLevelByOne();
                    }
                    boon_stats_ = boon_stats;
                    boon_timer_ = 2 + getSkill_2_();
                } else {
                    Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(2) + ".");
                }
            } else if (number == 3) {
                // bane - magic that does damage or harm.
                System.out.println("About to call Bane");
                Random randomGenerator = new Random();
                double failed = randomGenerator.nextDouble();
                if (failed + getSkill_3_() * .1 > .6) {
                    super.getEntity().getMapRelation().areaEffectFunctor.effectAreaWithinRadius(getSkill_3_() + 4, 6 + 6 * getSkill_3_(), Effect.HURT);
                } else {
                    Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(3) + ".");
                }
                System.out.println("Finished calling Bane");
            }
        } else if (number == 4) {
            // Staff attack
            Random randomGenerator = new Random();
            double failed = randomGenerator.nextDouble();
            if (failed + getSkill_4_() * .1 > .6) {
                if (staff_ != null && target != null) {
                    super.getEntity().sendAttack(target);
                    target.receiveAttack(getSkill_4_() * 2, null);
                }
            } else {
                Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(4) + ".");
            }
        } else {
            System.out.println("Out of mana");
        }
        return 0;
    }

    @Override
    public Summoner switchToNextSubOccupation() {
        return new SummonerRookie(this);
    }

}
