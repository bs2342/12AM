package com.example.billy.kilamonsta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Billy on 7/18/2016.
 */
public class CreateGameActivity extends AppCompatActivity {
    GameDbHelper myDb;
    EditText editName;
    RadioGroup players;
    private final String LOG_TAG = HelpActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new GameDbHelper(this);
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
        Intent intent;

        RadioButton random = (RadioButton)findViewById(R.id.random_choice);
        RadioButton playerChoice = (RadioButton)findViewById(R.id.players_choice);
        int players=getNumberOfPlayers();
        String game_name = createNewGame();
        if(game_name.equals("fail")){ //set this to "fail" for now but find better solution in case people name their game "fail"
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Game name has already been taken, please enter a new name")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                          dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            if (random.isChecked()){
                intent = new Intent(this,GameActivity.class);
            }
            else{
                intent = new Intent(this,CharacterSelectActivity.class);
            }
            intent.putExtra("numberOfPlayers",players);
            intent.putExtra("game_name",game_name);
            startActivity(intent);
        }


    }
    public String createNewGame(){
        editName = (EditText)findViewById(R.id.game_name_value);
        String name = editName.getText().toString();

        players = (RadioGroup)findViewById(R.id.playerCount);
        String count = ((RadioButton)findViewById(players.getCheckedRadioButtonId())).getText().toString();
        int playerCount = Integer.parseInt(count);
        //Toast.makeText(CreateGameActivity.this,name+" "+count, Toast.LENGTH_SHORT).show();
        long id = myDb.insertNewGame(name, playerCount);
        if(id == -1){
            Toast.makeText(CreateGameActivity.this,"Game could not be created", Toast.LENGTH_LONG).show();
            return "fail";
        }else{
            Toast.makeText(CreateGameActivity.this, "New game "+name+" created.", Toast.LENGTH_SHORT).show();
            return name;
        }

    }


    public static class NumberPlayersFragment extends Fragment {
        public NumberPlayersFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_create_game, container, false);
            return rootView;
        }
    }
}
