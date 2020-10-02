package com.wordpress.smartedudotin.www.newsfakeornot;

import android.graphics.drawable.Drawable;

public class NewsProgressBar {
    private Drawable mDrawable;

    public NewsProgressBar(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }
}
