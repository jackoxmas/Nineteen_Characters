package src.EntityThing;
/**
 * Stats Pack for an Entity. Inherits from DrawableThingStatsPack. 
 */
public final class EntityStatsPack extends DrawableThingStatsPack {

// Primary stats
    public int lives_left_ = 1; // this can change without leveling up
    public int strength_level_ = 1;
    public int agility_level_ = 1;
    public int intellect_level_ = 1;
    public int hardiness_level_ = 1;
    public int quantity_of_experience_ = 1;
    public int movement_level_ = 1;

// Gets decremented every time an entity moves
    public int moves_left_in_turn_ = 1;

// Constant Secondary Stats
    public int cached_current_level_ = 1;

// Modifiable Secondary Stats
// These secondary stats can be modified without leveling up
    public int current_life_ = 1;
    public int current_mana_ = 1;
    public int current_offensive_rating_ = 1;
    public int current_defensive_rating_ = 1;
    public int current_armor_rating_ = 1;

    @Override
    public String toString() {
        return super.toString()
                + "lives_left_: " + lives_left_ + "\n"
                + "strength_level_: " + strength_level_ + "\n"
                + "agility_level_: " + agility_level_ + "\n"
                + "intellect_level_: " + intellect_level_ + "\n"
                + "hardiness_level_: " + hardiness_level_ + "\n"
                + "quantity_of_experience_: " + quantity_of_experience_ + "\n"
                + "movement_level_: " + movement_level_ + "\n"
                + "Armor: " + armor_rating_ + "\n"
                + "Mana: " + mana_ + "\n"
                + "Offense: " + offensive_rating_ + "\n"
                + "Defense: " + defensive_rating_ + "\n"
                + "Armor: " + armor_rating_ + "\n"
                + "moves_left_in_turn_: " + moves_left_in_turn_ + "\n"
                + "cached_current_level_: " + cached_current_level_ + "\n"
                + "current_life_: " + current_life_ + "\n"
                + "current_mana_: " + current_mana_ + "\n"
                + "current_offensive_rating_: " + current_offensive_rating_ + "\n"
                + "current_defensive_rating_: " + current_defensive_rating_ + "\n"
                + "current_armor_rating_: " + current_armor_rating_ + "\n";
    }
    
    /**
     * Constructor: sets values to 1. 
     */
    public EntityStatsPack() {
        super(1, 1, 1, 1, 1);
        
    }

}
