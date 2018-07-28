package dragons.android.bakingtime.ViewModels;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import dragons.android.bakingtime.Network.NetworkRepository;
import dragons.android.bakingtime.model.Recipe;

public class DataViewModel extends ViewModel {

    private final NetworkRepository networkRepository = new NetworkRepository();


    public LiveData<List<Recipe>> getRecipeList(){

        MutableLiveData<List<Recipe>> mutableRecipeList = networkRepository.getJson();

        Log.d("Start getRecipeList:","Before if statement");
        if(mutableRecipeList == null) {
            Log.d("ViewModel: ", "Starting getRecipeList");
            mutableRecipeList = networkRepository.getJson();
        }

        Log.d("returning", "Unknown returning of data");
        return mutableRecipeList;
    }

}
