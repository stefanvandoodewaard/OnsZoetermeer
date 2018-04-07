package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.zoetermeer.onszoetermeer.helpers.BadgeTypeConverter;
import nl.zoetermeer.onszoetermeer.helpers.DateConverter;
import nl.zoetermeer.onszoetermeer.helpers.GenderTypeConverter;
import nl.zoetermeer.onszoetermeer.helpers.RequestTypeConverter;
import nl.zoetermeer.onszoetermeer.helpers.VitalityTypeConverter;
import nl.zoetermeer.onszoetermeer.models.Achievement;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.Request;
import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.models.UserAchievements;
import nl.zoetermeer.onszoetermeer.models.UserChallenges;

@Database(entities = {
        User.class,
        Challenge.class,
        UserChallenges.class,
        Achievement.class,
        UserAchievements.class,
        Request.class},
        version = 6)
@TypeConverters({
        DateConverter.class,
        GenderTypeConverter.class,
        VitalityTypeConverter.class,
        BadgeTypeConverter.class,
        RequestTypeConverter.class})
public abstract class DummyDatabase extends RoomDatabase
{
    public abstract UserDAO userDAO();
    public abstract ChallengeDAO challengeDAO();
    public abstract UserChallengesDAO userChallengesDAO();
    public abstract AchievementDAO achievementDAO();
    public abstract UserAchievementsDAO userAchievementsDAO();
    public abstract RequestDAO requestDAO();

    private static DummyDatabase INSTANCE;

    public static DummyDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DummyDatabase.class, "DUMMY_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();
            Log.i("DATABASE:", "New instance created.");
            new PopulateDbAsync(INSTANCE).execute();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
        Log.i("DATABASE:", "Instance destroyed.");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {

        private UserDAO userDAO;
        private ChallengeDAO challengeDAO;
        private AchievementDAO achievementDAO;
        private UserAchievementsDAO userAchievementsDAO;
        private UserChallengesDAO userChallengesDAO;
        private RequestDAO requestDAO;

        PopulateDbAsync(DummyDatabase db) {
            userDAO = db.userDAO();
            challengeDAO = db.challengeDAO();
            achievementDAO = db.achievementDAO();
            userAchievementsDAO = db.userAchievementsDAO();
            userChallengesDAO = db.userChallengesDAO();
            requestDAO = db.requestDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            userDAO.deleteAll();
            User stefan = new User();
            stefan.setM_email("doodewaard@hotmail.com");
            stefan.setM_password("wachtwoord");
            stefan.setM_first_name("Stefan");
            stefan.setM_last_name("van Doodewaard");
            stefan.gender = User.Gender.Man;
            userDAO.insert(stefan);
            User kenny = new User();
            kenny.setM_email("k.dillewaard@hotmail.com");
            kenny.setM_password("wachtwoord");
            kenny.setM_first_name("Kenny");
            kenny.setM_last_name("Dillewaard");
            kenny.gender = User.Gender.Man;
            userDAO.insert(kenny);

            challengeDAO.deleteAll();
            List<Challenge> challenges = new ArrayList<Challenge>();
            String turbo_lopen_details = "Maak gedurende 3x10 tellen snelle pasjes op de plaats" +
                    "zodat je uit je hoofd gaat. Je gronding versterkt en de energie lekker door" +
                    " je lichaam stroomt. Dribbel op je tenen. Voer zo mogelijk de intensiteit op" +
                    "door je voeten van de grond te halen en je knieen hoger op te trekken. Als " +
                    "je klaar bent, laat je je bovenlichaam en hoofd even ontspannen tussen je " +
                    "benen hangen. Kom dan weer langzaam rechtop en rek je uit.";
            String kloppen_details = "Ga stevig staan. Beklop je hele lichaam liefdevol van top " +
                    "tot teen met je vingers, vuisten en met je hele hand. Begin bij je hoofd, je" +
                    "schouders, armen en handen. Verplaats je aandacht dan naar je borst en buik." +
                    "Ga dan verder met je billen en rug. Eindig met je benen en voeten. Je lichaam" +
                    "gaat tintelen en de warmtestroom komt op gang.";
            String boksen_details = "Maak boksbewegingen afwisselend met je armen en benen. " +
                    "Varieer met rechts en links. Adem uit als je een boksbeweging maakt. Het is" +
                    "nog effectiever als je er een geluid bij maakt of het woord 'weg' roept. " +
                    "Deze exercitie is ook goed om boosheid en stress los te laten.";
            String aanspannen_loslaten_details = "Deze oefening kun je liggend, zittend of staand" +
                    "doen. Span in 1 keer al je spieren in je lichaam aan inclusief je " +
                    "gezichtsspieren. Houd deze spanning tien tellen vast en adem door. Laat " +
                    "daarna de aanspanning in 1 keer los zodat al je spieren ontspannen. Herhaal" +
                    "deze oefening een paar keer.";
            String houthakken_details = "Ga stevig op de grond staan met je voeten ongeveer een" +
                    "halve meter uit elkaar. Strek beide armen met je handen in elkaar boven je " +
                    "hoofd. Buig iets achterover en laat je bovenlichaam vervolgens snel. Soepel," +
                    "met zoveel mogelijk kracht en een 'Ah'-kreet vallen met je armen tussen je " +
                    "benen door. Herhaal deze oefening 10x. Het is een goede oefening om irritatie" +
                    "boosheid, frustratie en stress los te laten.";

            challenges.add(new Challenge("Turbo lopen", turbo_lopen_details, Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Kloppen", kloppen_details, Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Boksen", boksen_details, Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Aanspannen & loslaten", aanspannen_loslaten_details, Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Houthakken", houthakken_details, Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Sudoku", null, Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Kruiswoordpuzzel", null, Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Zoek de verschillen", null, Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Memocoly", null, Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Tik de juiste volgorde", null, Challenge.VitalityType.Mentaal));
            challengeDAO.insertAll(challenges);

            achievementDAO.deleteAll();
            List<Achievement> achievements = new ArrayList<Achievement>();
            achievements.add(new Achievement(1,"50 Mentale Uitdagingen", Achievement.BadgeType.Goud));
            achievements.add(new Achievement(2,"25 Mentale Uitdagingen", Achievement.BadgeType.Zilver));
            achievements.add(new Achievement(3,"10 Mentale Uitdagingen", Achievement.BadgeType.Brons));
            achievements.add(new Achievement(4,"50 Fysieke Uitdagingen", Achievement.BadgeType.Goud));
            achievements.add(new Achievement(5,"25 Fysieke Uitdagingen", Achievement.BadgeType.Zilver));
            achievements.add(new Achievement(6,"10 Fysieke Uitdagingen", Achievement.BadgeType.Brons));
            achievementDAO.insertAll(achievements);

            User testKenny = userDAO.getByEmail("k.dillewaard@hotmail.com");
            User testStefan = userDAO.getByEmail("doodewaard@hotmail.com");
            int testIdKenny = testKenny.getId();
            int testIdStefan = testStefan.getId();

            challenges = challengeDAO.getAllChallenges();
            List<UserChallenges> userChallenges = new ArrayList<UserChallenges>();
            for(int i = 0; i < 10; i++) {
                int challengeId = challenges.get(i).getId();
                userChallenges.add(new UserChallenges(testIdKenny, challengeId, new Date()));
                userChallenges.add(new UserChallenges(testIdStefan, challengeId, new Date()));
            }
            userChallengesDAO.insertAll(userChallenges);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("DATABASE:", "Test data (re)created.");
        }
    }

}
