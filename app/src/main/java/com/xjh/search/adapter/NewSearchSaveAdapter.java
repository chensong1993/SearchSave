package com.xjh.search.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xjh.search.R;
import com.xjh.search.db.SearchName;

import java.util.List;


public class NewSearchSaveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SearchName> Newslist;
    private Context context;

    public NewSearchSaveAdapter(Context context, List<SearchName> Newlist) {
        this.context = context;
        this.Newslist = Newlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_search_save, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mViewHolder = (ViewHolder) holder;
        mViewHolder.setItem(Newslist.get(position));
        mViewHolder.refreshView();

    }


    @Override
    public int getItemCount() {
        return Newslist == null ? 0 : Newslist.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvtitleContent;
        private SearchName items;

        public ViewHolder(final View item) {
            super(item);

            mTvtitleContent = item.findViewById(R.id.tv_search_saves);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        public void setItem(SearchName item) {
            this.items = item;
        }

        void refreshView() {

            mTvtitleContent.setText(items.getName());
        }
    }


    public void changeData(List<SearchName> newsList) {
        Newslist = newsList;
        notifyDataSetChanged();

    }


}