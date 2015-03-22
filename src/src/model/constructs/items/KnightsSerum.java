package src.model.constructs.items;

import src.model.constructs.Entity;

public class KnightsSerum extends PickupableItem {

    public int getID() { return 4; }

    public KnightsSerum(String name, char representation) {
        super(name, representation);
    }


    @Override
    public void use(Entity target) {
        target.getMapRelation().becomeKnightRelation();
    }

}
