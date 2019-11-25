package com.alirnp.salizsalon.Expand;


import com.alirnp.salizsalon.Utils.Constants;

import java.util.List;

public class GroupHelper {

    private List<? extends GroupList> groupList;

    public GroupHelper(List<? extends GroupList> groupList) {
        this.groupList = groupList;
    }


    private int numberOfItemsInGroup(int groupIndex) {
        return groupList.get(groupIndex).getItemCount() + 1;
    }


    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < groupList.size(); i++) {
            count += numberOfItemsInGroup(i);
        }

        return count;
    }


    public SearchListPosition getUnflattenedPosition(int flPos) {
        int groupItemCount;
        int adapted = flPos;
        System.out.println("gp list " + groupList.size());
        for (int i = 0; i < groupList.size(); i++) {

            groupItemCount = numberOfItemsInGroup(i);
            if (adapted == 0) {
                return SearchListPosition.obtain(Constants.state.HEADER.getStatus(), i, -1, flPos);
            } else if (adapted < groupItemCount) {
                return SearchListPosition.obtain(Constants.state.MAIN.getStatus(), i, adapted - 1, flPos);
            }
            adapted -= groupItemCount;
        }
        throw new RuntimeException("Unknown state");
    }


    public GroupList getGroup(SearchListPosition listPosition) {
        return groupList.get(listPosition.groupPos);
    }

}
