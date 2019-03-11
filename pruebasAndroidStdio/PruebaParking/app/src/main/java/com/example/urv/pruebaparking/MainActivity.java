package com.example.urv.pruebaparking;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.urv.pruebaparking.Models.Floor;
import com.example.urv.pruebaparking.Models.Parking;
import com.example.urv.pruebaparking.Models.Slot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;
import cat.tomasgis.module.communication.CommManager;
import cat.tomasgis.module.communication.base.AppURL;
import cat.tomasgis.module.communication.listeners.IDataReceiver;
import cat.tomasgis.module.communication.listeners.StringResponseListener;

public class MainActivity extends AppCompatActivity implements IDataReceiver, AdapterView.OnItemClickListener {
    private static final String TAG = com.example.urv.pruebaparking.MainActivity.class.getSimpleName();

    StringResponseListener responseListener = new StringResponseListener(this);

    private ListView mListView1;

    private List<String> mLista = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private Parking[] parking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView1 = findViewById(R.id.list);
        mListView1.setOnItemClickListener(this);

        CommManager.initializeQueu(this);
        if(!CommManager.callRequest(AppURL.PARKING_URL, responseListener)){
            Toast.makeText(this, "Error !!!Conexión ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onReceiveData(String data) {
        if(data != null){
            if(data.length()>0){
                Toast.makeText(this, "Recibiendo...", Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                parking = gson.fromJson(data, Parking[].class);
                for(int i=0; i<parking.length; i++){
                    String name = parking[i].getName();
                    mLista.add(name);
                    mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, mLista);
                    mListView1.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ListaParking.class);
        intent.putExtra("posicion", position);
        startActivity(intent);//Se puede poner MainActivity.this.startActivity(intent)
    }

}
