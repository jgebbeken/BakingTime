package dragons.android.bakingtime.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Objects;

import dragons.android.bakingtime.Adapters.IngredientsWithStepsAdapter;
import dragons.android.bakingtime.R;
import dragons.android.bakingtime.ViewModels.DetailViewModel;
import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.Step;

import static android.support.v7.widget.RecyclerView.*;

public class IngredientsWithSteps extends Fragment implements IngredientsWithStepsAdapter.OnStepClickHandler {

    private ChangeTitle changeTitle;
    private SelectStep selectStep;
    private DetailViewModel model;
    RecyclerView mRecyclerView;
    Recipe recipe;
    private static final String LAST_POSITION = "lastPosition";
    private static final String LAST_RECIPE = "lastRecipe";

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        selectStep = (SelectStep) context;
        changeTitle = (ChangeTitle) context;
    }

    public void setSelectStep(SelectStep selectStep) {
        this.selectStep = selectStep;
    }

    public IngredientsWithSteps() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.ingredients_and_steps,container,false);

        if(savedInstanceState == null) {
            Intent intent = Objects.requireNonNull(getActivity()).getIntent();

            recipe = intent.getParcelableExtra("sentRecipe");
        } else{
            recipe = savedInstanceState.getParcelable(LAST_RECIPE);
        }
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DetailViewModel.class);
        model.setRecipeMutableLiveData(recipe);

        changeTitle.changeTitle(recipe.getName());

        mRecyclerView = rootView.findViewById(R.id.ingredients_and_steps_recyclerview);
        LayoutManager mLayoutManger = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),LinearLayoutManager.VERTICAL));
        IngredientsWithStepsAdapter mAdapter = new IngredientsWithStepsAdapter(model.getRecyclerModel());
        mAdapter.setOnStepClickHandler(this);
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null){
            int position = savedInstanceState.getInt(LAST_POSITION);
            mRecyclerView.scrollToPosition(position);
        }

        return rootView;
    }

    @Override
    public void onStepClick(Step step) {
        model.setStep(step);
        selectStep.stepSelected();

    }
    public interface ChangeTitle{
        void changeTitle(String title);
    }


    public interface SelectStep {
        void stepSelected();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        int position = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();

        outState.putInt(LAST_POSITION,position);
        outState.putParcelable(LAST_RECIPE, recipe);

    }
}
