package com.example.urv.pruebaparking;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;
import cat.tomasgis.module.communication.listeners.IDataReceiver;

public class SlotListActivity extends AppCompatActivity {
    private static final String TAG = com.example.urv.pruebaparking.SlotListActivity.class.getSimpleName();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_slot);

        ((ListView)this.findViewById(R.id.listDetall)).setAdapter(null);
        mContext = this.getApplicationContext();



        Cursor cursor = this.getContentResolver().query(ModelContracts.SlotModel.buildContentUri(),
                ModelContracts.SlotModel.DEFAULT_PROJECTIONS,null, null,
                ModelContracts.SlotModel.DEFAULT_SORT);


        SlotListAdapter mSlotAdapter = new SlotListAdapter(mContext,cursor);
        //ListView mListView = findViewById(R.id.listDetall);
        //mListView.setAdapter(mSlotAdapter);
        ((ListView) this.findViewById(R.id.listDetall)).setAdapter(mSlotAdapter);


        Toast.makeText(this, "Entro aqui", Toast.LENGTH_LONG).show();
    }

}
