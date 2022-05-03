package com.example.project2.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2.dao.IPhoneDao;
import com.example.project2.entities.Phone;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneDatabase extends RoomDatabase {
    private static final String DB_NAME = "phone_db";
    private static final int THREAD_COUNT = 4;
    private static volatile PhoneDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);

        databaseWriteExecutor.execute(() -> {
            IPhoneDao dao = INSTANCE.getPhoneDao();
            dao.insertAll(PhoneSeeder.getPhones());
        });
      }
    };
    public static synchronized PhoneDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhoneDatabase.class, DB_NAME)
                    .addCallback(roomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract IPhoneDao getPhoneDao();
}
