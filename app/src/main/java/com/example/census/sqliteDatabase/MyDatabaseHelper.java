package com.example.census.sqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.census.model.Citizen;
import com.example.census.model.CitizenLogin;
import com.example.census.model.Controller;
import com.example.census.model.Household;
import com.example.census.model.HouseholdType;
import com.example.census.model.HouseholdTypee;
import com.example.census.model.Stationary;
import com.example.census.modelDAO.ControllerDAO;
import com.example.census.modelDAO.StationaryDAO;

import java.util.Arrays;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private             Context context;
    private static final String  DATABASE_NAME    = "Census.db";
    private static final int     DATABASE_VERSION = 4; // todo increment (only!!! when you need clear database)

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

    //region Region table
    private static final String REGION_TABLE_NAME   = "region";
    private static final String REGION_ID           = "_id";
    private static final String REGION_NAME         = "region_name";

    private List<String> regionsList = Arrays.asList(
            "Aktau"      , "Aktobe"   , "Almaty"   , "Atyrau"  , "Astana"  ,
            "Taldykorgan", "Taraz"    , "Turkistan", "Oral"     , "Oskemen" ,
            "Kokshetau"  , "Kostanay" , "Pavlodar" , "Petropavl", "Semey"   ,
            "Jezkazgan"  , "Karaganda", "Kyzylorda", "Shymkent"
    );

    //endregion Region table

    //region Household table
    private static final String HOUSEHOLD_TABLE_NAME = "household";
    private static final String HOUSEHOLD_ID = "_id";
    private static final String HOUSEHOLD_CITIZEN_TIN = "citizen_tin";
    private static final String HOUSEHOLD_ADDRESS = "household_address";
    private static final String HOUSEHOLD_TYPE = "household_type";
    private static final String HOUSEHOLD_REGION = "region";
    private static final String HOUSEHOLD_YEAR = "household_year";
    private static final String HOUSEHOLD_FLOOR = "household_floor";
    private static final String HOUSEHOLD_MATERIAL = "household_material";
    private static final String HOUSEHOLD_LANDSCAPE = "household_landscape";
    private static final String HOUSEHOLD_SIZE = "household_size";
    private static final String HOUSEHOLD_WO_SIZE = "household_wo_size";
    private static final String HOUSEHOLD_ROOM = "household_room";
    private static final String HOUSEHOLD_OWNER = "household_owner";
    //end region Household table

    //region HouseholdType table
    private static final String HOUSEHOLD_TYPE_TABLE_NAME = "household_type";
    private static final String HOUSEHOLD_TYPE_ID = "_id";
    private static final String HOUSEHOLD_TYPE_NAME = "household_type_name";
    private static final String HOUSEHOLD_TYPE_TYPE = "household_type_type";
    //end region HouseholdType table

    //region HouseholdTypee table
    private static final String HOUSEHOLD_TYPEE_TABLE_NAME = "household_typee";
    private static final String HOUSEHOLD_TYPEE_ID = "_id";
    private static final String HOUSEHOLD_TYPEE_NAME = "household_typee_name";
    //end region HouseholdTypee table

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

        String createTableRegion = "CREATE TABLE " + REGION_TABLE_NAME +
                " (" + REGION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       REGION_NAME + " TEXT);";
        db.execSQL(createTableRegion);

        String createTableHousehold = "CREATE TABLE " + HOUSEHOLD_TABLE_NAME +
                " (" + HOUSEHOLD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HOUSEHOLD_CITIZEN_TIN + " INTEGER, " +
                HOUSEHOLD_ADDRESS + " TEXT, " +
                HOUSEHOLD_TYPE + " INTEGER, " +
                HOUSEHOLD_REGION + " INTEGER, " +
                HOUSEHOLD_YEAR + " INTEGER, " +
                HOUSEHOLD_FLOOR + " INTEGER, " +
                HOUSEHOLD_MATERIAL + " TEXT, " +
                HOUSEHOLD_LANDSCAPE + " TEXT, " +
                HOUSEHOLD_SIZE + " INTEGER, " +
                HOUSEHOLD_WO_SIZE + " INTEGER, " +
                HOUSEHOLD_ROOM + " INTEGER, " +
                HOUSEHOLD_OWNER + " TEXT);";
        db.execSQL(createTableHousehold);

        String createTableHouseholdType = "CREATE TABLE " + HOUSEHOLD_TYPE_TABLE_NAME +
                " (" + HOUSEHOLD_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HOUSEHOLD_TYPE_NAME + " TEXT, " +
                HOUSEHOLD_TYPE_TYPE + " INTEGER);";
        db.execSQL(createTableHouseholdType);

        String createTableHouseholdTypee = "CREATE TABLE " + HOUSEHOLD_TYPEE_TABLE_NAME +
                " (" + HOUSEHOLD_TYPEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HOUSEHOLD_TYPEE_NAME + " TEXT);";
        db.execSQL(createTableHouseholdTypee);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STATIONARY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONTROLLER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CITIZEN_LOGIN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CITIZEN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REGION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD_TYPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD_TYPEE_TABLE_NAME);
        onCreate(db);
    }

    public long addStationary(Stationary stationary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(STATIONARY_USERNAME, stationary.getStationary_username());
        cv.put(STATIONARY_PASSWORD, stationary.getStationary_password());
        cv.put(STATIONARY_APIKEY, stationary.getStationary_apikey());
        cv.put(STATIONARY_REGION_ID, stationary.getRegion_id());

        return db.insert(STATIONARY_TABLE_NAME, null, cv);
    }

    public long addController(Controller controller) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CONTROLLER_NAME, controller.getController_name());
        cv.put(CONTROLLER_USERNAME, controller.getController_username());
        cv.put(CONTROLLER_PASSWORD, controller.getController_password());
        cv.put(CONTROLLER_APIKEY, controller.getController_apikey());
        cv.put(CONTROLLER_REGION_ID, controller.getRegion_id());

        return db.insert(CONTROLLER_TABLE_NAME, null, cv);
    }

    public long addCitizenLogin(CitizenLogin citizenLogin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CITIZEN_LOGIN_USERNAME, citizenLogin.getUsername());
        cv.put(CITIZEN_LOGIN_PASSWORD, citizenLogin.getPassword());
        cv.put(CITIZEN_LOGIN_FINGER_PRINT, citizenLogin.getFinger_print());
        cv.put(CITIZEN_LOGIN_FACIAL_PRINT, citizenLogin.getFacial_print());
        cv.put(CITIZEN_LOGIN_APIKEY, citizenLogin.getApi_key());

        return db.insert(CITIZEN_LOGIN_TABLE_NAME, null, cv);
    }

    public long addCitizen(Citizen citizen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CITIZEN_TIN, citizen.getCitizen_tin());
        cv.put(CITIZEN_FULL_NAME, citizen.getCitizen_fullName());
        cv.put(CITIZEN_USERNAME_ID, citizen.getUsername_id());
        cv.put(CITIZEN_REGION_ID, citizen.getRegion_id());

        return db.insert(CITIZEN_TABLE_NAME, null, cv);
    }

    public long addRegion() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + REGION_TABLE_NAME);

        String createTableRegion = "CREATE TABLE " + REGION_TABLE_NAME +
                " (" + REGION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REGION_NAME + " TEXT);";
        db.execSQL(createTableRegion);

        ContentValues cv = new ContentValues();
        for (String region: regionsList) {
            cv.put(REGION_NAME, region);
            long result = db.insert(REGION_TABLE_NAME, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed to insert region " + region, Toast.LENGTH_SHORT).show();
            }
        }

        return db.insert(REGION_TABLE_NAME, null, cv);
    }

    public long addHousehold(Household household) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HOUSEHOLD_CITIZEN_TIN, household.getCitizen_tin());
        cv.put(HOUSEHOLD_ADDRESS, household.getAddress());
        cv.put(HOUSEHOLD_TYPE, household.getType());
        cv.put(HOUSEHOLD_REGION, household.getRegion());
        cv.put(HOUSEHOLD_YEAR, household.getYear());
        cv.put(HOUSEHOLD_FLOOR, household.getFloor());
        cv.put(HOUSEHOLD_MATERIAL, household.getMaterial());
        cv.put(HOUSEHOLD_LANDSCAPE, household.getLandscape());
        cv.put(HOUSEHOLD_SIZE, household.getSize());
        cv.put(HOUSEHOLD_WO_SIZE, household.getWo_size());
        cv.put(HOUSEHOLD_ROOM, household.getRoom());
        cv.put(HOUSEHOLD_OWNER, household.getOwner());

        return db.insert(HOUSEHOLD_TABLE_NAME, null, cv);
    }

    public long addHouseholdType(HouseholdType householdType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HOUSEHOLD_TYPE_NAME, householdType.getName());
        cv.put(HOUSEHOLD_TYPE_TYPE, householdType.getType());

        return db.insert(HOUSEHOLD_TYPE_TABLE_NAME, null, cv);
    }

    public long addHouseholdTypee(HouseholdTypee householdTypee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HOUSEHOLD_TYPEE_NAME, householdTypee.getName());

        return db.insert(HOUSEHOLD_TYPEE_TABLE_NAME, null, cv);
    }

    public Cursor selectFromTable(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, null);
        }
        return cursor;
    }

    public long deleteOneRow(String tableName, String rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, "_id=?", new String[] {rowId});
    }

    public long updateStationary(StationaryDAO stationaryDAO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATIONARY_USERNAME, stationaryDAO.getStationary_username());
        cv.put(STATIONARY_PASSWORD, stationaryDAO.getStationary_password());
        cv.put(STATIONARY_REGION_ID, stationaryDAO.getRegion_id());

        return db.update(STATIONARY_TABLE_NAME, cv, "_id=?", new String[]{String.valueOf(stationaryDAO.getStationary_id())});
    }

    public long updateController(ControllerDAO controllerDAO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONTROLLER_USERNAME, controllerDAO.getController_username());
        cv.put(CONTROLLER_PASSWORD, controllerDAO.getController_password());
        cv.put(CONTROLLER_REGION_ID, controllerDAO.getRegion_id());

        return db.update(CONTROLLER_TABLE_NAME, cv, "_id=?", new String[] {String.valueOf(controllerDAO.getController_id())});
    }

    public long updateCitizen(int usernameId, String citizenTIN, String citizenFullName, int citizenRegionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CITIZEN_TIN, citizenTIN);
        cv.put(CITIZEN_FULL_NAME, citizenFullName);
        cv.put(CITIZEN_REGION_ID, citizenRegionId);

        return db.update(CITIZEN_TABLE_NAME, cv, "username_id=?", new String[]{String.valueOf(usernameId)});
    }
}
