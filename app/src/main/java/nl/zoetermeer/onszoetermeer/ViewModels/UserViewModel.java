package nl.zoetermeer.onszoetermeer.ViewModels;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.Repositories.UserRepository;

public class UserViewModel extends AndroidViewModel
    {
        private UserRepository mRepository;
        private LiveData<List<User>> mAllUsers;

        public UserViewModel (Application application) {
            super(application);
            mRepository = new UserRepository(application);
            mAllUsers = mRepository.getAllUsers();
        }

        LiveData<List<User>> getAllUsers() {
            return mAllUsers;
        }

        public void insert (User user) {
            mRepository.insert(user);
        }
}
