package com.example.billy.kilamonsta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Billy on 7/21/2016.
 */
public class GameFragment extends Fragment {
    public GameFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        int message=extras.getInt("numberOfPlayers");

        final ArrayList player_details = getListData(message);

        final PlayerDetailAdapter mDetailAdapter = new PlayerDetailAdapter(getActivity(), player_details);
        final ListView mListView = (ListView) rootView.findViewById(R.id.listview_players);
        mListView.setAdapter(mDetailAdapter);
        mListView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                final PlayerStats playerData=(PlayerStats)o;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                        playerData.setStatIcon(5);
                                        playerData.setPlayerStat("spirit");
                                        playerData.setSpecialEventTag("haunting Manny");
                                    }

                                }
                                else if(checkedItem.equals("infected")){
                                    if(playerData.getPlayerStat().equals("dead")){
                                        Toast.makeText(getActivity(), "Player is already dead", Toast.LENGTH_SHORT).show();
                                    }else{
                                        playerData.setPlayerStat("infected");
                                    }

                                }
                                else if(checkedItem.equals("dead")){
                                    if(playerData.getPlayerStat().equals("dead")){
                                        Toast.makeText(getActivity(), "Player is already dead", Toast.LENGTH_SHORT).show();
                                    }else{
                                        playerData.setPlayerStat("dead");
                                        player_details.remove(playerData);
                                        player_details.add(playerData);
                                    }

                                }

                                mDetailAdapter.notifyDataSetChanged();
                                gameOverCheck(player_details);
                              //  Toast.makeText(getActivity(), "Selected: "+ checkedItem, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
             //   Toast.makeText(getActivity(), "Selected: "+ playerData.getPlayerName(), Toast.LENGTH_SHORT).show();
            }
        }));

        return rootView;
    }
    private boolean gameOverCheck(ArrayList players){
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
                icon = playerData.getIcon();
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
    }
    private boolean confirmUpdate(){

        final boolean result;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.update_dialog_message)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        return false;
    }
    private ArrayList getListData(int i){
        ArrayList<PlayerStats> results = new ArrayList<PlayerStats>();
        String[] names = {"Billy","Manny", "Tracy", "Angel"};
        String[] monsters={"Boogeyman","Werewolf", "Clown", "Zombie"};
        int[] icons = {1,2,3,4};
        for(int j=0; j<i; j++){
            PlayerStats p = new PlayerStats();
            p.setPlayerName(names[j]);
            p.setPlayerStat("alive");
            p.setPlayerMonster(monsters[j]);
            p.setStatIcon(icons[j]);
            results.add(p);
        }

        return results;
    }
}
