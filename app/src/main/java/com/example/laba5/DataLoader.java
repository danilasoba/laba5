package com.example.laba5;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader extends AsyncTask<Void, Void, List<ListItemClass>> {

    private DataLoaderListener listener;

    public DataLoader(DataLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<ListItemClass> doInBackground(Void... voids) {
        try {
            Document doc = Jsoup.connect("https://minfin.com.ua/currency/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element our_table = tables.get(0);
            Elements elements_from_table = our_table.children();
            List<ListItemClass> dataList = new ArrayList<>();
            for (int i = 0; i < elements_from_table.size(); i++) {
                ListItemClass items = new ListItemClass();
                items.setData_1(elements_from_table.get(i).child(0).text());
                items.setData_2(elements_from_table.get(i).child(1).text());
                items.setData_3(elements_from_table.get(i).child(2).text().substring(0, 7));
                items.setData_4(elements_from_table.get(i).child(3).text());
                dataList.add(items);
            }
            return dataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ListItemClass> result) {
        if (result != null) {
            listener.onDataLoaded(result);
        }
    }

    public interface DataLoaderListener {
        void onDataLoaded(List<ListItemClass> data);
    }
}
