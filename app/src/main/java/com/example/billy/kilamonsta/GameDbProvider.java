package com.example.billy.kilamonsta;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Billy on 8/20/2016.
 */

public class GameDbProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private GameDbHelper mOpenHelper;

    static final int GAME = 100;
    static final int PLAYER = 200;
    static final int PLAYER_WITH_GAME = 201;

    private static final SQLiteQueryBuilder sPlayersByGameQueryBuilder;

    static{
        sPlayersByGameQueryBuilder = new SQLiteQueryBuilder();
        //This is an innder join which look like
        //Game INNER JOIN Player ON game.game_name = player.game_id
        sPlayersByGameQueryBuilder.setTables(
                GameContract.PlayerEntry.TABLE_NAME + " INNER JOIN "+
                        GameContract.GameEntry.TABLE_NAME +
                        " ON " + GameContract.PlayerEntry.TABLE_NAME +
                        "." + GameContract.PlayerEntry.COLUMN_GAME_ID +
                        " = " + GameContract.GameEntry.TABLE_NAME+
                        "."+ GameContract.GameEntry.COLUMN_GAME_NAME
        );
    }

    private static final String sGameName=
            GameContract.PlayerEntry.TABLE_NAME+
                    "."+ GameContract.PlayerEntry.COLUMN_GAME_ID+ " = ?";

    private Cursor getPlayersByGameName(Uri uri,String[] projection, String sortOder){
        String game_name = GameContract.PlayerEntry.getColumnGameId(uri);
        return sPlayersByGameQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sGameName,
                new String[]{game_name},
                null,
                null,
                sortOder
                );
    }

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GameContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, GameContract.PATH_GAME, GAME);
        matcher.addURI(authority, GameContract.PATH_PLAYER+"/*", PLAYER_WITH_GAME);
        matcher.addURI(authority, GameContract.PATH_PLAYER, PLAYER);

        return matcher;
    }

    @Override
    public boolean onCreate(){
        mOpenHelper = new GameDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);

        switch(match){
            case GAME:
                return GameContract.GameEntry.CONTENT_TYPE;
            case PLAYER:
                return GameContract.PlayerEntry.CONTENT_TYPE;
            case PLAYER_WITH_GAME:
                return GameContract.PlayerEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            case PLAYER_WITH_GAME: {
                retCursor = getPlayersByGameName(uri, projection, sortOrder);
                break;
            }
            case PLAYER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        GameContract.PlayerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case GAME:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        GameContract.GameEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri:" +uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case GAME:{
                long _id = db.insert(GameContract.GameEntry.TABLE_NAME, null, values);
                if(_id >0){
                    returnUri = GameContract.GameEntry.buildGameUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into "+uri);
                }
                break;
            }
            case PLAYER: {
                long _id = db.insert(GameContract.PlayerEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = GameContract.PlayerEntry.buildPlayerUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into "+uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if(null == selection){
            selection = "1";
        }

        switch(match){
            case GAME:{
                rowsDeleted = db.delete(GameContract.GameEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case PLAYER:{
                rowsDeleted = db.delete(GameContract.PlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        if(rowsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case GAME:{
                rowsUpdated = db.update(GameContract.GameEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case PLAYER:{
                rowsUpdated = db.update(GameContract.PlayerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsUpdated;
    }

}
