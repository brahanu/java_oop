package filesprocessing;


import java.io.File;

/**
 * The main class for the program
 */
public class DirectoryProcessor {

    private static final int DIRECTORY_PLACE = 0;
    private static final int COMMAND_FILE_PLACE = 1;
    private static final int NUM_ARGS = 2;

    /**
     * the main mehtod for the project will handle the basic input Exceptions and then will call
     * the manager
     *
     * @param args the user input
     */
    public static void main(String[] args) {
        try {
            if (args.length != NUM_ARGS) {
                throw new InvalidUsageException();
            }
            File dir = new File(args[DIRECTORY_PLACE]);
            File commandFile = new File(args[COMMAND_FILE_PLACE]);
            if (!dir.isDirectory() || !commandFile.isFile()) {
                throw new IOException();
            }
            Manager manger = new Manager(dir, commandFile);
            manger.manageIt();
        } catch (InvalidUsageException | IOException e) {
            System.err.println(e.getMessage());
        }

    }


}
