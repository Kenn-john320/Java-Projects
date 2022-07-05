// Kennedy Johnson
// Project 2
// 07/01/2022

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Project2 {

    // Global variables set for the number of intervals and the interval intervalsay
    static int numOfIntervals;
    static ArrayList<int[]> intervals;
    static ArrayList<int[]> alreadyCheck; 

    public static void main(String[] args) throws NumberFormatException, IOException {

        intervals = new ArrayList<int[]>();
        alreadyCheck = new ArrayList<int[]>();
        // Uses one line (most optimal tbh) and takes the first input and changes from a
        // string to an integer
        numOfIntervals = Integer.parseInt(Files.readAllLines(Paths.get("input.txt")).get(0));

        // Goes through the txt file the same way and reads each of the inputs
        // Starts at one because we have already taken and read the first input
        for (int i = 1; i <= numOfIntervals; i++) {

            // Takes the inputs and separates them by the whitespace
            String temp = Files.readAllLines(Paths.get("input.txt")).get(i);
            String[] temp2 = temp.split(" ");

            // Adds in int[] of the intervals into each of the spots in the intervalsaylist
            intervals.add(new int[] { Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]) });
            // [1,2] [1,3] [1,4] .. .
        } // end of for loop

        // System.out.println(findStartIndex());
        // System.out.println(checkOverlaps());
         removeOverlaps();
         System.out.println(intervals.size());
    }// end of main

    public static int findStartIndex() {
        int minStartTime = Integer.MAX_VALUE;
        int index = -1;
        boolean sameStart = false;

        // goes through each element of intervals
        for (int i = 0; i < intervals.size(); i++) {
            // sets the first found start time to the minimum startTime
            if (intervals.get(i)[0] < minStartTime && !alreadyCheck.contains(intervals.get(i))) {
                // if the current start time is greater than the one found, the new one found
                // will replace it
                minStartTime = intervals.get(i)[0];
                sameStart = false;
                // updates the new minStart time with the new found #
                index = i;
                // sets index to current index of the min startTime found
            } else if (intervals.get(i)[0] == minStartTime && !alreadyCheck.contains(intervals.get(i))){
                sameStart = true;
            }
        } // end of for loop

        // saves the index of the Finishtime
        index = (sameStart) ? findFinishIndex( minStartTime) : index;

        // System.out.println(minStartTime);
        alreadyCheck.add(intervals.get(index));
        return index;
    }// end of method

    public static int findFinishIndex(int sameStart) {
        int minFinishTime = Integer.MAX_VALUE;
        int index = -1;

        // goes through each element of intervalsay
        for (int i = 0; i < intervals.size(); i++) {

            // sets the first found start time to the minimum finish tie
            if (intervals.get(i)[1] < minFinishTime && intervals.get(i)[0] == sameStart) {
                // if the current finish time is greater than the one found, the new one found
                // will replace it
                minFinishTime = intervals.get(i)[1];
                // updates the new minFinish time with the new found #
                index = i;
                // sets index to current index of the min finish Time found
            }
        }
        // System.out.println(minFinishTime);
        return index;
    }// end of method


    // checks to see if there are any overlapping intervals
    public static boolean checkOverlaps(){
       for(int i = 0; i < intervals.size(); i++){
            for(int j = 0;  j < intervals.size(); j++){
                //checks to see if the second intervals first point is greater than or equal too the first intervals point
                boolean startBetween = intervals.get(j)[0] >= intervals.get(i)[0] && intervals.get(j)[0] < intervals.get(i)[1];
                //checks to see if the first intervals second point is greater than the first intervals first point
                boolean finishBetween = intervals.get(j)[1] > intervals.get(i)[0] && intervals.get(j)[1] <= intervals.get(i)[1];
                if(i != j && (startBetween || finishBetween)){
                        return true;
                }
            }

       }//end of for loop
        return false; 
    }//end of method




    // removes the intervals from the list of intervals
    public static void removeOverlaps() {
        while(checkOverlaps()){
            int earliestIndex = findStartIndex();
            for(int i = 0; i < intervals.size(); i++){
                if(i != earliestIndex){
                    // if the intervals at the first 
                    boolean startBetween = intervals.get(i)[0] >= intervals.get(earliestIndex)[0] && intervals.get(i)[0] < intervals.get(earliestIndex)[1];
                    boolean finishBetween = intervals.get(i)[1] > intervals.get(earliestIndex)[0] && intervals.get(i)[1] <= intervals.get(earliestIndex)[1];
                    if((startBetween || finishBetween)){
                        intervals.remove(i);
                        //if i is less than the earliest index than subtract the earliest index by one
                        earliestIndex = (i < earliestIndex) ? earliestIndex - 1 : earliestIndex;
                        i--;
                    }

                }   
            }


        }
    }



}// end of class
