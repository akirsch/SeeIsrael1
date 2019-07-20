package com.example.android.seeisrael.viewmodels;

import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.repositories.TravelDataRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryLocationListViewModel extends ViewModel {

    private String id;
    private String category;
    private final int API_RESULT_LIMIT = 50;
    private TravelDataRepository travelDataRepository;
    private MutableLiveData<TownQueryMainBodyResponse> locationsListResponseMutableLiveData;

    public CategoryLocationListViewModel(String id, String category){
        this.id = id;
        this.category = category;


    }

    public void initialize(){

        if (locationsListResponseMutableLiveData != null) {
            return;
        }

        travelDataRepository = TravelDataRepository.getInstance();
        locationsListResponseMutableLiveData =
                travelDataRepository.getCategorySpecificLocationsList(id, category, API_RESULT_LIMIT);
    }

    public LiveData<TownQueryMainBodyResponse> getListOfLocationsData(){
        return locationsListResponseMutableLiveData;
    }
}
