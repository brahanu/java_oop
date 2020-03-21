package filesprocessing;

import java.io.*;
import java.util.ArrayList;


/**
 * The class will mange the whole project, the class will take care of Type 2 Errors
 */
class Manager {
    private File[] directory;
    private File commandFile;

    Manager(File directory, File commandFile) {
        this.directory = directory.listFiles();
        this.commandFile = commandFile;
    }

    /**
     * The method responsible on manging the program will call the needed method from the project
     *
     * @throws IOException throwed if there were a problem accessing the commandFile
     */
    void manageIt() throws IOException {
        try {
            validateDirectory(this.directory);
            ArrayList<String> commandFile = Parser.parseCommandFile(this.commandFile);
            ArrayList<File> validDir = validateDirectory(this.directory);
            ArrayList<Section> sections = Parser.createSections(commandFile);
            for (Section section : sections) {
                ArrayList<File> preOutput = Parser.createOutput(validDir, section);
                for (File file : preOutput) {
                    System.out.println(file.getName());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException();
        } catch (TypeTwoExceptions typeTwoExceptions) {
            System.err.println(typeTwoExceptions.getMessage());
        }

    }


    /**
     * check if the directory is valid and then parse it
     *
     * @param directory the given directory
     * @return ArrayList of valid files in the directory
     */
    public ArrayList<File> validateDirectory(File[] directory) {
        ArrayList<File> fileList = new ArrayList<File>();
        if (directory != null) {
            for (File file : directory) {
                if (file.isFile()) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

}
