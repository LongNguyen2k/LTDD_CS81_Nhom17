package com.example.quanlybandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.quanlybandienthoai.R;

public class ThongBaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        TextView textView = findViewById(R.id.textViewThongBao);

        String message = getIntent().getStringExtra("message");
        textView.setText(message);


    }
}