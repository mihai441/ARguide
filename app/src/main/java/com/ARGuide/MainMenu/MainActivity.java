package com.ARGuide.MainMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.ARGuide.MainMenu.Models.Locatie;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locatieAleasa = null;
        locatii = getLocatii();

        ArrayList<String> locatiiNames = new ArrayList<>();

        for(int i=0;i<locatii.size();i++){
            locatiiNames.add(locatii.get(i).getDenumire());
        }



        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.listview_item_layout, locatiiNames);

        scan = (Button) findViewById(R.id.scan);
        listaView = (ListView) findViewById(R.id.list_viewLocatii);

        listaView.setAdapter(itemsAdapter);


        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    locatieAleasa = locatii.get(i);

                    scan.setEnabled(true);
                }
                catch(Exception ex){
                    scan.setEnabled(false);
                }
            }});

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rIntent = new Intent(MainActivity.this, ReaderActivity.class);
                startActivity(rIntent);
            }
        });
    }


    private List<Locatie> getLocatii() {
        final ArrayList<Locatie> locatii = new ArrayList<>();
        AndroidNetworking.get("https://192.168.0.195:44342/path/getlocatii")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                                Locatie locatieNoua = new Locatie(jsonobject.getString("denumire"),jsonobject.getString("cod"), jsonobject.getString("codQR"), jsonobject.getInt("etaj"));
                                locatii.add(locatieNoua);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
        return locatii;
    }

    private Button gen, scan;
    private ListView listaView;
    private List<Locatie> locatii;
    private Locatie locatieAleasa;
}
