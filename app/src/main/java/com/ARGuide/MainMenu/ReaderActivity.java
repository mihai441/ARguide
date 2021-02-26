package com.ARGuide.MainMenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ARGuide.MainMenu.Models.Cale;
import com.ARGuide.MainMenu.Models.Locatie;
import com.ARGuide.androidarsceneform.MainARActivity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                final Cale calePrimita = null;
                String contents = data.getStringExtra("SCAN_RESULT");
                AndroidNetworking.get("localhost/Path/GetLocatii")
                        .addHeaders("token", "1234")
                        .setTag("apiCall")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                JSONObject jsonobject = null;
                                    try {
                                        if(response.get(0) != null){
                                            Intent rIntent = new Intent(ReaderActivity.this, MainARActivity.class);
                                            rIntent.putExtra("directie",response.get(0).toString());
                                            startActivity(rIntent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
