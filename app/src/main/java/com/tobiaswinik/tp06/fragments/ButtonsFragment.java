package com.tobiaswinik.tp06.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
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
    PackageManager  pm;
    private CameraManager manager;
    boolean compatible;
    boolean prendida;

    public ButtonsFragment() {
        compatible = false;
        prendida = false;
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

        pm  =  getActivity().getPackageManager();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,}, 1000);
        } else {
            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
                compatible = true;
            }
        }



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
            if (compatible){
                if(!prendida){
                    try {
                        manager.setTorchMode("0", true);
                        imgbtnOnOff.setImageResource(R.drawable.buttonon);
                        imgLampara.setImageResource(R.drawable.lighton);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    } prendida=true;
                }
                else {
                    try {
                        manager.setTorchMode("0", false);
                        imgbtnOnOff.setImageResource(R.drawable.buttonoff);
                        imgLampara.setImageResource(R.drawable.lightoff);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    prendida=false;
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error");
                builder.setMessage("El flash no es compatible en este dispositivo");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
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