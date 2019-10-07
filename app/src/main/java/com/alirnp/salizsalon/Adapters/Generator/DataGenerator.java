package com.alirnp.salizsalon.Adapters.Generator;

import com.alirnp.salizsalon.Model.Item;
import com.alirnp.salizsalon.R;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<Item> getHomeItems() {

        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            switch (i) {

                case 0:
                    itemList.add(new Item(i, "گرفتن نوبت", R.drawable.ic_clock));
                    break;


                case 1:
                    itemList.add(new Item(i, "خدمات سالیز", R.drawable.ic_shop));
                    break;


            }
        }

        return itemList;
    }
}
