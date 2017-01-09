package com.xinzy.devtools.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YANGSHAOZENG227 on 2017-01-09.
 */
public class Item
{
    private String title;
    private String drawable;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDrawable()
    {
        return drawable;
    }

    public void setDrawable(String drawable)
    {
        this.drawable = drawable;
    }

    public static List<Item> parse(String json)
    {
        List<Item> lists = new ArrayList<>();

        try
        {
            JSONArray array = new JSONArray(json);
            final int length = array.length();
            for (int i = 0; i < length; i++)
            {
                JSONObject obj = array.getJSONObject(i);
                Item item = new Item();
                item.title = obj.optString("title");
                item.drawable = obj.getString("drawable");
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return lists;
    }
}
