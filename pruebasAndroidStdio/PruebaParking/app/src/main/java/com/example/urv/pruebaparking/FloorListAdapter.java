package com.example.urv.pruebaparking;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;

public class FloorListAdapter extends BaseAdapter {

    private static final String TAG = FloorListAdapter.class.getSimpleName();
    private Context mContext;
    Cursor mData;


    public FloorListAdapter(Context context, Cursor data) {
        if (context==null)Log.e(TAG, "Context is null");
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        if(mData==null)return 0;
        return  mData.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_list_adapter, null);
        }
        mData.moveToPosition(position);

        String name = mData.getString(mData.getColumnIndex(ModelContracts.FloorModel.NAME));
        ((TextView) convertView.findViewById(R.id.mNameFloor)).setText(name);

        Cursor cursor = mContext.getContentResolver().query(ModelContracts.SlotModel.buildContentUri(),
                ModelContracts.SlotModel.DEFAULT_PROJECTIONS,
                "floor_id=?",
                new String[]{"1"},
                ModelContracts.SlotModel.DEFAULT_SORT);
        ((TextView) convertView.findViewById(R.id.mNameFloorSlots)).setText(String.valueOf(cursor.getCount()));
        cursor.close();

        return convertView;
    }
}
