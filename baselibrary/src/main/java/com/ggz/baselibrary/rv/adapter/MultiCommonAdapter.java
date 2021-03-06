package com.ggz.baselibrary.rv.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.ggz.baselibrary.base.ViewHolder;

import java.util.List;

/**
 * RecyclerView多种ItemViewType 的adapter
 * @author fangs
 */
public abstract class MultiCommonAdapter<Item> extends RvCommonAdapter<Item> {

    protected MultiTypeSupport<Item> mMultiTypeSupport;

    public MultiCommonAdapter(Context context, List<Item> datas,
                              MultiTypeSupport<Item> multiTypeSupport) {
        super(context, -1, datas);
        mMultiTypeSupport = multiTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = mMultiTypeSupport.getLayoutId(viewType);
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, layoutId);

        bindOnClick(viewHolder);

        return viewHolder;
    }

}
