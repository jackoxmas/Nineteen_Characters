import src.Effect;
import src.FacingDirection;
import src.io.view.display.Display;
import src.model.map.constructs.Entity;
import src.model.map.constructs.Item;
import src.model.map.constructs.PickupableItem;
import src.model.map.constructs.Trap;

public class MapKnight_Relation extends MapEntity_Relation {
  
      public MapKnight_Relation(Map m, Entity entity, int x_respawn_point,
            int y_respawn_point) {
        super(m, entity, x_respawn_point, y_respawn_point);
    }

  @Override
  public int moveInDirection(int x, int y) {
    if (x == 0 && y == 0) {
            // nothing
        } else if (x == 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP);
        } else if (x == 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN);
        } else if (x > 0 && y == 0) {
            entity_.setFacingDirection(FacingDirection.RIGHT);
        } else if (x < 0 && y == 0) {
            entity_.setFacingDirection(FacingDirection.LEFT);
        } else if (x > 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP_RIGHT);
        } else if (x > 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN_RIGHT);
        } else if (x < 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP_LEFT);
        } else if (x < 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN_LEFT);
        } else {
            System.err
                    .print("An impossible error occured in MapEntity_Relation.moveInDirection()");
            System.exit(-1); // Impossible
        }
        return super.pushEntityInDirection(entity_, 2*x, 2*y);
  }

}
