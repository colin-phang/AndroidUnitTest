package com.colin.unittest;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by colin 2020-02-05.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
@SuppressStaticInitializationFor("com.colin.unittest.FileUtil")//阻止FileUtil类的静态代码块运行
public class PowerMockitoSampleIII {

    @Before
    public void setUp() throws Exception {
        //抑制Log相关代码的执行
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void test_FileUtil() {
        FileUtil.init(true);
        Assert.assertTrue(FileUtil.DEBUG);
    }
}
