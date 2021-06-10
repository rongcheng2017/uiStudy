package com.rong.cheng.uistudy;

import org.junit.Test;

/**
 * @author: frc
 * @description:
 * @date: 2021/6/8 5:00 下午
 */
class Frc {
    protected static int FLAG_DISALLOW_INTERCEPT = 0x80000;
    protected static int mGroupFlags=0x0001;
    public static void Tst() {
        mGroupFlags &= ~FLAG_DISALLOW_INTERCEPT;
        System.out.println(" "+mGroupFlags);
        int des=mGroupFlags & FLAG_DISALLOW_INTERCEPT;
        System.out.println(" "+des);
    }
}
