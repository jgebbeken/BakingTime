package dragons.android.bakingtime;

import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.MimeTypeMap;

import dragons.android.bakingtime.model.DetailViewModel;
import dragons.android.bakingtime.model.Recipe;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Recipe recipe;
        recipe = getIntent().getParcelableExtra("sentRecipe");

        DetailViewModel detailViewModel = ViewModelProviders.of(this)
                .get(DetailViewModel.class);

        detailViewModel.setRecipeMutableLiveData(recipe);

        IngredientsWithSteps ingredientsWithSteps = new IngredientsWithSteps();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.ingredients_and_steps_container, ingredientsWithSteps)
                .addToBackStack(null)
                .commit();


        Log.d("DetailActivity: ", recipe.getName());

        String test = MimeTypeMap.getFileExtensionFromUrl(recipe.getSteps().get(0).getVideoURL());

        Log.d("MimeType test: ", test);

    }
}
