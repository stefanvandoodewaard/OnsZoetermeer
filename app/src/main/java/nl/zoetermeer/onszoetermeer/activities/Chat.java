package nl.zoetermeer.onszoetermeer.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.ChatAdapter;

public class Chat extends Base
{
    private List<ChatMessage> chatMessageList;
    private RecyclerView recyclerView;
    private ChatAdapter chatArrayAdapter;
    private ArrayList<String> chatMessagesSocialWorker;

    public Chat()
    {
        chatMessagesSocialWorker = new ArrayList<>();
        chatMessagesSocialWorker.add("Ja, dat kan");
        chatMessagesSocialWorker.add("Naar welk adres wilt u het laten versturen");
        chatMessagesSocialWorker.add("Om hoeveel maaltijden gaat het?");
        chatMessagesSocialWorker.add("Heeft u verder nog vragen?");
        chatMessagesSocialWorker.add("Nee dat kan niet");
        chatMessagesSocialWorker.add("Tot straks");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get RecyclerView object.
        recyclerView = findViewById(R.id.chat_recycler_messageview);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Create the initial data list.
        chatMessageList = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(true, "Hoe kan ik u helpen?");
        chatMessageList.add(chatMessage);

        // Create the data adapter with above data list.
        chatArrayAdapter = new ChatAdapter(chatMessageList);

        // Set data adapter to RecyclerView.
        recyclerView.setAdapter(chatArrayAdapter);
    }

    @Override
    public boolean useToolbar()
    {
        return false;
    }

    public void SendChatMessage(View view)
    {
        final EditText chatText = findViewById(R.id.msg);

        String msgContent = chatText.getText().toString();
        if (!TextUtils.isEmpty(msgContent))
        {
            // Add a new sent message to the list.
            ChatMessage chatMessageUser = new ChatMessage(false, msgContent);
            chatMessageList.add(chatMessageUser);

            int currentIndex = chatMessageList.size();
            if (currentIndex % 2 == 0)
            {
                ChatMessage chatMessage = new ChatMessage(true, chatMessagesSocialWorker.get(0));
                chatMessageList.add(chatMessage);
                chatMessagesSocialWorker.remove(0);
            }

            int newMsgPosition = chatMessageList.size() - 1;

            // Notify recycler view insert one new data.
            chatArrayAdapter.notifyItemInserted(newMsgPosition);

            // Scroll RecyclerView to the last message.
            recyclerView.scrollToPosition(newMsgPosition);

            // Empty the input edit text box.
            chatText.setText("");
        }
    }
}
