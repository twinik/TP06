package com.tobiaswinik.tp06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tobiaswinik.tp06.fragments.ButtonsFragment;
import com.tobiaswinik.tp06.fragments.SeteosFragment;

public class MainActivity extends AppCompatActivity {

    ButtonsFragment fragmentButtons;
    SeteosFragment fragmentSeteos;
    SharedPreferences preferences;
    BackgroundSound mBackgroundSound ;
    MediaPlayer player;
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)  {
        getMenuInflater().inflate(R.menu.main_menu,  menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("telefono", Context.MODE_PRIVATE);
        CrearFragments();
        reemplazarFragment(fragmentButtons, false);
    }

    private void CrearFragments() {
        fragmentButtons = new ButtonsFragment();
        fragmentSeteos = new SeteosFragment();
        mBackgroundSound= new BackgroundSound();
        player = MediaPlayer.create(MainActivity.this, R.raw.backmusic);
        player.setLooping(true); // Set looping
        player.setVolume(2.0f, 2.0f);

    }

    public void reemplazarFragment(Fragment fragmento){
        reemplazarFragment(fragmento, true);
    }

    public void reemplazarFragment(Fragment fragmento, boolean blnAddToBackStack) {
        FragmentManager manager;
        FragmentTransaction transaction;

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContainer, fragmento, null);
        if (blnAddToBackStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.backmusic);
            player.setLooping(true); // Set looping
            player.setVolume(2.0f, 2.0f);
            player.start();

            return null;
        }

    }



    public void guardarTelefono(String numero) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NumeroTelefono", numero);
        editor.commit();
    }

    public String getTelefono(){
        String numero = preferences.getString("NumeroTelefono", "911");
        return numero;
    }

    public void irAFragmentButtons(){
        reemplazarFragment(fragmentButtons, false);
    }
    public void irAFragmentSeteos(){
        reemplazarFragment(fragmentSeteos, false);
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean blnReturn = true;
        String strTitle;
        strTitle = item.getTitle().toString();
        switch (item.getItemId()) {
            case R.id.action_linterna:
                irAFragmentButtons();
                break;
            case R.id.action_configuracion:
                irAFragmentSeteos();
                break;
            default:
                blnReturn = false;
                break;
        }
        return blnReturn;
    }
    public void activarMusica(){
        player.start();
    }

    public void onPause() {
        super.onPause();
        player.pause();
    }

    public void onResume() {
        super.onResume();
        player.start();
    }
}