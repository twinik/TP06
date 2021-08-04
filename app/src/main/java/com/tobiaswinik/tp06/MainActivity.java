package com.tobiaswinik.tp06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.tobiaswinik.tp06.fragments.ButtonsFragment;
import com.tobiaswinik.tp06.fragments.SeteosFragment;

public class MainActivity extends AppCompatActivity {

    ButtonsFragment fragmentButtons;
    SeteosFragment fragmentSeteos;
    SharedPreferences preferences;

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

    public void guardarTelefono(String numero) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NumeroTelefono", numero);
        editor.commit();
    }

    public String getTelefono(){
        String numero = preferences.getString("NumeroTelefono", "1198765432");
        return numero;
    }

    public void irAFragmentButtons(){
        reemplazarFragment(fragmentButtons, false);
    }
    public void irAFragmentSeteos(){
        reemplazarFragment(fragmentSeteos, false);
    }
}