package com.tenseenola.dnui.lsn4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tenseenola.dnui.R;
import com.tseenola.commonui.ArrowMenue;

public class TestArrowMenueAty extends AppCompatActivity implements View.OnClickListener {
    private Button btOpen;
    private ArrowMenue mViewById;
    private Button btClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_arrow_menue);
        mViewById = findViewById(R.id.amMenu);
        btOpen = findViewById(R.id.btOpen);
        btClose = findViewById(R.id.btClose);
        btClose.setOnClickListener(this);
        btOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btOpen:
                mViewById.open();
                break;
            case R.id.btClose:
                mViewById.close();
                break;
            default:
                break;
        }

    }
}
