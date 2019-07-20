package com.example.android.seeisrael.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryLocationListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String id;
    private final String category;

    public CategoryLocationListViewModelFactory (String id, String category){

        this.id = id;
        this.category = category;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new CategoryLocationListViewModel(id, category);

    }

}
