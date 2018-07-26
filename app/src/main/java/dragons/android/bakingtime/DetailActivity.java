package dragons.android.bakingtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.MimeTypeMap;

import dragons.android.bakingtime.model.DetailViewModel;
import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.Step;

public class DetailActivity extends AppCompatActivity implements IngredientsWithSteps.SelectStep{

    private boolean mTwoPane;
    Recipe recipe;
    IngredientsWithSteps ingredientsWithSteps = new IngredientsWithSteps();
    final FragmentManager fragmentManager =  getSupportFragmentManager();
    DetailViewModel detailViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailViewModel detailViewModel = ViewModelProviders.of(this)
                .get(DetailViewModel.class);
        ingredientsWithSteps.setSelectStep(this);


        if(findViewById(R.id.tablet_ingredients_steps) != null){
            mTwoPane = true;
        } else {
            mTwoPane = false;
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients_and_steps_container, ingredientsWithSteps)
                    .addToBackStack(null)
                    .commit();
        }


    }


    @Override
    public void stepSelected() {

        StepsDetailView stepsDetailView = new StepsDetailView();
        if(mTwoPane){

            fragmentManager.beginTransaction()
                    .replace(R.id.tablet_step_detail, stepsDetailView)
                    .commit();

        }else{
           fragmentManager.beginTransaction()
                   .replace(R.id.ingredients_and_steps_container, stepsDetailView)
                   .addToBackStack(null)
                   .commit();
        }

    }
}
