package com.example.billy.kilamonsta;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Billy on 7/31/2016.
 */
public class GameContract {

    public static final String CONTENT_AUTHORITY = "com.example.billy.kilamonsta";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_GAME = "game";
    public static final String PATH_PLAYER = "player";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class GameEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME;

        // Table name
        public static final String TABLE_NAME = "game";

      //  public static final String COLUMN_GAME_ID = "game_id";

        public static final String COLUMN_GAME_NAME = "game_name";

        public static final String COLUMN_NUMBER_OF_PLAYERS = "num_of_players";
        public static final String COLUMN_LOG = "log";
        public static final String COLUMN_PLAYER_1_ID = "pl_id";
        public static final String COLUMN_PLAYER_2_ID = "p2_id";
        public static final String COLUMN_PLAYER_3_ID = "p3_id";
        public static final String COLUMN_PLAYER_4_ID = "p4_id";
        public static final String COLUMN_PLAYER_WINNER_ID = "w_id";

        public static Uri buildGameUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PlayerEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLAYER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;

        public static final String TABLE_NAME = "player";

      //  public static final String COLUMN_PLAYER_ID = "player_id";
        public static final String COLUMN_PLAYER_NAME = "name";
        public static final String COLUMN_PLAYER_MONSTER = "monster";
        public static final String COLUMN_PLAYER_STATUS = "status";
        public static final String COLUMN_PLAYER_ICON = "icon";
        public static final String COLUMN_PLAYER_EVENT_TAG = "event_tag";
        public static final String COLUMN_GAME_ID = "game_id";

        public static Uri buildPlayerUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPlayerWithGameName(String game_name){
            return CONTENT_URI.buildUpon().appendPath(game_name).build();
        }
        public static String getColumnGameId(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }

}
