package com.example.quanlybandienthoai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.activity.ChiTietDienThoai;
import com.example.quanlybandienthoai.model.DienThoai;
import com.example.quanlybandienthoai.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.ItemHolder> {
    Context context;
    ArrayList<DienThoai> arrayDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<DienThoai> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_dienthoaimoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        DienThoai dienThoai = arrayDienThoai.get(position);
        holder.txtTenDienThoai.setText(dienThoai.getTenDienThoai());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaDienThoai.setText("Giá : " + decimalFormat.format(dienThoai.getGiaTien()) + "Đ");

        ImageView imgHinhDaiDien = holder.imgHinhDienThoai;
        Bitmap bmHinhAnh = BitmapFactory.decodeByteArray(dienThoai.getHinhAnh(),0,dienThoai.getHinhAnh().length);
        imgHinhDaiDien.setImageBitmap(bmHinhAnh);



    }

    @Override
    public int getItemCount() {
        return arrayDienThoai.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgHinhDienThoai;
        public TextView txtTenDienThoai , txtGiaDienThoai;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhDienThoai = itemView.findViewById(R.id.imageViewDienThoai);
            txtTenDienThoai = itemView.findViewById(R.id.textViewDienThoai);
            txtGiaDienThoai = itemView.findViewById(R.id.texViewGiaDienThoai);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietDienThoai.class);
                    intent.putExtra("thongTinDienThoai",arrayDienThoai.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showToast_Short(context,arrayDienThoai.get(getPosition()).getTenDienThoai());
                    context.startActivity(intent);


                }
            });
        }
    }

}
