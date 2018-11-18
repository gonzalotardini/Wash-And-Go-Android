package com.example.gtardini.washandgo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetData();
    }


    public void GetData(){
        List<Lavado> listlavados= new ArrayList<Lavado>();

        listlavados=GetLavados();

        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"
        layout.removeAllViews();

        for (Lavado item : listlavados) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView txt = new TextView(this);
            txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setText("Fecha: "+ item.Fecha +"\nAuto: " + item.Marca + " " + item.Modelo + "\nServicio: " + item.Servicio );
            txt.setId(1 + 1 + (1 * 4));
            row.addView(txt);
            layout.addView(row);
            LinearLayout row2 = new LinearLayout(this);
            row2.setLayoutParams(new LinearLayout.LayoutParams(1425, LinearLayout.LayoutParams.WRAP_CONTENT));
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(1425, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText("Prueba");
            btnTag.setId(1 + 1 + (1 * 4));

            row2.addView(btnTag);


            layout.addView(row2);
        }
    }



    public List<Lavado>  GetLavados(){
        List<Lavado> listlavados= new ArrayList<>() ;
        String apilink= "https://washandgo.azurewebsites.net/SolicitarLavado/GetLavadosFinalizados";
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

            while ((inputline= in.readLine()) != null){

                response.append(inputline);
            }

            json= response.toString();

            JSONArray jsonArr= null;
            jsonArr = new JSONArray(json);



            for (int i = 0;  i<jsonArr.length(); i++){

                Lavado lavado= new Lavado();

                JSONObject jsonObject = jsonArr.getJSONObject(i);
                lavado.Fecha=jsonObject.getString("FechaApi");
                lavado.id=jsonObject.getString("IdLavado");
                lavado.Marca=jsonObject.getString("Marca");
                lavado.Modelo=jsonObject.getString("Modelo");
                lavado.Servicio=jsonObject.getString("Servicio");

                listlavados.add(lavado);
                Log.d("SALIDA", lavado.Servicio);

            }

            return  listlavados;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  listlavados;
    }

    public void Refresh (View view) {

        GetData();
    }
}
