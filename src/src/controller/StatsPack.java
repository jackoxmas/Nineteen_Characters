package src.controller;

import java.io.Serializable;

/**
 * StatsPacks contain stats. StatsPacks can be combined to form new StatsPacks.
 *
 */
public final class StatsPack implements Serializable {

    // Primary stats
    private final int lives_left_;
    private final int strength_level_;
    private final int agility_level_;
    private final int intellect_level_;
    private final int hardiness_level_;

    private int quantity_of_experience_;
    private final int movement_level_;

    // Gets decremented every time an entity moves
    private int moves_left_in_turn_;
    
    // Constant Secondary Stats
    private final int cached_current_level_;
    private final int max_life_at_current_level_;
    private final int max_mana_at_current_level_;
    private final int max_offensive_rating_at_current_level_;
    private final int max_defensive_rating_at_current_level_;
    private final int max_armor_rating_at_current_level_;
    
    // Modifiable Secondary Stats
    // These secondary stats can be modified without leveling up
    private int current_life_;
    private int current_mana_;
    private int current_offensive_rating_;
    private int current_defensive_rating_;
    private int current_armor_rating_;

    StatsPack() {
        lives_left_ = 0;
        strength_level_ = 0;
        agility_level_ = 0;
        intellect_level_ = 0;
        hardiness_level_ = 0;
        quantity_of_experience_ = 0;
        movement_level_ = 0;
        moves_left_in_turn_ = 0;
        cached_current_level_ = 0;
        max_life_at_current_level_ = 0;
        max_mana_at_current_level_ = 0;
        max_offensive_rating_at_current_level_ = 0;
        max_defensive_rating_at_current_level_ = 0;
        max_armor_rating_at_current_level_ = 0;
        current_life_ = 0;
        current_mana_ = 0;
        current_offensive_rating_ = 0;
        current_defensive_rating_ = 0;
        current_armor_rating_ = 0;
    }

    StatsPack(
            int lives_left,
            int strength_level,
            int agility_level,
            int intellect_level,
            int hardiness_level,
            int quantity_of_experience,
            int movement_level,
            int moves_left_in_turn,
            int cached_current_level
    ) {
        lives_left_ = lives_left;
        strength_level_ = strength_level;
        agility_level_ = agility_level;
        intellect_level_ = intellect_level;
        hardiness_level_ = hardiness_level;
        quantity_of_experience_ = quantity_of_experience;
        movement_level_ = movement_level;
        moves_left_in_turn_ = moves_left_in_turn;
        cached_current_level_ = cached_current_level;
        max_life_at_current_level_ = hardiness_level_*10;
        max_mana_at_current_level_ = intellect_level_*10;
        max_offensive_rating_at_current_level_ = strength_level_+agility_level_;
        max_defensive_rating_at_current_level_ = agility_level_+intellect_level_;
        max_armor_rating_at_current_level_ = strength_level_+intellect_level_;
        current_life_ =  max_life_at_current_level_;
        current_mana_ = max_mana_at_current_level_;
        current_offensive_rating_ =  max_offensive_rating_at_current_level_;
        current_defensive_rating_ = max_defensive_rating_at_current_level_;
        current_armor_rating_ = max_armor_rating_at_current_level_;
    }

    StatsPack(
            int lives_left,
            int strength_level,
            int agility_level,
            int intellect_level,
            int hardiness_level,
            int quantity_of_experience,
            int movement_level,
            int moves_left_in_turn,
            int cached_current_level,
            int max_life_at_current_level,
            int max_mana_at_current_level,
            int max_offensive_rating_at_current_level,
            int max_defensive_rating_at_current_level,
            int max_armor_rating_at_current_level,
            int current_life,
            int current_mana,
            int current_offensive_rating,
            int current_defensive_rating,
            int current_armor_rating
    ) {
        lives_left_ = lives_left;
        strength_level_ = strength_level;
        agility_level_ = agility_level;
        intellect_level_ = intellect_level;
        hardiness_level_ = hardiness_level;
        quantity_of_experience_ = quantity_of_experience;
        movement_level_ = movement_level;
        moves_left_in_turn_ = moves_left_in_turn;
        cached_current_level_ = cached_current_level;
        max_life_at_current_level_ = max_life_at_current_level;
        max_mana_at_current_level_ = max_mana_at_current_level;
        max_offensive_rating_at_current_level_ = max_offensive_rating_at_current_level;
        max_defensive_rating_at_current_level_ = max_defensive_rating_at_current_level;
        max_armor_rating_at_current_level_ = max_armor_rating_at_current_level;
        current_life_ = current_life;
        current_mana_ = current_mana;
        current_offensive_rating_ = current_offensive_rating;
        current_defensive_rating_ = current_defensive_rating;
        current_armor_rating_ = current_armor_rating;
    }

    /**
     * This function is for when one DrawableThing modifies [increases] the
     * stats of another DrawableThing.
     *
     * @param modifier - gets added to the currect statspack
     * @return modified StatsPack
     * @author John-Michael Reed
     */
    public StatsPack add(final StatsPack modifier) {
    	return new StatsPack(
	    	lives_left_ + modifier.getLivesLeft(),
	        strength_level_ + modifier.getStrengthLevel(),
	        agility_level_ + modifier.getAgilityLevel(),
	        intellect_level_ + modifier.getIntellectLevel(),
	        hardiness_level_ + modifier.getHardinessLevel(),
	        quantity_of_experience_ + modifier.getQuantityOfExperience(),
	        movement_level_ + modifier.getMovementLevel(),
	        moves_left_in_turn_ + modifier.getMovesLeftInTurn(),
	        cached_current_level_ + modifier.getCachedCurrentLevel(),
	        max_life_at_current_level_ + modifier.getMaxLifeAtCurrentLevel(),
	        max_mana_at_current_level_ + modifier.getMaxManaAtCurrentLevel(),
	        max_offensive_rating_at_current_level_ + modifier.getMaxOffensiveRatingAtCurrentLevel(),
	        max_defensive_rating_at_current_level_ + modifier.getMaxDefensiveRatingAtCurrentLevel(),
	        max_armor_rating_at_current_level_ + modifier.getMaxArmorRatingAtCurrentLevel(),
	        current_life_ + modifier.getCurrentLife(),
	        current_mana_ + modifier.getCurrentMana(),
	        current_offensive_rating_ + modifier.getCurrentOffensiveRating(),
	        current_defensive_rating_ + modifier.getCurrentDefensiveRating(),
	        current_armor_rating_ + modifier.getCurrentArmorRating()
        );
	}

    /**
     * This function is for when one DrawableThing modifies [decreases] the
     * stats of another DrawableThing.
     *
     * @param modifier - gets subtracted from the current statspack
     * @return modified StatsPack
     * @author John-Michael Reed
     */

    public StatsPack subtract(final StatsPack modifier) {
		return new StatsPack(
	    	lives_left_ - modifier.getLivesLeft(),
	        strength_level_ - modifier.getStrengthLevel(),
	        agility_level_ - modifier.getAgilityLevel(),
	        intellect_level_ - modifier.getIntellectLevel(),
	        hardiness_level_ - modifier.getHardinessLevel(),
	        quantity_of_experience_ - modifier.getQuantityOfExperience(),
	        movement_level_ - modifier.getMovementLevel(),
	        moves_left_in_turn_ - modifier.getMovesLeftInTurn(),
	        cached_current_level_ - modifier.getCachedCurrentLevel(),
	        max_life_at_current_level_ - modifier.getMaxLifeAtCurrentLevel(),
	        max_mana_at_current_level_ - modifier.getMaxManaAtCurrentLevel(),
	        max_offensive_rating_at_current_level_ - modifier.getMaxOffensiveRatingAtCurrentLevel(),
	        max_defensive_rating_at_current_level_ - modifier.getMaxDefensiveRatingAtCurrentLevel(),
	        max_armor_rating_at_current_level_ - modifier.getMaxArmorRatingAtCurrentLevel(),
	        current_life_ - modifier.getCurrentLife(),
	        current_mana_ - modifier.getCurrentMana(),
	        current_offensive_rating_ - modifier.getCurrentOffensiveRating(),
	        current_defensive_rating_ - modifier.getCurrentDefensiveRating(),
	        current_armor_rating_ - modifier.getCurrentArmorRating()
	    );
	}
    
    public int getQuantityOfExperience() {
		return quantity_of_experience_;
	}

	public void setQuantityOfExperience(int quantity_of_experience) {
		quantity_of_experience_ = quantity_of_experience;
	}

	public void addQuantityOfExperience(int quantity_of_experience) {
		
		quantity_of_experience_ += quantity_of_experience;
	}

	public int getMovesLeftInTurn() {
		return moves_left_in_turn_;
	}

	public void setMovesLeftInTurn(int moves_left_in_turn) {
		moves_left_in_turn_ = moves_left_in_turn;
	}

	public boolean moveOne() {
		if (moves_left_in_turn_ == 0)
			return false;
		moves_left_in_turn_ -= 1;
		return true;
	}

	public void resetMove() {
		moves_left_in_turn_ = movement_level_;
	}
	
	public int getCurrentLife() {
		return current_life_;
	}

	public void setCurrentLife(int current_life) {
		current_life_ = current_life;
	}

	public int getCurrentMana() {
		return current_mana_;
	}

	public void setCurrentMana(int current_mana) {
		current_mana_ = current_mana;
	}

	public int getCurrentOffensiveRating() {
		return current_offensive_rating_;
	}

	public void setCurrentOffensiveRating(int current_offensive_rating) {
		current_offensive_rating_ = current_offensive_rating;
	}

	public int getCurrentDefensiveRating() {
		return current_defensive_rating_;
	}

	public void setCurrentDefensiveRating(int current_defensive_rating) {
		current_defensive_rating_ = current_defensive_rating;
	}

	public int getCurrentArmorRating() {
		return current_armor_rating_;
	}

	public void setCurrentArmorRating(int current_armor_rating) {
		current_armor_rating_ = current_armor_rating;
	}

	public int getLivesLeft() {
		return lives_left_;
	}

	public int getStrengthLevel() {
		return strength_level_;
	}

	public int getAgilityLevel() {
		return agility_level_;
	}

	public int getIntellectLevel() {
		return intellect_level_;
	}

	public int getHardinessLevel() {
		return hardiness_level_;
	}

	public int getMovementLevel() {
		return movement_level_;
	}

	public int getCachedCurrentLevel() {
		return cached_current_level_;
	}

	public int getMaxLifeAtCurrentLevel() {
		return max_life_at_current_level_;
	}

	public int getMaxManaAtCurrentLevel() {
		return max_mana_at_current_level_;
	}

	public int getMaxOffensiveRatingAtCurrentLevel() {
		return max_offensive_rating_at_current_level_;
	}

	public int getMaxDefensiveRatingAtCurrentLevel() {
		return max_defensive_rating_at_current_level_;
	}

	public int getMaxArmorRatingAtCurrentLevel() {
		return max_armor_rating_at_current_level_;
	}
	
}