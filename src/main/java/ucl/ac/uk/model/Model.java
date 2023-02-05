package ucl.ac.uk.model;

import java.io.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    //Will be used to store all the lists
    private ArrayList<List> listOfLists;
    //Path of the text file that will contain the name of every list
    private final String listOfListsPath = "./data/listoflists.txt";

    public Model() {
        listOfLists = new ArrayList<>();
    }

    public ArrayList<List> getListOfLists() {
        return listOfLists;
    }

    //Determine whether a given name is the name of a List in listOfLists, used to avoid having lists with repeated
    //names
    public boolean uniqueName(String name) {
        for (List list : listOfLists) {
            if (list.getName().equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    public void newList(String name) {
        if (uniqueName(name)) {
            listOfLists.add(new List(name));
        }
    }

    public List getList(String name) {
        for (List list : listOfLists) {
            if (list.getName().equalsIgnoreCase(name)) return list;
        }
        return null;
    }

    public void removeList(String listName) {
        List removedList = getList(listName);
        listOfLists.remove(removedList);
    }

    //Reads the file holding the name of all the lists stored, it will also call all the methods needed to load and
    //initialise all the data into our data structure.
    public void readMainFile() throws IOException {
        File file = new File(listOfListsPath);
        //Buffered reader will allow us to read the file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        //For each line, creates a new List with name equal to the value of the line.
        while ((line = reader.readLine()) != null) {
            newList(line);
        }
        //We now have an empty list for every entry in our main list file. readListFiles() will read the file of
        //each list and load its elements into it.
        readListFiles();
        //Close the file to avoid loss of data
        reader.close();
    }

    //Reads through each of the list files specified in the main listoflists.txt file and
    //adds each of the lines in each list file to the List object.
    public void readListFiles() throws IOException {
        for (List list : listOfLists) {
            //Open the list file
            File listFile = new File("./data/" + list.getName() + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(listFile));

            String line;
            //Copy each of the lines of the file as Strings into the List object
            while ((line = reader.readLine()) != null) {
                list.addElement(line);
            }
            //Close the file to avoid loss of data
            reader.close();
        }
    }

    public ArrayList<String> getListNames() {
        ArrayList<String> listOfNames = new ArrayList<>();
        for (List list : listOfLists) {
            listOfNames.add(list.getName());
        }
        return listOfNames;
    }

    //Used when we create a new list through the interface of our web app
    public void addNewEmptyList(String listName) throws IOException {
        if (uniqueName(listName) && !listName.equals("")) {
            //Creates a new list with the given name
            newList(listName);
            //Creates a text file for that list to store strings
            File newFile = new File("./data/" + listName + ".txt");
            newFile.createNewFile();
            //Adds the name of the string to our main text file containing the names of every list we hold
            FileHandling.addLine(listName, new File(listOfListsPath));
        }
    }

    //Used when deleting a list through the interface
    public void deleteList(String listName) throws IOException {
        //Remove the list from our listOfLists
        removeList(listName);

        //Delete the text file for the list
        File deletedFile = new File("./data/" + listName + ".txt");
        deletedFile.delete();

        //Remove the list name from the main text file
        FileHandling.removeLine(listName, new File(listOfListsPath));
    }

    //Used when adding an element to a list through the interface
    public void addElement(String element, String listName) throws IOException {
        List myList = getList(listName);
        //If the element is already part of the list, don't do anything (we are not accepting duplicates)
        for (String elements : myList.getList()) {
            if (elements.equalsIgnoreCase(element)) {
                return;
            }
        }

        //Add element to our List object
        myList.addElement(element);
        //Open the text file of the list
        File listFile = new File("./data/" + listName + ".txt");
        //Add the value of the element to the text file.
        FileHandling.addLine(element, listFile);
    }

    //Used when deleting an element of a list through the interface
    public void removeElement(String element, String listName) throws IOException {
        List myList = getList(listName);
        //Remove the element from the list object
        myList.removeElement(element);

        //Open the List text file
        File listFile = new File("./data/" + listName + ".txt");
        //Remove the line containing the element from the textfile
        FileHandling.removeLine(element, listFile);
    }

    //Used when renaming a list through the interface
    public void renameList(String newName, String listName) throws IOException {
        //Only do something if the new list name is not taken.
        if (uniqueName(newName)) {
            List myList = getList(listName);
            //Rename the List object
            myList.renameList(newName);

            //Rename the List's text file
            File listFile = new File("./data/" + listName + ".txt");
            listFile.renameTo(new File("./data/" + newName + ".txt"));

            //In our main text file, substitute the line containing the old list name by the new list name.
            FileHandling.substituteLine(listName, newName, new File(listOfListsPath));
        }
    }

    //Used when renaming an element of a list through the interface.
    public void renameElement(String oldElementName, String newElementName, String listName) throws IOException {
        List myList = getList(listName);
        if (!myList.exists(newElementName)) {
            //Change the string value to the new value
            myList.renameElement(oldElementName, newElementName);

            //Open our List text file
            File listFile = new File("./data/" + listName + ".txt");
            //Substitute the line containing the old element by the new element.
            FileHandling.substituteLine(oldElementName, newElementName, listFile);
        }
    }

    //Used for searching, returns an arraylist containing two arraylists, one with strings containing the searched term,
    //and the other one holds the List object the string belongs to.
    public ArrayList<ArrayList<String>> contains(String substring) {
        ArrayList<ArrayList<String>> valueAndList = new ArrayList<>();
        //array list containing the string that contains the substring
        ArrayList<String> value = new ArrayList<>();
        //Array list containing the List that contains the string that contains the substring
        ArrayList<String> valueBelongsTo = new ArrayList<>();

        valueAndList.add(value);
        valueAndList.add(valueBelongsTo);

        //Scan through all the strings in all the List's and add the strings that contain the substring and the
        //List it was found in/
        for (List list : getListOfLists()) {
            for (String string : list.getList()) {
                if (string.contains(substring)) {
                    value.add(string);
                    valueBelongsTo.add(list.getName());
                }
            }
        }
        return valueAndList;
    }

}


