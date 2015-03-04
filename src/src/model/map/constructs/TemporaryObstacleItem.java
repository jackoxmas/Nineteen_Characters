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
     * @return 
     */
    public ObstacleRemovingItem checkKey() {
        return this.keyItem_;
    }
}
