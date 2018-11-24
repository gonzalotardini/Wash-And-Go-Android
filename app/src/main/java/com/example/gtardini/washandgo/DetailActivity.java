package com.example.gtardini.washandgo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

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

        tv1.setText("Fecha: "+  detalle.Fecha + "\nIdLavado: " + detalle.IdLavado + "\nComentario cliente: " + detalle.ComentarioCliente);
    }


    public LavadoDetalle GetDetalle (int id){
        LavadoDetalle detalle= new LavadoDetalle();
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


}
