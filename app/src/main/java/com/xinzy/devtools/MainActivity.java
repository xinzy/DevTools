package com.xinzy.devtools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.xinzy.devtools.model.Item;
import com.xinzy.devtools.utils.Utils;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView mRecycleView;

    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        items = Item.parse(Utils.read(this, "dataSource.json"));
    }
}
