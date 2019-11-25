package com.alirnp.salizsalon.Expand;

import com.alirnp.salizsalon.NestedJson.Item;

import java.util.ArrayList;
import java.util.List;

public class GroupList extends ArrayList {

    private String title;
    private List<Item> child;

    public GroupList(String title, List<Item> child) {
        this.title = title;
        this.child = child;
    }

    public String getTitle() {
        return title;
    }

    public List<Item> getChild() {
        return child;
    }

    public int getItemCount() {
        return child == null ? 0 : child.size();
    }
}
