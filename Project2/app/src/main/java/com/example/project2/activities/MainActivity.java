package com.example.project2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project2.R;
import com.example.project2.data.PhoneSeeder;
import com.example.project2.entities.Phone;
import com.example.project2.models.PhoneViewModel;
import com.example.project2.views.PhoneListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private PhoneListAdapter adapter;
    private PhoneViewModel phoneViewModel;

    private final int ADD_PHONE_REQUEST_CODE = 1;
    private final int EDIT_PHONE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new PhoneListAdapter(this, phone -> {
            Intent intent = new Intent(this, AddPhoneActivity.class);
            intent.putExtra("phone", phone);
            startActivityForResult(intent, EDIT_PHONE_REQUEST_CODE);
        });
        RecyclerView recyclerView = findViewById(R.id.phoneList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        phoneViewModel.getAll().observe(this, phones -> adapter.setPhones(phones));

        FloatingActionButton fab = findViewById(R.id.addPhoneFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddPhoneActivity.class);
            startActivityForResult(intent, ADD_PHONE_REQUEST_CODE);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Phone phone = adapter.getPhoneAt(adapterPosition);
                phoneViewModel.delete(phone);
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clearAllDataItem) {
            phoneViewModel.deleteAll();
            return true;
        } else if (id == R.id.addDataItem) {
            phoneViewModel.insertAll(PhoneSeeder.getPhones());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_PHONE_REQUEST_CODE: {
                if (resultCode == RESULT_OK && data != null) {
                    Phone phone = (Phone) data.getExtras().get("phone");
                    phoneViewModel.insert(phone);
                }
                break;
            }
            case EDIT_PHONE_REQUEST_CODE: {
                if (resultCode == RESULT_OK && data != null) {
                    Phone phone = (Phone) data.getExtras().get("phone");
                    phoneViewModel.update(phone);
                }
                break;
            }
        }
    }
}