package com.example.project2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project2.entities.Phone;
import com.example.project2.repositories.PhoneRepository;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository phoneRepository;
    private final LiveData<List<Phone>> allPhones;

    public PhoneViewModel(Application application) {
        super(application);
        phoneRepository = new PhoneRepository(application);
        allPhones = phoneRepository.getAll();
    }

    public LiveData<List<Phone>> getAll() {
        return allPhones;
    }

    public void deleteAll() {
        phoneRepository.deleteAll();
    }

    public void insertAll(List<Phone> phones) {
        phoneRepository.insertAll(phones);
    }

    public void insert(Phone phone) {
        phoneRepository.insert(phone);
    }

    public void update(Phone phone) {
        phoneRepository.update(phone);
    }

    public void delete(Phone phone) {
        phoneRepository.delete(phone);
    }
}
