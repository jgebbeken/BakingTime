package dragons.android.bakingtime;

import android.app.PictureInPictureParams;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Rational;
import android.view.MenuItem;

import java.util.Objects;

import dragons.android.bakingtime.Fragments.IngredientsWithSteps;
import dragons.android.bakingtime.Fragments.StepsDetailView;
import dragons.android.bakingtime.ViewModels.DetailViewModel;
import dragons.android.bakingtime.model.Step;

public class DetailActivity extends AppCompatActivity implements IngredientsWithSteps.SelectStep, IngredientsWithSteps.ChangeTitle{

    private boolean mTwoPane;
    private final IngredientsWithSteps ingredientsWithSteps = new IngredientsWithSteps();
    private final FragmentManager fragmentManager =  getSupportFragmentManager();
    private static final String SAVED_STEP = "savedStep";
    private static final String WHEN_READY = "whenReady";


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


    // For Android Oreo. Shows entire activity in PiP but not the video player. Will need to make
    // button so that it will just do the video player only. Will follow up after application passes
    // required steps


    /*@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        PackageManager pm = getApplicationContext().getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Rational aspectRatio = new Rational(
                        16, 9);
                PictureInPictureParams params = new PictureInPictureParams.Builder()
                        .setAspectRatio(aspectRatio)
                        .build();
                enterPictureInPictureMode(params);
            }



        }
    }*/



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DetailViewModel detailViewModel = new DetailViewModel();
        ViewModelProviders.of(this).get(DetailViewModel.class);
        Step step = savedInstanceState.getParcelable(SAVED_STEP);
        detailViewModel.setStep(step);

        boolean playWhenReady = savedInstanceState.getBoolean(WHEN_READY);
        Bundle bundle = new Bundle();

        bundle.putBoolean(WHEN_READY,playWhenReady);
    }
}
