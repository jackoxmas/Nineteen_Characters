/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import java.util.ArrayList;
import src.FacingDirection;

import src.model.map.MapEntity_Relation;
import src.io.view.Display;
import src.FacingDirection;

/**
 * Entity inherits from DrawableThing. Entity is a DrawableThing that can move
 * on the map.
 */
abstract public class Entity extends DrawableThing {

    private FacingDirection direction_ = FacingDirection.UP;

    public FacingDirection getFacingDirection() {
        return direction_;
    }

    public void setFacingDirection(FacingDirection dir) {
        direction_ = dir;
    }
    private static final int experience_between_levels = 100;

    /**
     * Include stat increase from item i.e., item with stat increase is equipped
     *
     * @param item
     */
    public void addItemStatsToMyStats(Item item) {
        stats_pack_.addOn(item.getStatsPack());
    }

    /**
     * Entities should check their health after they are damaged.
     */
    public void checkHealth() {
        if (stats_pack_.getCurrent_life_() <= 0) {
            commitSuicide();
        }
    }

    /**
     * Entity dies, Game Over.
     */
    public void commitSuicide() {
        int health_left = stats_pack_.getCurrent_life_();
        stats_pack_.deductCurrentLifeBy(health_left);
        getMapRelation().respawn();
        if (stats_pack_.getLives_left_() < 0) {
            gameOver();
        }
    }

    public void gameOver() {
        System.out.println("game over");
    }

    /**
     * Entity Constructor
     *
     * @param name
     * @param representation - What will represent the Entity on the screen.
     */
    public Entity(String name, char representation) {
        super(name, representation);
        inventory_ = new ArrayList<Item>();
    }

    // Only 1 equipped item in iteration 1
    private Item equipped_item_;

    public Item getEquipped() {
        if (equipped_item_ != null) {
            return equipped_item_;
        }
        return null;
    }

    /**
     * Equip Item at final position in the Inventory ArrayList.
     *
     * @author John-Michael Reed
     * @return error codes: -2, inventory has no item; -1, cannot equip another
     * item; -3 inventory is empty;
     */
    public int equipInventoryItem() {
        if (!inventory_.isEmpty()) {
            if (equipped_item_ == null) {
                if (!inventory_.isEmpty()) {
                    Item last = inventory_.get(inventory_.size() - 1);
                    DrawableThingStatsPack to_add = last.getStatsPack();
                    this.stats_pack_.addOn(to_add);
                    equipped_item_ = last;
                    inventory_.remove(inventory_.size() - 1);
                    return 0;
                } else {
                    return -3;
                }
            } else {
                return -1;
            }
        } else {
            Display.getDisplay().setMessage("You don't have anything to equip!");
            return -2;
        }
    }

    /**
     * @author John-Michael Reed
     * @return error codes: -1 inventory is too full for item [not yet
     * availible]
     */
    public int unEquipInventoryItem() {
        if (equipped_item_ != null) {
            Display.getDisplay().setMessage("Unequipping item: " + equipped_item_.name_);
            DrawableThingStatsPack to_remove = equipped_item_.getStatsPack();
            this.stats_pack_.reduceBy(to_remove);
            inventory_.add(equipped_item_);
            equipped_item_ = null;
            return 0;
        } else {
            Display.getDisplay().setMessage("No equipped item to unequip");
            return -1;
        }
    }

    /**
     * this function levels up an entity Modified to make it "gain enough
     * experience to level up"
     *
     * @author John
     */
    public void gainEnoughExperienceTolevelUp() {
        stats_pack_.increaseQuantityOfExperienceToNextLevel();
    }

    public boolean hasEquipped() {
        if (equipped_item_ != null) {
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

    protected ArrayList<Item> inventory_;

    /**
     * Add item to the inventory.
     *
     * @param item
     */
    public void addItemToInventory(Item item) {
        inventory_.add(item);
    }

    /**
     * Returns first Item object in Inventory
     *
     * @return Item
     */
    public Item getLastItemInInventory() {
        if (!inventory_.isEmpty()) {
            return inventory_.get(inventory_.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Gets the Inventory of Entity.
     *
     * @return ArrayList of Items that are in the Entities Inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory_;
    }

    /**
     * Gets last Item in Inventory. In the 0 position of the arrayList.
     *
     * @return Null if list is empty
     */
    public Item pullLastItemOutOfInventory() {
        if (!inventory_.isEmpty()) {
            return inventory_.remove(inventory_.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Uses first item in inventory Does not destroy the item
     *
     * @return 0 on success, -1 on fail (no item to use)
     */
    public int useLastInventoryItem() {
        Item i = getLastItemInInventory();
        if (i == null) {
            Display.getDisplay().setMessage("You have no items to use.");
            return -1;
        } else {
            i.use(this);
            return 0;
        }
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
        occupation_ = new Smasher(this);
        return 0;
    }

    /**
     * Sets Entities Occupation to smasher. Resets stats
     */
    public int becomeSummoner() {
        occupation_ = new Summoner(this);
        return 0;
    }

    /**
     * Sets Entities Occupation to smasher. Resets stats
     */
    public int becomeSneak() {
        occupation_ = new Sneak(this);
        return 0;
    }

    /**
     * Adds default stats to item stats and updates my_stats_after_powerups
     *
     * @author Jessan
     */
    private void recalculateStats() {
        //my_stats_after_powerups_.equals(my_stats_after_powerups_.add(equipped_item_.get_stats_pack_()));

    }

    /**
     * Specify null if the attacker is not an entity that can be attacked.
     * @param damage - damage received
     * @param attacker - who the attack is coming from
     */
    public void receiveAttack(int damage, Entity attacker) {
        int did_I_run_out_of_health = stats_pack_.deductCurrentLifeBy(damage - stats_pack_.getDefensive_rating_() - stats_pack_.getArmor_rating_());
        if (did_I_run_out_of_health != 0) {
            getMapRelation().respawn();
            if (stats_pack_.getLives_left_() < 0) {
                gameOver();
            }
        } else {
            if (attacker != null) {
                this.replyToAttackFrom(attacker);
            }
        }
    }

    public void receiveHeal(int strength) {
        this.stats_pack_.increaseCurrentLifeBy(strength);
    }

    // reply(greeting, this);
    public String reply(String recieved_text, Entity speaker) {
        String reply = "";
        if (recieved_text == "hello") {
            reply = "goodbye";
            return speaker.reply(reply, this);
        } else if (recieved_text == "goodbye") {
            reply = "";
            return speaker.reply(reply, this);
        } else if (recieved_text == "") {
            return "";
        } else {
            return "";
        }
    }

    /**
     * Called by an entity that was attacked by another entity. Override for
     * monster/villager.
     *
     * @author John-Michael Reed
     * @param attacker
     * @return 0 if reply succeeded, non-zero otherwise [ex. if entity is null
     * or off the map]
     */
    public abstract int replyToAttackFrom(Entity attacker);

    //private final int max_level_;
    private EntityStatsPack stats_pack_ = new EntityStatsPack(this);

    /**
     * Get Entities StatsPack - only to be used by the view for displaying
     * stats.
     */
    public EntityStatsPack getStatsPack() {
        return stats_pack_;
    }

    /**
     * Removes state increase from item i.e., item with stat increase is
     * unequipped
     *
     * @param item
     */
    public void subtractItemStatsFromMyStats(Item item) {
        stats_pack_.reduceBy(item.getStatsPack());
    }

    public String toString() {
        String s = "Entity name: " + name_;

        if (!(equipped_item_ == null)) {
            s += "\n equppied item: " + equipped_item_.name_;
        } else {
            s += "\n equppied item: null";
        }

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
