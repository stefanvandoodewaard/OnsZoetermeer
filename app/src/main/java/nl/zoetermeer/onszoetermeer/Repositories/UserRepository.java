package nl.zoetermeer.onszoetermeer.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Data.UserDAO;
import nl.zoetermeer.onszoetermeer.Models.User;

public class UserRepository
{
    private UserDAO userDAO;
    private DummyDatabase dummyDB;

    public UserRepository(Application application) {
        dummyDB = DummyDatabase.getDatabase(application);
        userDAO = dummyDB.userDAO();
        
    }


    public void insert(User user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void>
    {

        private UserDAO AsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
            AsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            AsyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("REPOSITORY:", "User row inserted.");
        }
    }

//    public List<User> selectAll() {
//        List<User> users = null;
//        SelectAsyncTask task = new SelectAsyncTask();
//            users =  task.get();
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
            Log.d("Select",users.size()+" row found");

//            adapter.setData(users);
//            adapter.notifyDataSetChanged();
        }
    }
}
