package com.example.quanlybandienthoai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.quanlybandienthoai.R;

public class LienHeActivity extends AppCompatActivity {

    Toolbar ToolbarLienHe ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        ToolbarLienHe  = findViewById(R.id.ToolbarLienHe);
        ActionBar();

    }
    private void ActionBar() {
        setSupportActionBar(ToolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ToolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
