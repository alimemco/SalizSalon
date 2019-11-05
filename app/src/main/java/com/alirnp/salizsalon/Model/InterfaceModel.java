package com.alirnp.salizsalon.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alirnp.salizsalon.Interface.OnLoginUser;
import com.alirnp.salizsalon.Interface.OnLogoutUser;

public class InterfaceModel implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<InterfaceModel> CREATOR = new Parcelable.Creator<InterfaceModel>() {
        @Override
        public InterfaceModel createFromParcel(Parcel in) {
            return new InterfaceModel(in);
        }

        @Override
        public InterfaceModel[] newArray(int size) {
            return new InterfaceModel[size];
        }
    };
    private OnLoginUser onLoginUser;
    private OnLogoutUser onLogoutUser;

    public InterfaceModel(OnLoginUser onLoginUser) {
        this.onLoginUser = onLoginUser;
    }

    public InterfaceModel(OnLogoutUser onLogoutUser) {
        this.onLogoutUser = onLogoutUser;
    }

    public InterfaceModel() {
    }

    protected InterfaceModel(Parcel in) {
        onLoginUser = (OnLoginUser) in.readValue(OnLoginUser.class.getClassLoader());
        onLogoutUser = (OnLogoutUser) in.readValue(OnLogoutUser.class.getClassLoader());
    }

    public OnLoginUser getOnLoginUser() {
        return onLoginUser;
    }

    public void setOnLoginUser(OnLoginUser onLoginUser) {
        this.onLoginUser = onLoginUser;
    }

    public OnLogoutUser getOnLogoutUser() {
        return onLogoutUser;
    }

    public void setOnLogoutUser(OnLogoutUser onLogoutUser) {
        this.onLogoutUser = onLogoutUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(onLoginUser);
        dest.writeValue(onLogoutUser);
    }
}