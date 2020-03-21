/**
 * A subclass of Automatic Ships, define the aggressive ships, which are ships the
 * pursue after other ships and attempt to shoot them
 */

public class Aggressive extends AutomaticShips {

    private final double MIN_ATT_DISTANCE = 0.21;

    public void doAction(SpaceWars game) {

        super.doAction(game);

        if (this.distanceFromClosestShip < MIN_ATT_DISTANCE) { // close enough to shoot
            fire(game);
        }
        if (this.angleFromClosestShip> ANGLE_RELATION){  // chase other ships
            DIRECTION = MOVE_LEFT;
        }else if(this.angleFromClosestShip< ANGLE_RELATION){
            DIRECTION = MOVE_RIGHT;
        }

        spaceShipLocation.move(ACCELERATE,DIRECTION);
    }
}