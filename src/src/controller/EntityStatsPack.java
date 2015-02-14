package src.controller;

public final class EntityStatsPack extends DrawableThingStatsPack {

// Primary stats
    public int lives_left_ = 0; // this can change without leveling up
    public int strength_level_ = 1;
    public int agility_level_ = 1;
    public int intellect_level_ = 1;
    public int hardiness_level_ = 1;
    public int quantity_of_experience_ = 1;
    public int movement_level_ = 1;

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
                + "Armor: " + armor_rating_ + "\n";
    }

// Gets decremented every time an entity moves
    public int moves_left_in_turn_ = 1;

// Constant Secondary Stats
    public int cached_current_level_ = 1;

// Modifiable Secondary Stats
// These secondary stats can be modified without leveling up
    private int current_life_ = 1;
    private int current_mana_ = 1;
    private int current_offensive_rating_ = 1;
    private int current_defensive_rating_ = 1;
    private int current_armor_rating_ = 1;

    public EntityStatsPack() {
        super(1, 1, 1, 1, 1);
    }

}
