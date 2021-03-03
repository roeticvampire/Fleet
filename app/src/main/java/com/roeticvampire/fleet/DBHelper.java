package com.roeticvampire.fleet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USERLIST_DATABASE_NAME="userlist.db";
    public static final String USERLIST_TABLE_NAME="userlist_table";
    public static final String USERLIST_USERNAME="username"; //this'll be our ID for userlist
    public static final String USERLIST_NAME="name";
    public static final String USERLIST_PUBLIC_KEY="public_key"; //we'll add you later brother don't worry homie!
    public static final String USERLIST_CHAT_TABLE_NAME="chat_table_name";
    public static final String USERLIST_CHAT_TABLE_PREFIX="Fleet_";

    public static final String CHATS_DATABASE_NAME="chatlist.db";
    public static final String CHATS_IS_USER="isUser";
    public static final String CHATS_MESSAGE= "message";
    public static final String CHATS_MESSAGE_TIME="messageTime";

    public static final int VERSION=1;
   /* Userlist.sql should have: Name/username/public_key/chat_tablename
<each_chat>.sql should have: sent_or_recieved(boolean) /the message/ time
    */


    public DBHelper(@Nullable Context context) {
        super(context, USERLIST_DATABASE_NAME, null, VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

       // String sql="create table "+USERLIST_TABLE_NAME+" (_id integer primary key autoincrement, "+USERLIST_NAME+" text,"+USERLIST_USERNAME+" text,"+USERLIST_CHAT_TABLE_NAME+" text, "+USERLIST_PUBLIC_KEY+" blob)";
        String sql="create table "+USERLIST_TABLE_NAME+" (_id integer primary key autoincrement, "+USERLIST_NAME+" text,"+USERLIST_USERNAME+" text,"+USERLIST_CHAT_TABLE_NAME+" text)";
         db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USERLIST_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertUser (String name, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERLIST_NAME, name);
        contentValues.put(USERLIST_USERNAME, username);
        contentValues.put(USERLIST_CHAT_TABLE_NAME, USERLIST_CHAT_TABLE_PREFIX+username);
        db.insert(USERLIST_TABLE_NAME, null, contentValues);
        return true;
    }



    //implement other methods too
    //https://www.tutorialspoint.com/android/android_sqlite_database.htm
    //we got the addition working, now to get the retrival and update/deletes working too (Maybe)
}
