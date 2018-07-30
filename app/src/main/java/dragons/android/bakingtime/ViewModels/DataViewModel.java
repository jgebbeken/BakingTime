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

    private List<Recipe> mSavedRecipeList;


    public LiveData<List<Recipe>> getRecipeList(){

        MutableLiveData<List<Recipe>> mutableRecipeList = new MutableLiveData<>();

        if(mSavedRecipeList == null) {
            mutableRecipeList = networkRepository.getJson();
        } else{
            Log.d("Data Loading: ", "From savedInstanceState");
            mutableRecipeList.setValue(mSavedRecipeList);
        }


        Log.d("Start getRecipeList: ","Before if statement");
        if(mutableRecipeList == null) {
            Log.d("ViewModel: ", "Starting getRecipeList");
            mutableRecipeList = networkRepository.getJson();
        }

        Log.d("returning: ", "Recipe List");
        return mutableRecipeList;
    }


    public void setmSavedRecipeList(List<Recipe> mSavedRecipeList) {
        this.mSavedRecipeList = mSavedRecipeList;
    }
}
