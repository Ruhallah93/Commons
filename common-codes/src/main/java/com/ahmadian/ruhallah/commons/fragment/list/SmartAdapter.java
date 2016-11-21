package com.ahmadian.ruhallah.commons.fragment.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hsiami on 19/11/2016.
 */
public class SmartAdapter<T> extends RecyclerView.Adapter<SmartAdapter<T>.ViewHolder>{
    protected List<T> list;
    protected Context context;
    protected FactorySmartItemView factoryItemView;
    protected String searchPhrase = "";

    public SmartAdapter(Context context, List<T> list, FactorySmartItemView factory) {
        this.context = context;
        this.list = list;
        factoryItemView = factory;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(factoryItemView.makeView(searchPhrase));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        T item = list.get(position);
        holder.setItem(item, position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private SmartItemView<T> itemView;
        private T item;

        public ViewHolder(View view) {
            super(view);
            itemView = (SmartItemView<T>) view;
        }

        public void setItem(T item, int position) {
            this.item = item;
            itemView.setSearchPhrase(searchPhrase);
            itemView.setItem(item, position);
        }
    }
}
