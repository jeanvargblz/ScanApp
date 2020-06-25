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

    private Button btnScanner, btnPromociones;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    /**
     private DbManager db;
     private ListView lista;
     private ProductoAdapter adapter;
     private Cursor c;
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        setTitle("Principal");

//        db= new DbManager(getApplicationContext());
        btnScanner =findViewById(R.id.btnScanner);
 //       btnPromociones=findViewById(R.id.btnPromociones);

//        lista = findViewById(R.id.listaProductos);

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LectorActivity.class));
            }
        });

        /**
        btnPromociones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InventarioActivity.class));
            }
        });


 c = db.getCursor("productos","1");

 if(c.moveToFirst()){
 adapter = new ProductoAdapter(getApplicationContext(), c);
 lista.setAdapter(adapter);
 }
 else{
 Log.d("ERROR VACIO","pro");
 }
 lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
Cursor item=(Cursor) lista.getItemAtPosition(i);
int id = item.getInt(item.getColumnIndexOrThrow("_id"));
//         Toast.makeText(getApplicationContext(),"id"+id,Toast.LENGTH_LONG).show();

startActivity(new Intent(getApplicationContext(),ProductoActivity.class).putExtra("id",id));
}
});
 **/
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
/**
 class ProductoAdapter extends CursorAdapter{

 public ProductoAdapter(Context context, Cursor c) {
 super(context, c, 0);
 }

 @Override
 public View newView(Context context, Cursor cursor, ViewGroup parent) {
 return LayoutInflater.from(context).inflate(R.layout.listaproducto,parent,false);
 }


 @Override
 public void bindView(View view, Context context, Cursor cursor) {
 ImageView urlfoto_ = view.findViewById(R.id.urlfoto);
 TextView titulo_ = view.findViewById(R.id.titulo);
 TextView precio_ = view.findViewById(R.id.precio);
 titulo_.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
 precio_.setText(cursor.getString(cursor.getColumnIndexOrThrow("precio")));

 Picasso.with(context)
 .load("http://192.168.0.31/QR/img/"+cursor.getString(cursor.getColumnIndexOrThrow("foto")))
 .into(urlfoto_, new Callback() {
 @Override
 public void onSuccess() {
 }
 @Override
 public void onError() {
 }
 });
 }
 }
 **/