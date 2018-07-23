package dragons.android.bakingtime.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dragons.android.bakingtime.R;


public class StepsHeaderViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tvStepsHeader) TextView tvStepsHeader;


    public StepsHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }


    public TextView getTvStepsHeader() {
        return tvStepsHeader;
    }
}



