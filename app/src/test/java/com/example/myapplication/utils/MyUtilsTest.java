package com.example.myapplication.utils;

import android.graphics.Bitmap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by junsuk on 2017. 4. 3..
 */
public class MyUtilsTest {
    Bitmap bitmap;
    @Before
    public void setUp() throws Exception {
        // 비트맵 생성
        bitmap = null;
    }

    @After
    public void tearDown() throws Exception {
        // 메모리 해제
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Test
    public void sum() throws Exception {
        int result = MyUtils.sum(10, 20);
        // case1
        Assert.assertEquals(30, result);
        // case2
        Assert.assertNotEquals(29, result);
    }

}