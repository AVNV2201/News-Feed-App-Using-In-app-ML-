package com.abhinav.newsfeed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MLHelper {

    static String  storageNameForNoOfRounds = "ab" ;
    static String  storageNameForNoOfRewards = "bc" ;
    static String  storageNameForNoOfSelections = "cd" ;

    static int TOTAL_NEWS_COUNT = 20;

    static int NO_FILTER = 0;
    static int SQUARE_FILTER = 1;
    static int CUBIC_FILTER = 2;

    private static void filter(int filterType, double[] arr){

        if( filterType == SQUARE_FILTER ){
            for( int i = 0; i < arr.length; i++ ){
                arr[i] *= arr[i] ;
            }
        }
        else if( filterType == CUBIC_FILTER ){
            for( int i = 0; i < arr.length; i++ ){
                arr[i] *= arr[i] * arr[i] ;
            }
        }
    }

    static ArrayList<Integer> getSelectionList( ArrayList<Integer> no_of_selections,
                                                ArrayList<Integer> no_of_rewards,
                                                int no_of_rounds,
                                                int filterType ){
        int no_of_categories = ResourceHelper.categoryCodes.size();
        ArrayList<Integer> selections = new ArrayList<Integer>(Collections.nCopies(no_of_categories,0));

        if( no_of_rounds == 1 ){

            int temp = TOTAL_NEWS_COUNT / no_of_categories;
            for( int i = 0; i < no_of_categories; i++ ){
                selections.set( i, temp);
            }

        } else {

            double delta, averageRewards, sum_of_ucb = 0.0;
            double[] ucb = new double[no_of_categories];

            for( int i = 0; i < no_of_categories; i++ ){

                averageRewards = (double)no_of_rewards.get(i) / no_of_selections.get(i) ;
                delta = Math.sqrt( ( (double)3/2 ) * Math.log( (double)no_of_rounds ) / no_of_selections.get(i) );
                ucb[i] = averageRewards + delta;

            }

            filter( filterType, ucb);

            for (double v : ucb) {
                sum_of_ucb += v;
            }

            double ratio = (double) (TOTAL_NEWS_COUNT / sum_of_ucb) ;
            for( int i = 0; i < no_of_categories; i++ ){
                selections.set( i, (int)(ucb[i] * ratio) );
            }
        }

        return selections;
    }

}
