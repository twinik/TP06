package com.tobiaswinik.tp06.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tobiaswinik.tp06.MainActivity;
import com.tobiaswinik.tp06.R;

public class SeteosFragment extends Fragment {

    View layoutRoot;
    EditText edtTelefono;
    Button btnGuardar;

    public SeteosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRoot == null){
            layoutRoot = inflater.inflate(R.layout.fragment_seteos, container, false);
        }
        ObtenerReferencias();
        SetearListeners();
        return layoutRoot;
    }

    public void ObtenerReferencias(){
        edtTelefono = (EditText) layoutRoot.findViewById(R.id.edtTelefono);
        btnGuardar = (Button) layoutRoot.findViewById(R.id.btnGuardar);
    }

    public void SetearListeners() {
        btnGuardar.setOnClickListener(btnGuardar_Click);
    }

    View.OnClickListener btnGuardar_Click = new View.OnClickListener() {
        @Override
        public void onClick(View V){
            MainActivity actividadContenedora = (MainActivity) getActivity();
            String numTelefono = edtTelefono.getText().toString();
            actividadContenedora.guardarTelefono(numTelefono);

            Toast.makeText(getActivity(), "Numero guardado", Toast.LENGTH_SHORT).show();

            actividadContenedora.irAFragmentButtons();

        }
    };

}