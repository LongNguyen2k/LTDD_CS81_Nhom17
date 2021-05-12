package com.example.quanlybandienthoai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.activity.GioHangActivity;
import com.example.quanlybandienthoai.activity.MainActivity;
import com.example.quanlybandienthoai.model.GioHang;
import com.example.quanlybandienthoai.model.HangDT;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> listGioHang;
    // vấn đề ở vùng biên 10 và 1
    public GioHangAdapter(Context context, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }

    @Override
    public int getCount() {
        return listGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return listGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if( convertView == null)
        {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listviewgiohang,null);
            viewHoler.txtTenDienThoai =  convertView.findViewById(R.id.textViewGioHang);
            viewHoler.txtGiaDienThoai = convertView.findViewById(R.id.textViewGiaDT);
            viewHoler.imageViewDienThoaiGioHang = convertView.findViewById(R.id.imgGioHang);
            viewHoler.btnMinus = convertView.findViewById(R.id.btnMinus);
            viewHoler.btnPlus = convertView.findViewById(R.id.btnPlus);
            viewHoler.btnValues = convertView.findViewById(R.id.btnValue);
            convertView.setTag(viewHoler);
        }
        else
        {
            viewHoler = (ViewHoler) convertView.getTag();

        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHoler.txtTenDienThoai.setText(gioHang.getTenDienThoai());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtGiaDienThoai.setText("Giá : " + decimalFormat.format(gioHang.getGiaTien()) + "Đ");

        // lấy img view từ dong_listview gio hang
        ImageView imgHinhDaiDien = convertView.findViewById(R.id.imgGioHang);
        Bitmap bmHinhAnh = BitmapFactory.decodeByteArray(gioHang.getHinhAnhDT(),0,gioHang.getHinhAnhDT().length);
        imgHinhDaiDien.setImageBitmap(bmHinhAnh);
        viewHoler.btnValues.setText(gioHang.getSoLuongDienThoai() + "");

        // bắt sự kiện nút + - tăng dần giá trị
        int soLuong = Integer.parseInt(viewHoler.btnValues.getText().toString());

        if(soLuong >= 10)
        {
            viewHoler.btnPlus.setVisibility(View.INVISIBLE);
            viewHoler.btnMinus.setVisibility(View.VISIBLE);

        }
        else if(soLuong <= 1)
        {
            viewHoler.btnMinus.setVisibility(View.INVISIBLE);

        }
        else if( soLuong >= 1)
        {
            viewHoler.btnMinus.setVisibility(View.VISIBLE);
            viewHoler.btnPlus.setVisibility(View.VISIBLE);
        }

        // update tổng  giá tiền khi ấn nút tăng hoặc giảm số lượng mỗi sản phẩm



        // có bug tại các vị trí biên
        // bat su kien nut cong
        ViewHoler finalViewHoler = viewHoler;
        finalViewHoler.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bấm + tăng sl sp len 1
                int slDienThoaiHienTai = MainActivity.gioHangs.get(position).getSoLuongDienThoai();
                int slDienThoaiCapNhat = Integer.parseInt(finalViewHoler.btnValues.getText().toString()) + 1;
                int giaTienHienTai = MainActivity.gioHangs.get(position).getGiaTien();
                // cập nhật số lượng
                MainActivity.gioHangs.get(position).setSoLuongDienThoai(slDienThoaiCapNhat);

                // cập nhật giá tiền mới
                int giaTienCapNhat =  MainActivity.gioHangs.get(position).getGiaTienDienThoai() * slDienThoaiCapNhat ;

                MainActivity.gioHangs.get(position).setGiaTien(giaTienCapNhat);

                // hiện thị lên textView
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtGiaDienThoai.setText("Giá : " + decimalFormat.format(MainActivity.gioHangs.get(position).getGiaTien()) + "Đ");

                // cập nhật tổng giá tiền
                GioHangActivity.EvenUtil();


                if( slDienThoaiCapNhat > 9 )
                {
                    finalViewHoler.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnValues.setText(String.valueOf(slDienThoaiCapNhat));
                }
                else
                {
                    finalViewHoler.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnValues.setText(String.valueOf(slDienThoaiCapNhat));
                }

            }
        });


        // bat su kien nut tru
        finalViewHoler.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // bấm - giảm soLuong di 1 sl sp len 1
                int slDienThoaiHienTai = MainActivity.gioHangs.get(position).getSoLuongDienThoai();
                int slDienThoaiCapNhat = Integer.parseInt(finalViewHoler.btnValues.getText().toString()) - 1;
                int giaTienHienTai = MainActivity.gioHangs.get(position).getGiaTien();


                // cập nhật số lượng
                MainActivity.gioHangs.get(position).setSoLuongDienThoai(slDienThoaiCapNhat);

                // cập nhật giá tiền mới
                int giaTienCapNhat =  MainActivity.gioHangs.get(position).getGiaTienDienThoai() * slDienThoaiCapNhat ;


                MainActivity.gioHangs.get(position).setGiaTien(giaTienCapNhat);

                // hiện thị lên textView
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtGiaDienThoai.setText("Giá : " + decimalFormat.format(MainActivity.gioHangs.get(position).getGiaTien()) + "Đ");

                // cập nhật tổng giá tiền
                GioHangActivity.EvenUtil();


                if( slDienThoaiCapNhat < 2 )
                {
                    finalViewHoler.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnValues.setText(String.valueOf(slDienThoaiCapNhat));
                }
                else
                {
                    finalViewHoler.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnValues.setText(String.valueOf(slDienThoaiCapNhat));
                }


            }
        });



        viewHoler = finalViewHoler;




        return convertView;
    }


    public class ViewHoler
    {
        TextView txtTenDienThoai , txtGiaDienThoai ;
        ImageView imageViewDienThoaiGioHang;
        Button btnMinus , btnPlus , btnValues;
    }
}
