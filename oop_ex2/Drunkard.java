import java.util.Random;

/**
 * This unit been partying before going to the mission so they trying to avoid problems
 * and not ruin the mission, but the alcohol makes them irrational
 * so they randomly change rules in the battlefield
 */
public class Drunkard extends AutomaticShips {

    private Random rand = new Random();

    private int getMood(){
         return this.rand.nextInt(3);
    }

    @Override
    public void doAction(SpaceWars game) {

        super.doAction(game);

        int mood = getMood();

        if (this.angleFromClosestShip< ANGLE_RELATION){  // trying to avoid problems by running from everybody
            DIRECTION = MOVE_LEFT;
        }else if(this.angleFromClosestShip> ANGLE_RELATION){
            DIRECTION = MOVE_RIGHT;
        }
        switch (mood){  // three moods defined by the possible rules in the game, fight flight or defend.
            case 0:
                teleport();
                break;
            case 1:
                shieldOn();
                break;
            case 2:
                fire(game);
                break;

        }

        spaceShipLocation.move(ACCELERATE,DIRECTION);
    }
}
