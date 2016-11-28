package com.uandme.dbhandle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uandme.singleton.AppLocalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand-3985 on 24/11/16.
 */
public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "uandme";
    private static final String TABLE_MSG = "messages";

    private static final String KEY_ID = "id";
    private static final String KEY_SENDER = "sender";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_MSG = "msg";
    private static final String KEY_IMG = "img_uri";

    private Context context;
    private static DBHandler dbHandler = null;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
          }

    public static DBHandler getInstance(Context context){
        if(dbHandler == null ){
         dbHandler = new DBHandler(context);
        }
        return dbHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB","Constructor #######################################################\n" +
                "######################## Database Created ########################\n" +
                "##################################################################");

        Log.d("DB","Creating table #######################################################");

            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MSG + "("
                    + KEY_ID + " TEXT PRIMARY KEY," + KEY_TIMESTAMP + " TEXT,"
                    + KEY_SENDER + " TEXT," + KEY_MSG + " TEXT," + KEY_IMG + " TEXT)";

            db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMessages(Messages message) {

        Log.d("insert :: ",message.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,message.getId());
        values.put(KEY_TIMESTAMP,message.getTimestamp());
        values.put(KEY_SENDER,message.getSender());
        values.put(KEY_MSG,message.getMsg());
        values.put(KEY_IMG,message.getImg_uri());
        db.insert(TABLE_MSG, null, values);

    }


    public Messages getMessage(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MSG, new String[] {
                KEY_ID,
                KEY_TIMESTAMP,
                KEY_SENDER,
                KEY_MSG,
                KEY_IMG
        }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Messages message = new Messages(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        return message;
    }

    public List<Messages> getAllMessagess() {
        List<Messages> contactList = new ArrayList<Messages>();

        String selectQuery = "SELECT  * FROM   (SELECT * FROM " + TABLE_MSG + " ORDER BY id DESC LIMIT 200) ORDER BY id ASC " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Messages message = new Messages();
                message.setId(cursor.getString(0));
                message.setTimestamp(cursor.getString(1));
                message.setSender(cursor.getString(2));
                message.setMsg(cursor.getString(3));
                message.setImg_uri(cursor.getString(4));
                contactList.add(message);
            } while (cursor.moveToNext());
        }
        Log.d("get all :: ",contactList.size()+"");
        return contactList;
    }

    public List<Messages> getNMessagess(int n) {
        List<Messages> contactList = new ArrayList<Messages>();

        String selectQuery = "SELECT  * FROM " + TABLE_MSG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Messages message = new Messages();
                message.setId(cursor.getString(0) );
                message.setTimestamp(cursor.getString(1));
                message.setSender(cursor.getString(2));
                message.setMsg(cursor.getString(3));
                message.setImg_uri(cursor.getString(4));
                contactList.add(message);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int getMessagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MSG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

    public void deleteMessages(Messages message) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MSG, KEY_ID + " = ?",
                new String[] { String.valueOf(message.getId()) });
    //    db.close();
    }


    public void deleteAllMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MSG,null,null);

        //    db.close();
    }
    public void delete(){
        Log.d("","deleted");
        this.getReadableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_MSG);
    }

}

