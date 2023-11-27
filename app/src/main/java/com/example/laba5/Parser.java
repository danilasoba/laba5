package com.example.laba5;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<ListItemClass> parseXmlData(Document doc) {
        List<ListItemClass> dataList = new ArrayList<>();
        Elements tables = doc.getElementsByTag("tbody");
        Element our_table = tables.get(0);
        Elements elements_from_table = our_table.children();
        for (int i = 0; i < elements_from_table.size(); i++) {
            ListItemClass items = new ListItemClass();
            items.setData_1(elements_from_table.get(i).child(0).text());
            items.setData_2(elements_from_table.get(i).child(1).text());
            items.setData_3(elements_from_table.get(i).child(2).text().substring(0, 7));
            items.setData_4(elements_from_table.get(i).child(3).text());
            dataList.add(items);
        }
        return dataList;
    }
}
