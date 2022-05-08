package com.example.project2.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project2.dao.IPhoneDao;
import com.example.project2.data.PhoneDatabase;
import com.example.project2.entities.Phone;

import java.util.List;

public class PhoneRepository {
    private IPhoneDao phoneDao;
    private LiveData<List<Phone>> allPhones;

    public PhoneRepository(Application application) {
        PhoneDatabase database = PhoneDatabase.getInstance(application);
        phoneDao = database.getPhoneDao();
        allPhones = phoneDao.getAll();
    }

    public LiveData<List<Phone>> getAll() {
        return allPhones;
    }

    public void insert(Phone phone) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insert(phone));
    }

    public void insertAll(List<Phone> phones) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insertAll(phones));
    }

    public void deleteAll() {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.deleteAll());
    }
}
