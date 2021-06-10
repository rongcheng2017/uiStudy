package com.rong.cheng.nestedscroll.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

/**
 * @author: frc
 * @description:
 * @date: 2021/6/10 4:55 下午
 */
public class FViewPager extends ViewPager {
    public FViewPager(@NonNull @NotNull Context context) {
        super(context);
    }

    public FViewPager(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
