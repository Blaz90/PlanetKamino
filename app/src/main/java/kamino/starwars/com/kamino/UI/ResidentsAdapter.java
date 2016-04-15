package kamino.starwars.com.kamino.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import kamino.starwars.com.kamino.R;


/**
 * Created by blazzajec on 15/04/16.
 */
public class ResidentsAdapter extends RecyclerView.Adapter<ResidentsAdapter.ChatViewHolder> {

    private List<AllResidentsActivity.ContactInfo> contactList;

    public ResidentsAdapter(List<AllResidentsActivity.ContactInfo> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ChatViewHolder contactViewHolder, int i) {
        AllResidentsActivity.ContactInfo ci = contactList.get(i);
        contactViewHolder.vFullName.setText(ci.ciResidentName);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.resident_layout_list, viewGroup, false);

        return new ChatViewHolder(itemView);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        protected TextView vFullName;

        public ChatViewHolder(View v) {
            super(v);
            vFullName =  (TextView) v.findViewById(R.id.residentName);

        }
    }
}
