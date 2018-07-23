package dragons.android.bakingtime.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import dragons.android.bakingtime.R;
import butterknife.BindView;

public class StepsViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tvSteps) TextView tvSteps;

    public StepsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public TextView getTvSteps() {
        return tvSteps;
    }
}
