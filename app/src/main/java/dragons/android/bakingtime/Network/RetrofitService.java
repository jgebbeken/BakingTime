package dragons.android.bakingtime.Network;

import java.util.List;

import dragons.android.bakingtime.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

interface RetrofitService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipeData();
}
