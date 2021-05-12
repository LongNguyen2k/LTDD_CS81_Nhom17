package com.example.quanlybandienthoai.activity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection extends SQLiteOpenHelper {
    static final String Database_Name = "db_qlbh.sqlite";
    static final int Database_version = 1;

     static final  String Table_Name = "donhang";
     static final String Collumn_ID = "idDonHang";
     static final String Collumn_TenKH = "TenKH";
     static final String Collumn_SDT = "SDT";
     static final String Collumn_Email = "Email";
     static final String Collumn_GhiChu = "GhiChu";


    public DatabaseConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseConnection(Context context) {
        super(context, Database_Name,null,Database_version);


    }

    public static SQLiteDatabase initDatabase(Activity activity, String databaseName){
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if(!f.exists()) {
                InputStream e = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();


            }
            // nếu database đã có tồn tại thì xóa database cũ và tạo lại database mới dựa trên file db_qlbh.sqlite
            // đây là cách để update database
            else {

               // activity.deleteDatabase(databaseName);
              // initDatabase(activity, databaseName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("");
    }

    public Boolean insertThongTinDonHang(String tenKH , String SDT , String Email , String ghiChu)
    {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenKH",tenKH);
        contentValues.put("SDT",SDT);
        contentValues.put("Email",Email);
        contentValues.put("GhiChu",ghiChu);

        long result = DB.insert("donhang",  null, contentValues);

        DB.close();
        if (result == -1)
            return false;

        else
            return true;


    }
    public int getIDDonHangMoiInsert()
    {   int MaDonHangMoiInsert = 0 ;
        int temp = 0 ;
        // get data from database
        String queryString = "Select * From  donhang";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                temp = cursor.getInt(0);
               if(MaDonHangMoiInsert < temp)
                   MaDonHangMoiInsert = temp;



            }while (cursor.moveToNext());

        }else
        {

        }
        if(MaDonHangMoiInsert == 0 )
            MaDonHangMoiInsert = 1;

        db.close();
        cursor.close();
        return MaDonHangMoiInsert;
    }

    public Boolean insertThongTinChiTietDonHang(String TenSanPham , int SoLuong , int GiaTien1SanPham , int idDonHang , int idDienThoai)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenSP",TenSanPham);
        contentValues.put("SoLuong",SoLuong);
        contentValues.put("TongTien",GiaTien1SanPham);
        contentValues.put("idDH",idDonHang);
        contentValues.put("idDT",idDienThoai);

        long result = DB.insert("cthd",  null, contentValues);
        DB.close();
        if (result == -1)
            return false;

        else
            return true;
    }
}
