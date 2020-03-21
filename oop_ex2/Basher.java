/**
 * defines a subclass of Automatic ships - basher
 * an attacker ship, attack via colliding with other ships
 */

public class Basher extends AutomaticShips {

    private final double MIN_BASH_DISTANCE = 0.19;

    public void doAction(SpaceWars game) {

        super.doAction(game);

        if (this.distanceFromClosestShip <= MIN_BASH_DISTANCE) { // close enough to bash
            shieldOn();
        }
        if (this.angleFromClosestShip > ANGLE_RELATION) { // if the ships are in our eyesight chase them
            DIRECTION = MOVE_LEFT;
        } else if (this.angleFromClosestShip < ANGLE_RELATION) {
            DIRECTION = MOVE_RIGHT;
        }


        spaceShipLocation.move(ACCELERATE, DIRECTION);
    }
}