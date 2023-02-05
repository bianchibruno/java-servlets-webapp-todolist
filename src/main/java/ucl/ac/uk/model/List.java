package ucl.ac.uk.model;

import java.util.ArrayList;


public class List {
    //It will hold the elements we want to store as Strings in an ArrayList. Each List object will have a name.
    private ArrayList<String> list;
    private String name;

    public List(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    //Determines if a given string is part of a list. Used to determine uniqueness of an element when trying to add
    // it into a list.
    public boolean exists(String element) {
        for (String string : list) {
            if (element.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    public void addElement(String string) {
        if (!exists(string)) {
            list.add(string);
        }
    }

    public void removeElement(String element) {
        for (String string : getList()) {
            if (string.equals(element)) {
                list.remove(string);
                return;
            }
        }
    }

    public void renameList(String newName) {
        this.name = newName;
    }

    //Replaces a string in the list with a new string, used when user wants to rename an entry of the list.
    public void renameElement(String oldName, String newName) {
        for (String string : getList()) {
            if (string.equals(oldName)) {
                list.set(getList().indexOf(string), newName);
            }
        }
    }

}
