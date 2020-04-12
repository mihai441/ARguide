package com.example.newpc.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.newpc.qrcode.Models.Locatie;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locatieAleasa = null;
        locatii = getLocatii();

        ArrayAdapter<Locatie> itemsAdapter = new ArrayAdapter<Locatie>(this.getApplicationContext(), R.layout.listview_item_layout, locatii);

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

        //TODO de unde luam locatiile??
        ArrayList<Locatie> locatiiLocale = new ArrayList<Locatie>();
        locatiiLocale.add(locatiiLocale.size(), new Locatie("Baie Rector", "BR", "SADLJ!@OIU()AJDJ128479128JASJD"));

        return locatiiLocale;
    }

    private Button gen, scan;
    private ListView listaView;
    private List<Locatie> locatii;
    private Locatie locatieAleasa;
}
