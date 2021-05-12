package com.example.quanlybandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.adapter.GioHangAdapter;
import com.example.quanlybandienthoai.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    ListView lvGioHang;
    TextView tvthongBao;
    static TextView tvTongTien ;
    Button btnThanhToan , btnTiepTucMua;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolbarGioHang();
        CheckDataGioHang();
        EvenUtil();
        CatchOnItemListView();
        EventButton();

    }

    private void EventButton() {

        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangs.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHang.class);
                    startActivity(intent);
                }
                else
                {
                    CheckConnection.showToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm để thanh toán  !");
                }


            }
        });
    }

    // đè lâu sẽ xóa 1 phần tử trong mảng giỏ hàng
    private void CatchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm ?");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ");
                // bắt sự kiện nút có để xóa
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.gioHangs.size() <= 0 )
                        {
                            tvthongBao.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            // xóa phần tử chỉ định trong giỏ hàng
                            MainActivity.gioHangs.remove(position);
                            // cập nhật bản vẽ giỏ hàng
                            gioHangAdapter.notifyDataSetChanged();
                            // cập nhật giá tiền
                            EvenUtil();
                            if(MainActivity.gioHangs.size() <= 0 )
                            {
                                // khi giỏ hàng hết sản phẩm thì thông báo giỏ hàng trống hiện lên
                                tvthongBao.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                // có sp thì ẩn
                                tvthongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EvenUtil();
                            }
                        }
                    }
                });

                // bắt sự kiện nút ko trong thông báo
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUtil() {

        int tongTien = 0;
        for(int i = 0 ; i <MainActivity.gioHangs.size() ; i++)
        {
            tongTien += MainActivity.gioHangs.get(i).getGiaTien();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien) + "Đ");
    }

    private void CheckDataGioHang() {
        if(MainActivity.gioHangs.size() <= 0 )
        {
            gioHangAdapter.notifyDataSetChanged();
            tvthongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);

        }
        else
        {
            gioHangAdapter.notifyDataSetChanged();
            tvthongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }

    }

    private void ActionToolbarGioHang() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGioHang = findViewById(R.id.listViewGioHang);
        tvthongBao = findViewById(R.id.tvTBaoGioHang);
        tvTongTien = findViewById(R.id.tvTongTien);
        toolbarGioHang = findViewById(R.id.toolbarGioHang);
        btnThanhToan= findViewById(R.id.btnThanhToanGH);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMuaHang);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this,MainActivity.gioHangs);
        lvGioHang.setAdapter(gioHangAdapter);

    }


}