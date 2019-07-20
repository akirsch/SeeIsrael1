package com.example.android.seeisrael.viewmodels;

import com.example.android.seeisrael.models.PlaceDetailsMainBodyResponse;
import com.example.android.seeisrael.repositories.TravelDataRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationDetailsViewModel extends ViewModel {

    private String id;
    private TravelDataRepository repository;
    private MutableLiveData<PlaceDetailsMainBodyResponse> placeDetailsMainBodyResponseMutableLiveData;

    public LocationDetailsViewModel (String id){
        this.id = id;
    }

    public void initialize(){
        if (placeDetailsMainBodyResponseMutableLiveData != null){
            return;
        }
        repository = TravelDataRepository.getInstance();
        placeDetailsMainBodyResponseMutableLiveData = repository.getPlaceDetails(id);
    }

    public LiveData<PlaceDetailsMainBodyResponse> getLocationDetailsData(){
        return placeDetailsMainBodyResponseMutableLiveData;
    }


}
