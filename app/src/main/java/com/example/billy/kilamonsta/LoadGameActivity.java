package com.example.billy.kilamonsta;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Billy on 8/17/2016.
 */
public class LoadGameActivity extends AppCompatActivity {

    private final String LOG_TAG = LoadGameActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoadGameFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public static class LoadGameFragment extends Fragment {
        ListView myListView;
        LoadGameAdapter loadGameAdapter;
        public LoadGameFragment(){
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            if (v.getId()==R.id.load_game_list_view) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_list, menu);
            }
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch(item.getItemId()) {
                case R.id.delete:
                    // remove stuff here
                //    Toast.makeText(this, "Selected: "+(info.position), Toast.LENGTH_SHORT).show();
                    GameDbHelper db = new GameDbHelper(this.getContext());
                    int position = info.position;
                    db.deleteGame(Integer.toString(position));
               //     loadGameAdapter.re(loadGameAdapter.getItemId(position));

                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_load_game, container, false);
            GameDbHelper myDb = new GameDbHelper(this.getContext());
            ArrayList <String> game_names=new ArrayList<>();
            ArrayList <String> player_names=new ArrayList<>();
            Cursor res = myDb.getAllGames();
            while(res.moveToNext()){
                game_names.add(res.getString(0));
                Cursor players = myDb.getPlayers(res.getString(0));
                String names = "";
                while(players.moveToNext()){
                    names += players.getString(1)+"\n";
                }
                player_names.add(names);
            }

            loadGameAdapter = new LoadGameAdapter(getActivity(), game_names, player_names);
            myListView = (ListView) rootView.findViewById(R.id.load_game_list_view);
            registerForContextMenu(myListView);
            myListView.setAdapter(loadGameAdapter);
            myListView.setOnItemClickListener((new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = myListView.getItemAtPosition(position);
                    final String game_name=(String)o;
                    Toast.makeText(getActivity(), "game_name="+game_name, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure you want to pick this game?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(getActivity(),GameActivity.class);
                                    intent.putExtra("game_name",game_name);
                                    startActivity(intent);
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
            }));

            return rootView;
        }
    }
}
