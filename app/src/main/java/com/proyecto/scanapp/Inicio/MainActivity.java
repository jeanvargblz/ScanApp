package com.proyecto.scanapp.Inicio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.proyecto.scanapp.DB.DbManager;
import com.proyecto.scanapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Button btn_entrar;
    private DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        db= new DbManager(getApplicationContext());
        setTitle("");


        btn_entrar= findViewById(R.id.btnentrar);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        String sesion = GetSession("nombre");
        if(!sesion.isEmpty())
            startActivity(new Intent(getApplicationContext(), ListadoActivity.class));
        getProductos();
    }
    private String GetSession(String nombre){
        SharedPreferences sharedPref = getSharedPreferences("loginsesion", Context.MODE_PRIVATE);
        return sharedPref.getString(nombre,"");
    }
    private void getProductos() {
        RequestParams params = new RequestParams();
        params.put("app", "ok");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.0.29/QR/procesos/api.php",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.d("PRODUCTOS",response.toString());
                    //llenando el listview
                    db.borrarTable("productos");
                    if(response.getBoolean("success")){
                        JSONArray pro = response.getJSONArray("listaproductos");
                        for(int i=0;i<pro.length();i++){
                            JSONObject o = pro.getJSONObject(i);
                            try {
                                db.insertarProductos(
                                        o.getInt("id"),
                                        o.getString("nombre"),
                                        o.getString("descripcion"),
                                        o.getString("foto"),
                                        o.getDouble("precio"),
                                        o.getInt("stock")
                                );
                                Log.d("INSERCION CORRECTA PRO","OK");
                            }catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        /*
                        db.borrarTable("promociones");
                        JSONArray prm = response.getJSONArray("listapromociones");
                        for(int i=0;i<prm.length();i++){
                            JSONObject o = prm.getJSONObject(i);
                            try {
                                db.insertarPromociones(
                                        o.getInt("id"),
                                        o.getString("nombre"),
                                        o.getString("descripcion"),
                                        o.getString("foto"),
                                        o.getInt("productos_id")
                                );
                                Log.d("INSERCION CORRECTA PROM","OK");
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                         */
                    }
                    //llenar db sqlite
                }catch (Exception e){
                    e.getMessage();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR", errorResponse.toString());
            }
        });

    }
}
