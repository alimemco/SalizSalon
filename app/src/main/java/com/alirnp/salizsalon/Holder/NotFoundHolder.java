package com.alirnp.salizsalon.Holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.R;

public class NotFoundHolder extends RecyclerView.ViewHolder {

    MyTextView notFountTxt;

    public NotFoundHolder(@NonNull View itemView) {
        super(itemView);
        notFountTxt = itemView.findViewById(R.id.rv_not_found_tv);
        notFountTxt.setText(R.string.itemNotFound);
    }
}