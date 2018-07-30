package dragons.android.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Objects;

import dragons.android.bakingtime.Fragments.IngredientsWithSteps;
import dragons.android.bakingtime.Fragments.StepsDetailView;

public class DetailActivity extends AppCompatActivity implements IngredientsWithSteps.SelectStep, IngredientsWithSteps.ChangeTitle{

    private boolean mTwoPane;
    private final IngredientsWithSteps ingredientsWithSteps = new IngredientsWithSteps();
    private final FragmentManager fragmentManager =  getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsWithSteps.setSelectStep(this);

        if(findViewById(R.id.tablet_ingredients_steps) != null){
            mTwoPane = true;
        } else {
            mTwoPane = false;

            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                    if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    } else {
                        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                        getSupportActionBar().setHomeButtonEnabled(false);
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);

                    }
                }

            });

            if(savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.ingredients_and_steps_container, ingredientsWithSteps)
                        .addToBackStack(null)
                        .commit();
            }
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

        if(!mTwoPane){
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
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void changeTitle(String title) {
        if(getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(title);
        }
    }
}
