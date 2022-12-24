package com.example.skillsmatcher.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // DB name
    public static final String DB_NAME = "registration.db";
    // Database version
    static final int DB_VERSION = 2;

    // Table names
    public static final String USER_TABLE = "USER";
    public static final String SKILLS_TABLE = "SKILL";
    public static final String JOBS_TABLE = "JOB";

    // Table Columns
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String BIRTHDATE = "birthdate";
    ////////////////////
    public static final String ID = "_id";
    public static final String SKILLS = "skills";
    ////////////////////////////
    public static final String JOB_ID = "id";
    public static final String TITLE = "title";
    public static final String DESC = "description";
    public static final String MATCH = "matched";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /// Create table query
    private static final String CREATE_USER_TABLE = "create table " + USER_TABLE + "(" + USERNAME +
            " TEXT PRIMARY KEY NOT NULL, " + PASSWORD + " TEXT NOT NULL, " + BIRTHDATE + " TEXT NOT NULL  );";

    private static final String CREATE_SKILLS_TABLE = "create table " + SKILLS_TABLE + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SKILLS +
            " TEXT NOT NULL );";

    private static final String CREATE_JOBS_TABLE = "create table " + JOBS_TABLE + "(" + JOB_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL," + DESC + " TEXT NOT NULL, " + MATCH +
            " TEXT NOT NULL );";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDB) {
        Log.d("DBHelper", "onCreate");
        sqLiteDB.execSQL(CREATE_USER_TABLE);
        sqLiteDB.execSQL(CREATE_SKILLS_TABLE);
        sqLiteDB.execSQL(CREATE_JOBS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDB, int oldVersion, int newVersion) {
        Log.d("VERSION CHANGE", "oldVersion: " + oldVersion + " , newVersion: " + newVersion);
        sqLiteDB.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDB.execSQL("DROP TABLE IF EXISTS " + SKILLS_TABLE);
        sqLiteDB.execSQL("DROP TABLE IF EXISTS " + JOBS_TABLE);
        onCreate(sqLiteDB);

    }
}
