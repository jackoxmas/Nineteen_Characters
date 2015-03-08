package src.model.map.constructs;

import src.Effect;

public class Trap extends OneShotAreaEffectItem {

    public Trap(String name, char representation, boolean goes_in_inventory,
            Effect effect, int power) {
        super(name, representation, effect, power);
        this.setViewable(false);
    }

    public void removeMyselfFromMap() {
        this.getMapRelation().getMapTile().removeTopItem();
    }

}
