package com.example.quanlybandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.model.DienThoai;
import com.example.quanlybandienthoai.model.GioHang;

import java.text.DecimalFormat;

public class ChiTietDienThoai extends AppCompatActivity {


    Toolbar toolbarChiTiet;
    ImageView imageViewChiTiet;
    TextView tvTenChiTiet , tvGiaTienChiTiet , tvHeDieuHanh  , tvManHinh , tvCameraTrC , tvCameraSau ;
    TextView tvChip , tvRam , tvBoNhoTrong , tvPIN , tvMoTaChiTiet;
    Spinner spinnerChiTiet;
    Button btnThemVaoGio;

    int id  = 0 ;
    String tenDienThoai = "";
    String heDieuHanh = "";
    String ManHinh = "";
    String cameraTruoc = "";
    String cameraSau = "";
    String Chip ="";
    String Ram ="";
    String BoNhoTrong = "";
    String PIN = "";
    byte[] hinhAnh;
    int giaTien;
    String gioiThieu = "";
    int HangDT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dien_thoai);
        AnhXa();
        ActionToolBar();
        GetInformationPhone();
        CatchEventSpinner();
        EventButtonThemVaoGio();

    }

    // 2 function để hiện thị nút giỏ hàng và bấm vào giỏ hàng chuyển vào trang giỏ hàng .
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButtonThemVaoGio() {
        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // đã có sản phẩm trong giỏ thì thêm vào giỏ và cập nhật
                if(MainActivity.gioHangs.size() > 0 )
                {


                    // đã có sản phẩm cần thêm thì cập nhật biến vòng lập for kiểm tra nếu đã có sản phẩm trong
                    // giỏ hàng thì + số lượng thêm vào
                    int sL = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString()) ;
                    boolean exists = false;
                    for (int i = 0 ; i < MainActivity.gioHangs.size() ; i++)
                    {
                        if(MainActivity.gioHangs.get(i).getIdDienThoai() == id) {
                            MainActivity.gioHangs.get(i).setSoLuongDienThoai(MainActivity.gioHangs.get(i).getSoLuongDienThoai() + sL);
                            // neu so luong vuot qua 10 thi chi cho la 10 thoi
                            if(MainActivity.gioHangs.get(i).getSoLuongDienThoai() >= 10) {
                                MainActivity.gioHangs.get(i).setSoLuongDienThoai(10);
                            }
                            MainActivity.gioHangs.get(i).setGiaTien(giaTien * MainActivity.gioHangs.get(i).getSoLuongDienThoai());
                            exists = true ;

                        }


                    }
                    // nếu không có phần tử nào để cập nhật giá tiền thì tạo 1 đối tượng mới add vào giỏ hàng .
                    if (exists == false)
                    {

                        int soLuongDienThoai = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString()) ;
                        int TongGiaMoi = soLuongDienThoai * giaTien ;
                        MainActivity.gioHangs.add(new GioHang(id,tenDienThoai,TongGiaMoi,hinhAnh,soLuongDienThoai,giaTien));
                    }

                }
                else
                    // add vào giỏ hàng đang ko có sản phẩm và
                {
                    int soLuongDienThoai = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString()) ;
                    int TongGiaMoi = soLuongDienThoai * giaTien ;
                    MainActivity.gioHangs.add(new GioHang(id,tenDienThoai,TongGiaMoi,hinhAnh,soLuongDienThoai,giaTien));

                }

                // bấm vào chuyển qua trang giao diện giỏ hàng
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);

            }
        });
    }

    private void CatchEventSpinner() {
        // màn hình hiện tại là this
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soLuong);
        spinnerChiTiet.setAdapter(arrayAdapter);
    }

    private void GetInformationPhone() {


        DienThoai dienThoai = (DienThoai) getIntent().getSerializableExtra("thongTinDienThoai");

        // lay du lieu  dien thoai tu trang main truyen qua cac bien de lay du lieu
        id = dienThoai.getId();
        tenDienThoai = dienThoai.getTenDienThoai();
        heDieuHanh = dienThoai.getHeDieuHanh();
        ManHinh = dienThoai.getManHinh();
        cameraTruoc = dienThoai.getCameraTruoc();
        cameraSau = dienThoai.getCameraSau();
        Chip = dienThoai.getChip();
        Ram = dienThoai.getChip();
        BoNhoTrong = dienThoai.getBoNhoTrong();
        PIN = dienThoai.getPIN();
        hinhAnh = dienThoai.getHinhAnh();
        giaTien = dienThoai.getGiaTien();
        gioiThieu = dienThoai.getGioiThieu();
        HangDT = dienThoai.getHangDT();

        tvTenChiTiet.setText(tenDienThoai);
        tvHeDieuHanh.setText(heDieuHanh);
        tvManHinh.setText(ManHinh);
        tvCameraTrC.setText(cameraTruoc);
        tvCameraSau.setText(cameraSau);
        tvChip.setText(Chip);
        tvRam.setText(Ram);
        tvBoNhoTrong.setText(BoNhoTrong);
        tvPIN.setText(PIN);

        // xuat ra view
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiaTienChiTiet.setText("Giá : " + decimalFormat.format(giaTien) + "Đ");

        tvMoTaChiTiet.setText(gioiThieu);

        ImageView imgHinhMoTaDienThoai = imageViewChiTiet;
        Bitmap bmHinhAnh = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        imgHinhMoTaDienThoai.setImageBitmap(bmHinhAnh);





    }

    private void ActionToolBar() {
       setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AnhXa() {
        toolbarChiTiet = findViewById(R.id.toolbarChiTietDT);
        imageViewChiTiet = findViewById(R.id.imageViewChiTietDT);
        tvTenChiTiet = findViewById(R.id.tvTenDTChiTietDT);
        tvGiaTienChiTiet = findViewById(R.id.tvGiaTienChiTietDT);
        tvHeDieuHanh = findViewById(R.id.tvHeDieuHanh);
        tvManHinh = findViewById(R.id.tvManHinhChiTiet);
        tvCameraTrC = findViewById(R.id.tvCameRaTruoc);
        tvCameraSau = findViewById(R.id.tvCameRaSau);
        tvChip = findViewById(R.id.tvChip);
        tvRam = findViewById(R.id.tvRam);
        tvBoNhoTrong = findViewById(R.id.tvBoNhoTrong);
        tvPIN = findViewById(R.id.tvPIN);
        tvMoTaChiTiet = findViewById(R.id.tvViewMoTaChiTietDT);
        spinnerChiTiet = findViewById(R.id.spinner);
        btnThemVaoGio = findViewById(R.id.buttonDatMua);
    }
}