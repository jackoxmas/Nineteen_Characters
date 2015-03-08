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
     * @author John-Michael Reed
     * @param recieved_text - what was said to me
     * @param speaker - the person who I am talking to
     * @return - what I said back
     */
    @Override
    public ArrayList<String> reply(String recieved_text, Entity speaker) {
        return super.reply(recieved_text, speaker);
    }

    /**
     * Monsters will attack back.
     *
     * @param damage
     * @param attacker
     */
    @Override
    public boolean receiveAttack(int damage, Entity attacker) {
        boolean isAlive = super.receiveAttack(damage, attacker);
        if (isAlive) {
            if (attacker != null) {
                this.sendAttack(attacker);
            }
        }
        return isAlive;
    }
}
