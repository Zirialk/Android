package com.example.usuario.pr033recyclerview;

import android.os.Parcelable;


public abstract class ListItem implements Parcelable{
    static final int TYPE_HEADER = 0;
    static final int TYPE_CHILD = 1;

    public abstract int getType();
}
