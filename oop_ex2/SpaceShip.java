import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip {

    // movement MAGIC_NUMBERS

    protected int DIRECTION = 0;
    protected final int MOVE_NOWHERE = 0;
    protected final int MOVE_LEFT = 1;
    protected final int MOVE_RIGHT = -1;

    // game rule MAGIC_NUMBERS
    private final int READY_TO_SHOOT = 0;
    private int COOLDOWN_TO_SHOOT = 8;
    private final int ABLE_TO_SHOOT = 19;
    private final int ABLE_TO_SHIELD = 3;
    private final int ABLE_TO_TELEPORT = 140;
    private final int BASHING_ENERGY = 18;
    private final int HIT_ENERGY = 10;
    private final int NO_ENERGY = 0;
    private final int NO_HEALTH = 0;
    private int START_HEALTH = 22;
    private int START_MAX_ENERGY = 210;
    private int START_CUR_ENERGY = 190;

    // master class related
    protected int health;
    protected int maximalEnergyLvl;
    protected int currentEnergyLvl;
    public SpaceShipPhysics spaceShipLocation;
    public boolean isShieldActivated;
    public int roundCounter;
    protected boolean ACCELERATE = false;


    public SpaceShip() {
        reset();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {

        roundCounter++;

        if (currentEnergyLvl < maximalEnergyLvl) { // add energt in every turn
            currentEnergyLvl++;
        }

        if (roundCounter == COOLDOWN_TO_SHOOT) {  // count the rounds, they help us check if cooldown is off
            roundCounter = READY_TO_SHOOT;
        }
        this.isShieldActivated = false; //deactivated the shield in every turn

    }

    /**
     * This method responsible on the energy management when the ship getting hit
     */
    private void shipEnergyManagementSystem() {
        maximalEnergyLvl -= HIT_ENERGY; // getting hit decrease the maximal energy by 10

        if (maximalEnergyLvl < HIT_ENERGY) { // energy is non negative number
            maximalEnergyLvl = NO_ENERGY;
        }

        if (maximalEnergyLvl <= currentEnergyLvl) { // even between the current and the maximal energy lvls
            currentEnergyLvl = maximalEnergyLvl;
        }

    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (isShieldActivated) { // bashing as defined in the exercise
            maximalEnergyLvl += BASHING_ENERGY;
            currentEnergyLvl += BASHING_ENERGY;
        } else {
            health--;  // shield is not activated == not bashing
            shipEnergyManagementSystem();
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        this.health = START_HEALTH;  // reset to the default values given by the exercise
        this.maximalEnergyLvl = START_MAX_ENERGY;
        this.currentEnergyLvl = START_CUR_ENERGY;
        this.spaceShipLocation = new SpaceShipPhysics();
        this.roundCounter = READY_TO_SHOOT;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return health <= NO_HEALTH;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    protected SpaceShipPhysics getPhysics() {
        return spaceShipLocation;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!isShieldActivated) {
            health--;
            shipEnergyManagementSystem();
        }

    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public abstract Image getImage();  // implemented by the subclasses


    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (currentEnergyLvl >= ABLE_TO_SHOOT && roundCounter == READY_TO_SHOOT) {
            game.addShot(spaceShipLocation);  // shoot if we have enough energy
            currentEnergyLvl -= ABLE_TO_SHOOT;
        }

    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (currentEnergyLvl >= ABLE_TO_SHIELD) {  // check if we have enough energy
            currentEnergyLvl -= ABLE_TO_SHIELD;
            isShieldActivated = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (currentEnergyLvl >= ABLE_TO_TELEPORT) { // check if we have enough energy
            currentEnergyLvl -= ABLE_TO_TELEPORT;
            spaceShipLocation = new SpaceShipPhysics();
        }
    }

}
