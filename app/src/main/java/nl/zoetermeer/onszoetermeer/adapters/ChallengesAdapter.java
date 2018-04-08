package nl.zoetermeer.onszoetermeer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.models.Challenge;

public class ChallengesAdapter extends RecyclerView.Adapter <ChallengesAdapter.ViewHolder>
{
    public List<Challenge> challenges;

    public ChallengesAdapter(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    @Override
    public ChallengesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChallengesAdapter.ViewHolder holder, int position) {
        holder.name.setText(challenges.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder (View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.challenge_row_name);
        }
    }
}

