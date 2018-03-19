package nl.zoetermeer.onszoetermeer.activities;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;

public class Challenges extends Activity
{

    ListView challengeListView;
    List<String> ChallengeList;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.list_challenges);

//        ListView challengeListView = (ListView) findViewById(R.id.challenges_list);
//
//        ChallengeRepository repository = new ChallengeRepository(getApplication());
//
//
//        ListView challengesListView = (ListView)findViewById(R.id.challenges_list);
//        List<Challenge> challengeList = repository.getMentalChallenges();
//
//
//        ArrayAdapter<Challenge> arrayAdapter =
//                new ArrayAdapter<Challenge>(this,android.R.layout.simple_list_item_1, challengeList);
//        // Set The Adapter
//        challengesListView.setAdapter(arrayAdapter);


    }


}
