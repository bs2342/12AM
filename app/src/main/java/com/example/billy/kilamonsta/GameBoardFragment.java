package com.example.billy.kilamonsta;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Billy on 7/21/2016.
 */
public class GameBoardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    GameDbHelper myDb;
    String status = "alive";
    String old_status;
    String status_icon, event_tag;
    long playerId;
    String game_name;
    private PlayerDetailAdapter mDetailAdapter;
    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;

    private static final int GAMEBOARD_LOADER=0;

    private static final String[] GAMEBOARD_COLUMNS = {
            GameContract.PlayerEntry.TABLE_NAME +"."+ GameContract.PlayerEntry._ID,
            GameContract.PlayerEntry.COLUMN_PLAYER_NAME,
            GameContract.PlayerEntry.COLUMN_PLAYER_MONSTER,
            GameContract.PlayerEntry.COLUMN_PLAYER_STATUS,
            GameContract.PlayerEntry.COLUMN_PLAYER_ICON,
            GameContract.PlayerEntry.COLUMN_PLAYER_EVENT_TAG
    };

    static final int COL_ID = 0;
    static final int COL_NAME = 1;
    static final int COL_MONSTER = 2;
    static final int COL_STATUS = 3;
    static final int COL_ICON = 4;
    static final int COL_EVENT_TAG = 5;
    static final int COL_GAME_ID = 6;

    public GameBoardFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        myDb = new GameDbHelper(this.getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        int message=extras.getInt("numberOfPlayers");
        game_name=extras.getString("game_name");

        final ArrayList player_details = loadBoard(game_name);//getListData(message);
        final TextView GameTitle = (TextView) rootView.findViewById(R.id.game_name_id);

        GameTitle.setText(game_name);

        mDetailAdapter = new PlayerDetailAdapter(getActivity(), null, 0);
        mListView = (ListView) rootView.findViewById(R.id.listview_players);
        mListView.setAdapter(mDetailAdapter);
        mListView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mListView.getItemAtPosition(position);
              //  final PlayerStats playerData=(PlayerStats)o;
                if(cursor != null){
                    ShowAlert();
                    playerId = cursor.getLong(COL_ID);
                    old_status = cursor.getString(COL_STATUS);
          //          Toast.makeText(getActivity(), "id="+cursor.getString(COL_ID), Toast.LENGTH_LONG).show();
/*                    String name = cursor.getString(COL_NAME);
                    Toast.makeText(getActivity(), "Name="+name, Toast.LENGTH_SHORT).show();
                    String monster = cursor.getString(COL_MONSTER);
                    Toast.makeText(getActivity(), "Monster="+monster, Toast.LENGTH_SHORT).show();*/
                }
               /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.update_dialog_message)
                        .setSingleChoiceItems(R.array.status_selection, 3, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                //do nothing
                            }
                        })
                        .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                int length = getResources().getStringArray(R.array.status_selection).length;
                                ListView lw = ((AlertDialog)dialog).getListView();
                                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                if(checkedItem.equals("spirit")){
                                    if(playerData.getPlayerStat().equals("dead")){
                                        Toast.makeText(getActivity(), "Player is already dead", Toast.LENGTH_SHORT).show();
                                    }else{
                                        playerData.setStatIcon("spirit");
                                        playerData.setPlayerStat("spirit");
                                        playerData.setSpecialEventTag("haunting Manny");
                                        myDb.updatePlayerIcon(playerData.getID(), "spirit");
                                        myDb.updatePlayerStatus(playerData.getID(), "spirit");
                                        myDb.updatePlayerEventTag(playerData.getID(),"haunting manny");
                                    }
                                }
                                else if(checkedItem.equals("infected")){
                                    if(playerData.getPlayerStat().equals("dead")){
                                        Toast.makeText(getActivity(), "Player is already dead", Toast.LENGTH_SHORT).show();
                                    }else{
                                        playerData.setPlayerStat("infected");
                                        myDb.updatePlayerStatus(playerData.getID(), "infected");
                                    }

                                }
                                else if(checkedItem.equals("dead")){
                                    if(playerData.getPlayerStat().equals("dead")){
                                        Toast.makeText(getActivity(), "Player is already dead", Toast.LENGTH_SHORT).show();
                                    }else{
                                        playerData.setPlayerStat("dead");
                                        player_details.remove(playerData);
                                        player_details.add(playerData);
                                        myDb.updatePlayerStatus(playerData.getID(), "dead");
                                    }

                                }

                                mDetailAdapter.notifyDataSetChanged();
                               // gameOverCheck(player_details);
                              //  Toast.makeText(getActivity(), "Selected: "+ checkedItem, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();*/
             //   Toast.makeText(getActivity(), "Selected: "+ playerData.getPlayerName(), Toast.LENGTH_SHORT).show();
            }
        }));

        return rootView;
    }
    public void ShowAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.update_dialog_message)
                .setSingleChoiceItems(R.array.status_selection, 3, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //do nothing
                    }
                })
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());

                        if(checkedItem.equals("spirit")) {
                            status = "spirit";
                            status_icon ="spirit";
                            event_tag="haunting Angel";
                          //  status_icon = "spirit"
                        }
                        else if(checkedItem.equals("infected")) {
                            status = "infected";
                        }
                        else if(checkedItem.equals("dead")) {
                            status = "dead";
                        }
                        confirmUpdate();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        // User cancelled the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        //Uri PlayerUri = GameContract.PlayerEntry.buildPlayerUri(GAMEBOARD_LOADER);
        Uri PlayerUri = GameContract.PlayerEntry.buildPlayerWithGameName(game_name);

        return new CursorLoader(getActivity(),
                PlayerUri,
                GAMEBOARD_COLUMNS,
                null,
                null,
                null);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDetailAdapter.swapCursor(data);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(GAMEBOARD_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDetailAdapter.swapCursor(null);
    }

    private ArrayList loadBoard(String game_name){
        Cursor res = myDb.getPlayers(game_name);
        ArrayList<PlayerStats> results = new ArrayList<PlayerStats>();
        while(res.moveToNext()){
         PlayerStats p = new PlayerStats();
            p.setPlayerID(Integer.parseInt(res.getString(0)));
            p.setPlayerName(res.getString(1));
            p.setPlayerStat(res.getString(3));
            p.setPlayerMonster(res.getString(2));
            p.setStatIcon(res.getString(4));
            p.setSpecialEventTag(res.getString(5));
            results.add(p);
        }
        return results;
    }
    private void updateGame(){
        String filter = "_ID="+String.valueOf(playerId);
        ContentValues cv = new ContentValues();
        if(old_status.equals(status)){
            Toast.makeText(getContext(),"Player is already "+status, Toast.LENGTH_LONG).show();
        }else{
            if(status_icon != null){
                cv.put(GameContract.PlayerEntry.COLUMN_PLAYER_ICON, status_icon);
                status_icon = null;
            }
            if(event_tag != null){
                cv.put(GameContract.PlayerEntry.COLUMN_PLAYER_EVENT_TAG, event_tag);
                event_tag = null;
            }
            cv.put(GameContract.PlayerEntry.COLUMN_PLAYER_STATUS, status);
            int updatedUri = getContext().getContentResolver().update(GameContract.PlayerEntry.CONTENT_URI, cv,filter, null);
            getLoaderManager().restartLoader(GAMEBOARD_LOADER, null, this);
            mDetailAdapter.notifyDataSetChanged();
        }
    }
    private void confirmUpdate(){
        final boolean result = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want to change the status?")
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateGame();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /*    private boolean gameOverCheck(ArrayList players){
        int alive = 0;
        int dead = 0;
        int infected = 0;
        int spirit = 0;
        int icon = 1;
        ArrayList<String> alive_list = new ArrayList<String>();

        for(int j=0; j<players.size();j++){
            Object p=players.get(j);
            PlayerStats playerData=(PlayerStats)p;
            String status = playerData.getPlayerStat();

            if(status.equals("alive")){
                alive_list.add(playerData.getPlayerName());
             //   icon = playerData.getIcon();
                alive++;
            }else if (status.equals("dead")){
                dead++;
            }else if (status.equals("infected")){
                infected ++;
            }else if(status.equals("spirit")){
                spirit++;
            }
        }
        //if there is only one person alive

        if(alive == 1){
            ImageView image = new ImageView(getActivity());
            image.setImageResource(icon);
            image.setMinimumHeight(500);
            image.setMinimumWidth(505);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Game Over")
                    .setView(image)
                    .setMessage(alive_list.get(0) + " Wins!")

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return false;
    }*/

/*    private ArrayList getListData(int i){
        ArrayList<PlayerStats> results = new ArrayList<PlayerStats>();
        String[] names = {"Billy","Manny", "Tracy", "Angel"};
        String[] monsters={"boogeyman","werewolf", "clown", "zombie"};
        int[] icons = {1,2,3,4};
        for(int j=0; j<i; j++){
            PlayerStats p = new PlayerStats();
            p.setPlayerName(names[j]);
            p.setPlayerStat("alive");
            p.setPlayerMonster(monsters[j]);
            p.setStatIcon(monsters[j]);
            results.add(p);
        }

        return results;
    }*/
}
