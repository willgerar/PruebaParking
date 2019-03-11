package com.example.urv.pruebaparking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;

public class FloorListActivity extends AppCompatActivity {

    //public static final String TAG = com.example.urv.pruebaparking.FloorListActivity.class.getSimpleName();
    private Context mContext;
    //private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_floor);

        ((ListView)this.findViewById(R.id.list)).setAdapter(null);

        mContext = this.getApplicationContext();

        Cursor cursor = this.getContentResolver().query(ModelContracts.FloorModel.buildContentUri(), ModelContracts.FloorModel.DEFAULT_PROJECTIONS,
                null,null, ModelContracts.FloorModel.DEFAULT_SORT);

        FloorListAdapter mFloorAdapter = new FloorListAdapter(mContext, cursor);
        ListView mListView = findViewById(R.id.list);
        mListView.setAdapter(mFloorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FloorListActivity.this, SlotListActivity.class);
                FloorListActivity.this.startActivity(intent);
            }
        });

    }
}
