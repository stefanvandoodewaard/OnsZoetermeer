package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.ChatAdapter;

public class Chat extends AppCompatActivity
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

        drawToolbar();

        // Get RecyclerView object.
        recyclerView = findViewById(R.id.chat_recycler_messageview);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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

    private void drawToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();

        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_back);
        actionbar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Log.i("BUTTON:", "Home > Contact.");
                Intent messageContact = new Intent(this, Contact.class);
                startActivity(messageContact);
                return true;
        }
        Log.i("ACTIVITY:", "Contact created.");

        return super.onOptionsItemSelected(item);
    }
}
