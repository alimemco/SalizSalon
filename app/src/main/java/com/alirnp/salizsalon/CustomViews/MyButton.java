package com.alirnp.salizsalon.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.MyApplication;

public class MyButton extends AppCompatButton {

    private static final int BYEKAN_FONT = 0;
    private static final int IRAN_SANS_FONT = 1;
    private static final int IRAN_SANS_BOLD_FONT = 2;


    public MyButton(Context context) {
        super(context);
        setupFont(null);
    }


    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupFont(attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupFont(attrs);
    }


    private void setupFont(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyButton);

            try {
                int Font = attributes.getInteger(R.styleable.MyButton_fontCustomButton, IRAN_SANS_FONT);

                switch (Font) {
                  /*  case BYEKAN_FONT:
                        setTypeface(MyApplication.getBYekan(getContext()));
                        break;*/

                    case IRAN_SANS_FONT:
                        setTypeface(MyApplication.getIranSans(getContext()));
                        break;

                    case IRAN_SANS_BOLD_FONT:
                        setTypeface(MyApplication.getIranSansBold(getContext()));
                        break;


                }

            } finally {
                invalidate();
                requestLayout();
                attributes.recycle();
            }
        }
    }
}