package dragons.android.bakingtime.Network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.model.Recipe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtil {

    private static final String sUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private static List<Recipe> recipeList = new ArrayList<>();


    public MutableLiveData<List<Recipe>> getRecipes(){
        if(recipes == null)        {
            getJSON();
        }

        return recipes;
    }

    public NetworkUtil(){

    }

    public void getJSON(){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(sUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    String json = response.body().string();
                    //Log.d("JSON", json);

                    // Parse the JSON
                    Gson gson = new Gson();
                    recipeList = gson.fromJson(json,
                            (new TypeToken<ArrayList<Recipe>>(){}).getType());

                }
            }
        });

        Log.d("test", "test");

        recipes.setValue(recipeList);

    }
}
