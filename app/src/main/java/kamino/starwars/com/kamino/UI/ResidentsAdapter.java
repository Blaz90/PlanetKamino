package kamino.starwars.com.kamino.UI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import kamino.starwars.com.kamino.R;


/**
 * Created by blazzajec on 15/04/16.
 */
public class ResidentsAdapter extends RecyclerView.Adapter<ResidentsAdapter.ViewHolder> {

    private List<ResidentListActivity.ContactInfo> contactList;

    public ResidentsAdapter(List<ResidentListActivity.ContactInfo> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder contactViewHolder, int i) {
        contactViewHolder.bind(contactList.get(i));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.resident_layout_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vFullName;
        protected ImageView vImageView;

        public ViewHolder(View v) {
            super(v);
            vFullName =  (TextView) v.findViewById(R.id.residentName);
            vImageView = (ImageView) itemView.findViewById(R.id.residentImage);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("OnClick", "blabla" + getItemViewType());
        }

        public void bind(final ResidentListActivity.ContactInfo item) {
            vFullName.setText(item.ciResidentName);
            Picasso.with(itemView.getContext()).load(item.ciResidentUrl).into(vImageView);
        }
    }
}
