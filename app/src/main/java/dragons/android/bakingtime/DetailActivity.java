package dragons.android.bakingtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);

                }
            }

        });



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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
