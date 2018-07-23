package dragons.android.bakingtime;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.Adapters.RecipeAdapter;
import dragons.android.bakingtime.model.DataViewModel;
import dragons.android.bakingtime.model.Recipe;

public class RecipesFragment extends Fragment {



    private RecyclerView recyclerView;
    private RecipeAdapter mAdapter;
    private DataViewModel model;
    private List<Recipe> recipeList = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private final int mSpanCount = 1;

    public RecipesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.recipe_recycleview, container, false);


        model = ViewModelProviders.of(getActivity()).get(DataViewModel.class);

        recipeList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recipe_name_recycleview);
        gridLayoutManager = new GridLayoutManager(getContext(),mSpanCount);
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



}
