package kamino.starwars.com.kamino.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kamino.starwars.com.kamino.R;


/**
 * Created by blazzajec on 15/04/16.
 */
public class ResidentsAdapter extends RecyclerView.Adapter<ResidentsAdapter.ViewHolder> {

    private List<ResidentListActivity.ResidentInfo> mResidentList;
    private ArrayList mResidentsIds;
    private String mPlanetName;

    public ResidentsAdapter(List<ResidentListActivity.ResidentInfo> mResidentList, ArrayList residentIds, String planetName) {
        this.mResidentList = mResidentList;
        mResidentsIds = residentIds;
        mPlanetName = planetName;
    }

    @Override
    public int getItemCount() {
        return mResidentList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder contactViewHolder, int i) {
        contactViewHolder.bind(mResidentList.get(i));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.resident_layout_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vFullName;
        protected ImageView vImageView;

        public ViewHolder(View v) {
            super(v);
            vFullName =  (TextView) v.findViewById(R.id.residentName);
            vImageView = (ImageView) v.findViewById(R.id.residentImage);

            v.setOnClickListener(this);
        }

        // onClick go to ResidentDetailsActivity
        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, ResidentDetailsActivity.class);
            intent.putExtra("residentIds", mResidentsIds);
            intent.putExtra("planetName", mPlanetName);
            intent.putExtra("position", getAdapterPosition());
            context.startActivity(intent);
        }

        public void bind(final ResidentListActivity.ResidentInfo item) {
            vFullName.setText(item.ciResidentName);
            Picasso.with(itemView.getContext()).load(item.ciResidentUrl).into(vImageView);
        }
    }
}
