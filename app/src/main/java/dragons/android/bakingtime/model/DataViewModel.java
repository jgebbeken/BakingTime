package dragons.android.bakingtime.model;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import dragons.android.bakingtime.NetworkRepository;

public class DataViewModel extends ViewModel {

    private NetworkRepository networkRepository = new NetworkRepository();

    MutableLiveData<List<Recipe>> mutableRecipeList = new MutableLiveData<>();


    public LiveData<List<Recipe>> getRecipeList(){

        mutableRecipeList = networkRepository.getJson();

        Log.d("Start getRecipeList:","Before if statement");
        if(mutableRecipeList == null) {
            Log.d("ViewModel: ", "Starting getRecipeList");
            mutableRecipeList = networkRepository.getJson();
            Log.d("ViewModel mutableList: ", mutableRecipeList.getValue().get(1).getName());
        }

        Log.d("returning", "Unknown returning of data");
        return  mutableRecipeList;
    }

}
