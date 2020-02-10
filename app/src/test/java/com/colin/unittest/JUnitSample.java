package com.colin.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by colin on 2020-01-29.
 */
//指定一个Runner来提供测试代码运行的环境
@RunWith(JUnit4.class)
public class JUnitSample {
    Object object;

    //初始化方法，通常进行用于测试的前置条件/依赖对象的初始化
    @Before
    public void setUp() throws Exception {
        object = new Object();
    }

    //测试方法，必须是public void
    @Test
    public void test() {
        Assert.assertNotNull(object);
    }
}
