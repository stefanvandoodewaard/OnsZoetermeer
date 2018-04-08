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

public class ProfileAchievementsAdapter extends RecyclerView.Adapter <ProfileAchievementsAdapter.ViewHolder>
{
    public List<Achievement> achievements;
    private Drawable drawableGoldSmall;
    private Drawable drawableSilverSmall;
    private Drawable drawableBronzeSmall;

    public ProfileAchievementsAdapter(List<Achievement> achievements, Context context) {
        this.achievements = achievements;
        drawableGoldSmall = context.getResources().getDrawable( R.drawable.badge_gold_small);
        drawableSilverSmall = context.getResources().getDrawable( R.drawable.badge_silver_small);
        drawableBronzeSmall = context.getResources().getDrawable( R.drawable.badge_bronze_small);
    }

    @Override
    public ProfileAchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_row_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileAchievementsAdapter.ViewHolder holder, int position) {

        if (achievements.get(position).badgeType == Achievement.BadgeType.Goud) {
            holder.button.setCompoundDrawablesWithIntrinsicBounds(null, drawableGoldSmall, null, null);
        }
        else if (achievements.get(position).badgeType == Achievement.BadgeType.Zilver) {
            holder.button.setCompoundDrawablesWithIntrinsicBounds(null, drawableSilverSmall, null, null);
        }
        else if (achievements.get(position).badgeType == Achievement.BadgeType.Brons) {
            holder.button.setCompoundDrawablesWithIntrinsicBounds(null, drawableBronzeSmall, null, null);
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