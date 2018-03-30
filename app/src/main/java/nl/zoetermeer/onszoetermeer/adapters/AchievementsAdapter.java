package nl.zoetermeer.onszoetermeer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.models.Achievement;

public class AchievementsAdapter extends RecyclerView.Adapter <AchievementsAdapter.ViewHolder>
{
    public List<Achievement> achievements;

    public AchievementsAdapter(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AchievementsAdapter.ViewHolder holder, int position) {
        holder.name.setText(achievements.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder (View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.achievement_row_name);
        }
    }
}