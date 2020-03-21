import oop.ex2.*;

import java.awt.*;

/**
 * Humanship class defines the human controlled spaceship
 * includes the controllers for the game and the image
 */
public class HumanShip extends SpaceShip {

    @Override
    public void doAction(SpaceWars game) {

        super.doAction(game); // energy + energy reset + round reset

        if (game.getGUI().isTeleportPressed()) {  // attempt to teleport
            teleport();
        }
        // movement
        if (game.getGUI().isUpPressed()) {
            ACCELERATE = true;
        }
        if ((game.getGUI().isRightPressed() && game.getGUI().isLeftPressed()) ||
                !(game.getGUI().isRightPressed() || game.getGUI().isLeftPressed())) {
            DIRECTION = MOVE_NOWHERE; // if we left&right together or didnt push either of them do nothing
        } else if (game.getGUI().isRightPressed()) {
            DIRECTION = MOVE_RIGHT;
        } else if (game.getGUI().isLeftPressed()) {
            DIRECTION = MOVE_LEFT;
        }
        spaceShipLocation.move(ACCELERATE, DIRECTION);

        // operations
        if (game.getGUI().isShieldsPressed()) {  // attempt to put shield on
            shieldOn();
        }
        if (game.getGUI().isShotPressed()) { // attempt to shoot
            fire(game);
        }


    }

    /**
     *  return the human ship photo from the given files
     * @return human ship photo
     */

    @Override
    public Image getImage() {
        if (this.isShieldActivated) { // return human ship image, as given
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;

    }
}