package com.example.android.seeisrael.viewmodels;

import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.repositories.TravelDataRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TownListFragmentViewModel extends ViewModel {

    private MutableLiveData<TownQueryMainBodyResponse> townResponseMutableLiveData;
    private TravelDataRepository travelDataRepository;

    public void initialize(){
        if (townResponseMutableLiveData != null){
            return;
        }

        travelDataRepository = TravelDataRepository.getInstance();
        townResponseMutableLiveData = travelDataRepository.getAllTowns();
    }

    public LiveData<TownQueryMainBodyResponse> getTownData(){

        return townResponseMutableLiveData;
    }
}
