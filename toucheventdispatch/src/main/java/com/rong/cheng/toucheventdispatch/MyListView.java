package com.rong.cheng.toucheventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 内部拦截法：子view处理事件冲突
//    private int mLastX, mLastY;
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                int deltaX = x - mLastX;
//                int deltaY = y - mLastY;
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                break;
//
//            }
//            default:
//                break;
//        }
//
//        mLastX = x;
//        mLastY = y;
//        return super.dispatchTouchEvent(event);
//    }

    private int mLastX, mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }


        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
