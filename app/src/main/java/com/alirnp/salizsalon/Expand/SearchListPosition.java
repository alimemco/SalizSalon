package com.alirnp.salizsalon.Expand;


import java.util.ArrayList;

public class SearchListPosition {

    private static final int MAX_POOL_SIZE = 5;
    private static final ArrayList<SearchListPosition> sPool = new ArrayList<>(MAX_POOL_SIZE);
    public int groupPos;
    public int childPos;
    public int type;
    int flatListPos;

    public static SearchListPosition obtain(int type, int groupPos, int childPos,
                                            int flatListPos) {
        SearchListPosition slp = getRecycledOrCreate();
        slp.type = type;
        slp.groupPos = groupPos;
        slp.childPos = childPos;
        slp.flatListPos = flatListPos;
        return slp;
    }

    private static SearchListPosition getRecycledOrCreate() {
        SearchListPosition slp;
        synchronized (sPool) {
            if (sPool.size() > 0) {
                slp = sPool.remove(0);
            } else {
                return new SearchListPosition();
            }
        }
        //elp.resetState();
        return slp;
    }

}
