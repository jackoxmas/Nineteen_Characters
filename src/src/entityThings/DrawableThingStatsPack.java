package src.entityThings;

/**
 * Stats Pack for Drawable Things.
 *
 */
public class DrawableThingStatsPack {

	public int life_;

	public int mana_;

	public int offensive_rating_;

	public int defensive_rating_;

	public int armor_rating_;

	/**
	 * Constructor: sets stats to 0.
	 */
	public DrawableThingStatsPack() {
		life_ = 0;
		mana_ = 0;
		offensive_rating_ = 0;
		defensive_rating_ = 0;
		armor_rating_ = 0;
	}

	/**
	 * Constructor: contains several parameters to set stats.
	 * @param l - life
	 * @param m - mana
	 * @param o - offensive rating
	 * @param d - defensive rating
	 * @param a - armor rating
	 */
	public DrawableThingStatsPack(int l, int m, int o, int d, int a) {
		life_ = l;
		mana_ = m;
		offensive_rating_ = o;
		defensive_rating_ = d;
		armor_rating_ = a;
	}
	/*
public DrawableThingStatsPack add(final DrawableThingStatsPack other) {
return new DrawableThingStatsPack(
life_ + other.life_,
mana_ + other.mana_,
offensive_rating_ + other.offensive_rating_,
defensive_rating_ + other.defensive_rating_,
armor_rating_ + other.armor_rating_
); 
}

public DrawableThingStatsPack subtract(final DrawableThingStatsPack other) {
return new DrawableThingStatsPack(
life_ - other.life_,
mana_ - other.mana_,
offensive_rating_ - other.offensive_rating_,
defensive_rating_ - other.defensive_rating_,
armor_rating_ - other.armor_rating_
); 
}
	 */
	/**
	 * Adds on to the Drawable Thing stats pack.
	 * @param other
	 */
	public void addOn(final DrawableThingStatsPack other) {
		life_ += other.life_;
		mana_ += other.mana_;
		offensive_rating_ += other.offensive_rating_;
		defensive_rating_ += other.defensive_rating_;
		armor_rating_ += other.armor_rating_;
	}

	/**
	 * reduces Drawable Thing's stat pack.
	 * @param other
	 */
	public void reduceBy(final DrawableThingStatsPack other) {
		life_ -= other.life_;
		mana_ -= other.mana_;
		offensive_rating_ -= other.offensive_rating_;
		defensive_rating_ -= other.defensive_rating_;
		armor_rating_ -= other.armor_rating_;
	}

	@Override
	public String toString() {
		return "Life: " + life_ + "\n" + 
				"Mana: " + mana_ + "\n" +
				"Offense: " + offensive_rating_ + "\n" +
				"Defense: " + defensive_rating_ + "\n" +
				"Armor: " + armor_rating_ + "\n";
	}
}
