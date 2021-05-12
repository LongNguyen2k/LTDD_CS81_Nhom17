package com.example.quanlybandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.quanlybandienthoai.R;
import com.example.quanlybandienthoai.ultil.CheckConnection;

import java.net.Inet4Address;

public class ThongTinKhachHang extends AppCompatActivity {

    static final String Database_Name = "db_qlbh.sqlite";

    SQLiteDatabase database;
    DatabaseConnection db;

    EditText editTenKH ,edtEmail , edtSoDienThoai , edtGhiChu;
    Button btnXacNhanDonHang , btnTroVe;
    String tbaoSubject = "Thông tin đơn hàng mới !";
    String tbaoThongTinDonhang = "";

    AwesomeValidation awsValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        awsValidate = new AwesomeValidation(ValidationStyle.BASIC);

        AnhXa();

        awsValidate.addValidation(ThongTinKhachHang.this,R.id.edt_TenKH,"[a-zA-Z0-9\\s]+",R.string.err_name);
        awsValidate.addValidation(ThongTinKhachHang.this,R.id.edt_SoDienThoai, RegexTemplate.TELEPHONE,R.string.err_tel);
        awsValidate.addValidation(ThongTinKhachHang.this,R.id.edt_Email , Patterns.EMAIL_ADDRESS,R.string.err_email);
        awsValidate.addValidation(ThongTinKhachHang.this,R.id.edt_GhiChu ,"[a-zA-Z0-9,.\\s]+",R.string.err_noted);

        CanCel();
        EventButtonXacNhan();


    }

    private void CanCel()
    {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void EventButtonXacNhan() {
        btnXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awsValidate.validate()) {
                    String ten = editTenKH.getText().toString().trim();
                    String sdt = edtSoDienThoai.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String ghiChu = edtGhiChu.getText().toString();


                int maDonHangInsert ;
                if(ten.length() > 0  && sdt.length() > 0 && email.length() > 0)
                {

                    tbaoThongTinDonhang += "Họ Tên Khách Hàng : " +  ten +"\n";
                    tbaoThongTinDonhang += "Số Điện Thoại  : " +  sdt +"\n";
                    tbaoThongTinDonhang +=  " Ghi Chú Khách Hang  : " + ghiChu + "\n";


                    // insert donhang
                   Boolean checkInsertData = db.insertThongTinDonHang(ten,sdt,email,ghiChu);
                    maDonHangInsert = db.getIDDonHangMoiInsert();
                    Boolean checkInsertAllDataInCTHD = false;
                    Boolean checkInsertOneDataCTHD;

                    tbaoThongTinDonhang += " Thông tin chi tiết đơn hàng ! \n";
                    // insert chitietdonhang da co idDienThoai con thieu idDonhang
                if(maDonHangInsert > 0 ) {
                    int TongTienDonHang = 0 ;
                    for (int i = 0; i < MainActivity.gioHangs.size(); i++) {

                         checkInsertOneDataCTHD = false;
                        String tenSp = MainActivity.gioHangs.get(i).getTenDienThoai();
                        int soLuong = MainActivity.gioHangs.get(i).getSoLuongDienThoai();
                        int GiaTien1SanPham = MainActivity.gioHangs.get(i).getGiaTien();
                        int idDienThoai = MainActivity.gioHangs.get(i).getIdDienThoai();

                        int idDonHang = maDonHangInsert;
                        Boolean checkInsertCTHD = db.insertThongTinChiTietDonHang(tenSp,soLuong,GiaTien1SanPham,idDonHang,idDienThoai);
                        TongTienDonHang += GiaTien1SanPham ;
                        tbaoThongTinDonhang += " Tên Điện Thoại : " + tenSp + "\n" ;
                        tbaoThongTinDonhang +=  "Số Lượng  : " + soLuong +  "cái  \n" + "Tổng Cộng " +  GiaTien1SanPham +"đ \n" ;
                        tbaoThongTinDonhang += "============= \n";

                        if(checkInsertCTHD == true)
                        {
                            Log.d("Success !", checkInsertData.toString());
                            Toast.makeText(getApplicationContext(),
                                    "Thêm Dữ Liệu 1 Sản Phẩm vào Chi Tiết Hóa Đơn Thành Công",
                                    Toast.LENGTH_SHORT).show();
                            checkInsertOneDataCTHD = true;
                        }
                        else
                        {
                            Log.d("Fail !", checkInsertData.toString());
                            Toast.makeText(getApplicationContext(),
                                    "Thêm Dữ Liệu  Chi tiết  Đơn Hàng thất bại ",
                                    Toast.LENGTH_SHORT).show();
                            checkInsertOneDataCTHD = false;
                            checkInsertAllDataInCTHD = false;
                            break;
                        }
                        checkInsertAllDataInCTHD = true;


                    }
                    tbaoThongTinDonhang +=  "Tổng Tiền Đơn Hàng :  " + TongTienDonHang + "đ  \n";



                    if (checkInsertData == true  && checkInsertAllDataInCTHD == true ) {


                        Log.d("Success !", checkInsertData.toString());
                        Toast.makeText(getApplicationContext(),
                                "Thêm Dữ Liệu Đơn Hàng Thành Công",
                                Toast.LENGTH_SHORT).show();


                        // API Gửi email
                        // truyền toàn bộ thông tin vào tbaoThongTinDonHang


                        sendEmail(email , tbaoSubject, tbaoThongTinDonhang);

                        // quay về trang chủ
                        MainActivity.gioHangs.clear();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),
                                "Mời Bạn tiếp tục mua hàng",
                                Toast.LENGTH_SHORT).show();


                    } else {
                        Log.d("Fail !", checkInsertData.toString());
                        Toast.makeText(getApplicationContext(),
                                "Thêm Dữ Liệu Đơn Hàng thất bại ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Thêm Dữ Liệu Đơn Hàng Thất bại ",
                            Toast.LENGTH_SHORT).show();
                }

                }else
                {
                    CheckConnection.showToast_Short(getApplicationContext(),"Hãy điền thông tin đầy đủ !");
                }




                }
                else
                {
                    Toast.makeText(getApplicationContext(),"EveryThing is fine" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmail(String email, String tbao, String thongBaoThongTinDonhang) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,email , tbao , thongBaoThongTinDonhang);
        javaMailAPI.execute();

    }

    private void AnhXa() {
        editTenKH = findViewById(R.id.edt_TenKH);
        edtEmail = findViewById(R.id.edt_Email);
        edtSoDienThoai = findViewById(R.id.edt_SoDienThoai);
        edtGhiChu = findViewById(R.id.edt_GhiChu);
        btnXacNhanDonHang = findViewById(R.id.buttonXacNhanDonhang);
        btnTroVe = findViewById(R.id.buttonTroVe);
        database = DatabaseConnection.initDatabase(this,Database_Name);
        db = new DatabaseConnection(getApplicationContext());
    }

}