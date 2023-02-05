package ucl.ac.uk.model;

import java.io.*;

public class FileHandling {
    private static final String listOfListsPath = "./data/listoflists.txt";

    //removes a given line from a text file
    public static void removeLine(String removedLine, File file) throws IOException {

        //We can't actually remove a line from a file, so we'll copy the file, with the exception of the line to be
        // removed, onto a temporary file and rename it to achieve this.
        BufferedWriter writer = new BufferedWriter(new FileWriter("./data/templist.txt"));
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            //Copy all lines in the file except the line we want to remove
            if (!line.equalsIgnoreCase(removedLine)) {
                writer.write(line);
                writer.newLine();
            }
        }
        //Close our files to avoid loss of data
        writer.close();
        reader.close();

        //Delete our original file, to be able to rename our temporary file into it.
        String path = file.getPath();
        file.delete();

        //Rename our temporary file to the name we wanted to keep in the first place, that completes the replacement.
        File newFile = new File("./data/templist.txt");
        newFile.renameTo(new File(path));
    }

    //Substitutes a given line with other value. Similar to removeLine().
    public static void substituteLine(String oldLine, String newLine, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./data/templist.txt"));
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            //If the line is equal to the line we want to remove, write new value instead. Keep all other values
            if (line.equalsIgnoreCase(oldLine)) {
                writer.write(newLine);
                writer.newLine();
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
        writer.close();
        reader.close();

        //Delete original file
        String path = file.getPath();
        file.delete();
        //Rename temporary file.
        File newFile = new File("./data/templist.txt");
        newFile.renameTo(new File(path));
    }

    //Adds a new line to a given file. Used to store new list names or a new element in a list.
    public static void addLine(String addedLine, File file) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.append(addedLine + "\n");
        writer.close();
    }


}
