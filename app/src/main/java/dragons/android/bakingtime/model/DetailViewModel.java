package dragons.android.bakingtime.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.ListSorter;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<List<Ingredient>> mIngredientsList = new MutableLiveData<>();
    private MutableLiveData<List<Step>> mStepsList = new MutableLiveData<>();
    private Recipe recipe = new Recipe();
    private Step step;
    private ListSorter listSorter = new ListSorter();


    public void setRecipeMutableLiveData(Recipe recipe){

        mIngredientsList.setValue(recipe.getIngredients());
        mStepsList.setValue(recipe.getSteps());
        this.recipe = recipe;

    }

    public MutableLiveData<List<Ingredient>> getmIngredientsList() {
        return mIngredientsList;
    }

    public MutableLiveData<List<Step>> getmStepsList() {
        return mStepsList;
    }

    public ArrayList<Object> getRecyclerModel(){

        List<Step> steps = mStepsList.getValue();
        List<Ingredient> ingredients = mIngredientsList.getValue();
        ArrayList<Object> sortedArrayList;

        sortedArrayList = listSorter.itemOrganizer(ingredients,steps);

        return sortedArrayList;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
