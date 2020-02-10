package com.colin.unittest;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * 测试代码需要运行在真机/模拟器上，
 * 运行过程中可以看到自动拉起MainActivity，
 * 并且自动点击了id为btn_get的按钮，
 * 然后loading结束后，检查到id为img_result正在展示中，
 * 符合预期，整个测试用例就执行成功了。
 * <p>
 * Created by colin on 2020-01-29.
 */
//使用Espresso提供的AndroidJUnit4运行测试代码
@RunWith(AndroidJUnit4.class)
public class EspressoSample {

    // 利用Espresso提供的ActivityTestRule拉起MainActivity
    @Rule
    public ActivityTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testNoContentView() throws Exception {
        //withId函数会返回一个ViewMatchers对象，用于查找id为R.id.btn_get的view
        onView(withId(R.id.btn_get))
                //click函数会返回一个ViewActions对象，用于发出点击事件
                .perform(click());

        //通过定时轮询loadingView是否展示中，来判断异步接口请求是否完成
        View loadingView = mIntentsRule.getActivity().findViewById(R.id.loading_view);
        while (true) {
            Thread.sleep(1000);
            if (loadingView.getVisibility() == View.GONE) {
                break;
            }
        }

        //请求请求完成后，检查UI状态
        //找到R.id.img_result的view
        onView(withId(R.id.img_result))
                //matches函数会返回一个ViewAssertions对象，检查这个view的某个状态是否符合预期
                .check(matches(isDisplayed()));
    }
}
