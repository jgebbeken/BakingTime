package dragons.android.bakingtime;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dragons.android.bakingtime.Adapters.IngredientsWithStepsAdapter;
import dragons.android.bakingtime.model.DetailViewModel;
import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.Step;

public class IngredientsWithSteps extends Fragment implements IngredientsWithStepsAdapter.OnStepClickHandler {

    private RecyclerView mRecyclerView;
    private IngredientsWithStepsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private SelectStep selectStep;
    private DetailViewModel model;
    Recipe recipe;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        selectStep = (SelectStep) context;
    }

    public void setSelectStep(SelectStep selectStep) {
        this.selectStep = selectStep;
    }

    public IngredientsWithSteps() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.ingredients_and_steps,container,false);
        recipe = getActivity().getIntent().getParcelableExtra("sentRecipe");
        model = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);
        model.setRecipeMutableLiveData(recipe);

        mRecyclerView = rootView.findViewById(R.id.ingredients_and_steps_recyclerview);
        mLayoutManger = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        mAdapter = new IngredientsWithStepsAdapter(model.getRecyclerModel(),getActivity());
        mAdapter.setOnStepClickHandler(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onStepClick(int position, Step step) {
        Log.d("Fragment onClick"," was Clicked");
        Log.d("Step: ", step.getDescription());
        model.setStep(step);
        selectStep.stepSelected();

    }


    public interface SelectStep {
        public void stepSelected();
    }






}
