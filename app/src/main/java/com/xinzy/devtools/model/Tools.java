package com.xinzy.devtools.model;

import android.text.TextUtils;

import com.xinzy.devtools.constant.Macro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xinzy on 2017-01-09.
 *
 */
public class Tools
{
    public static final String OPEN_ACTIVITY = "OPEN_ACTIVITY";
    public static final String ASSIST = "ASSIST";

    private String title;
    private String drawable;
    private String action;
    private String target;

    private String settingName;

    private static Tools currentSelector;

    public static Tools currentSelector()
    {
        if (currentSelector == null)
        {
            currentSelector = new Tools();
        }
        return currentSelector;
    }

    public void clear()
    {
        currentSelector = null;
    }

    public void copy(Tools tools)
    {
        title = tools.title;
        drawable = tools.drawable;
        action = tools.action;
        target = tools.target;
    }

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

    public boolean isItem()
    {
        return ! TextUtils.isEmpty(drawable);
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public String getSettingName()
    {
        return settingName;
    }

    public void setSettingName(String settingName)
    {
        this.settingName = settingName;
    }

    @Override
    public String toString()
    {
        if (Macro.DEBUG)
        {
            return "Tools{" + "title='" + title + '\'' + ", drawable='" + drawable + '\'' + '}';
        } else
        {
            return "";
        }
    }

    public static List<Tools> parse(String json)
    {
        List<Tools> lists = new ArrayList<>();

        try
        {
            JSONArray array = new JSONArray(json);
            final int length = array.length();
            for (int i = 0; i < length; i++)
            {
                JSONObject obj = array.optJSONObject(i);
                Tools tools = new Tools();
                tools.title = obj.optString("title");
                tools.drawable = obj.optString("drawable");
                tools.action = obj.optString("action");
                tools.target = obj.optString("target");

                lists.add(tools);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return lists;
    }
}
