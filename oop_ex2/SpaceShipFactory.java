
public class SpaceShipFactory {

    // string MAGIC_NUMBERS
    private static final String HUMAN_SHIP = "h";
    private static final String AGGERSSIVE = "a";
    private static final String BASHER = "b" ;
    private static final String DRUNK = "d";
    private static final String RUNNER = "r";
    private static final String SPECIAL_SHIP = "s";

    /**
     *
     * @param args the gamepieces the user wanna play with
     * @return list of all the game object the user have picked
     */

    protected static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] gamePieces = new SpaceShip[args.length];
        for (int characterIndex = 0; characterIndex < args.length; characterIndex++) {
            switch (args[characterIndex]) {
                case HUMAN_SHIP:
                    gamePieces[characterIndex] = new HumanShip();
                    break;

                case AGGERSSIVE:
                    gamePieces[characterIndex] = new Aggressive();
                    break;

                case BASHER:
                    gamePieces[characterIndex] = new Basher();
                    break;

                    case DRUNK:
                    gamePieces[characterIndex] = new Drunkard();
                    break;

                case RUNNER:
                    gamePieces[characterIndex] = new Runner();
                    break;

                case SPECIAL_SHIP:
                    gamePieces[characterIndex] = new SpecialShip();


            }

        }
        return gamePieces;
    }
}