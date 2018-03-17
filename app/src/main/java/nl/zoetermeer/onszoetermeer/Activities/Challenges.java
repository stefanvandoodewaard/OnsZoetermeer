package nl.zoetermeer.onszoetermeer.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.Models.Challenge;
import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.Repositories.ChallengeRepository;

public class Challenges extends Activity
{

    ListView challengeListView;
    List<String> ChallengeList;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.list_challenges);

        ListView challengeListView = (ListView) findViewById(R.id.challenges_list);

//        ChallengeRepository repository = new ChallengeRepository(getApplication());
//        Challenge test = new Challenge();
//        test.setName("test");
//        test.vitalityType = Challenge.VitalityType.Mentaal;
//        repository.insert(test);
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
