package nl.zoetermeer.onszoetermeer.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.models.User;

public class UserRepository
{
    private UserDAO userDAO;
    private DummyDatabase dummyDB;
    private List<User> users;

    public UserRepository(Application application) {
        dummyDB = DummyDatabase.getDatabase(application);
        userDAO = dummyDB.userDAO();
    }

    private void setUsers(List<User> users){
        this.users = users;
        Log.i("TEST:", "setUsers()");
    }

    public List<User> getUsers(){
        SelectAsyncTask task = new SelectAsyncTask();
        task.execute();

        Log.i("TEST:", "getUsers()");
        Log.d("TEST",users.size()+" in List<User>");
        return users;

    }


    public void insert(User user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    private class insertAsyncTask extends AsyncTask<User, Void, Void>
    {

        private UserDAO AsyncTaskDao;
        private List<User> users;

        insertAsyncTask(UserDAO dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            AsyncTaskDao.insert(params[0]);

            users = dummyDB.userDAO().getAll();


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.i("Select",users.size()+" row found");

            Log.i("REPOSITORY:", "User row inserted.");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("REPOSITORY:", "insertAsyncTask cancelled");
        }
    }

//    public List<User> selectAll() {
//        List<User> users = null;
//        SelectAsyncTask task = new SelectAsyncTask();
//            users =  task.execute().get();
//
//
//        return users;
//    }

    private class SelectAsyncTask extends AsyncTask<Void,Integer,List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            return dummyDB.userDAO().getAll();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            setUsers(users);
            Log.d("Select",users.size()+" row found");

//            adapter.setData(users);
//            adapter.notifyDataSetChanged();
        }
    }
}
