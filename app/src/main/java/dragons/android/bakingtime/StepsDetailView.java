package dragons.android.bakingtime;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dragons.android.bakingtime.model.DetailViewModel;

public class StepsDetailView extends Fragment {

    DetailViewModel model;

    TextView test;
    public StepsDetailView(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.steps_detail_view, container, false);
        model = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

        Log.d("test StepDetailView: ", model.getStep().getDescription());




        return rootView;
    }
}
