package nl.zoetermeer.onszoetermeer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.helpers.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{
    private List<ChatMessage> chatMessageList;

    public ChatAdapter(List<ChatMessage> chatMessageList)
    {
        this.chatMessageList = chatMessageList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ChatMessage chatMessage = this.chatMessageList.get(position);

        if (chatMessage != null)
        {
            if (chatMessage.left)
            {
                holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
                holder.bind(chatMessage);
                holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
            }
            else
            {
                holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
                holder.bind(chatMessage);
                holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_chat_app_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        if (chatMessageList == null)
        {
            chatMessageList = new ArrayList<>();
        }

        return this.chatMessageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout leftMsgLayout;
        LinearLayout rightMsgLayout;
        TextView timeTextLeft, timeTextRight, nameText, leftMessage, rightMessage;

        public ViewHolder(View itemView)
        {
            super(itemView);

            leftMsgLayout = itemView.findViewById(R.id.chat_left_msg_layout);
            nameText = itemView.findViewById(R.id.text_message_name);
            leftMessage = itemView.findViewById(R.id.chat_left_msg_text_view);
            timeTextLeft = itemView.findViewById(R.id.text_message_time_left);

            rightMsgLayout = itemView.findViewById(R.id.chat_right_msg_layout);
            rightMessage = itemView.findViewById(R.id.chat_right_msg_text_view);
            timeTextRight = itemView.findViewById(R.id.text_message_time_right);
        }

        void bind(ChatMessage message)
        {

            if (leftMessage != null)
            {
                leftMessage.setText(message.message);
                timeTextLeft.setText(message.getCreatedAt());
            }

            if (rightMessage != null)
            {
                rightMessage.setText(message.message);
                timeTextRight.setText(message.getCreatedAt());
            }

            nameText.setText(message.getSender());
        }
    }
}
