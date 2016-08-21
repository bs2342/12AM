package com.example.billy.kilamonsta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.billy.kilamonsta.GameContract.GameEntry;
import com.example.billy.kilamonsta.GameContract.PlayerEntry;

/**
 * Created by Billy on 7/31/2016.
 */
public class GameDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;

    static final String DATABASE_NAME= "12AM.db";

    public GameDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_GAME_TABLE = "CREATE TABLE " + GameEntry.TABLE_NAME + " (" +
          //      GameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GameEntry.COLUMN_GAME_NAME +" TEXT PRIMARY KEY UNIQUE, " +
                GameEntry.COLUMN_LOG + " TEXT, "+
                GameEntry.COLUMN_NUMBER_OF_PLAYERS + " INTEGER NOT NULL,"+
                GameEntry.COLUMN_PLAYER_1_ID + " INTEGER, " +
                GameEntry.COLUMN_PLAYER_2_ID + " INTEGER, " +
                GameEntry.COLUMN_PLAYER_3_ID + " INTEGER, " +
                GameEntry.COLUMN_PLAYER_4_ID + " INTEGER, " +
                GameEntry.COLUMN_PLAYER_WINNER_ID + " INTEGER, " +
                " FOREIGN KEY (" +GameEntry.COLUMN_PLAYER_1_ID +") REFERENCES " +
                PlayerEntry.TABLE_NAME +"(" + PlayerEntry._ID +") "+
                " FOREIGN KEY (" +GameEntry.COLUMN_PLAYER_2_ID +") REFERENCES " +
                PlayerEntry.TABLE_NAME +"(" + PlayerEntry._ID +") "+
                " FOREIGN KEY (" +GameEntry.COLUMN_PLAYER_3_ID +") REFERENCES " +
                PlayerEntry.TABLE_NAME +"(" + PlayerEntry._ID +") "+
                " FOREIGN KEY (" +GameEntry.COLUMN_PLAYER_4_ID +") REFERENCES " +
                PlayerEntry.TABLE_NAME +"(" + PlayerEntry._ID +") "+
                " FOREIGN KEY (" +GameEntry.COLUMN_PLAYER_WINNER_ID +") REFERENCES " +
                PlayerEntry.TABLE_NAME +"(" + PlayerEntry._ID +"));";

        final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE " + PlayerEntry.TABLE_NAME+" ("+
                PlayerEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PlayerEntry.COLUMN_PLAYER_NAME + " TEXT NOT NULL, "+
                PlayerEntry.COLUMN_PLAYER_MONSTER+" TEXT NOT NULL, "+
                PlayerEntry.COLUMN_PLAYER_STATUS+" TEXT NOT NULL, "+
                PlayerEntry.COLUMN_PLAYER_ICON+" TEXT NOT NULL, "+
                PlayerEntry.COLUMN_PLAYER_EVENT_TAG+" TEXT, " +
                PlayerEntry.COLUMN_GAME_ID+" TEXT NOT NULL, " +
                "UNIQUE ("+ PlayerEntry.COLUMN_PLAYER_NAME +"," +PlayerEntry.COLUMN_GAME_ID + ") ON CONFLICT ABORT, " +
                "FOREIGN KEY (" + PlayerEntry.COLUMN_GAME_ID + ") REFERENCES " +
                GameEntry.TABLE_NAME + "(" + GameEntry.COLUMN_GAME_NAME + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_GAME_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PLAYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertNewGame(String name, int playerCount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GameEntry.COLUMN_GAME_NAME, name);
        contentValues.put(GameEntry.COLUMN_NUMBER_OF_PLAYERS, playerCount);
        long id = db.insert(GameEntry.TABLE_NAME, null, contentValues);
        return id;
    }


    public long insertNewPlayer(String game_name, String name, String monster, String status, String icon, String player_position){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayerEntry.COLUMN_PLAYER_NAME, name);
        contentValues.put(PlayerEntry.COLUMN_PLAYER_MONSTER, monster);
        contentValues.put(PlayerEntry.COLUMN_PLAYER_STATUS, status);
        contentValues.put(PlayerEntry.COLUMN_PLAYER_ICON, icon);
        contentValues.put(PlayerEntry.COLUMN_GAME_ID, game_name);
        long id = db.insert(PlayerEntry.TABLE_NAME, null, contentValues);
        updateGame(game_name, id, player_position);
        return id;
    }

    public Cursor getPlayers(String game_name){
        SQLiteDatabase db = this.getWritableDatabase();
        final String Query = "SELECT * FROM "+
                            PlayerEntry.TABLE_NAME+ " WHERE "+
                            PlayerEntry.COLUMN_GAME_ID +"="+"\'"+game_name+"\'";
        Cursor res = db.rawQuery(Query, null);
        return res;
    }
    public boolean updatePlayerStatus(int player_id, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String filter = PlayerEntry._ID+"="+player_id;
        cv.put(PlayerEntry.COLUMN_PLAYER_STATUS, status);
        long id = db.update(PlayerEntry.TABLE_NAME, cv, filter, null);
        return true;
    }
    public boolean updatePlayerIcon(int player_id, String icon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String filter = PlayerEntry._ID+"="+player_id;
        cv.put(PlayerEntry.COLUMN_PLAYER_ICON, icon);
        long id = db.update(PlayerEntry.TABLE_NAME, cv, filter, null);
        return true;
    }
    public Integer deleteGame(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(GameEntry.TABLE_NAME, "rowid=?", new String[]{id});
    }
    public boolean updatePlayerEventTag(int player_id, String event_tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String filter = PlayerEntry._ID+"="+player_id;
        cv.put(PlayerEntry.COLUMN_PLAYER_EVENT_TAG, event_tag);
        long id = db.update(PlayerEntry.TABLE_NAME, cv, filter, null);
        return true;
    }

    public Cursor getAllGames(){
        SQLiteDatabase db = this.getWritableDatabase();
        final String Query = "SELECT * FROM "+
                GameEntry.TABLE_NAME;
        Cursor res = db.rawQuery(Query, null);
        return res;
    }

    public boolean updateGame(String game_name, long player_id, String position){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch(position){
            case "1" :
                cv.put(GameEntry.COLUMN_PLAYER_1_ID, player_id);
                break;
            case "2" :
                cv.put(GameEntry.COLUMN_PLAYER_2_ID, player_id);
                break;
            case "3" :
                cv.put(GameEntry.COLUMN_PLAYER_3_ID, player_id);
                break;
            case "4" :
                cv.put(GameEntry.COLUMN_PLAYER_4_ID, player_id);
                break;
            case "Winner" :
                cv.put(GameEntry.COLUMN_PLAYER_WINNER_ID, player_id);
                break;

        }
 //       cv.put(ColumnName, player_id);
        String filter = GameEntry.COLUMN_GAME_NAME+"=\'"+game_name+"\'";
        db.update(GameEntry.TABLE_NAME, cv, filter,null);
        return true;
    }
}
