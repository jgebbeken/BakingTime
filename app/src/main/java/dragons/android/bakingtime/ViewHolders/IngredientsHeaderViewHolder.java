package dragons.android.bakingtime.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dragons.android.bakingtime.R;

public class IngredientsHeaderViewHolder extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvIngredientsHeader) TextView tvIngredientsHeader;


    public IngredientsHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public TextView getTvIngredientsHeader() {
        return tvIngredientsHeader;
    }
}
