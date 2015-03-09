/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.ArrayList;
import java.util.Random;

import src.FacingDirection;
import src.HardCodedStrings;
import src.Key_Commands;
import src.RunGame;
import src.SkillEnum;
import src.io.view.display.Display;
import src.model.map.MapAvatar_Relation;

/**
 * Each avatar represents a player
 *
 * @author JohnReedLOL
 */
public final class Avatar extends Entity {

    private boolean isInExistance = true;

    public boolean getIsInExistance() {
        return isInExistance;
    }

    public Avatar(String name, char representation) {
        super(name, representation);
        setNumGoldCoinsWhenSpawned(0); // Avatars re-spawn with no cold coins.
    }

    // map_relationship_ is used in place of a map_reference_
    private MapAvatar_Relation map_relationship_;

    // map_relationship_ is used in place of a map_referance_
    private int num_skillpoints_ = 1;

    public int getNum_skillpoints_() {
        return num_skillpoints_;
    }

    /**
     * Same as superclass except increases skillpoints
     *
     * @param amount
     * @return number of level ups;
     */
    @Override
    public int gainExperiencePoints(int amount) {
        final int num_level_ups = super.gainExperiencePoints(amount);
        num_skillpoints_ += num_level_ups;
        return num_level_ups;
    }

    // Non-occupation specific skills
    private int bind_wounds_ = 1;

    public int getBind_wounds_() {
        return bind_wounds_;
    }

    public int bindWounds() {
        return 0;
    }

    private int bargain_ = 1;

    public int getBargain_() {
        return bargain_;
    }

    private int observation_ = 1;

    public int getObservation_() {
        return observation_;
    }

    public ArrayList<String> getInteractionOptionStrings() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Attack me. " + HardCodedStrings.attack);
        options.add("Start a conversation with me. " + HardCodedStrings.getChatOptions);
        options.add("Select a skill to use on me. " + HardCodedStrings.getAllSkills);
        options.add("Get a list of items that you can use on me. " + HardCodedStrings.getItemList);
        return options;
    }

    public ArrayList<String> getConversationStarterStrings() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Hello");
        return options;
    }

    public ArrayList<String> getConversationContinuationStrings(String what_you_just_said_to_me, Avatar who_is_talking_to_me) {
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

    /**
     * Gets information based on observation level. If the entity is facing up,
     * observation will work in the up direction.
     *
     * @author Reid Olsen
     * @return
     */
    public int observe() {
        Random rn = new Random();

        String s = "";

        // Get random number between 0 and 10.
        int chanceForSuccessfulObserve = rn.nextInt(11);
        // Checks if observe is succuessful, takes observation level into
        // account. If observation level is 11 or higher, success rate is %100.
        if (chanceForSuccessfulObserve >= (11 - observation_)) {
            Display.getDisplay().setMessage(
                    "Looking in direction: " + getFacingDirection());

            if (getFacingDirection() == FacingDirection.UP) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo(0, (i + 1));
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.UP_RIGHT) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo((i + 1), (i + 1));
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.RIGHT) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo((i + 1), 0);
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.DOWN_RIGHT) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo((i + 1), (i + 1));
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.DOWN) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo(0, (i + 1));
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.DOWN_LEFT) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo((i + 1), (i + 1));
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.LEFT) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo((i + 1), 0);
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            } else if (getFacingDirection() == FacingDirection.UP_LEFT) {
                for (int i = 0; i < observation_; ++i) {
                    s += " Tile " + (i + 1) + ": ";
                    try {
                        s += map_relationship_.getTileInfo((i + 1), (i + 1));
                        s += "\n";
                    } catch (NullPointerException e) {
                        s += "No tile here.\n";
                    }
                }
                Display.getDisplay().setMessage(s);
            }
            return 0;
        } else {
            Display.getDisplay().setMessage(
                    "Failed to look in direction: " + getFacingDirection());
            return -1;
        }
    }

    /**
     * Designates a skill point towards a skill.
     *
     * @author John-Michael Reed
     * @param skill
     * @return -2 if no skill points, -1 if skill cannot be spent [invalid
     * occupation]
     */
    public int spendSkillpointOn(SkillEnum skill) {
        if (num_skillpoints_ <= 0) {
            return -2;
        }
        Occupation occupation = this.getOccupation();
        switch (skill) {
            case BIND_WOUNDS:
                ++bind_wounds_;
                --num_skillpoints_;
                return 0;
            case BARGAIN:
                ++bargain_;
                --num_skillpoints_;
                return 0;
            case OBSERVATION:
                ++observation_;
                --num_skillpoints_;
                return 0;
            case OCCUPATION_SKILL_1:
                if (occupation == null) {
                    return -1;
                }
                int error_code = occupation.incrementSkill(skill);
                if (error_code == 0) {
                    --num_skillpoints_;
                }
                return error_code;
            case OCCUPATION_SKILL_2:
                if (occupation == null) {
                    return -1;
                }
                int error_code2 = occupation.incrementSkill(skill);
                if (error_code2 == 0) {
                    --num_skillpoints_;
                }
                return error_code2;
            case OCCUPATION_SKILL_3:
                if (occupation == null) {
                    return -1;
                }
                int error_code3 = occupation.incrementSkill(skill);
                if (error_code3 == 0) {
                    --num_skillpoints_;
                }
                return error_code3;
            case OCCUPATION_SKILL_4:
                if (occupation == null) {
                    return -1;
                }
                int error_code4 = occupation.incrementSkill(skill);
                if (error_code4 == 0) {
                    --num_skillpoints_;
                }
                return error_code4;
            default:
                System.exit(-1); // should never happen
                return -3;
        }
    }

    /**
     * Accepts a key command from the map
     *
     * @param command
     * @param optional_text - either the last thing that was said to you or the
     * thing you are about to say.
     * @return ArrayList of strings for IO_Bundle or null if nothing to display
     */
    public ArrayList<String> acceptKeyCommand(Key_Commands command, String optional_text) {
        MapAvatar_Relation mar = this.getMapRelation();
        if (mar == null) {
            System.out
                    .println("Avatar cannot be controlled without a MapAvatar_Relation");
            System.exit(-8);
        }
        Entity target = this.getMapRelation().getEntityInFacingDirection();
        switch (command) {
            case MOVE_DOWNLEFT:// Move SW
                mar.moveInDirection(-1, -1);
                return null;
            case MOVE_DOWN:// Move S
                mar.moveInDirection(0, -1);
                return null;
            case MOVE_DOWNRIGHT:// Move SE
                mar.moveInDirection(1, -1);
                return null;
            case MOVE_LEFT: // Move W
                mar.moveInDirection(-1, 0);
                return null;
            case MOVE_RIGHT:// Move E
                mar.moveInDirection(1, 0);
                return null;
            case MOVE_UPLEFT:// Move NW
                mar.moveInDirection(-1, 1);
                return null;
            case MOVE_UP:// Move N
                mar.moveInDirection(0, 1);
                return null;
            case MOVE_UPRIGHT: // Move NE
                mar.moveInDirection(1, 1);
                return null;
            case SAVE_GAME: // Save Game
                RunGame.saveGameToDisk(); // TODO: this is for testing, remove for
                // deployment
                return null;
            case USE_LAST_ITEM: // Use item in inventory
                super.useItemInFacingDirectionOnMyself();
                System.out.println("using item!");
                return null;
            case EQUIP_LAST_ITEM: // equipMyselfTo
                try {
                    EquipableItem item = (EquipableItem) this.getLastItemInInventory();
                    if (item != null) {
                        Display.getDisplay().setMessage("Attempted to Equip " + item.toString());
                    } else {
                        Display.getDisplay().setMessage("No item(s) to equip");
                    }
                    if (item != null) {
                        item.equipMyselfTo(this);
                        return null;
                    }
                } catch (ClassCastException e) {
                    // ignore it
                    Display.getDisplay().setMessage("Cannot Equip From Inventory");
                }
                return null;
            case UNEQUIP_EVERYTHING: // unEquip
                this.unEquipEverything();
                Display.getDisplay().setMessage("Unequipped Everything");
                return null;
            case DROP_LAST_ITEM: // drop item
                int error_code_D = mar.dropItem();
                return null;
            case PICK_UP_ITEM:// pickup item
                int error_code_p = mar.pickUpItemInDirection(0, 0);
                return null;
            case BECOME_SMASHER: // switch to Smasher
                this.setRepresentation('⚔');
                this.becomeSmasher();
                return null;
            case BECOME_SUMMONER: // switch to Summoner
                this.setRepresentation('☃');
                this.becomeSummoner();
                return null;
            case BECOME_SNEAK: // switch to Sneaker
                this.setRepresentation('☭');
                this.becomeSneak();
                return null;
            case BIND_WOUNDS:
                this.bindWounds();
                return null;
            case BARGAIN_AND_BARTER:
                if (target != null) {
                    return target.getInteractionOptionStrings();
                } else {
                    return null;
                }
            case OBSERVE:
                this.observe();
                return null;
            case USE_SKILL_1:
                this.getOccupation().performOccupationSkill(1);
                return null;
            case USE_SKILL_2:
                System.out.println("Performing Skill 2");
                this.getOccupation().performOccupationSkill(2);
                System.out.println("Already performed Skill 2");
                return null;
            case USE_SKILL_3:
                this.getOccupation().performOccupationSkill(3);
                return null;
            case USE_SKILL_4:
                this.getOccupation().performOccupationSkill(4);
                return null;
            case SPEND_SKILLPOINT_ON_BIND:
                this.spendSkillpointOn(SkillEnum.BIND_WOUNDS);
                return null;
            case SPEND_SKILLPOINT_ON_BARGAIN:
                this.spendSkillpointOn(SkillEnum.BARGAIN);
                return null;
            case SPEND_SKILLPOINT_ON_OBSERVE:
                this.spendSkillpointOn(SkillEnum.OBSERVATION);
                return null;
            case SPEND_SKILLPOINT_ON_SKILL_1:
                this.spendSkillpointOn(SkillEnum.OCCUPATION_SKILL_1);
                return null;
            case SPEND_SKILLPOINT_ON_SKILL_2:
                this.spendSkillpointOn(SkillEnum.OCCUPATION_SKILL_2);
                return null;
            case SPEND_SKILLPOINT_ON_SKILL_3:
                this.spendSkillpointOn(SkillEnum.OCCUPATION_SKILL_3);
                return null;
            case SPEND_SKILLPOINT_ON_SKILL_4:
                this.spendSkillpointOn(SkillEnum.OCCUPATION_SKILL_4);
                return null;
            case GET_INTERACTION_OPTIONS:
                if (target != null) {
                    return target.getInteractionOptionStrings();
                } else {
                    return null;
                }
            case GET_CONVERSATION_STARTERS:
                if (target != null) {
                    return target.getConversationStarterStrings();
                } else {
                    return null;
                }
            case GET_CONVERSATION_CONTINUATION_OPTIONS:
                if (target != null) {
                    return target.getConversationContinuationStrings(optional_text, this);
                } else {
                    return null;
                }
            case ATTACK:
                getMapRelation().sendAttackInFacingDirection();
                return null;
            default:
                System.out.println("Invalid command sent to avatar");
                break;
        }
        return null;
    }

    /**
     * Use this to call functions contained within the MapAvatar relationship
     *
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapAvatar_Relation getMapRelation() {
        return map_relationship_;
    }

    /**
     * Sets MapAvatar_Relation
     *
     * @param a
     */
    public void setMapRelation(MapAvatar_Relation a) {
        map_relationship_ = a;
    }

    @Override
    public void gameOver() {
        System.out.println("An avatar has run out of lives and is gone forever.");
        this.isInExistance = false;
        this.map_relationship_.removeMyselfFromTheMapCompletely();
    }

    /**
     * Avatars don't do anything when attacked.
     *
     * @author John-Michael Reed
     * @param damage - see super.receiveAttack()
     * @param attacker - see super.receiveAttack()
     * @return - see super.receiveAttack()
     */
    @Override
    public boolean receiveAttack(int damage, Entity attacker) {
        boolean isAlive = super.receiveAttack(damage, attacker);
        if (isAlive) {
            if (attacker != null) {
                System.out.println(name_ + " got attacked.");
            }
        }
        return isAlive;
    }

    @Override
    public String toString() {
        String s = "Avatar name: " + name_;

        s += "\n Inventory " + "(" + getInventory().size() + ")" + ":";
        for (int i = 0; i < getInventory().size(); ++i) {
            s += " " + getInventory().get(i).name_;
        }

        s += "\n";

        s += " map_relationship_: ";
        if (map_relationship_ == null) {
            s += "null";
        } else {
            s += "Not null";
        }

        s += "\n associated with map:"
                + map_relationship_.isAssociatedWithMap();

        return s;
    }
}
