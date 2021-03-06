package com.cs115.rex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RexDatabaseUtilities {

    private static String TAG = "databaseUtilities";

    /**
     * Gets elements of dog.
     *
     * @param context
     * @return
     * @author Karena Huang
     */
    public static Cursor getDog(Context context) {
        try {

            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.DOG,
                    new String[]{
                            RexDatabaseHelper.NAME,
                            RexDatabaseHelper.WEIGHT,
                            RexDatabaseHelper.BREED,
                            RexDatabaseHelper.PHOTO},
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);
        } catch (SQLiteException e) {
            return null;
        }
    }

    // get cursor of all allergies

    /**
     * //TODO: See if this is used anywhere before due date.
     * //TODO: add argument for dogId
     * Gets a cursor of all the allergies for a specific dog
     *
     * @param context
     * @return
     * @author Karena Huang
     */
    public static Cursor getAllergies(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.ALLERGIES,
                    new String[]{
                            RexDatabaseHelper.FOOD_ID,
                            RexDatabaseHelper.DOG_ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);
        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * Gets food name of each allergy for a certain dog.
     *
     * @param context
     * @param dogId
     * @return
     * @author Karena Huang
     */
    public static String[] getAllergyNames(Context context, String dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            String query = "SELECT FOOD._id, NAME FROM FOOD INNER JOIN ALLERGIES ON ALLERGIES.FOOD_ID = FOOD._id WHERE ALLERGIES.DOG_ID=" + dogId;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                int indexFoodNames = cursor.getColumnIndex(RexDatabaseHelper.NAME);
                String[] foodNames = new String[cursor.getCount()];
                int theCount = 0;
                while (cursor.moveToNext()) {
                    foodNames[theCount] = cursor.getString(indexFoodNames);
                    theCount += 1;
                }
                cursor.close();
                return foodNames;
            }
        } catch (SQLiteException e) {
            return new String[0];
        }
        return new String[0];
    }

    /**
     * Gets all food names.
     *
     * @param context
     * @return all food names
     * @author Karena Huang
     */
    public static String[] getAllFoodNames(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            RexDatabaseHelper.NAME},
                    null, null, null, null, null
            );
            String[] foodArray = new String[cursor.getCount()];
            int index = 0;
            while (cursor.moveToNext()) {
                foodArray[index] = cursor.getString(0);
                index += 1;
            }
            cursor.close();
            return foodArray;

        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * returns all food Ids
     *
     * @param context
     * @return food Ids
     * @author Karena Huang
     */
    public static int[] getAllFoodId(Context context) {
        try {
            SQLiteOpenHelper rexDatabasehelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabasehelper.getReadableDatabase();
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            RexDatabaseHelper.ID},
                    null, null, null, null, null
            );
            int[] intArray = new int[cursor.getCount()];
            int index = 0;
            while (cursor.moveToNext()) {
                intArray[index] = cursor.getInt(0);
                index += 1;
            }
            cursor.close();
            return intArray;
        } catch (SQLiteException e) {
            return null;
        }
    }
    public static Cursor getFood(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.ALLERGIES,
                    new String[]{
                            RexDatabaseHelper.FOOD_ID,
                            RexDatabaseHelper.DOG_ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);

        } catch (SQLiteException e) {
            return null;
        }
    }

    //get a cursor with all of the foods that match the given query
    public static Cursor getSelectedFoodList(Context context, String searchName){
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //Finds anything that includes the search String input by the user
            return db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            "_id", RexDatabaseHelper.NAME},
                    "NAME" + " LIKE ?", new String[]{searchName + "%"}, null, null, null
            );

        } catch (SQLiteException e) {
            Log.d("DebugLog: ", "MainActivity - Value: " + "Database exception");
            return null;
        }
    }

    //get all of the foods that match the given query
    public static String[] getSelectedFoodNames(Context context, String searchName){
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //Finds anything that includes the search String input by the user
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            "_id", RexDatabaseHelper.NAME},
                    "NAME" + " LIKE ?", new String[]{searchName + "%"}, null, null, null
            );


            String[] foodArray = new String[cursor.getCount()];
            int theCount = 0;
            while(cursor.moveToNext()) {
                foodArray[theCount] = cursor.getString(1);
                theCount += 1;
            }


            //return cursor;
            cursor.close();
            db.close();
            Log.d("DebugLog: ", "DatabaseUtils - Value: " + foodArray[foodArray.length-1]);
            return foodArray;

        } catch (SQLiteException e) {
            Log.d("DebugLog: ", "MainActivity - Value: " + "Database exception");
            return null;
        }
    }

    //get all of the database data for a particular food given that food's name
    public static String[] getFoodByName(Context context, String searchName){
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            RexDatabaseHelper.NAME, RexDatabaseHelper.TOXICITY, RexDatabaseHelper.IMAGE_RESOURCE_ID,
                            RexDatabaseHelper.QUOTE},
                    "NAME = ?", new String[]{searchName}, null, null, null
            );
            String[] thisFood = new String[4];
            int theCount = 0;
            if(cursor.moveToFirst()) {
                thisFood[0] = cursor.getString(0);
                thisFood[1] = cursor.getString(1);
                thisFood[2] = cursor.getString(2);
                thisFood[3] = cursor.getString(3);
            }
            cursor.close();
            db.close();
            return thisFood;

        } catch (SQLiteException e) {
            //TODO Add toast - food not available
            return null;
        }
    }

    /**
     * updates dog name
     *
     * @param context
     * @param newDogName
     * @return
     * @author Karena Huang
     */
    public static boolean updateName(Context context, String newDogName) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            ContentValues dogNameValues = new ContentValues();
            dogNameValues.put(RexDatabaseHelper.NAME, newDogName);
            db.update(RexDatabaseHelper.DOG, dogNameValues,
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});
            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    public static boolean updateBreed(Context context, String newBreed) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            ContentValues breedValues = new ContentValues();
            breedValues.put(RexDatabaseHelper.BREED, newBreed);
            db.update(RexDatabaseHelper.DOG, breedValues,
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    public static boolean updateWeight(Context context, String newWeight) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            ContentValues weightValues = new ContentValues();
            weightValues.put(RexDatabaseHelper.WEIGHT, newWeight);
            db.update(RexDatabaseHelper.DOG, weightValues,
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    public static boolean updatePhoto(Context context, String theImage) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            ContentValues dogPhoto = new ContentValues();
            dogPhoto.put(RexDatabaseHelper.PHOTO, theImage);
            db.update(RexDatabaseHelper.DOG, dogPhoto, RexDatabaseHelper.ID + " =?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;

        }
    }

    public static int addAllergy(Context context, int foodId, int dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //adding record to allergy table
            ContentValues foodAllergy = new ContentValues();
            foodAllergy.put(RexDatabaseHelper.FOOD_ID, foodId);
            foodAllergy.put(RexDatabaseHelper.DOG_ID, dogId);
            db.insert("ALLERGIES", null, foodAllergy);
            Cursor cursor = db.query(RexDatabaseHelper.ALLERGIES,
                    new String[]{
                            RexDatabaseHelper.ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?"
                            + " AND " + RexDatabaseHelper.FOOD_ID + " = ?",
                    new String[]{Integer.toString(dogId), Integer.toString(foodId)},
                    null, null, null);
            int allergyId = 0;
            if (cursor.moveToFirst()) {
                allergyId = cursor.getInt(0);
            }
            db.close();
            cursor.close();
            return allergyId;
        } catch (SQLiteException e) {
            return -1;
        }
    }

    public static boolean removeAllergy(Context context, String foodId, String dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //query where allergy equals allergy id
            db.delete("ALLERGIES", RexDatabaseHelper.FOOD_ID + " = ? AND " +
                            RexDatabaseHelper.DOG_ID + " = ? ",
                    new String[]{foodId, dogId});
            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }

    }
}