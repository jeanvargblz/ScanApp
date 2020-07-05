package com.proyecto.scanapp;

import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.scanapp.DB.DbManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ProductoActivity extends AppCompatActivity {

    private DbManager db;
    private Cursor c;
    private ImageView _foto;
    private TextView _nombre, _descripcion, _precio, _stock, estado;
    private ProgressBar _pb;
    private Button btnRegistrar, btnRegresar;

    private static  final String DB_URL = "jdbc:mysql://192.168.0.29/qr";
    private static  final String USER = "jean";
    private static  final String PASS = "jean";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        setTitle("Producto");

        _foto = findViewById(R.id.foto);
        _nombre = findViewById(R.id.nombre);
        _descripcion = findViewById(R.id.descripcion);
        _precio = findViewById(R.id.precio);
        _stock = findViewById(R.id.stock);
        _pb = findViewById(R.id.pb);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegresar = findViewById(R.id.btnRegresar);
        estado = findViewById(R.id.estado);


        int id = getIntent().getExtras().getInt("id");
//        Toast.makeText(getApplicationContext(),"ID : "+id,Toast.LENGTH_LONG).show();
        db= new DbManager(getApplicationContext());
        c = db.getCursor("productos","_id="+id);
        if(c.moveToFirst()){
            do{
                _nombre.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
                _descripcion.setText(c.getString(c.getColumnIndexOrThrow("descripcion")));
                _precio.setText(c.getString(c.getColumnIndexOrThrow("precio")));
                _stock.setText(c.getString(c.getColumnIndexOrThrow("stock")));
                Picasso.with(getApplicationContext()).load("http://192.168.0.29/QR/img/"+c.getString(c.getColumnIndexOrThrow("foto")))
                        .into(_foto, new Callback() {
                            @Override
                            public void onSuccess() {
                                _pb.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError() {
                                _pb.setVisibility(View.GONE);
                            }
                        });
            }while (c.moveToNext());
        }else {
            Toast.makeText(getApplicationContext(),"NO EXISTE VALOR",Toast.LENGTH_LONG).show();
        }

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //AQUI VA EL CODIGO PARA REGISTRAR A LA BD
                Send objSend= new Send();
                objSend.execute("");
            }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListadoActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private class Send extends AsyncTask<String,String,String> {

        String msg="";
        String nombre=_nombre.getText().toString();
        String descripcion=_descripcion.getText().toString();
        String precio= _precio.getText().toString();
        String stock= _stock.getText().toString();


        Calendar a = Calendar.getInstance();
        int año = a.get(Calendar.YEAR);
        int mes = a.get(Calendar.MONTH) + 1;
        int dia = a.get(Calendar.DAY_OF_MONTH);
        int hora = a.get(Calendar.HOUR_OF_DAY);
        int minuto = a.get(Calendar.MINUTE);
        int segundo = a.get(Calendar.SECOND);
        String fecha = String.valueOf(dia)+"/"+String.valueOf(mes)+"/"+String.valueOf(año)+" "+String.valueOf(hora)+":"+String.valueOf(minuto)+":"+String.valueOf(segundo);

        boolean isSuccess=false;

        protected void onPreExecute(){
            estado.setText("Espere por favor, su registro esta siendo realizada..."); }


        @Override
        protected String doInBackground(String... strings) {
            Log.d("URL:",""+DB_URL);
            Log.d("USER:",""+USER);
            Log.d("PASS:",""+PASS);
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                if(conn == null){
                    msg="Please check your internet connection";
                }
                else{
                    String query="insert into registro(nombre, descripcion, precio, stock, fechahora) values('"+nombre+"','"+descripcion+"','"+precio+"','"+stock+"','"+fecha+"')";
                    Statement stmt= conn.createStatement();
                    stmt.executeUpdate(query);
                    msg="Registro realizado con exito!";
                    isSuccess = true;
                }
            }catch(Exception e){
                msg="Conexion fallida";
                isSuccess = false;
                e.printStackTrace();
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String msg){
            estado.setText(msg);
            if(isSuccess) {
                startActivity(new Intent(ProductoActivity.this,ListadoActivity.class));
            }
        }
    }


}