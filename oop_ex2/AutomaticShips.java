import java.awt.*;
import oop.ex2.*;

/**
 * This class defines all the Automatic ships,
 * serves as the a second layer abstract class that defines subject that are specifically for the enemy ships.
 *  like image and common behavior for all the enemy ship
 */

public abstract class AutomaticShips extends SpaceShip {

    protected double distanceFromClosestShip;
    protected double angleFromClosestShip;
    protected final int ANGLE_RELATION = 0;

    /**
     *  the action that related to all the enmy ships like always accelerate , and getting the closest ship
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        super.doAction(game); // energy + energy reset + round reset

        ACCELERATE = true;
        SpaceShip closestShip = game.getClosestShipTo(this);
        distanceFromClosestShip = spaceShipLocation.distanceFrom(closestShip.getPhysics());
        angleFromClosestShip = getPhysics().angleTo(closestShip.getPhysics());

    }

    /**
     * return the enemy ship given photo
     * @return return the enemy ship given photo
     */
    @Override
    public Image getImage() {
        if (isShieldActivated){
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

}
