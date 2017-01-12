package com.xinzy.devtools.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Xinzy on 2017-01-09.
 *
 */
public class Utils
{

    public static String read(@NonNull Context context, String name)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            InputStream is = context.getAssets().open(name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
        } catch (IOException e)
        {
            Logger.e("Read assets fail", e);
        }

        return sb.toString();
    }
}
