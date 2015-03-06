package src.model.map.constructs;

import src.Effect;
import src.model.map.constructs.OneShotAreaEffectItem;
public class Trap extends OneShotAreaEffectItem {

	public Trap(String name, char representation, boolean goes_in_inventory,
			Effect effect, int power) {
		super(name, representation, effect, power);
		this.setViewable(false);
	}

	public Trap(String name, char representation, boolean is_passable,
			boolean goes_in_inventory, boolean is_one_shot, Effect effect) {
		super(name, representation, is_passable, goes_in_inventory,
				is_one_shot, effect);
		this.setViewable(false);
	}

}
