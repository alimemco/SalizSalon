package com.alirnp.salizsalon.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;

public class MyEditText extends AppCompatEditText {

    private static final int BYEKAN_FONT = 0;
    private static final int IRAN_SANS_FONT = 1;
    private static final int IRAN_SANS_BOLD_FONT = 2;


    public MyEditText(Context context) {
        super(context);
        setupFont(null);
    }


    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupFont(attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupFont(attrs);
    }

    private void setupFont(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyEdiText);

            try {
                int Font = attributes.getInteger(R.styleable.MyEdiText_fontCustomEdiText, IRAN_SANS_FONT);

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
