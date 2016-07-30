package com.example.billy.kilamonsta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Created by Billy on 7/18/2016.
 */
public class NumberOfPlayersActivity extends AppCompatActivity {
    private final String LOG_TAG = HelpActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NumberPlayersFragment())
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
/*    public void startCharSelect(View view){
        Intent intent = new Intent(this,CharacterSelectActivity.class);
        startActivity(intent);
    }
    public void startGame(View view){
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }*/
    public int getNumberOfPlayers(){
        RadioButton two = (RadioButton)findViewById(R.id.two_players);
        RadioButton three = (RadioButton)findViewById(R.id.three_players);
        RadioButton four = (RadioButton)findViewById(R.id.four_players);
        int result;
        if(two.isChecked()){
            result=2;
        }
        else if(three.isChecked()){
            result=3;
        }
        else{
            result=4;
        }
        return result;

    }


    public void startNextActivity(View view){
        RadioButton random = (RadioButton)findViewById(R.id.random_choice);
        RadioButton playerChoice = (RadioButton)findViewById(R.id.players_choice);

        if (random.isChecked()){
            Intent intent = new Intent(this,GameActivity.class);
            int players=getNumberOfPlayers();
            intent.putExtra("numberOfPlayers",players);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this,CharacterSelectActivity.class);
            startActivity(intent);
        }
    }


    public static class NumberPlayersFragment extends Fragment {
        public NumberPlayersFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_number_players, container, false);
            return rootView;
        }
    }
}
