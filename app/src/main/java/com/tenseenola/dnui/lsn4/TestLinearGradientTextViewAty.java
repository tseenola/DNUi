package com.tenseenola.dnui.lsn4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tenseenola.dnui.R;
import com.tseenola.commonui.ArrowMenue;

public class TestLinearGradientTextViewAty extends AppCompatActivity {
    private Button btOpen;
    private ArrowMenue mViewById;
    private Button btClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_linear_gradient_textview);
        mViewById = findViewById(R.id.amMenu);

    }

}
