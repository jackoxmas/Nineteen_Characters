/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.ArrayList;

/**
 *
 * @author JohnReedLOL
 */
public class Villager extends Entity {

    public ArrayList<String> getInteractionOptionStrings() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Attack me. [ Attack ]");
        options.add("Start a convetsation with me. [ Greet ]");
        options.add("Get a list of items that you can use on me. [ Item ]");
        return options;
    }

    public ArrayList<String> getConversationStarterStrings() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Hello");
        return options;
    }

    public ArrayList<String> getConversationContinuationStrings(String what_you_just_said_to_me) {
        ArrayList<String> options = new ArrayList<String>();
        if (what_you_just_said_to_me == "Hello") {
            options.add("Goodbye");
            return options;
        } else {
            return endConversation();
        }
    }

    public ArrayList<String> getListOfItemsYouCanUseOnMe() {
        ArrayList<String> options = new ArrayList<String>();
        return options;
    }

    public Villager(String name, char representation) {
        super(name, representation);
    }

    /**
     * Villagers automatically run away [in the opposite direction of attacker]
     * when attacked
     *
     * @author John-Michael Reed
     * @param attacker
     * @return 0 if reply succeeded, non-zero otherwise [failed to run away,
     * trapped]
     */
    @Override
    public int replyToAttackFrom(Entity attacker) {
        if (attacker == null) {
            return -1;
        }
        final int attackerX = attacker.getMapRelation().getMyXCoordinate();
        final int attackerY = attacker.getMapRelation().getMyYCoordinate();

        final int myX = this.getMapRelation().getMyXCoordinate();
        final int myY = this.getMapRelation().getMyYCoordinate();

        if (myX == attackerX && myY == attackerY) {
            System.exit(-6); // Impossible
            return -999;
        } else if (myX == attackerX && myY > attackerY) {
            return this.getMapRelation().moveInDirection(0, 1);
        } else if (myX == attackerX && myY < attackerY) {
            return this.getMapRelation().moveInDirection(0, -1);
        } else if (myX > attackerX && myY == attackerY) {
            return this.getMapRelation().moveInDirection(1, 0);
        } else if (myX < attackerX && myY == attackerY) {
            return this.getMapRelation().moveInDirection(-1, 0);
        } else if (myX > attackerX && myY > attackerY) {
            return this.getMapRelation().moveInDirection(1, 1);
        } else if (myX < attackerX && myY < attackerY) {
            return this.getMapRelation().moveInDirection(-1, -1);
        } else if (myX > attackerX && myY < attackerY) {
            return this.getMapRelation().moveInDirection(1, -1);
        } else if (myX < attackerX && myY > attackerY) {
            return this.getMapRelation().moveInDirection(-1, 1);
        } else {
            System.exit(-9); // Impossible
            return -999;
        }
    }
}
