package com.example.quanlybandienthoai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.adapter.DienThoaiAdapter;
import com.example.quanlybandienthoai.adapter.HangDTAdapter;
import com.example.quanlybandienthoai.model.DienThoai;
import com.example.quanlybandienthoai.model.GioHang;
import com.example.quanlybandienthoai.model.HangDT;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   static final String Database_Name = "db_qlbh.sqlite";

    SQLiteDatabase database;

    Toolbar toolBar;

    ViewFlipper viewFlipper;

    NavigationView navigationView;

    DrawerLayout drawerLayout;
    // giao dien toobar
    ListView listViewManHinhChinh;
    ArrayList<HangDT> mangHangDT;
    HangDTAdapter hangDTAdapter;

    // SanPhamMoiNhatManHinhChinh
    RecyclerView recyclerView;
    ArrayList<DienThoai> mangDienThoai;
    DienThoaiAdapter dienThoaiAdapter;

   byte[] temp = new byte[1024];
    String filepath ;

    // Gio Hang
    public static ArrayList<GioHang> gioHangs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Anhxa();
        ActionBar();
        ActionViewFlipper();
        GetDuLieuHangDienThoai();
        /*
            viewComponentToolbar();
        */
        GetDuLieuDienThoai();
        CatchOnItemListView();

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

    private void Anhxa()  {
        toolBar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewManHinhChinh = findViewById(R.id.listview);
        navigationView = findViewById(R.id.navigationview);
        recyclerView = findViewById(R.id.recyclelistView);
        drawerLayout = findViewById(R.id.drawerlayout);

        mangHangDT = new ArrayList<>();
        mangHangDT.clear();
        hangDTAdapter = new HangDTAdapter(mangHangDT,getApplicationContext());

        listViewManHinhChinh.setAdapter(hangDTAdapter);

        mangDienThoai = new ArrayList<>();
        mangDienThoai.clear();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangDienThoai);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(dienThoaiAdapter);

        // gio hang
        // tránh việc bị mất dữ liệu khi click màn hình trang chủ thì giỏ hàng sẽ được cập nhật và cấp phát lại vùng bộ nhớ mới
        // ko null thì sẽ giữ nguyên mảng toàn cục ko cấp phát lại nữa còn có null thì cấp phát

        if(gioHangs != null)
        {

        }
        else
        {
            gioHangs = new ArrayList<>();
        }

    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://znews-photo.zadn.vn/w860/Uploaded/wyhktpu/2020_10_15/lee_4.jpg");
        mangquangcao.add("https://www.thongtincongnghe.com/sites/default/files/images/2012/1/13/img-1326420488-2.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2020/12/16/1314108/oppo-f11-son-tung-m-tp_800x450.jpg");
        mangquangcao.add("https://whathifi.vn/wp-content/uploads/2019/01/dien-thoai-oppo-r17-pro.png");
        for(int i = 0 ;  i < mangquangcao.size(); i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation  animation_slide_in  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation  animation_slide_out  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }
   private void ActionBar()
    {
        //setSupportActionBar(toolBar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void GetDuLieuHangDienThoai() {


            database = DatabaseConnection.initDatabase(this,Database_Name);
            Cursor cursor = database.rawQuery("select * from hangdt", null);

        cursor.moveToFirst();
          for (int i = 0 ; i < cursor.getCount(); i++)
          {

              cursor.moveToPosition(i);
              int id = cursor.getInt(0);
              String tenHangDT = cursor.getString(1);
              byte[] anh = cursor.getBlob(2);
              mangHangDT.add(i, new HangDT(id,tenHangDT,anh));
          }
          cursor.close();
            hangDTAdapter.notifyDataSetChanged();
            database.close();
    }
    private void viewComponentToolbar()  {

      /*  filepath = "src/main/res/drawable/house.jpg";
        File imagefile = new File(filepath);
        FileInputStream fis = new FileInputStream(imagefile);
        Bitmap bm = BitmapFactory.decodeStream(fis);


       */

        Bitmap bm = getBitMapFromURL("https://png2.cleanpng.com/sh/768b32ab7b5929822529ab8d0524fbd0/L0KzQYm3UsA2N6N5j5H0aYP2gLBuTfhwdZYyeeJqcoTwdbB7Tflkd15rhNN9LXTog7rujr1qa5DzRdVxcnn2hL7ok71pd55qReJ3Zz3pgrbsTfRwf59xh9NtLUXkR4m9WcM6PmNpetQELkezQIW5VMI6OWY2T6o9MEa8R4W3VMkveJ9s/kisspng-home-apartment-ico-flat-design-icon-christmas-home-png-free-download-5a78693962dbb9.7004242915178406974049.png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        mangHangDT.add(3,new HangDT(0,"Trang Chính",b));
            /*
            "https://png.pngtree.com/png-clipart/20201208/original/pngtree-phone-icon-in-solid-circle-png-image_5552270.jpg"
            "https://png.pngtree.com/png-clipart/20190705/original/pngtree-vector-business-man-icon-png-image_4184669.jpg"

             */
        //  mangHangDT.add(4,new HangDT(0,"Liên Hệ " , null));
        //  mangHangDT.add(5,new HangDT(0 , "Thông Tin " , null));
        hangDTAdapter.notifyDataSetChanged();

    }

    public Bitmap getBitMapFromURL(String src)
    {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream  inputStream = connection.getInputStream();
            Bitmap myBitMap = BitmapFactory.decodeStream(inputStream);
            return myBitMap;


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private void CatchOnItemListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    // trang chủ
                    case 0: {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);


                    }
                    break;
                    // Apple

                    case 1: {
                        Intent intent = new Intent(MainActivity.this, AppleActivity.class);
                        intent.putExtra("idDienThoai", mangHangDT.get(position).getIdHang());
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);


                    }
                        break;
                    // Samsung

                    case 2:
                    {
                        Intent intent = new Intent(MainActivity.this, SamSungActivity.class);
                        intent.putExtra("idDienThoai", mangHangDT.get(position).getIdHang());
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);


                    }
                        break;
                   // Oppo
                    case 3:
                    {
                        Intent intent = new Intent(MainActivity.this, OppoActivity.class);
                        intent.putExtra("idDienThoai", mangHangDT.get(position).getIdHang());
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);


                    }
                    break;

                    // liên hệ
                    case 4:
                    {
                        Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);

                    }
                    break;
                    //thông tin
                    case 5:
                    {
                        Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    break;


                }
            }
        });
    }


    private void GetDuLieuDienThoai() {
        database = DatabaseConnection.initDatabase(this,Database_Name);
        Cursor cursor = database.rawQuery("select * from DienThoai", null);
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

            mangDienThoai.add(i, new DienThoai(id,tenDienThoai,heDieuHanh,manHinh,cameraTruoc,cameraSau,Chip,Ram,boNhoTrong,PIN,hinhAnh,giaTien,gioiThieu,idHangDT));
        }
        cursor.close();
        dienThoaiAdapter.notifyDataSetChanged();
        database.close();
    }
}