package com.xinzy.devtools.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.xinzy.devtools.R;
import com.xinzy.devtools.model.Tools;

/**
 * Created by Xinzy on 2017-01-11.
 *
 */
public class ToolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView title;
    private TextView name;
    private ImageView img;
    private ViewFlipper flipper;

    private String packageName;
    private Resources resources;
    private ToolAdapter.OnItemClickListener mOnItemClickListener;
    private Tools tools;
    private int position;

    public ToolViewHolder(View itemView)
    {
        super(itemView);
        Context context = itemView.getContext();
        packageName = context.getPackageName();
        resources = context.getResources();

        title = (TextView) itemView.findViewById(R.id.itemTitleText);
        name = (TextView) itemView.findViewById(R.id.itemName);
        img = (ImageView) itemView.findViewById(R.id.itemImg);
        flipper = (ViewFlipper) itemView.findViewById(R.id.itemFlipper);
        itemView.setOnClickListener(this);
    }

    public void setOnItemClickListener(ToolAdapter.OnItemClickListener l)
    {
        this.mOnItemClickListener = l;
    }

    public void setData(Tools tools, int position)
    {
        this.tools = tools;
        this.position = position;
        int titleId = resources.getIdentifier(tools.getTitle(), "string", packageName);
        if (tools.isItem())
        {
            flipper.setDisplayedChild(1);
            name.setText(titleId);
            img.setBackgroundResource(resources.getIdentifier(tools.getDrawable(),"drawable", packageName));
        } else
        {
            flipper.setDisplayedChild(0);
            title.setText(titleId);
        }
    }

    @Override
    public void onClick(View view)
    {
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onItemClick(tools, position);
        }
    }
}
