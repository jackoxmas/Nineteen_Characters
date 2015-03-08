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
public class Monster extends Entity {

    public Monster(String name, char representation) {
        super(name, representation);
    }

    public ArrayList<String> getInteractionOptionStrings() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Attack me. [ Attack ]");
        return options;
    }

    public ArrayList<String> getConversationStarterStrings() {
        ArrayList<String> options = new ArrayList<String>();
        return options;
    }

    public ArrayList<String> getConversationContinuationStrings(String what_you_just_said_to_me) {
        return endConversation();
    }

    public ArrayList<String> getListOfItemsYouCanUseOnMe() {
        ArrayList<String> options = new ArrayList<String>();
        return options;
    }

    /**
     * Monsters will attack back.
     * @param damage
     * @param attacker 
     */
    @Override
    public void receiveAttack(int damage, Entity attacker) {
        int amount_of_damage = damage - getStatsPack().getDefensive_rating_() - getStatsPack().getArmor_rating_();
        if (amount_of_damage < 0) {
            amount_of_damage = 0;
        }
        getStatsPack().deductCurrentLifeBy(amount_of_damage);
        boolean isAlive = isAlive();
        if (isAlive) {
            if (attacker != null) {
                this.sendAttack(attacker);
            }
        }
    }
}
