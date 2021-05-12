package com.example.quanlybandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.adapter.OPPOADAPTER;
import com.example.quanlybandienthoai.adapter.SamSungAdapter;
import com.example.quanlybandienthoai.model.DienThoai;
import com.example.quanlybandienthoai.ultil.CheckConnection;

import java.util.ArrayList;

public class OppoActivity extends AppCompatActivity {

    static final String Database_Name = "db_qlbh.sqlite";

    SQLiteDatabase database;


    Toolbar toolbar;
    ListView lvOOPO;
    OPPOADAPTER oppoadapter;
    ArrayList<DienThoai> arrayListOOPO;
    int idDienThoai = 0;

    int id  = 0 ;
    String tenDienThoai = "";
    int giaTien;
    String gioiThieu = "";
    byte[] hinhAnh;


    // loading progress bar
    // View footerView;
    //   boolean isLoading = false;
    //  mHandler mHandler;
    //  boolean limitData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppo);

        AnhXa();
        GetIDLoaiDienThoai();
        ActionToolbar();
        GetDataOOPO();
        // LoadMoreData();

        ActionItemViewHolder();
    }


    // chuyển dữ liệu qua trang chi tiet dien thoai
    private void ActionItemViewHolder() {
        lvOOPO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietDienThoai.class);
                intent.putExtra("thongTinDienThoai",arrayListOOPO.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CheckConnection.showToast_Short(getApplicationContext(),arrayListOOPO.get(position).getTenDienThoai());
                startActivity(intent);
            }
        });
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



    private void GetDataOOPO() {
        database = DatabaseConnection.initDatabase(this,Database_Name);
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM DienThoai WHERE HANG = %d",idDienThoai), null);

        cursor.moveToFirst();
        for (int i = 0 ; i < cursor.getCount(); i++)
        {

            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String tenDienThoai = cursor.getString(1);
            String heDieuHanh = cursor.getString(2);
            String manHinh = cursor.getString(3);
            String cameraTruoc = cursor.getString(4);
            String cameraSau = cursor.getString(5);
            String Chip = cursor.getString(6);
            String Ram = cursor.getString(7);
            String boNhoTrong = cursor.getString(8);
            String PIN = cursor.getString(9);
            byte[] hinhAnh = cursor.getBlob(10);
            int giaTien = cursor.getInt(11);
            String gioiThieu = cursor.getString(12);
            int idHangDT = cursor.getInt(13);

            arrayListOOPO.add(i, new DienThoai(id,tenDienThoai,heDieuHanh,manHinh,cameraTruoc,cameraSau,Chip,Ram,boNhoTrong,PIN,hinhAnh,giaTien,gioiThieu,idHangDT));
        }
        cursor.close();
        oppoadapter.notifyDataSetChanged();
        database.close();
    }

    private void ActionToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void GetIDLoaiDienThoai(){
        idDienThoai = getIntent().getIntExtra("idDienThoai",-1);
        Log.d("giatridienthoai",idDienThoai +"");

    }


    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarOPPO);
        lvOOPO = findViewById(R.id.listViewOPPO);
        arrayListOOPO = new ArrayList<>();
        oppoadapter = new OPPOADAPTER(getApplicationContext(),arrayListOOPO);
        lvOOPO.setAdapter(oppoadapter);

        //LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //footerView = layoutInflater.inflate(R.layout.progressbar,null);


    }
}