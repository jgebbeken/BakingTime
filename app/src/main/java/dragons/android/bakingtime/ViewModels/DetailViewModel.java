package dragons.android.bakingtime.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.ListSorter;
import dragons.android.bakingtime.model.Ingredient;
import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.Step;

public class DetailViewModel extends ViewModel {

    private final MutableLiveData<List<Ingredient>> mIngredientsList = new MutableLiveData<>();
    private final MutableLiveData<List<Step>> mStepsList = new MutableLiveData<>();
    private Step step;
    private final ListSorter listSorter = new ListSorter();


    public void setRecipeMutableLiveData(Recipe recipe){

        mIngredientsList.setValue(recipe.getIngredients());
        mStepsList.setValue(recipe.getSteps());

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

    public void setStep (Step step) {
        this.step = step;
    }

}
