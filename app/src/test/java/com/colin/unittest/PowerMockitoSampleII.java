package com.colin.unittest;

import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by colin 2020-02-05.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivity.class})
public class PowerMockitoSampleII {

    @Test
    public void test_mock() {
        //调用该对象所有方法都不会执行真实逻辑。
        MainActivity activity = PowerMockito.mock(MainActivity.class);
        //使activity的isFinishing方法总是返回true
        when(activity.isFinishing()).thenReturn(true);
        Assert.assertTrue(activity.isFinishing());
    }

    @Test
    public void test_spy() throws Exception {
        //调用该对象所有方法都会执行真实逻辑。
        MainActivity activity = PowerMockito.spy(new MainActivity());
        //使activity的isFinishing方法总是返回false
        PowerMockito.doReturn(false).when(activity).isFinishing();
        Assert.assertFalse(activity.isFinishing());
    }

    @Test
    public void test_WhiteBox() {
        MainActivity activity = PowerMockito.mock(MainActivity.class);
        TextView mockTv = PowerMockito.mock(TextView.class);
        //修改private变量
        Whitebox.setInternalState(activity, "resultTv", mockTv);
        //访问private变量
        TextView tv = Whitebox.getInternalState(activity, "resultTv");
        Assert.assertEquals(mockTv, tv);

        ImageView anotherTv = Whitebox.getInternalState(activity, "resultImg");
        Assert.assertNull(anotherTv);
    }

    @Test
    public void test_SuppressSuperDestroy() {
        //抑制父类的onDestroy方法
        Method method = PowerMockito.method(MainActivity.class.getSuperclass(), "onDestroy");
        PowerMockito.suppress(method);

        MainActivity activity = PowerMockito.spy(new MainActivity());
        activity.onDestroy();
        //获取私有变量presenter
        MainPresenter presenter = Whitebox.getInternalState(activity,"presenter");
        //activity.onDestroy()之后，presenter应该为空
        Assert.assertNull(presenter);

    }
}
