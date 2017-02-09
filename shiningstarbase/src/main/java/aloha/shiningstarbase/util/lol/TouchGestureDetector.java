package aloha.shiningstarbase.util.lol;

import android.content.Context;
import android.view.GestureDetector;

/**
 * Created by roly on 16/7/1.
 */
public class TouchGestureDetector extends GestureDetector {

    public TouchGestureDetector(OnGestureListener listener) {
        super(listener);
    }

    public TouchGestureDetector(Context context, OnGestureListener listener) {
        super(context, listener);
    }

    public static class B{

    }

}
