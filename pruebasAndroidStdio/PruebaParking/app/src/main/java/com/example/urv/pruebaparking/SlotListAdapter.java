package com.example.urv.pruebaparking;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;

public class SlotListAdapter extends BaseAdapter {
    private Context mContext;
    private static final String TAG = com.example.urv.pruebaparking.SlotListAdapter.class.getSimpleName();
    Cursor mData;
    private View view;

    public SlotListAdapter(Context context, Cursor data) {
        if(context==null) Log.e(TAG, "Context null");
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        if(mData==null)return 0;

        return mData.getCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.list_floor_item, null);
        }
        mData.moveToPosition(i);

        String name = mData.getString(mData.getColumnIndex(ModelContracts.SlotContract.NAME));
        ((TextView) view.findViewById(R.id.mTxtNameSlot)).setText(name);



        return view;
    }
}
