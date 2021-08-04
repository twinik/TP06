package com.tobiaswinik.tp06.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tobiaswinik.tp06.MainActivity;
import com.tobiaswinik.tp06.R;

public class ButtonsFragment extends Fragment {

    View layoutRoot;
    ImageButton imgbtnOnOff, imgbtnLlamada;
    ImageView imgLampara;
    Button btnSeteos;
    TextView tvNumeroTelefonico;
    MainActivity actividadContenedora;
    String phonenumber;

    public ButtonsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRoot == null){
            layoutRoot = inflater.inflate(R.layout.fragment_buttons, container, false);
        }

        actividadContenedora = (MainActivity) getActivity();
        phonenumber = actividadContenedora.getTelefono();

        ObtenerReferencias();
        SetearListeners();
        tvNumeroTelefonico.setText(phonenumber);
        return layoutRoot;
    }

    private void ObtenerReferencias() {
        imgbtnOnOff = (ImageButton) layoutRoot.findViewById(R.id.imgbtnOnOff);
        imgbtnLlamada = (ImageButton) layoutRoot.findViewById(R.id.imgbtnLlamada);
        imgLampara = (ImageView) layoutRoot.findViewById(R.id.imgLampara);
        btnSeteos = (Button) layoutRoot.findViewById(R.id.btnSeteos);
        tvNumeroTelefonico = (TextView) layoutRoot.findViewById(R.id.tvNumeroTelefonico);
    }

    private void SetearListeners() {
        imgbtnOnOff.setOnClickListener(btnOnOff_Click);
        imgbtnLlamada.setOnClickListener(btnLlamada_Click);
        btnSeteos.setOnClickListener(btnIrASeteos_Click);
    }

    View.OnClickListener btnOnOff_Click = new View.OnClickListener() {
        @Override
        public void onClick(View V) {

        }
    };

    View.OnClickListener btnLlamada_Click = new View.OnClickListener() {
        @Override
        public void onClick(View V){
            llamar(V);
        }
    };

    View.OnClickListener btnIrASeteos_Click = new View.OnClickListener() {
        @Override
        public void onClick(View V){
            MainActivity actividadContenedora = (MainActivity) getActivity();
            actividadContenedora.irAFragmentSeteos();
        }
    };

    public void llamar(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber));
        startActivity(intent);
    }
}