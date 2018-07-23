package dragons.android.bakingtime.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dragons.android.bakingtime.Adapters.RecipeAdapter;
import dragons.android.bakingtime.R;
import dragons.android.bakingtime.model.Recipe;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_name) TextView tvName;

    public RecipeViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);


    }

    public void bind(Recipe recipe){
        tvName.setText(recipe.getName());
    }


    public TextView getTvName(){
        return tvName;
    }



}


