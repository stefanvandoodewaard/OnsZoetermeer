package nl.zoetermeer.onszoetermeer.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Data.UserDAO;
import nl.zoetermeer.onszoetermeer.Models.User;

public class UserRepository
{
    private UserDAO mUserDAO;
    private LiveData<List<User>> mAllUsers;

    public UserRepository(Application application) {
        DummyDatabase dummyDB = DummyDatabase.getDatabase(application);
        mUserDAO = dummyDB.userDAO();
        mAllUsers = mUserDAO.getAll();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert (User user) {
        new insertAsyncTask(mUserDAO).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void>
    {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
