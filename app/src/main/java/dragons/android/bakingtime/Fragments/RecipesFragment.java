package dragons.android.bakingtime.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dragons.android.bakingtime.Adapters.RecipeAdapter;
import dragons.android.bakingtime.R;
import dragons.android.bakingtime.ViewModels.DataViewModel;
import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.SavedRecipeList;

public class RecipesFragment extends Fragment {


    private RecipeAdapter mAdapter;
    private DataViewModel model;
    private List<Recipe> recipeList = new ArrayList<>();
    public RecipesFragment() {

    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.recipe_recycleview, container, false);

        if(getActivity() != null) {
            model = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        }


        if(savedInstanceState != null) {
            SavedRecipeList savedRecipeList;
            savedRecipeList = savedInstanceState.getParcelable("savedData");


            List<Recipe> savedRecipes = new ArrayList<>(Objects.requireNonNull(savedRecipeList).getSavedRecipe());
            model.setmSavedRecipeList(savedRecipes);


        }

        recipeList = new ArrayList<>();

        RecyclerView recyclerView = rootView.findViewById(R.id.recipe_name_recycleview);
        int mSpanCount = 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mSpanCount);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new RecipeAdapter(recipeList, getActivity());
        recyclerView.setAdapter(mAdapter);


        model.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeList.clear();
                recipeList = recipes;
                mAdapter.updateAdapter(recipeList);
            }
        });




        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        List<Recipe> recipes;
        SavedRecipeList savedRecipeList = new SavedRecipeList();

        //noinspection unchecked
        recipes = model.getRecipeList().getValue();
        savedRecipeList.setSavedRecipe(recipes);
        bundle.putParcelable("savedData",savedRecipeList);
    }




}
