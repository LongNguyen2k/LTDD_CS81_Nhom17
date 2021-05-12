package com.example.quanlybandienthoai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.model.HangDT;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HangDTAdapter extends BaseAdapter {
    public HangDTAdapter(ArrayList<HangDT> hangDTArrayList, Context context) {
        this.hangDTArrayList = hangDTArrayList;
        this.context = context;
    }

    ArrayList<HangDT> hangDTArrayList ;
    Context context;

    @Override
    public int getCount() {
        return hangDTArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return hangDTArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class  ViewHolder
    {
        TextView txtTenHangDT;
        ImageView imgHangDT;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if( view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_hangdt,null);
            viewHolder.txtTenHangDT = (TextView) view.findViewById(R.id.textViewHangDT);
            viewHolder.imgHangDT = (ImageView) view.findViewById(R.id.imageViewHangDT);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();

        }
        HangDT hangDT = (HangDT) getItem(position);
        viewHolder.txtTenHangDT.setText(hangDT.getTenHang());
        ImageView imgHinhDaiDien = view.findViewById(R.id.imageViewHangDT);
        Bitmap bmHinhAnh = BitmapFactory.decodeByteArray(hangDT.getHinhAnh(),0,hangDT.getHinhAnh().length);
        imgHinhDaiDien.setImageBitmap(bmHinhAnh);



        return view;
    }
}
