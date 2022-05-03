package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project2.data.PhoneSeeder;
import com.example.project2.entities.Phone;
import com.example.project2.models.PhoneViewModel;
import com.example.project2.views.PhoneListAdapter;


public class MainActivity extends AppCompatActivity {
    private PhoneListAdapter adapter;
    private PhoneViewModel phoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new PhoneListAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.phoneList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        phoneViewModel.getAll().observe(this, phones -> adapter.setPhones(phones));
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
}