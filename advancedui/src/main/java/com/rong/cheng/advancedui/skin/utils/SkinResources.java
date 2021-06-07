package com.rong.cheng.advancedui.skin.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.appcompat.content.res.AppCompatResources;

/**
 * @author: frc
 * @description:
 * @date: 2021/6/7 6:37 下午
 */
public class SkinResources {
    private String mSkinPkgName;
    private boolean isDefaultSkin = true;
    private final Resources mAppResources;

    private Resources mSkinResources;


    private volatile static SkinResources instance;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    public void reset() {
        mSkinPkgName = "";
        mSkinResources = null;
        isDefaultSkin = true;
    }

    public void applySkin(Resources res, String pkgName) {
        mSkinResources = res;
        mSkinPkgName = pkgName;
        isDefaultSkin = TextUtils.isEmpty(pkgName) || res == null;
    }

    /**
     * 1.通过原始app中的resId(R.color.xx)获取到自己的名字
     * 2.根据名字和类型获取皮肤包中的ID号
     */
    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);

        return mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
    }


    /**
     * 输入住App id，到皮肤包中找到对应的Id
     *
     * @param resId 资源id
     * @return id
     */
    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }

        return mSkinResources.getColor(resId);
    }


    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        //通过 app的resource 获取id 对应的 资源名 与 资源类型
        //找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }

    /**
     * 可能是Color 也可能是drawable
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if ("color".equals(resourceTypeName)) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

}
