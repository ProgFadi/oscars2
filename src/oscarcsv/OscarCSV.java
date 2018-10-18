/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oscarcsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Eng. Fadi R
 */
// this is  the main method in java fx file
public class OscarCSV extends Application {

    // declare hashMap to use it when i need to get actor name who has more than oscars from others
    HashMap<String, Integer> hash = new HashMap<>();

    // declare and init list of oscar object (oscar object implemented Comparable class so enable me to sort the oscar list by year :) )
    List<Oscar> oscarList = new ArrayList<>();

    // start method : started after the main method
    @Override
    public void start(Stage stage) throws Exception {

        // declare path of csv file (in desktop as osca.csv)
        String filePath = FileSystemView.getFileSystemView().getHomeDirectory() + "\\oscar.csv";
        // first load the csv data to the oscar list once and then i will use the oscar list in my functions
        //cause i wan't to read csv data each time and each function i will need
        readCSVToList(filePath); // read all data from csv with provided pathString in desktop
        System.out.println("oscar list size is : " + oscarList.size()); // get list size

        // first check if the list is not null and list size is not zero
        if (oscarList != null) {
            if (!oscarList.isEmpty()) {
                // first print the name of actor who has more Oscars from the others
                System.out.println("actor who has more Oscars from the others :  " + actorHasMoreOscarsFromOthers());

                // get actor name who has the oldest oscar with year and movie name
                System.out.println("oldest actor is  :  " + oldestOscar());

                // get actors name who have more than oscars in the same row
                System.out.println("actor/s who have more than oscars in the same raw are/is : " + actorHasMoreThanOscar());
            }
        }

        //
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    // access to csv file in desktop,read the data and put them in oscarList of oscar object 
    public void readCSVToList(String filePath) {

        // clear the list
        oscarList.clear();
        // 
        BufferedReader br = null;
        
        // to hold line data of each row
        String line = "";
        try {
            /// set the fiePath and reader with biffered reader to start reading from the file
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) { // read next line and check if it is not null i will continue to get teh data
             
                // declare local var of array of string to hold the row line data - size is 5 -> 5 cols seperated by ,
                String[] colsVal = new String[5];
                // trim the line and then specify the number of comma to be splited
                colsVal = line.trim().split(",", 5); // seperate the line with first 5 commas only , i used this method with this param to keep last col with comma (more than oscars in row) "x , y" 
                //since  "x,y" in the last col is complete data value and i shouldn't seperate it to more than col
                

                // now add each line data to ocscar list as Oscar obejct to use the list simply in future
                oscarList.add(new Oscar(Integer.parseInt(colsVal[0].trim()), Integer.parseInt(colsVal[1].trim()), Integer.parseInt(colsVal[2].trim()), colsVal[3].trim(), colsVal[4].trim()));

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(OscarCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OscarCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // get actor who has more than oscars form the others ,
    // notice : i ignored the case which is -> last col might has more than movie seperated by , and satisfied by the main oscars
    // in this method i used the hashMap to make me simply sum number of oscars for each actor in one key in hashMap
    // <String,integer>  : string is actor name , integer is number of oscars
    public String actorHasMoreOscarsFromOthers() {
        hash.clear();

        //  hold max value of oscars which actor have oscars more than others
        int maxVal = 0;
        String actorKeyName = ""; // hold the name of actor who have oscars more than others

        for (int i = 0; i < oscarList.size(); i++) {

            // first check if the actor name is already exist in hashmap or not
            // if not then i'll add it as first time with number of oscars 1
            if (hash.get(oscarList.get(i).getName()) == null) {
                hash.put(oscarList.get(i).getName(), 1);
            } else { // if it was exist already in the haspmap so i don;t need to add it again , then i just will increase  oscars number and update it again
                int oldValFreq = hash.get(oscarList.get(i).getName());
                hash.put(oscarList.get(i).getName(), oldValFreq + 1);

            }
            // last updated actor name and max value in hashmap
            if (hash.get(oscarList.get(i).getName()) > maxVal) {
                maxVal = hash.get(oscarList.get(i).getName());
                actorKeyName = oscarList.get(i).getName();
            }
        }

        // return the actor who has more than oscars
        return actorKeyName;

    }

    
    // this function will calculate oldest actor year with movie name
    public String oldestOscar() {

        // first i need to sort the list by year ascending then i will get smallest number from 0 index location in list
        // fatest way :)
        Collections.sort(oscarList);

        for (int i = 0; i < oscarList.size(); i++) {
            System.out.println("name is : " + oscarList.get(i).getName() + ", year is : " + oscarList.get(i).getYear());
        }

        return oscarList.get(0).getName() + ", year :" + oscarList.get(0).getYear() + ", movie : " + oscarList.get(0).getMovie();

    }

    //  function to get actor who has more than oscars in the same row in last col[4] which was sepereted with , comma
    // so each last col has comma  means more than oscars he have
    //
    public String actorHasMoreThanOscar() {
        String actors = ""; //  
        for (int i = 0; i < oscarList.size(); i++) {
            if (oscarList.get(i).getMovie().contains(",")) {
                
                actors = actors + " ," + oscarList.get(i).getName();
            }
        }
        return actors;
    }

}
