package com.proyecto.scanapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ListadoActivity extends AppCompatActivity {

    private Button btnScanner, btnUbi;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        setTitle("Principal");

        btnScanner =findViewById(R.id.btnScanner);
        btnUbi  =findViewById(R.id.btnUbicacion);


        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LectorActivity.class));
            }
        });

        btnUbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ubicacion.class));
            }
        });

        checkPermission();
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            //  Config.mensaje(this, "This version is not android 6 or later " + Build.VERSION.SDK_INT);
        }else {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
            if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                //        Config.mensaje(this, "Requesting permissions");
            }else if(hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
                //      Config.mensaje(this, "The permissions are already granted ");
                //open camera
            }
        }
        return;
    }
    public void onRequestPemissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode){
            if (grantResults[0] ==PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar:
                SharedPreferences sharedPref = getSharedPreferences("loginsesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nombre", "");
                editor.apply();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
