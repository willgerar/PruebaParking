package com.example.urv.pruebaparking;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urv.pruebaparking.Models.Floor;
import com.example.urv.pruebaparking.Models.Parking;
import com.example.urv.pruebaparking.Models.Slot;
import com.example.urv.pruebaparking.Models.Location;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;
import cat.tomasgis.module.communication.CommManager;
import cat.tomasgis.module.communication.base.AppURL;
import cat.tomasgis.module.communication.listeners.IDataReceiver;
import cat.tomasgis.module.communication.listeners.StringResponseListener;

public class ListaParking extends AppCompatActivity implements IDataReceiver, View.OnClickListener{

    private static final String TAG = com.example.urv.pruebaparking.ListaParking.class.getSimpleName();
    StringResponseListener responseListener = new StringResponseListener(this);

    private TextView nameSelecion;
    private Button btnEntrarP, btnVerLo, btnBuscarS;



    private ListView mListView;//definir mi lista
    private List<String> mLista = new ArrayList<>(); //definir una lista de strings
    private ArrayAdapter<String> mAdapter; //definir un array de listas

    private Parking[] parking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_parking);

        btnEntrarP = findViewById(R.id.btnEntrar);
        //btnEntrarP.setOnClickListener(this);
        btnVerLo = findViewById(R.id.btnVerLocal);
        //btnVerLo.setOnClickListener(this);
        btnBuscarS = findViewById(R.id.btnBuscar);
        //btnBuscarS.setOnClickListener(this);

        //inicializamos la conexión con el servidor
        CommManager.initializeQueu(this);
        if(!CommManager.callRequest(AppURL.PARKING_URL,responseListener)){//peticon datos a la htpps de  Parking
            Toast.makeText(this, "Eror ! Conexión !!!!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v){
        if(v==btnEntrarP){
            Intent intentEntrar = new Intent(ListaParking.this, FloorListActivity.class);
            startActivity(intentEntrar);
        }
        if(v==btnVerLo){
            Intent intentVerL = new Intent(ListaParking.this, MapsActivity.class);
            startActivity(intentVerL);
        }
        if(v==btnBuscarS){
            Intent intentBuscar = new Intent(ListaParking.this, SlotListActivity.class);
            startActivity(intentBuscar);
        }
    }


    @Override
    public void onReceiveData(String dataList) {

        Bundle params = getIntent().getExtras();
        int pos = params.getInt("posicion");

        if(dataList!=null){
            if(dataList.length()>0){
                Gson gson = new Gson();
                parking = gson.fromJson(dataList, Parking[].class);

                Toast.makeText(this, "tengo la posicion " + pos, Toast.LENGTH_SHORT).show();
                nameSelecion = findViewById(R.id.txtMostraP);
                nameSelecion.setText(parking[pos].getName());

                clearAllData();
                createBaseData();
            }
        }

    }

    //Borrar base de datos
    protected void clearAllData(){
        int numElementsBorrados;
        ContentResolver contentResolver = this.getContentResolver();

        //Slot
        numElementsBorrados = contentResolver.delete(ModelContracts.SlotModel.buildContentUri(),null,null);
        Log.d(TAG, String.format("Slot deleted: %d ", numElementsBorrados));

        //Floor
        numElementsBorrados = contentResolver.delete(ModelContracts.FloorModel.buildContentUri(), null, null);
        Log.d(TAG, String.format("Floor deleted: %d ", numElementsBorrados));

        //Location
        numElementsBorrados = contentResolver.delete(ModelContracts.LocationModel.buildContentUri(), null, null);
        Log.d(TAG, String.format("Locations deleted: %d ", numElementsBorrados));

        //Parking
        numElementsBorrados = contentResolver.delete(ModelContracts.ParkingModel.buildContentUri(), null, null);
        Log.d(TAG, String.format("parking deleted: %d ", numElementsBorrados));
    }

    //Crear base de datos
    protected void createBaseData(){

        ContentResolver contentResolver = this.getContentResolver();
        ContentValues contentValuesParking;
        ContentValues contentValuesFloor;
        ContentValues contentValuesSlot;
        ContentValues contentValuesLocation;

        Bundle params = getIntent().getExtras();
        int pos = params.getInt("posicion");

        Toast.makeText(this, "que posición tengo aquí " + pos, Toast.LENGTH_SHORT).show();

        contentValuesParking = ContentValuesUtils.modelToContentValuesP(parking[pos]);
        Uri insertUri;
        for(Floor floor: parking[pos].getFloors()){
            //Insertar floor
            contentValuesFloor = ContentValuesUtils.modelToContentValuesF(floor);
            insertUri = contentResolver.insert(ModelContracts.FloorModel.buildContentUri(),contentValuesFloor);
            Log.d(TAG, String.format("Floor insert: %s", insertUri.toString()));

            for(Slot slot: floor.getSlots()){
                contentValuesSlot = ContentValuesUtils.modelToContentValuesS(slot,floor.getCompany_number());

                //Insertar slot
                insertUri = contentResolver.insert(ModelContracts.SlotModel.buildContentUri(),contentValuesSlot);
                Log.d(TAG, String.format("Slot insert: %s", insertUri.toString()));
            }

            //Insertar location
            contentValuesLocation = ContentValuesUtils.modelToContentValuesL(parking[pos].getLocation(),parking[pos].getName());
            insertUri = contentResolver.insert(ModelContracts.LocationModel.buildContentUri(), contentValuesLocation);
            Log.d(TAG, String.format("Location insert: %s", insertUri.toString()));
        }

        //insertar parking
        insertUri = contentResolver.insert(ModelContracts.ParkingModel.buildContentUri(),contentValuesParking);
        Log.d(TAG, String.format("Parking insert: %s", insertUri.toString()));

    }

}



