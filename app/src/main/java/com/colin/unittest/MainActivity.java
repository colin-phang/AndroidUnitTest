package com.colin.unittest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private MainPresenter presenter;
    private ProgressBar loadingView;
    private ImageView resultImg;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_get).setOnClickListener(this);
        loadingView = findViewById(R.id.loading_view);
        resultImg = findViewById(R.id.img_result);
        resultTv = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get) {
            showLoading();
            presenter.requestIPInfo();
        }
    }

    @Override
    public void onIpInfoResponse(String json) {
        Log.d(TAG, "onIpInfoResponse: " + json);
        if (TextUtils.isEmpty(json)) {
            resultImg.setImageResource(R.drawable.ic_error);
            resultTv.setText("Json is null");
        } else {
            resultImg.setImageResource(R.drawable.ic_ok);
            resultTv.setText(json);
        }
        hideLoading();
    }

    @Override
    public void onFail(String errorMsg) {
        Log.d(TAG, "onFail: " + errorMsg);
        resultImg.setImageResource(R.drawable.ic_error);
        resultTv.setText(errorMsg);
        hideLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    private void showLoading() {
        resultImg.setVisibility(View.GONE);
        resultTv.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        resultTv.setVisibility(View.VISIBLE);
        resultImg.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }
}
