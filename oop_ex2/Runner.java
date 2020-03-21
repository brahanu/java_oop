/**
 * Runner class, trying to avoid all the other ships by teleporting or manually run away
 */
public class Runner extends AutomaticShips {
    // exercise operation constants
    private final double MIN_RUNNING_DISTANCE = 0.25;
    private final double MIN_RUNNING_ANGLE = 0.23;

    @Override
    public void doAction(SpaceWars game) {

        super.doAction(game);

        if (this.distanceFromClosestShip < MIN_RUNNING_DISTANCE && this.angleFromClosestShip < MIN_RUNNING_ANGLE){
            teleport();  // ships are too close attempt to teleport
        } else if (this.angleFromClosestShip< ANGLE_RELATION){  // attempt to run away manually
            DIRECTION = MOVE_LEFT;
        }else if(this.angleFromClosestShip> ANGLE_RELATION){
            DIRECTION = MOVE_RIGHT;
        }

        this.spaceShipLocation.move(ACCELERATE,DIRECTION);

    }




}
