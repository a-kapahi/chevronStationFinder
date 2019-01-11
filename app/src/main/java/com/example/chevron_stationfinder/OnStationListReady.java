package com.example.chevron_stationfinder;

import com.example.chevron_stationfinder.models.Station;

import java.util.ArrayList;

public interface OnStationListReady {
    void onListReady(ArrayList<Station> stations);

    void changeAddressText(String address);

    void scrollToStation(Station station);
}
