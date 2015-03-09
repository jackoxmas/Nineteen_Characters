/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.ArrayList;

import src.FacingDirection;
import src.io.view.display.Display;
import src.model.map.MapEntity_Relation;

/**
 * Entity inherits from DrawableThing. Entity is a DrawableThing that can move
 * on the map.
 */
abstract public class Entity extends DrawableThing {

    private PrimaryHandHoldable primary_hand_ = null;
    private SecondaryHandHoldable secondary_hand_ = null;
    private FacingDirection direction_ = FacingDirection.UP;
    private ArrayList<PickupableItem> inventory_;
    private EntityStatsPack stats_pack_ = new EntityStatsPack(this);
    private int num_gold_coins_ = 10;

    public int getNumGoldCoins() {
        return num_gold_coins_;
    }

    public int decrementNumGoldCoinsBy(int amount) {
        num_gold_coins_ -= amount;
        if(num_gold_coins_ >= 0) {
            return num_gold_coins_;
        } else {
            System.err.println("Number of coins going negative in Entity.decrementNumGoldCoinsBy(int amount)");
            num_gold_coins_ = 0;
            return num_gold_coins_;
        }
    }

    public int incrementNumGoldCoinsBy(int amount) {
        return (num_gold_coins_ += amount);
    }

    /**
     * Entity Constructor
     *
     * @param name
     * @param representation - What will represent the Entity on the screen.
     */
    public Entity(String name, char representation) {
        super(name, representation);
        inventory_ = new ArrayList<PickupableItem>();
    }

    public abstract ArrayList<String> getInteractionOptionStrings();

    public abstract ArrayList<String> getConversationStarterStrings();

    /**
     * This function returns a list of appropriate responses to the string that
     * you recieved last.
     *
     * @author John-Michael Reed
     * @param what_you_just_said_to_me - same as "what you last said to me"
     * @return conversation options
     */
    public abstract ArrayList<String> getConversationContinuationStrings(String what_you_just_said_to_me);

    public abstract ArrayList<String> getListOfItemsYouCanUseOnMe();

    public ArrayList<String> endConversation() {
        ArrayList<String> silence = new ArrayList<>();
        return silence;
    }

    /**
     * @author John-Michael Reed
     * @param recieved_text - what was said to me
     * @param speaker - the person who I am talking to
     * @return - what I said back
     */
    public ArrayList<String> reply(String recieved_text, Entity speaker) {
        ArrayList<String> reply = new ArrayList<>();
        if (recieved_text.equalsIgnoreCase("Hello")) {
            reply.add("Goodbye");
            return reply;
        } else if (recieved_text.equalsIgnoreCase("Goodbye")) {
            reply.add("");
            return reply;
        } else if (recieved_text.equalsIgnoreCase("")) {
            return reply;
        } else {
            return reply;
        }
    }

    public int getExperienceBetweenLevels() {
        if (stats_pack_ != null) {
            return stats_pack_.NUMBER_OF_EXPERIENCE_POINT_PER_LEVEL;
        } else {
            return -1;
        }
    }

    public FacingDirection getFacingDirection() {
        return direction_;
    }

    public void setFacingDirection(FacingDirection dir) {
        direction_ = dir;
    }

    public ArrayList<PickupableItem> getInventory() {
        return this.inventory_;
    }

    /**
     * Include stat increase from item i.e., item with stat increase is equipped
     *
     * @param item
     */
    private void addItemStatsToMyStats(Item item) {
        stats_pack_.addOn(item.getStatsPack());
    }

    /**
     * Entities must check their health after they are damaged.
     *
     * @return true if alive false is dead
     */
    public boolean isAlive() {
        if (stats_pack_.getCurrent_life_() <= 0) {
            commitSuicide();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Entity dies, Game Over.
     */
    public void commitSuicide() {
        int health_left = stats_pack_.getCurrent_life_();
        stats_pack_.deductCurrentLifeBy(health_left);
        stats_pack_.decreaseLivesLeftByOne();
        getMapRelation().respawn();
        if (stats_pack_.getLives_left_() < 0) {
            gameOver();
        }
    }

    public void gameOver() {
        System.out.println("An entity has run out of lives and is gone forever.");
        getMapRelation().removeMyselfFromTheMapCompletely();
    }

    public PrimaryHandHoldable getPrimaryEquipped() {
        return primary_hand_;
    }

    public SecondaryHandHoldable getSecondaryEquipped() {
        return secondary_hand_;
    }

    public void setPrimaryEquipped(PrimaryHandHoldable primary_hard) {
        primary_hand_ = primary_hard;
    }

    public void setSecondaryEquipped(SecondaryHandHoldable secondary_hard) {
        secondary_hand_ = secondary_hard;
    }

    /**
     * @author John-Michael Reed
     * @return
     */
    public int equip(EquipableItem item) {
        int temp = item.equipMyselfTo(this);
        return temp;

    }

    /**
     * @author John-Michael Reed
     * @param sheild
     * @return
     */
    public int equipSheild(Sheild sheild) {
        if (sheild != null) {
            // In the case of a 2H sword
            if ((primary_hand_ == secondary_hand_) && (secondary_hand_ != null)) {
                inventory_.add((PickupableItem) primary_hand_);
                stats_pack_.reduceBy(secondary_hand_.getStatsPack());
                primary_hand_ = null;
                secondary_hand_ = null;
            } // In the case of a sheild
            else if ((primary_hand_ != secondary_hand_) && (secondary_hand_ != null)) {
                inventory_.add((PickupableItem) primary_hand_);
                stats_pack_.reduceBy(secondary_hand_.getStatsPack());
                secondary_hand_ = null;
            }

            secondary_hand_ = sheild;
            stats_pack_.addOn(secondary_hand_.getStatsPack());
            boolean successful_removal = inventory_.remove((PickupableItem) sheild);
            if (successful_removal != true) {
                System.exit(-66);
            }
            return 0;
        } else {
            return -5;
        }
    }

    /**
     * @author John-Michael Reed Equips the weapon and modifies stats
     * @param one_hand_weapon
     * @return
     */
    public int equip1hWeapon(final OneHandedWeapon one_hand_weapon) {
        if (one_hand_weapon != null) {
            // In the case of a 2H sword
            if ((primary_hand_ == secondary_hand_) && (primary_hand_ != null)) {
                inventory_.add((PickupableItem) primary_hand_);
                stats_pack_.reduceBy(primary_hand_.getStatsPack());
                primary_hand_ = null;
                secondary_hand_ = null;
            } // In the case of a 1h sword
            else if ((primary_hand_ != secondary_hand_) && (primary_hand_ != null)) {
                inventory_.add((PickupableItem) primary_hand_);
                stats_pack_.reduceBy(primary_hand_.getStatsPack());
                primary_hand_ = null;
            }

            int error_code = -1; // by default null occupation cannot equipMyselfTo any weapon
            if (occupation_ != null) {
                error_code = occupation_.equipOneHandWeapon(one_hand_weapon);
            }
            if (error_code == 0) {
                primary_hand_ = one_hand_weapon;
                stats_pack_.addOn(primary_hand_.getStatsPack());
                boolean successful_removal = inventory_.remove((PickupableItem) one_hand_weapon);
                if (successful_removal != true) {
                    System.exit(-77);
                }
                return 0;
            } else {
                return error_code;
            }
        } else {
            return -5;
        }
    }

    /**
     * @author John-Michael Reed
     * @param two_hand_weapon
     * @return
     */
    public int equip2hWeapon(TwoHandedWeapon two_hand_weapon) {
        if (two_hand_weapon != null) {
            // case of 2H sword
            if ((primary_hand_ == secondary_hand_) && (primary_hand_ != null)) {
                inventory_.add((PickupableItem) primary_hand_);
                stats_pack_.reduceBy(primary_hand_.getStatsPack());
                primary_hand_ = null;
                secondary_hand_ = null;
            } else if ((primary_hand_ != secondary_hand_)) {
                // case of 1h SWORD
                if (primary_hand_ != null) {
                    inventory_.add((PickupableItem) primary_hand_);
                    stats_pack_.reduceBy(primary_hand_.getStatsPack());
                    primary_hand_ = null;
                }
                // case of sheld
                if (secondary_hand_ != null) {
                    inventory_.add((PickupableItem) secondary_hand_);
                    stats_pack_.reduceBy(secondary_hand_.getStatsPack());
                    secondary_hand_ = null;
                }
            }
            primary_hand_ = null;
            secondary_hand_ = null;

            int error_code = -1; // by default null occupation cannot equipMyselfTo any weapon
            if (occupation_ != null) {
                error_code = occupation_.equipTwoHandWeapon(two_hand_weapon);
            }
            if (error_code == 0) {
                primary_hand_ = two_hand_weapon;
                secondary_hand_ = two_hand_weapon;
                stats_pack_.addOn(primary_hand_.getStatsPack());
                boolean successful_removal = inventory_.remove((PickupableItem) two_hand_weapon);
                if (successful_removal != true) {
                    System.exit(-88);
                }
                return 0;
            } else {
                return error_code;
            }
        } else {
            return -5;
        }
    }

    public int unEquipEverything() {
        if (primary_hand_ == secondary_hand_ && primary_hand_ != null) {
            inventory_.add((PickupableItem) primary_hand_);
            stats_pack_.reduceBy(primary_hand_.getStatsPack());
        } else if (primary_hand_ != secondary_hand_) {
            if (primary_hand_ != null) {
                inventory_.add((PickupableItem) primary_hand_);
                stats_pack_.reduceBy(primary_hand_.getStatsPack());
            }
            if (secondary_hand_ != null) {
                inventory_.add((PickupableItem) secondary_hand_);
                stats_pack_.reduceBy(secondary_hand_.getStatsPack());
            }
        }
        primary_hand_ = null;
        secondary_hand_ = null;

        if (occupation_ == null) {
            return 0;
        } else {
            return occupation_.unEquipEverything();
        }
    }

    /**
     * this function levels up an entity Modified to make it "gain enough
     * experience to level up"
     *
     * @author John
     */
    public void gainEnoughExperienceTolevelUp() {
        final int number_of_experience_points_per_level = getExperienceBetweenLevels();
        int exp_to_next = number_of_experience_points_per_level
                - (stats_pack_.getQuantity_of_experience_() % number_of_experience_points_per_level);
        int old_level = stats_pack_.getCached_current_level_();
        gainExperiencePoints(exp_to_next);
        int new_level = stats_pack_.getCached_current_level_();
        if (new_level - old_level != 1) {
            System.err.println("Error in Entity.gainEnoughExperienceTolevelUp()");
            System.exit(-45);
        }

    }

    public boolean hasEquippedPrimaryHand() {
        if (primary_hand_ != null) {
            return true;
        }
        return false;
    }

    public boolean hasEquippedSecondaryHand() {
        if (secondary_hand_ != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns false because Entities are not passable.
     */
    @Override
    public boolean isPassable() {
        return false;
    }

    /**
     * Add item to the inventory.
     *
     * @param item
     */
    public void addItemToInventory(PickupableItem item) {
        inventory_.add(item);
    }

    /**
     * Returns first Item object in Inventory
     *
     * @return Item
     */
    public PickupableItem getLastItemInInventory() {
        if (!inventory_.isEmpty()) {
            return inventory_.get(inventory_.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Gets last Item in Inventory. In the 0 position of the arrayList.
     *
     * @return Null if list is empty
     */
    public PickupableItem pullLastItemOutOfInventory() {
        if (!inventory_.isEmpty()) {
            return inventory_.remove(inventory_.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Sends an attack to an entity.
     *
     * @author John-Michael Reed
     * @param target - entity to hit
     * @return -1 if target is null, 0 if success
     */
    public int sendAttack(Entity target_entity) {
        if (target_entity == null) {
            return -1;
        } else {
            target_entity.receiveAttack(3 + this.getStatsPack().getOffensive_rating_(), this);
            return 0;
        }
    }

    /**
     * Uses the item I am facing on myself. If I have a key the door will unlock
     * itself.\
     *
     * @return 0 on success, -1 on fail (no item to use)
     */
    public int useItemInFacingDirectionOnMyself() {
        Item target = getMapRelation().getTopmostItemInFacingDirection();
        if (target != null) {
            target.use(this);
            return 0;
        }
        return -1;
    }

    /**
     *
     * @param amount
     * @return number of level ups;
     */
    public int gainExperiencePoints(int amount) {
        int num_level_ups = stats_pack_.increaseQuantityOfExperienceBy(amount);
        return num_level_ups;
    }

    public int checkLevel() {
        return stats_pack_.getCached_current_level_();
    }

    // map_relationship_ is used in place of a map_referance_
    private MapEntity_Relation map_relationship_;

    /**
     * Use this to call functions contained within the MapEntity relationship
     *
     * @return map_relationship_
     * @author Reed, John
     */
    public MapEntity_Relation getMapRelation() {
        return map_relationship_;
    }

    /**
     * Set MapEntity_Relation
     *
     * @param e - MapEntity_Relation
     */
    public void setMapRelation(MapEntity_Relation e) {
        map_relationship_ = e;
    }

    private Occupation occupation_ = null;

    /**
     * Get the Entities occupation.
     *
     * @return Occupation of Entity
     */
    public Occupation getOccupation() {
        return occupation_;
    }

    /**
     * Sets Entities Occupation.
     *
     * @param occupation
     */
    public void setOccupation(Occupation occupation) {
        occupation_ = occupation;
    }

    /**
     * Sets Entities Occupation to smasher. Resets stats
     */
    public int becomeSmasher() {
        if (occupation_ != null) {
            occupation_ = new Smasher(this.occupation_);
        } else {
            occupation_ = new Smasher(this);
        }
        return 0;
    }

    /**
     * Sets Entities Occupation to smasher. Resets stats
     */
    public int becomeSummoner() {
        if (occupation_ != null) {
            occupation_ = new Summoner(this.occupation_);
        } else {
            occupation_ = new Summoner(this);
        }
        return 0;
    }

    /**
     * Sets Entities Occupation to smasher. Resets stats
     */
    public int becomeSneak() {
        if (occupation_ != null) {
            occupation_ = new Sneak(this.occupation_);
        } else {
            occupation_ = new Sneak(this);
        }
        return 0;
    }

    /**
     *
     * Override for villager/monster
     *
     * @author John-Michael Reed
     * @param damage - damage received
     * @param attacker - who the attack is coming from. Specify null if the
     * attacker is from an unreachable source i.e. the map
     * @return true if I am still alive, false if I am dead
     */
    public boolean receiveAttack(int damage, Entity attacker) {
        int amount_of_damage = damage - getStatsPack().getDefensive_rating_() - getStatsPack().getArmor_rating_();
        if (amount_of_damage < 0) {
            amount_of_damage = 0;
        }
        getStatsPack().deductCurrentLifeBy(amount_of_damage);
        return isAlive();
    }

    public void receiveHeal(int strength) {
        this.stats_pack_.increaseCurrentLifeBy(strength);
    }

    public void receiveMana(int strength) {
        this.stats_pack_.increaseCurrentManaBy(strength);
    }

    /**
     * Return the combined stats of the entity, includes armour stats.
     */
    public EntityStatsPack getStatsPack() {
        return stats_pack_;
    }

    public String toString() {
        String s = "Entity name: " + name_;

        /*if (!(equipped_item_ == null)) {
         s += "\n equppied item: " + equipped_item_.name_;
         } else {
         s += "\n equppied item: null";
         }*/
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
