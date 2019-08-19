package com.plunex.simpleprogressbutton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mNormalBtn;
    private Button mLoadingBtn;
    private Button mPassedBtn;
    private Button mRetryBtn;

    private SimpleProgressButton simpleProgressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleProgressButton = findViewById(R.id.simpleProgressButton);

        mNormalBtn = findViewById(R.id.button);
        mLoadingBtn = findViewById(R.id.button1);
        mPassedBtn = findViewById(R.id.button2);
        mRetryBtn = findViewById(R.id.button3);

        mNormalBtn.setOnClickListener(this);
        mLoadingBtn.setOnClickListener(this);
        mPassedBtn.setOnClickListener(this);
        mRetryBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int x = v.getId();
        switch (x) {
            case R.id.button:

                simpleProgressButton.showDefault();
                break;
            case R.id.button1:
                simpleProgressButton.showDefaultProgress("Posting");

                break;
            case R.id.button2:
                simpleProgressButton.showCustomMessage("Posted", R.drawable.ic_checked);
                break;

            case R.id.button3:
                simpleProgressButton.showCustomMessage("Retry", 0);
                break;

            default:
                break;
        }
    }
}
