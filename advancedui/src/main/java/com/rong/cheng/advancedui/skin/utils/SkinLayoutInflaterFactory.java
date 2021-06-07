package com.rong.cheng.advancedui.skin.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @author: frc
 * @description: 用来接管系统的View的生产过程
 * @date: 2021/6/7 7:20 下午
 */
class SkinLayoutInflaterFactory implements LayoutInflater.Factory2 {


    private static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    private SkinAttribute mSkinAttribute;

    private Activity activity;

    public SkinLayoutInflaterFactory(SkinAttribute iSkinAttribute, Activity iActivity) {
        mSkinAttribute = iSkinAttribute;
        activity = iActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Constructor<? extends View> constructor = findConstructor(context, name);
        try {
            return constructor.newInstance(constructor, attrs);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException iE) {
            iE.printStackTrace();
        }
        return null;
    }

    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (constructor == null) {
            try {
                Class<? extends View> clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = clazz.getConstructor(mConstructorSignature);
                sConstructorMap.put(name, constructor);

            } catch (ClassNotFoundException | NoSuchMethodException iE) {
                iE.printStackTrace();
            }
        }
        return constructor;
    }
}
