package com.example.project2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.entities.Phone;

import java.util.Collection;
import java.util.List;

@Dao
public interface IPhoneDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone phone);

    @Query("SELECT * FROM phones")
    LiveData<List<Phone>> getAll();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertAll(Collection<Phone> phones);

    @Query("DELETE FROM phones")
    void deleteAll();

    @Update
    void update(Phone phone);

    @Delete
    void delete(Phone phone);
}
