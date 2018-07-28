package dragons.android.bakingtime.Adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dragons.android.bakingtime.R;
import dragons.android.bakingtime.ViewHolders.IngredientsHeaderViewHolder;
import dragons.android.bakingtime.ViewHolders.IngredientsViewHolder;
import dragons.android.bakingtime.ViewHolders.StepsHeaderViewHolder;
import dragons.android.bakingtime.ViewHolders.StepsViewHolder;
import dragons.android.bakingtime.model.Ingredient;
import dragons.android.bakingtime.model.IngredientHeader;
import dragons.android.bakingtime.model.Step;
import dragons.android.bakingtime.model.StepsHeader;

public class IngredientsWithStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Object> sortedList;
    private OnStepClickHandler mStepHandler;
    private static final int INGREDIENTS_HEADER = 0;
    private static final int INGREDIENTS = 1;
    private static final int STEPS_HEADER = 2;
    private static final int STEPS = 3;


    public interface OnStepClickHandler{
        void onStepClick(Step step);
    }

    public void setOnStepClickHandler(OnStepClickHandler onStepClickHandler){
        this.mStepHandler = onStepClickHandler;
    }

    public IngredientsWithStepsAdapter(ArrayList<Object> sortedList){
        this.sortedList = sortedList;

    }

    @Override
    public int getItemViewType(int position){

        if(sortedList.get(position) instanceof IngredientHeader){
            return INGREDIENTS_HEADER;
        } else if(sortedList.get(position) instanceof Ingredient){
            return INGREDIENTS;
        } else if(sortedList.get(position) instanceof StepsHeader){
            return STEPS_HEADER;
        } else if (sortedList.get(position)instanceof Step){
            return STEPS;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case INGREDIENTS_HEADER:
                View ingredientHeader = inflater.inflate(R.layout.ingredients_header, parent, false);
                viewHolder = new IngredientsHeaderViewHolder(ingredientHeader);
                break;
            case INGREDIENTS:
                View ingredientsView = inflater.inflate(R.layout.ingredients_list, parent, false);
                viewHolder = new IngredientsViewHolder(ingredientsView);
                break;
            case STEPS_HEADER:
                View stepsHeader = inflater.inflate(R.layout.steps_header, parent, false);
                viewHolder = new StepsHeaderViewHolder(stepsHeader);
                break;
            default:
                View stepsView = inflater.inflate(R.layout.steps_list, parent, false);
                viewHolder = new StepsViewHolder(stepsView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case INGREDIENTS_HEADER:
                IngredientsHeaderViewHolder ingredientsHeaderViewHolder = (IngredientsHeaderViewHolder) holder;
                configIngredientsHeaderViewHolder(ingredientsHeaderViewHolder,position);
                break;
            case INGREDIENTS:
                IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
                configIngredientViewHolder(ingredientsViewHolder, position);
                break;
            case STEPS_HEADER:
                StepsHeaderViewHolder stepsHeaderViewHolder = (StepsHeaderViewHolder) holder;
                configStepsHeaderViewHolder(stepsHeaderViewHolder,position);
                break;
            case STEPS:
                StepsViewHolder stepsViewHolder = (StepsViewHolder) holder;
                configStepsViewHolder(stepsViewHolder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    private void configIngredientsHeaderViewHolder(final IngredientsHeaderViewHolder holder, int position){

        final IngredientHeader ingredientHeader = (IngredientHeader) sortedList.get(position);
        holder.getTvIngredientsHeader().setPaintFlags(holder.getTvIngredientsHeader().getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.getTvIngredientsHeader().setText(ingredientHeader.getHeader());
    }

    private void configStepsHeaderViewHolder(final StepsHeaderViewHolder holder, int position){

        final StepsHeader stepsHeader = (StepsHeader) sortedList.get(position);
        holder.getTvStepsHeader().setText(stepsHeader.getHeader());
    }

    private void configIngredientViewHolder(final IngredientsViewHolder holder, int position){
        final Ingredient ingredient = (Ingredient) sortedList.get(position);
        holder.getTvIngredients().setText(ingredient.getIngredient());
        holder.getTvQuantity().setText(String.valueOf(ingredient.getQuantity()));
        holder.getTvMeasure().setText(ingredient.getMeasure());
    }

    private void configStepsViewHolder(final StepsViewHolder holder, final int position){
        final Step step = (Step) sortedList.get(position);
        holder.getTvSteps().setText(step.getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStepHandler != null){
                    mStepHandler.onStepClick(step);
                }
            }
        });
    }
}
