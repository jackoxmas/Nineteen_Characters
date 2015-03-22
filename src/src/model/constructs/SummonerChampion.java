package src.model.constructs;

import java.util.Random;

import src.Effect;
import src.HardCodedStrings;
import src.io.view.display.Display;

public class SummonerChampion extends Summoner {

    public SummonerChampion(Entity e) {
        super(e);
    }

    public SummonerChampion(Occupation o) {
        super(o);
    }
	
    @Override
    public String getSkillNameFromNumber(int skill_number) {
        super.getSkillNameFromNumber(skill_number); // checks input
        switch (skill_number) {
            case 1:
                return "Sleep";
            case 2:
                return "Heal Self";
            case 3:
                return "Flammenwerfer";
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
        Entity target = super.getEntity().getMapRelation().getEntityInFacingDirection();
        if (has_run_out_of_mana == 0) {
            if (number == 1) {
                // enchantment, puts target to sleep (stops them from chasing until more damage is taken)
                Random randomGenerator = new Random();
                double failed = randomGenerator.nextDouble();
                if (failed + getSkill_1_()*.1 > .6) {
                    if (target != null) {
                    	try { ((Monster)target).stopFollowing(); } catch (Exception e) {}
                    } else {
                        // get your mana back
                        getEntity().getStatsPack().increaseCurrentManaBy(cost);
                    }
                } else {
                	target.receiveAttack(0, super.getEntity());
                    Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(1) + ".");
                }
            } else if (number == 2) {
                // boon - magic that heals
                Random randomGenerator = new Random();
                double failed = randomGenerator.nextDouble();
                if (failed + getSkill_2_()*.1 > .4) {
                	super.getEntity().getMapRelation().areaEffectFunctor.effectAreaWithinRadius(1, 4 + getSkill_2_() * 4, Effect.HEAL);
                } else {
                    Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(2) + ".");
                }
            } else if (number == 3) {
                // bane - magic that does damage or harm.
                System.out.println("About to call Bane");
                Random randomGenerator = new Random();
                double failed = randomGenerator.nextDouble();
                if (failed + getSkill_3_()*.1 > .5) {
                	super.getEntity().getMapRelation().areaEffectFunctor.effectAreaWithinLine(getSkill_3_() + 4, 2 + 4 * getSkill_3_(), Effect.HURT);
                } else {
                    Display.getDisplay().setMessage(HardCodedStrings.failed + getSkillNameFromNumber(3) + ".");
                }
                System.out.println("Finished calling Bane");
            }
        } else if (number == 4) {
            // Staff attack
            Random randomGenerator = new Random();
            double failed = randomGenerator.nextDouble();
            if (failed + getSkill_4_()*.1 > .6) {
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
		return new SummonerUltimate(this);
	}
	
}
