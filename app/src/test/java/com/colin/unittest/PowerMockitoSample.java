package com.colin.unittest;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.verify;

/**
 * 简单的PowerMockito使用场景
 */
public class PowerMockitoSample {
    private MainActivity activity;
    private ImageView mockImg;
    private TextView mockTv;

    @Before
    public void setUp() {
        activity = new MainActivity();
        // 1. Mock被依赖的复杂对象。
        // MainActivity依赖了一些View，下面就是Mock出被依赖的复杂对象，并使之成为MainActivity的私有变量
        mockImg = PowerMockito.mock(ImageView.class);
        Whitebox.setInternalState(activity, "resultImg", mockImg);
        mockTv = PowerMockito.mock(TextView.class);
        Whitebox.setInternalState(activity, "resultTv", mockTv);
        Whitebox.setInternalState(activity, "loadingView", PowerMockito.mock(ProgressBar.class));
    }

    @Test
    public void test_onFail() throws Exception {
        // 2. 执行被测代码。
        // 这里要验证activity.onFail()函数
        String errorMessage = "test";
        activity.onFail(errorMessage);
        // 3. 验证逻辑是否按照预期执行/返回。
        // 这里需要验证resultImg 和 resultTv有没有按照预期进行UI状态的改变
        verify(mockImg).setImageResource(R.drawable.ic_error);
        verify(mockTv).setText(errorMessage);
    }
}