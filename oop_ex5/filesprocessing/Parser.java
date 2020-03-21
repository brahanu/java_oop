package filesprocessing;


import filesprocessing.orders.*;
import filesprocessing.filters.*;

import java.io.*;

import java.util.*;
import java.util.Collections;


/**
 * The class will mange the section module, will be the only one that
 * knows the logical orders of the commands file
 */
public class Parser {

    private static final int SPECIAL_SECTION_SIZE = 3;
    private static final int DEFAULT_SECTION_SIZE = 4;
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String DEFAULT_ORDER = "abs";
    private static final int SUBSECTION = 2;
    private static final int FILTER_PLACE = 1;
    private static final int ORDER_PLACE = 4;

    /**
     * the method will parse the command file and c
     *
     * @param commandFile blah blha
     * @return an array list representing the commandFile
     */
    static ArrayList<String> parseCommandFile(File commandFile) throws java.io.IOException {
        BufferedReader reader = new BufferedReader(new FileReader(commandFile));
        Object[] temp = reader.lines().toArray();
        String[] strings = Arrays.stream(temp).toArray(String[]::new);
        ArrayList<String> newList = new ArrayList<String>();
        Collections.addAll(newList, strings);
        return newList;
    }

    /**
     * the method responsible on creating the sections
     *
     * @param commandFile an array list representing the commandFile
     * @return will return an arrayList of sections
     * @throws BadSubSectionException will be thrown if the sections are not in a good format
     */
    static ArrayList<Section> createSections(ArrayList<String> commandFile)
            throws BadSubSectionException {
        ArrayList<Section> sections = new ArrayList<Section>();
        int i = 0;
        while (i < commandFile.size()) {
            if (commandFile.get(i).equals(FILTER)) {
                if (i + SPECIAL_SECTION_SIZE > commandFile.size() ||
                        commandFile.get(i + FILTER_PLACE) == null) {
                    throw new BadSubSectionException();
                }
                //size three section
                if ((commandFile.get(i + SUBSECTION).equals(ORDER)) &&
                        (i + SPECIAL_SECTION_SIZE >= commandFile.size() ||
                                (commandFile.get(i + SPECIAL_SECTION_SIZE).equals(FILTER)))) {
                    sections.add(new Section(commandFile.get(i + FILTER_PLACE), i + SUBSECTION,
                            DEFAULT_ORDER, i + ORDER_PLACE));
                    i += SPECIAL_SECTION_SIZE;
                } else if ((commandFile.get(i + SUBSECTION).equals(ORDER)) &&
                        (i + DEFAULT_SECTION_SIZE >= commandFile.size() ||
                                (commandFile.get(i + DEFAULT_SECTION_SIZE).equals(FILTER)))) {
                    //regular size section
                    sections.add(new Section(commandFile.get(i + FILTER_PLACE), i + SUBSECTION,
                            commandFile.get(i + SPECIAL_SECTION_SIZE), i + ORDER_PLACE));
                    i += DEFAULT_SECTION_SIZE;
                } else {
                    throw new BadSubSectionException();
                }
            } else {
                throw new BadSubSectionException();
            }
        }
        return sections;
    }

    /**
     * the method will create the final program output, filtered and sorted arrayList
     *
     * @param dir     the given directory
     * @param section the section according to the commandFile
     * @return filtered and sorted Arraylist
     */
    static ArrayList<File> createOutput(ArrayList<File> dir, Section section) {
        ArrayList<File> outputFile = new ArrayList<File>();
        Filter filter = section.getFilter();
        for (File file : dir) {
            if (filter.filter(file)) {
                outputFile.add(file);
            }
        }
        Sorter sortIt = new Sorter(outputFile, section.getOrder());
        sortIt.sortIt();
        return outputFile;
    }


}
