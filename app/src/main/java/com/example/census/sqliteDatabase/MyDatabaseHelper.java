package com.example.census.sqliteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private             Context context;
    private static final String  DATABASE_NAME    = "Census.db";
    private static final int     DATABASE_VERSION = 1;

    //region Stationary table
    private static final String STATIONARY_TABLE_NAME = "stationary";
    private static final String STATIONARY_ID         = "_id";
    private static final String STATIONARY_USERNAME   = "stationary_username";
    private static final String STATIONARY_PASSWORD   = "stationary_password";
    private static final String STATIONARY_APIKEY     = "stationary_apikey";
    private static final String STATIONARY_REGION_ID  = "region_id";
    //end region Stationary table

    //region Controller table
    private static final String CONTROLLER_TABLE_NAME = "controller";
    private static final String CONTROLLER_ID         = "_id";
    private static final String CONTROLLER_NAME       = "controller_name";
    private static final String CONTROLLER_USERNAME   = "controller_username";
    private static final String CONTROLLER_PASSWORD   = "controller_password";
    private static final String CONTROLLER_APIKEY     = "controller_apikey";
    private static final String CONTROLLER_REGION_ID  = "region_id";
    //end region Controller table

    //region CitizenLogin table
    private static final String CITIZEN_LOGIN_TABLE_NAME   = "citizen_login";
    private static final String CITIZEN_LOGIN_ID           = "_id";
    private static final String CITIZEN_LOGIN_USERNAME     = "username";
    private static final String CITIZEN_LOGIN_PASSWORD     = "password";
    private static final String CITIZEN_LOGIN_FINGER_PRINT = "finger_print";
    private static final String CITIZEN_LOGIN_FACIAL_PRINT = "facial_print";
    private static final String CITIZEN_LOGIN_APIKEY       = "api_key";
    //end region CitizenLogin table

    //region Citizen table
    private static final String CITIZEN_TABLE_NAME  = "citizen";
    private static final String CITIZEN_TIN         = "citizen_tin";
    private static final String CITIZEN_FULL_NAME   = "citizen_fullName";
    private static final String CITIZEN_USERNAME_ID = "username_id";
    private static final String CITIZEN_REGION_ID   = "region_id";
    //end region Citizen table


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStationary = "CREATE TABLE " + STATIONARY_TABLE_NAME +
                " (" + STATIONARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       STATIONARY_USERNAME + " TEXT, " +
                       STATIONARY_PASSWORD + " TEXT, " +
                       STATIONARY_APIKEY + " INTEGER, " +
                       STATIONARY_REGION_ID + " INTEGER);";
        db.execSQL(createTableStationary);

        String createTableController = "CREATE TABLE " + CONTROLLER_TABLE_NAME +
                " (" + CONTROLLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       CONTROLLER_NAME + " TEXT, " +
                       CONTROLLER_USERNAME + " TEXT, " +
                       CONTROLLER_PASSWORD + " TEXT, " +
                       CONTROLLER_APIKEY + " INTEGER, " +
                       CONTROLLER_REGION_ID + " INTEGER);";
        db.execSQL(createTableController);

        String createTableCitizenLogin = "CREATE TABLE " + CITIZEN_LOGIN_TABLE_NAME +
                " (" + CITIZEN_LOGIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       CITIZEN_LOGIN_USERNAME + " TEXT, " +
                       CITIZEN_LOGIN_PASSWORD + " TEXT, " +
                       CITIZEN_LOGIN_FINGER_PRINT + " TEXT, " +
                       CITIZEN_LOGIN_FACIAL_PRINT + " TEXT, " +
                       CITIZEN_LOGIN_APIKEY + " INTEGER);";
        db.execSQL(createTableCitizenLogin);

        String createTableCitizen = "CREATE TABLE " + CITIZEN_TABLE_NAME +
                " (" + CITIZEN_TIN + " INTEGER, " +
                CITIZEN_FULL_NAME + " TEXT, " +
                CITIZEN_USERNAME_ID + " INTEGER, " +
                CITIZEN_REGION_ID + " INTEGER);";
        db.execSQL(createTableCitizen);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STATIONARY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONTROLLER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CITIZEN_LOGIN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CITIZEN_TABLE_NAME);
        onCreate(db);
    }
}
