package edu.orangecoastcollege.cs273.wheretonext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "WhereToNext";
    private static final String DATABASE_TABLE = "Colleges";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_POPULATION = "population";
    private static final String FIELD_TUITION = "tuition";
    private static final String FIELD_RATING = "rating";
    private static final String FIELD_IMAGE_NAME = "image_name";


    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){

        // TODO:  Write code to create the database
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + "INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_POPULATION + " INTEGER, "
                + FIELD_TUITION + " REAL, "
                + FIELD_RATING + " REAL, "
                + FIELD_IMAGE_NAME + " TEXT, " + ")";

        database.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {

        // TODO:  Write code to upgrade the database
        database.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
        onCreate(database);

    }

    //********** DATABASE OPERATIONS:  ADD, GETALL

    public void addCollege(College college) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, college.getName());
        values.put(FIELD_POPULATION, college.getPopulation());
        values.put(FIELD_TUITION, college.getTuition());
        values.put(FIELD_RATING, college.getRating());
        values.put(FIELD_IMAGE_NAME, college.getImageName());
        // TODO:  Write code to add a College to the database
        long id = db.insert(DATABASE_TABLE, null, values);
        college.setId(id);
        db.close();

    }

    public ArrayList<College> getAllColleges() {
        ArrayList<College> collegeList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        // TODO:  Write the code to get all the colleges from the database.
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_POPULATION, FIELD_TUITION,
                        FIELD_RATING, FIELD_IMAGE_NAME},
                null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                College college = new College(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5));
                collegeList.add(college);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return collegeList;
    }








}
