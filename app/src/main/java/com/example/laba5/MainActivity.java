package com.example.laba5;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataLoader.DataLoaderListener {

    private ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItemClass> arrayList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        listView = findViewById(R.id.listView);
        searchEditText = findViewById(R.id.searchEditText);
        arrayList = new ArrayList<>();
        adapter = new CustomArrayAdapter(this, R.layout.list_item_1, arrayList, getLayoutInflater());
        listView.setAdapter(adapter);

        // Set up text change listener for search
        // Set up text change listener for search
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the list based on the search input
                filterCurrencies(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Handle updates when deleting symbols in the search text
                filterCurrencies(editable.toString());
            }
        });


        // Execute DataLoader to load data
        new DataLoader(this).execute();
    }

    private List<ListItemClass> originalList;

    private void filterCurrencies(String filter) {
        List<ListItemClass> filteredList = new ArrayList<>();

        if (originalList == null) {
            // Save the original list on the first filtering
            originalList = new ArrayList<>(arrayList);
        }

        if (filter.isEmpty()) {
            // If the search text is empty, show the original list
            adapter.setData(originalList);
        } else {
            // Filter the list based on the search input
            for (ListItemClass item : originalList) {
                if (item.getData_1().toLowerCase().contains(filter.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            adapter.setData(filteredList);
        }
    }





    @Override
    public void onDataLoaded(List<ListItemClass> data) {
        arrayList.clear();
        arrayList.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
