package dragons.android.bakingtime;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import dragons.android.bakingtime.model.DataViewModel;
import dragons.android.bakingtime.model.Recipe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataViewModel mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        RecipesFragment recipesFragment = new RecipesFragment();

        // Fragment manager to add in new fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipesFragment)
                .commit();
    }
}
