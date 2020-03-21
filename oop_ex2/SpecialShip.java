import java.awt.*;
import javax.swing.ImageIcon;

/**
 * Special unit of aggressive type ships that have invisibility cover
 * all of the pilot are well trained and have one goal-to destroy the human ships in the most efficient way
 */
public class SpecialShip extends Aggressive {
    private static final String SPECIAL_IMAGE = "Ghost.gif";
    private static final String SPECIAL_DESCRIPTION =" gui related invisible ship";

    private static final Image GHOST_IMAGE =
            new ImageIcon(SPECIAL_IMAGE , SPECIAL_DESCRIPTION).getImage();

    /**
     *
     * @return  a black image of the spaceships, meaning usinig the given GUI they will be invisible
     */
    @Override
    public Image getImage() {
        return GHOST_IMAGE;
    }  // special ship photo, one mode needed because its an attacker
    // type ship his defense mechanism is by disappearing.

}
