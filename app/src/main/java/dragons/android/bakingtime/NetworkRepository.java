package dragons.android.bakingtime;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import dragons.android.bakingtime.model.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepository {

    private Recipe recipe;

    MutableLiveData<List<Recipe>> mutableRecipeList = new MutableLiveData<>();


    public NetworkRepository(){

    }

    public MutableLiveData<List<Recipe>> getJson() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<List<Recipe>> call = retrofitService.getRecipeData();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
               //List<Recipe> recipes = response.body();
                mutableRecipeList.setValue(response.body());
            //    Log.d("RecipeTest: ", recipes.get(1).getName());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Network Error", "Unable to reach JSON service");
            }
        });

        return  mutableRecipeList;

    }

}
