package com.example.gtardini.washandgo;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DetailActivity extends AppCompatActivity {

    LavadoDetalle detalle= new LavadoDetalle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        String iddd = intent.getStringExtra("LAVADOID");


        setContentView(R.layout.activity_detail);
        TextView tv1 = (TextView)findViewById(R.id.textView);

        LavadoDetalle detalle = new LavadoDetalle();
       detalle= GetDetalle(Integer.parseInt(iddd));

        tv1.setText("\nFecha: "+  detalle.Fecha+ "\n" + "\nServicio: " + detalle.Servicio+ "\n" + "\nAuto: "+ detalle.Auto+ "\n"  + "\nTotal: " + detalle.Total+ "\n"  + "\nCliente: " + detalle.NombreCliente+ "\n" + "\nLavador: "+ detalle.NombreLavador+ "\n" + "\nCalificación Cliente: " + detalle.CalificacionCliente+ "\n"   + "\nComentario Cliente: " + detalle.ComentarioCliente+ "\n"  + "\n"+ "Calificación Cliente: " + detalle.CalificacionLavador+ "\n"  + "\nComentario Lavador: " + detalle.ComentarioLavador+ "\n" );
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
    }


    public LavadoDetalle GetDetalle (int id){

    String apilink= "https://washandgo.azurewebsites.net/SolicitarLavado/GetLavadoDetalleAPI?idlavado=" +id;
    StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    URL url= null;
    HttpURLConnection conn;

        try {

            url= new URL(apilink);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputline;

            StringBuffer response= new StringBuffer();
            String json="";

            while ((inputline= in.readLine()) !=null){
                response.append(inputline);
            }

            json = response.toString();

            JSONObject jsonObj=null;
            jsonObj= new JSONObject(json);


            detalle.Fecha=jsonObj.getString("Fecha");
            detalle.Total=jsonObj.getString("Total");
            detalle.ComentarioCliente=jsonObj.getString("ComentarioCliente");
            detalle.Auto=jsonObj.getString("Auto");
            detalle.CalificacionCliente=jsonObj.getString("CalificacionCliente");
            detalle.CalificacionLavador=jsonObj.getString("CalificacionLavador");
            detalle.ComentarioLavador  =jsonObj.getString("ComentarioLavador");
            detalle.IdLavado  =jsonObj.getString("IdLavado");
            detalle.NombreCliente  =jsonObj.getString("NombreCliente");
            detalle.NombreLavador  =jsonObj.getString("NombreLavador");
            detalle.Servicio  =jsonObj.getString("Servicio");

            Log.d("GONZA", detalle.Servicio + detalle.NombreCliente);
            return detalle;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  detalle;
    }


    public void Aprobar (View view){
        AprobarLavado(Integer.parseInt(detalle.IdLavado));
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void AprobarLavado (int id){
        String apilink="https://washandgo.azurewebsites.net/SolicitarLavado/AprobarLavado?idlavado="+ id;
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url= null;
        HttpURLConnection conn;

        try {

            url= new URL(apilink);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputline;

            StringBuffer response= new StringBuffer();
            String json="";

            while ((inputline= in.readLine()) !=null){
                response.append(inputline);
            }

            json = response.toString();

            JSONObject jsonObj=null;
            jsonObj= new JSONObject(json);

            Log.d("GONZA", "TERMINO");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
