package com.example.skillsmatcher.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.skillsmatcher.MainActivity;

import java.sql.SQLException;

public class DBManager {

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c){
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Boolean insert(String userN, String pass, String birthdate){
        ContentValues values = new ContentValues();
        values.put(DBHelper.USERNAME, userN);
        values.put(DBHelper.PASSWORD, pass);
        values.put(DBHelper.BIRTHDATE, birthdate);

        long result = database.insert(DBHelper.USER_TABLE, null, values);

        if(result == 1){
            return true;
        } else {
            return false;
        }
    }

    public Cursor fetch() {
        String[] columns = new String[] {DBHelper.USERNAME, DBHelper.PASSWORD, DBHelper.BIRTHDATE};
        Cursor cursor = database.query(DBHelper.USER_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public Boolean checkUsername(String username){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from USER where username = ?", new String[] {username});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from USER where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //// for skills methods:
    public void insertSkills(String skills){
        ContentValues values = new ContentValues();
        values.put(DBHelper.SKILLS, skills);
        database.insert(DBHelper.SKILLS_TABLE, null, values);
    }

    public Boolean checkSkills(String skill){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM SKILL WHERE skills = ?", new String[] {skill});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor fetchSkills() {
        String[] columns = new String[] {DBHelper.ID, DBHelper.SKILLS};
        Cursor cursor = database.query(DBHelper.SKILLS_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public void deleteSkill(long id){
        database.delete(DBHelper.SKILLS_TABLE, DBHelper.ID + "=" + id, null);
    }


    // for jobs results table methods:
    public void insertJobMatch(String title, String desc, String matchResult){
        ContentValues values = new ContentValues();
        values.put(DBHelper.TITLE, title);
        values.put(DBHelper.DESC, desc);
        values.put(DBHelper.MATCH, matchResult);

        database.insert(DBHelper.JOBS_TABLE, null, values);
    }

    public Cursor fetchJobMatch() {
        String[] columns = new String[] {DBHelper.JOB_ID, DBHelper.TITLE, DBHelper.DESC, DBHelper.MATCH};
        Cursor cursor = database.query(DBHelper.JOBS_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public void deleteJobMatch(long id){
        database.delete(DBHelper.JOBS_TABLE, DBHelper.JOB_ID + "=" + id, null);
    }

}
