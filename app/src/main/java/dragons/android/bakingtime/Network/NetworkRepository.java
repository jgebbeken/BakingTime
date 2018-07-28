package dragons.android.bakingtime.Network;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import dragons.android.bakingtime.Network.RetrofitService;
import dragons.android.bakingtime.model.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepository {

    @SuppressWarnings("WeakerAccess")
    final MutableLiveData<List<Recipe>> mutableRecipeList = new MutableLiveData<>();


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
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mutableRecipeList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d("Network Error", "Unable to reach JSON service");
            }
        });

        return  mutableRecipeList;

    }

}
