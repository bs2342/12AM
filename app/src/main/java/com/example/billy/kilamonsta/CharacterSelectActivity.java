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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Billy on 7/19/2016.
 */
public class CharacterSelectActivity extends AppCompatActivity{
    GameDbHelper myDb;
    EditText editName;
    RadioGroup charSel;
    String game_name;
    int playerCount;
    int position = 1;
    private final String LOG_TAG = HelpActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new GameDbHelper(this);
        Bundle extras = this.getIntent().getExtras();

        game_name=extras.getString("game_name");
        playerCount=extras.getInt("numberOfPlayers");
        Toast.makeText(CharacterSelectActivity.this, "game_name="+game_name, Toast.LENGTH_SHORT).show();
//        addNewCharacter();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CharacterSelectFragment())
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

    public void addNewCharacter(View view){
        editName = (EditText)findViewById(R.id.player_one_name_value);
        String name = editName.getText().toString();

        charSel = (RadioGroup)findViewById(R.id.characterSelect);
        String character = ((RadioButton)findViewById(charSel.getCheckedRadioButtonId())).getText().toString();
        String status = "alive";
        int icon = 1;
        Toast.makeText(CharacterSelectActivity.this, name+" "+character+" "+status, Toast.LENGTH_SHORT).show();
        long result = myDb.insertNewPlayer(game_name,name, character, status, character, String.valueOf(position));
        if(result != -1){
            Toast.makeText(CharacterSelectActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            position ++;
            if(position > playerCount){
                Intent intent = new Intent(this,GameActivity.class);

                intent.putExtra("numberOfPlayers",playerCount);
                intent.putExtra("game_name",game_name);
                startActivity(intent);
            }

        }else{
            Toast.makeText(CharacterSelectActivity.this,"Data could not be inserted", Toast.LENGTH_LONG).show();
        }

    }

    public static class CharacterSelectFragment extends Fragment {

        public CharacterSelectFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_character_select, container, false);
            return rootView;
        }
    }
}
