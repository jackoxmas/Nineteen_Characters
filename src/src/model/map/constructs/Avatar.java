/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import src.SavedGame;
import src.SkillEnum;
import src.model.map.MapAvatar_Relation;
import src.io.view.AvatarCreationView;
import src.io.view.MapView;
import src.io.view.StatsView;
import src.io.view.Viewport;

/**
 * Each avatar represents a player
 *
 * @author JohnReedLOL
 */
public final class Avatar extends Entity {

    // map_relationship_ is used in place of a map_referance_
    private MapView map_view_;
    private StatsView stats_view_;

    private int num_skillpoints_ = 1;

    public int getNum_skillpoints_() {
        return num_skillpoints_;
    }

    /**
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

    /**
     * this function levels up an entity Modified to make it "gain enough
     * experience to level up"
     *
     * @author John
     */
    @Override
    public void gainEnoughExperienceTolevelUp() {
        super.gainEnoughExperienceTolevelUp();
        ++num_skillpoints_; // gain a skillpoint on level up
    }

    // Non-occupation specific skills
    private int bind_wounds_ = 1;

    public int getBind_wounds_() {
        return bind_wounds_;
    }
    private int bargain_ = 1;

    public int getBargain_() {
        return bargain_;
    }
    private int observation_ = 1;

    public int getObservation_() {
        return observation_;
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
     * @return 0 on success, not zero if command cannot be accepted
     */
    public int acceptKeyCommand(char command) {
        return 0;
    }

    public Avatar(String name, char representation) {
        super(name, representation);
        map_view_ = null;
        generateStatsView();//Statsview done
        current_viewport_ = new AvatarCreationView(this);
    }

    // holds the views
    private Viewport current_viewport_;

    /**
     * Used to return the current view of the Avatar
     *
     * @return
     */
    public Viewport getMyView() {
        return this.current_viewport_;
    }

    /**
     * Handles Avatar input.
     *
     * @param c
     */
    public void getInput(char c) {
        if (current_viewport_ == map_view_) {//If we currently have our mapview equipped(check by reference)

            MapAvatar_Relation mar = this.getMapRelation();
            if (mar == null) {
                System.out.println("Avatar cannot be controlled without a MapAvatar_Relation");
                return;
            }//If the avatar is not on the map, it can't really do anything.
            map_view_.setCenter(mar.getMyXCoordinate(), mar.getMyYCoordinate());
            switch (c) {
                case '1'://Move SW
                    mar.moveInDirection(-1, -1);
                    break;
                case '2'://Move S
                    mar.moveInDirection(0, -1);
                    break;
                case '3'://Move SE
                    mar.moveInDirection(1, -1);
                    break;
                case '4': // Move W
                    mar.moveInDirection(-1, 0);
                    break;
                case '6'://Move E
                    mar.moveInDirection(1, 0);
                    break;
                case '7'://Move NW
                    mar.moveInDirection(-1, 1);
                    break;
                case '8'://Move N
                    mar.moveInDirection(0, 1);
                    break;
                case '9': //Move NE
                    mar.moveInDirection(1, 1);
                    break;
                case 'S': //Save game
                    saveGame();
                    break;
                case 'v': //Open stats
                    break;
                case 'i': //Use item in direction
                    switchToStatsView();
                    break;
                case 'u': //Use item in inventory
                    int error_code_u = this.useLastInventoryItem();
                    break;
                case 'q'://move NW
                    mar.moveInDirection(-1, 1);
                    break;
                case 'w': //move N
                    mar.moveInDirection(0, 1);
                    break;
                case 'e'://move NE
                    mar.moveInDirection(1, 1);
                    break;
                case 'a': //move W
                    mar.moveInDirection(-1, 0);
                    break;
                case 's'://Move stationary?
                    mar.moveInDirection(0, 0);
                    break;
                case 'd'://Move E
                    mar.moveInDirection(1, 0);
                    break;
                case 'z'://Move SW
                    mar.moveInDirection(-1, -1);
                    break;
                case 'x'://move s
                    mar.moveInDirection(0, -1);
                    break;
                case 'c'://move SE
                    mar.moveInDirection(1, -1);
                    break;
                case 'D': //drop item
                    int error_code_D = mar.dropItem();
                    break;
                case 'E': // equip
                    this.equipInventoryItem();
                    break;
                case 'U': // unEquip
                    unEquipInventoryItem();
                    break;
                case 'p'://pickup item
                    int error_code_p = mar.pickUpItemInDirection(0, 0);
                    break;
                default: //no valid input
                    break;
            }
            current_viewport_.renderToDisplay(); //See lower comment, maybe avatar should have a Display also to print it's views?
        } else {
            current_viewport_.getInput(c);
            current_viewport_.renderToDisplay();//Although printing with display already calls this, might just want to move the display into avatar or something, not really sure
        }
    }

    // map_relationship_ is used in place of a map_referance_
    private MapAvatar_Relation map_relationship_;

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
    /* Make sure to call set map after this!
     * 
     */

    private void saveGame() {
        SavedGame saveGame = new SavedGame("save.dave");
        saveGame.saveGame(this);
    }

    public void generateMapView(MapViewable _map) {
        map_view_ = new MapView(_map);

    }

    private void generateStatsView() {
        stats_view_ = new StatsView(this);
    }

    /**
     * Avatars automatically do nothing when attacked
     *
     * @author John-Michael Reed
     * @param attacker
     * @return 0 if reply succeeded, non-zero otherwise [ex. if entity is null
     * or off the map]
     */
    @Override
    public int replyToAttackFrom(Entity attacker) {
        if (attacker == null) {
            return -1;
        }
        // return this.getMapRelation().sendAttack(attacker);
        return 0;
    }

    /**
     * determine if input is not important or if we already did something then
     * if true
     *
     * storedInput = '~';
     */
    public void sendInput(char current) {
        if (map_relationship_ == null) {
            System.out.println("Avatar cannot be controlled without a MapAvatar_Relation");
            return;
        } else if (current_viewport_ != map_view_) {
            current_viewport_.getInput(current);
            current_viewport_.renderToDisplay(); //See lower comment, maybe avatar should have a Display also to print it's views?
        } else {
            map_view_.setCenter(map_relationship_.getMyXCoordinate(), map_relationship_.getMyYCoordinate());
            if (storedInput == 'p') {
                int error_code = 0;
                switch (current) {
                    case '1':
                        error_code = map_relationship_.pickUpItemInDirection(0, -1);
                        break;
                    case '2':
                        error_code = map_relationship_.pickUpItemInDirection(0, -1);
                        break;
                    case '3':
                        error_code = map_relationship_.pickUpItemInDirection(1, -1);
                        break;
                    case '4':
                        error_code = map_relationship_.pickUpItemInDirection(-1, 0);
                        break;
                    case '5':
                        error_code = map_relationship_.pickUpItemInDirection(0, 0);
                        break;
                    case '6':
                        error_code = map_relationship_.pickUpItemInDirection(1, 0);
                        break;
                    case '7':
                        error_code = map_relationship_.pickUpItemInDirection(-1, 1);
                        break;
                    case '8':
                        error_code = map_relationship_.pickUpItemInDirection(0, 1);
                        break;
                    case '9':
                        error_code = map_relationship_.pickUpItemInDirection(1, 1);
                        break;
                }
                storedInput = '~';
                if (error_code != -1) {
                    System.out.println("pickUpItem function failed to get an item");
                }
            } else if (storedInput == 'u') {
                switch (current) {
                    case '1':
                        map_relationship_.sendAttack(0, -1);
                        break;
                    case '2':
                        map_relationship_.sendAttack(0, -1);
                        break;
                    case '3':
                        map_relationship_.sendAttack(1, -1);
                        break;
                    case '4':
                        map_relationship_.sendAttack(-1, 0);
                        break;
                    case '5':
                        map_relationship_.sendAttack(0, 0);
                        break;
                    case '6':
                        map_relationship_.sendAttack(1, 0);
                        break;
                    case '7':
                        map_relationship_.sendAttack(-1, 1);
                        break;
                    case '8':
                        map_relationship_.sendAttack(0, 1);
                        break;
                    case '9':
                        map_relationship_.sendAttack(1, 1);
                        break;
                }
            } else if (storedInput == ' ') {
                switch (current) {
                    case '1':
                        map_relationship_.sendAttack(0, -1);
                        break;
                    case '2':
                        map_relationship_.sendAttack(0, -1);
                        break;
                    case '3':
                        map_relationship_.sendAttack(1, -1);
                        break;
                    case '4':
                        map_relationship_.sendAttack(-1, 0);
                        break;
                    case '5':
                        map_relationship_.sendAttack(0, 0);
                        break;
                    case '6':
                        map_relationship_.sendAttack(1, 0);
                        break;
                    case '7':
                        map_relationship_.sendAttack(-1, 1);
                        break;
                    case '8':
                        map_relationship_.sendAttack(0, 1);
                        break;
                    case '9':
                        map_relationship_.sendAttack(1, 1);
                        break;
                }
                storedInput = '~';
            } else if (storedInput == '~') {
                switch (current) {
                    case '1':
                        map_relationship_.moveInDirection(0, -1);
                        break;
                    case '2':
                        map_relationship_.moveInDirection(0, -1);
                        break;
                    case '3':
                        map_relationship_.moveInDirection(1, -1);
                        break;
                    case '4':
                        map_relationship_.moveInDirection(-1, 0);
                        break;
                    case '5':
                        map_relationship_.moveInDirection(0, 0);
                        break;
                    case '6':
                        map_relationship_.moveInDirection(1, 0);
                        break;
                    case '7':
                        map_relationship_.moveInDirection(-1, 1);
                        break;
                    case '8':
                        map_relationship_.moveInDirection(0, 1);
                        break;
                    case '9':
                        map_relationship_.moveInDirection(1, 1);
                        break;
                    case 'z':
                        map_relationship_.moveInDirection(0, -1);
                        break;
                    case 'x':
                        map_relationship_.moveInDirection(0, -1);
                        break;
                    case 'c':
                        map_relationship_.moveInDirection(1, -1);
                        break;
                    case 'a':
                        map_relationship_.moveInDirection(-1, 0);
                        break;
                    case 's':
                        map_relationship_.moveInDirection(0, 0);
                        break;
                    case 'd':
                        map_relationship_.moveInDirection(1, 0);
                        break;
                    case 'q':
                        map_relationship_.moveInDirection(-1, 1);
                        break;
                    case 'w':
                        map_relationship_.moveInDirection(0, 1);
                        break;
                    case 'e':
                        map_relationship_.moveInDirection(1, 1);
                        break;
                    case 'S': // Save the game
                        break;
                    case 'i':
                        switchToStatsView();
                        break;
                    case 'u': // Uses item
                        break;
                    case 'D':
                        int error_code_D = map_relationship_.dropItem();
                        if (error_code_D != 0) {
                            System.out.println("dropItem function failed to drop an item");
                        }
                        break;
                    case ' ': // Attack in next direction
                        storedInput = ' ';
                        break;
                    case 'p': // Pick up in next direction
                        storedInput = 'p';
                        break;
                    default:
                        break;
                }
            }
            current_viewport_.renderToDisplay();
        }
    }

    private char storedChoice;
    private char storedInput;

    /**
     * Switches to Map View.
     */
    public void switchToMapView() {
        current_viewport_ = map_view_;
    }

    /**
     * Switches to Stats View.
     */
    public void switchToStatsView() {
        current_viewport_ = stats_view_;
    }

    @Override
    public String toString() {
        String s = "Avatar name: " + name_;

        s += "\n Inventory " + "(" + inventory_.size() + ")" + ":";
        for (int i = 0; i < inventory_.size(); ++i) {
            s += " " + inventory_.get(i).name_;
        }

        s += "\n";

        s += " map_relationship_: ";
        if (map_relationship_ == null) {
            s += "null";
        } else {
            s += "Not null";
        }

        s += "\n associated with map:" + map_relationship_.isAssociatedWithMap();

        return s;
    }
}
