package com.xinzy.devtools;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;

import com.xinzy.devtools.adapter.ToolAdapter;
import com.xinzy.devtools.model.Tools;
import com.xinzy.devtools.services.ToolsService;
import com.xinzy.devtools.utils.Logger;
import com.xinzy.devtools.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ToolAdapter.OnItemClickListener
{
    private static final int DEFAULT_SPAN_SIZE = 4;

    private RecyclerView mRecycleView;

    private List<Tools> tools;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tools = Tools.parse(Utils.read(this, "dataSource.json"));
        Logger.d("tools = " + tools);
        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        GridLayoutManager manager = new GridLayoutManager(this, DEFAULT_SPAN_SIZE);
        manager.setSpanSizeLookup(new SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                return tools.get(position).isItem() ? 1 : DEFAULT_SPAN_SIZE;
            }
        });
        mRecycleView.setLayoutManager(manager);
        ToolAdapter adapter = new ToolAdapter(tools);
        adapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Tools tools, int position)
    {
        if (tools != null && tools.isItem())
        {
            if (Tools.OPEN_ACTIVITY.equals(tools.getAction()))
            {
                startActivity(new Intent(tools.getTarget()));
            } else if (Tools.ASSIST.equals(tools.getAction()))
            {
                if (checkServiceStart())
                {
                    Tools.currentSelector().copy(tools);
                    Tools.currentSelector().setSettingName(getString(getResources().getIdentifier(tools.getTitle(), "string", getPackageName())));
                    startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                }
            }
        }
    }

    private boolean checkServiceStart()
    {
        if (ToolsService.isServiceStart(this))
        {
            return true;
        }
        String msg = getString(R.string.accessibility_permission, getString(R.string.accessibility_label));
        new AlertDialog.Builder(this).setTitle(R.string.accessibility_apply).setCancelable(true)
                .setMessage(msg).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                    }
                }).create().show();
        return false;
    }

}
