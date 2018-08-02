package dragons.android.bakingtime;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.Adapters.RecipeAdapter;
import dragons.android.bakingtime.ViewModels.DataViewModel;
import dragons.android.bakingtime.model.Ingredient;
import dragons.android.bakingtime.model.Recipe;

/**
 * The configuration screen for the {@link IngredientWidget IngredientWidget} AppWidget.
 */
public class IngredientWidgetConfigureActivity extends AppCompatActivity implements RecipeAdapter.OnWidgetSelectorHandler {

    private static final String PREFS_NAME = "dragons.android.bakingtime.IngredientWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private RecipeAdapter mAdapter;
    private List<Recipe> recipeList = new ArrayList<>();

    public IngredientWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    private static void saveIngredientsPref(Context context, int appWidgetId, List<Ingredient> ingredients) {

        Gson gson = new Gson();
        String json = gson.toJson(ingredients);
        Log.d("JSON from SP", json);

        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, json);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadIngredientsPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String json = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (json != null) {
            return json;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        Log.d("Widget", "onCreate");

        setContentView(R.layout.ingredient_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        DataViewModel model = ViewModelProviders.of(this).get(DataViewModel.class);


        recipeList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.widget_recycleview);
        int mSpanCount = 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, mSpanCount);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new RecipeAdapter(recipeList, this);
        mAdapter.setmWidgetHandler(this);
        mAdapter.fromWidget(true);
        recyclerView.setAdapter(mAdapter);


        model.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeList.clear();
                recipeList = recipes;
                mAdapter.updateAdapter(recipeList);
            }
        });

    }

    @Override
    public void onWidgetSelectorClick(Recipe recipe){

        final Context context = IngredientWidgetConfigureActivity.this;

        List<Ingredient> ingredients = recipe.getIngredients();


        // When the button is clicked, store the string locally
        // String widgetText = mAppWidgetText.getText().toString();
         saveIngredientsPref(context, mAppWidgetId, ingredients);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        IngredientWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }
}

