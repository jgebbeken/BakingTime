package dragons.android.bakingtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.DetailActivity;
import dragons.android.bakingtime.R;
import dragons.android.bakingtime.ViewHolders.RecipeViewHolder;
import dragons.android.bakingtime.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();
    private Context context;
    private boolean mWidget = false;
    private OnWidgetSelectorHandler mWidgetHandler;

    public RecipeAdapter(List<Recipe> recipes, Context context) {

        if(recipes != null) {
            this.recipes = recipes;
            this.context = context;
        }
    }

    public interface OnWidgetSelectorHandler{
        void onWidgetSelectorClick(Recipe recipe);
    }


    public void updateAdapter(List<Recipe> incoming){
        this.recipes.clear();
        this.recipes.addAll(incoming);
        notifyDataSetChanged();

    }

    public void fromWidget(boolean mWidget){
        this.mWidget = mWidget;
    }

    public void setmWidgetHandler(OnWidgetSelectorHandler clickHandler){
        mWidgetHandler = clickHandler;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.recipe_list,parent,false);
        viewHolder = new RecipeViewHolder(recipeView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;
        configRecipeViewHolder(recipeViewHolder,position);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }



    private void configRecipeViewHolder(RecipeViewHolder holder, final int position){
        final Recipe recipe = recipes.get(position);

        holder.getTvName().setText(recipe.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicking works Row : ", String.valueOf(position));

                if(!mWidget) {
                    Recipe selectedRecipe = recipes.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("sentRecipe", selectedRecipe);
                    context.startActivity(intent);
                } else{
                    Log.d("From ", "Widget");
                    mWidgetHandler.onWidgetSelectorClick(recipe);

                }
            }
        });


    }

}
