package com.colin.unittest;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

/**
 * MainActivity测试用例
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivity.class, Log.class})
public class MainActivityTest {
    private MainActivity activity;
    private ProgressBar loadingView;
    private ImageView resultImg;
    private TextView resultTv;
    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        activity = PowerMockito.spy(new MainActivity());
        loadingView = mockPrivateFiled(activity, "loadingView", ProgressBar.class);
        resultImg = mockPrivateFiled(activity, "resultImg", ImageView.class);
        resultTv = mockPrivateFiled(activity, "resultTv", TextView.class);
        presenter = mockPrivateFiled(activity, "presenter", MainPresenter.class);
        PowerMockito.doReturn(false).when(activity, "isFinishing");
//        when(activity.isFinishing()).thenReturn(false);
        System.out.println(activity.isFinishing());
        //抑制Log相关代码的执行
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void test_loading() throws Exception {
        callPrivateMethod(activity, "showLoading");
        verify(resultImg).setVisibility(View.GONE);
        verify(resultTv).setVisibility(View.GONE);
        verify(loadingView).setVisibility(View.VISIBLE);

        callPrivateMethod(activity, "hideLoading");
        verify(resultImg).setVisibility(View.VISIBLE);
        verify(resultTv).setVisibility(View.VISIBLE);
        verify(loadingView).setVisibility(View.GONE);
    }

    @Test
    public void test_onClick() throws Exception {
        View mockView = PowerMockito.mock(View.class);
        when(mockView.getId()).thenReturn(R.id.btn_get);

        activity.onClick(mockView);

        verifyPrivate(activity).invoke("showLoading");
        verify(presenter).requestIPInfo();
    }

    @Test
    public void test_onIpInfoResponse() throws Exception {
        activity.onIpInfoResponse("");
        verify(resultImg).setImageResource(R.drawable.ic_error);
        verify(resultTv).setText("Json is null");
        verifyPrivate(activity).invoke("hideLoading");

        Mockito.reset(activity, resultImg, resultTv);

        String testResult = "你好hello안녕하세요";
        activity.onIpInfoResponse(testResult);
        verify(resultImg).setImageResource(R.drawable.ic_ok);
        verify(resultTv).setText(testResult);
        verifyPrivate(activity).invoke("hideLoading");
    }

    @Test
    public void test_onFail() throws Exception {
        String errorMessage = "test";
        activity.onFail(errorMessage);
        verify(resultImg).setImageResource(R.drawable.ic_error);
        verify(resultTv).setText(errorMessage);
        verifyPrivate(activity).invoke("hideLoading");
    }

    @Test
    public void test_onDestroy() {
        MainActivity activity = PowerMockito.spy(new MainActivity());
        Method method = PowerMockito.method(MainActivity.class.getSuperclass(), "onDestroy");
        //抑制父类的onDestroy方法
        PowerMockito.suppress(method);

        activity.onDestroy();
        //获取私有变量presenter
        MainPresenter presenter = Whitebox.getInternalState(activity,"presenter");
        //activity.onDestroy()之后，presenter应该为空
        Assert.assertNull(presenter);

    }

    /**
     * mock私有变量的对象
     *
     * @param instance  被修改对象
     * @param fieldName 变量名
     * @param clazz     变量值的class对象
     * @return mock出来的私有变量的对象
     */
    public static final <T> T mockPrivateFiled(Object instance, String fieldName, Class<T> clazz) {
        T value = PowerMockito.mock(clazz);
        Whitebox.setInternalState(instance, fieldName, value);
        return value;
    }


    /**
     * 调用私有方法
     *
     * @param instance   被修改对象
     * @param methodName 方法名
     * @param args       形参列表
     */
    public static final Object callPrivateMethod(Object instance, String methodName, Object... args) throws Exception {
        return Whitebox.invokeMethod(instance, methodName, args);
    }
}