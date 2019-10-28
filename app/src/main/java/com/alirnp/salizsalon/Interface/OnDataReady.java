package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.Model.Service;

import java.util.ArrayList;

public interface OnDataReady {

    void onDayReceived(Day day);

    void onHourReceived(Hour hour);

    void onServicesReceived(ArrayList<Service> services);
}
