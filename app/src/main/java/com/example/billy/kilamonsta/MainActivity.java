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


public class MainActivity extends AppCompatActivity {
    GameDbHelper myDb;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new GameDbHelper(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new BeginGameFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
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

    public void startHelp(View view){
        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);
    }
    public void startGameSetup(View view){
        Intent intent = new Intent(this,CreateGameActivity.class);
        startActivity(intent);
    }
    public void startLoadGame(View view){
        Intent intent = new Intent(this, LoadGameActivity.class);
        startActivity(intent);
    }


    public static class BeginGameFragment extends Fragment {
        public BeginGameFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_begin_game, container, false);
            return rootView;
        }
    }


}