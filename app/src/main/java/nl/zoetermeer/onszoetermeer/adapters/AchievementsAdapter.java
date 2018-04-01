package nl.zoetermeer.onszoetermeer.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.models.Achievement;

public class AchievementsAdapter extends RecyclerView.Adapter <AchievementsAdapter.ViewHolder>
{
    public List<Achievement> achievements;
    private Drawable drawableGold;
    private Drawable drawableSilver;
    private Drawable drawableBronze;

    public AchievementsAdapter(List<Achievement> achievements, Context context) {
        this.achievements = achievements;
        drawableGold = context.getResources().getDrawable( R.drawable.badge_gold_normal );
        drawableSilver = context.getResources().getDrawable( R.drawable.badge_silver_normal);
        drawableBronze = context.getResources().getDrawable( R.drawable.badge_bronze_normal);
    }

    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AchievementsAdapter.ViewHolder holder, int position) {

        if (achievements.get(position).badgeType == Achievement.BadgeType.Goud) {
            holder.button.setCompoundDrawablesWithIntrinsicBounds(null, drawableGold, null, null);
        }
        else if (achievements.get(position).badgeType == Achievement.BadgeType.Zilver) {
            holder.button.setCompoundDrawablesWithIntrinsicBounds(null, drawableSilver, null, null);
        }
        else if (achievements.get(position).badgeType == Achievement.BadgeType.Brons) {
            holder.button.setCompoundDrawablesWithIntrinsicBounds(null, drawableBronze, null, null);
        }
        holder.button.setText(achievements.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public Button button;
        public ViewHolder (View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.badge_button_1);
        }
    }
}