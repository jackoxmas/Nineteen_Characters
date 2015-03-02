/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map.constructs;

import src.FacingDirection;

/**
 *
 * @author JohnReedLOL
 */
public class Villager extends Entity {

    public Villager(String name, char representation) {
        super(name, representation);
    }

    /**
     * Villagers automatically run away [in the opposite direction of attacker] when attacked
     * @author John-Michael Reed
     * @param attacker
     * @return 0 if reply succeeded, non-zero otherwise [failed to run away, trapped]
     */
    @Override
    public int replyToAttackFrom(Entity attacker) {
        if (attacker == null) {
            return -1;
        }
        final int attackerX = attacker.getMapRelation().getMyXCoordinate();
        final int attackerY = attacker.getMapRelation().getMyYCoordinate();

        final int myX = this.getMapRelation().getMyXCoordinate();
        final int myY = this.getMapRelation().getMyYCoordinate();

        if (myX == attackerX && myY == attackerY) {
            System.exit(-6); // Impossible
            return -999;
        } else if ( myX == attackerX && myY > attackerY ) {
            return this.getMapRelation().moveInDirection(0, 1);
        } else if ( myX == attackerX && myY < attackerY ) {
            return this.getMapRelation().moveInDirection(0, -1 );
        } else if ( myX > attackerX && myY == attackerY ) {
            return this.getMapRelation().moveInDirection(1, 0);
        } else if ( myX < attackerX && myY == attackerY ) {
            return this.getMapRelation().moveInDirection(-1, 0);
        } else if ( myX > attackerX && myY > attackerY ) {
            return this.getMapRelation().moveInDirection(1, 1);
        } else if ( myX < attackerX && myY < attackerY ) {
            return this.getMapRelation().moveInDirection(-1, -1 );
        } else if ( myX > attackerX && myY < attackerY ) {
            return this.getMapRelation().moveInDirection(1, -1);
        } else if ( myX < attackerX && myY > attackerY ) {
            return this.getMapRelation().moveInDirection(-1, 1);
        } else {
            System.exit(-9); // Impossible
            return -999;
        }
    }
}
