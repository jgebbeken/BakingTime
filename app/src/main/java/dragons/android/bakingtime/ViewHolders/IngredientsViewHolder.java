package dragons.android.bakingtime.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dragons.android.bakingtime.R;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvIngredients) TextView tvIngredients;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvQuantity) TextView tvQuantity;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvMeasure) TextView tvMeasure;

    public IngredientsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public TextView getTvIngredients() {
        return tvIngredients;
    }

    public TextView getTvQuantity() {
        return tvQuantity;
    }

    public TextView getTvMeasure() {
        return tvMeasure;
    }
}
