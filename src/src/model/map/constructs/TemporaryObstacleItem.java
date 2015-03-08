/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

/**
 *
 * @author JohnReedLOL
 */
public class TemporaryObstacleItem extends ObstacleItem {

    private final ObstacleRemovingItem keyItem_;

    public TemporaryObstacleItem(String name, char representation, ObstacleRemovingItem keyItem) {
        super(name, representation);
        keyItem_ = keyItem;
    }

    /**
     * Returns a reference to the keyItem needed to open this door.
     *
     * @return
     */
    public ObstacleRemovingItem checkKey() {
        return this.keyItem_;
    }

    /**
     * The use function allows an item to exert its effect on an entity.
     *
     * @param target - The entity that the item will be used on.
     */
    @Override
    public void use(Entity target) {
        if(target.getInventory().contains(keyItem_)) {
            this.setPassable(true);
        }
    }

    /**
     * The use function also allows an item to exert an effect on another item.
     *
     * @param target - The item that this item will be used upon.
     */
    @Override
    public void use(Item target) {
        if(target == keyItem_) {
            this.setPassable(true);
        }
    }
}
