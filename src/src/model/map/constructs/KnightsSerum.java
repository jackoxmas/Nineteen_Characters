package src.model.map.constructs;

public class KnightsSerum extends PickupableItem {

    public KnightsSerum(String name, char representation) {
        super(name, representation);
    }


    @Override
    public void use(Entity target) {
        target.getMapRelation().becomeKnightRelation();
    }

}
