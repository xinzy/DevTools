package com.xinzy.devtools.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinzy.devtools.R;
import com.xinzy.devtools.model.Tools;

import java.util.List;

/**
 * Created by Xinzy on 2017-01-11.
 *
 */
public class ToolAdapter extends RecyclerView.Adapter<ToolViewHolder>
{
    private LayoutInflater inflater;
    private List<Tools> tools;
    private OnItemClickListener mOnItemClickListener;

    public ToolAdapter(List<Tools> tools)
    {
        this.tools = tools;
    }

    public void setOnItemClickListener(OnItemClickListener l)
    {
        this.mOnItemClickListener = l;
    }

    @Override
    public ToolViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tools, parent, false);
        ToolViewHolder holder = new ToolViewHolder(view);
        holder.setOnItemClickListener(mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ToolViewHolder holder, int position)
    {
        Tools tool = tools.get(position);
        holder.setData(tool, position);
    }

    @Override
    public int getItemCount()
    {
        return tools == null ? 0 : tools.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(Tools tools, int position);
    }
}
