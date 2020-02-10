package com.colin.unittest;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by colin on 2020-01-25.
 */
public class MainPresenter {
    private OkHttpClient client = new OkHttpClient();
    private final View view;
    private Handler handler = new Handler();

    public MainPresenter(View view) {
        this.view = view;
    }

    public void requestIPInfo() {
        Request request = new Request.Builder()
                .url("http://ipinfo.io/?token=dc23883befb5a6")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.onFail(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            view.onIpInfoResponse(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            view.onFail(e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public interface View {
        void onIpInfoResponse(String json);

        void onFail(String errorMessage);
    }
}
