

package com.alirnp.salizsalon.Test;

public abstract class AbstractDataProvider {

    public abstract int getCount();

    public abstract Data getItem(int index);

    public abstract void removeItem(int position);

    public abstract void moveItem(int fromPosition, int toPosition);

    public abstract void swapItem(int fromPosition, int toPosition);

    public abstract int undoLastRemoval();

    public static abstract class Data {
        public abstract long getId();

        public abstract boolean isSectionHeader();

        public abstract int getViewType();

        public abstract String getText();

        public abstract String setUrl();

        public abstract String getUrl();

        public abstract boolean isPinned();

        public abstract void setPinned(boolean pinned);
    }
}
