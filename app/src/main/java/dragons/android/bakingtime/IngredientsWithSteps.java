package dragons.android.bakingtime;

import android.arch.lifecycle.ViewModelProviders;
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
import dragons.android.bakingtime.Adapters.IngredientsWithStepsAdapter;
import dragons.android.bakingtime.model.DetailViewModel;
import dragons.android.bakingtime.model.Step;

public class IngredientsWithSteps extends Fragment implements IngredientsWithStepsAdapter.OnStepClickHandler  {

    private RecyclerView mRecyclerView;
    private IngredientsWithStepsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private  DetailViewModel model;

    public IngredientsWithSteps() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.ingredients_and_steps,container,false);

        model = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

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

        StepsDetailView stepsDetailView = new StepsDetailView();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ingredients_and_steps_container,stepsDetailView);
                transaction.addToBackStack(null);
                transaction.commit();
    }
}
