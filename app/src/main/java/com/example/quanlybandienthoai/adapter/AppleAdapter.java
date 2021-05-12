package com.example.quanlybandienthoai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.model.DienThoai;
import com.example.quanlybandienthoai.model.HangDT;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AppleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DienThoai> arrayListApple;


    public AppleAdapter(Context context, ArrayList<DienThoai> arrayListApple) {
        this.setContext(context);
        this.setArrayListApple(arrayListApple);
    }


    @Override
    public int getCount() {
        return arrayListApple.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListApple.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
       TextView txtTenDienThoai, txtGiaDienThoai , txtMotaDienThoai;
       ImageView imgViewDienThoai;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if( convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txtTenDienThoai = (TextView) convertView.findViewById(R.id.tv_TenDienThoai);
            viewHolder.imgViewDienThoai = (ImageView) convertView.findViewById(R.id.imageView_CacDienThoai);
            viewHolder.txtGiaDienThoai = (TextView) convertView.findViewById(R.id.tv_GiaTienDienThoai);
            viewHolder.txtMotaDienThoai = (TextView) convertView.findViewById(R.id.tv_MoTaDienThoai);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        DienThoai dienThoai = (DienThoai) getItem(position);
        viewHolder.txtTenDienThoai.setText(dienThoai.getTenDienThoai());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienThoai.setText("Giá : " + decimalFormat.format(dienThoai.getGiaTien()) + "Đ");
        viewHolder.txtMotaDienThoai.setMaxLines(2);
        viewHolder.txtMotaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaDienThoai.setText(dienThoai.getGioiThieu());
        ImageView imgHinhDaiDien = convertView.findViewById(R.id.imageView_CacDienThoai);
        Bitmap bmHinhAnh = BitmapFactory.decodeByteArray(dienThoai.getHinhAnh(),0,dienThoai.getHinhAnh().length);
        imgHinhDaiDien.setImageBitmap(bmHinhAnh);


        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<DienThoai> getArrayListApple() {
        return arrayListApple;
    }

    public void setArrayListApple(ArrayList<DienThoai> arrayListApple) {
        this.arrayListApple = arrayListApple;
    }
}