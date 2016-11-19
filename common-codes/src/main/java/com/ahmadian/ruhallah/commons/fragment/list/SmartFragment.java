package com.ahmadian.ruhallah.commons.fragment.list;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmadian.ruhallah.commons.R;
import com.ahmadian.ruhallah.commons.widgets.LoadingMaster;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by hsiami on 19/11/2016.
 */
public abstract class SmartFragment<T> extends Fragment {

    public static final String PARAM_SEARCH_PHRASE = "search_phrase";

    protected SuperRecyclerView recyclerView;

    public static final int ITEM_PER_PAGE = 20;
    public static final int START_PAGE = 1;

    protected LoadingMaster loadingView;
    protected List<T> list = new ArrayList<>(0);
    protected SmartAdapter<T> adapter;
    protected boolean reachedEnd = false;
    protected String searchPhrase = "";
    private ListLoader loader;
    protected int columnCount = 1;
    protected String tagName = "fragment base";
    protected View headerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchPhrase = getArguments().getString(PARAM_SEARCH_PHRASE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingView = new LoadingMaster(getActivity());
        loadingView.setContentView(R.layout.fragment_base);
        loadingView.setOnRetryListener(new LoadingMaster.OnRetryListener() {
            @Override
            public void onRetry() {
                reachedEnd = false;
                new ListLoader(START_PAGE, ITEM_PER_PAGE, true).execute();
            }
        });

        recyclerView = (SuperRecyclerView) loadingView.findViewById(R.id.super_recyclerview);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reachedEnd = false;
                new ListLoader(START_PAGE, ITEM_PER_PAGE, true).execute();
            }
        });

        recyclerView.setRefreshingColorResources(R.color.amber_A700, R.color.blue_A400
                , R.color.red_A400, R.color.light_blue_A400);

        recyclerView.setupMoreListener(
                new OnMoreListener() {
                    @Override
                    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                        if (!reachedEnd) {
                            recyclerView.getMoreProgressView().setVisibility(View.VISIBLE);
                            new ListLoader((numberOfItems / numberBeforeMore) + 1, ITEM_PER_PAGE, false).execute();
                        } else {
                            recyclerView.hideMoreProgress();
                        }
                    }
                }
                , ITEM_PER_PAGE);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        recyclerView.getRecyclerView().setItemAnimator(new SlideInLeftAnimator());
        adapter = new SmartAdapter<>(getContext(), list, getViewFactory());
        adapter.setSearchPhrase(searchPhrase);
        recyclerView.setAdapter(getAdapterAnimator());

        return loadingView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    @Override
    public void onDestroy() {
        cancelLoad();
        super.onDestroy();
    }

    public void setColumnCount(int columnCount) {
        if (columnCount < 1) {
            columnCount = 1;
        }

        this.columnCount = columnCount;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        recyclerView.setAdapter(getAdapterAnimator());
        adapter.notifyDataSetChanged();
    }

    private AnimationAdapter getAdapterAnimator() {
        AlphaInAnimationAdapter adapterAnimation = new AlphaInAnimationAdapter(adapter);
        adapterAnimation.setDuration(100);
//        adapterAnimation.setFirstOnly(false);
        return adapterAnimation;
    }

    public void setRefreshing(boolean enabled) {
        recyclerView.getSwipeToRefresh().setEnabled(enabled);
    }

    protected void loadData() {
        cancelLoad();
        loader = new ListLoader(START_PAGE, ITEM_PER_PAGE, true);
        loader.execute();
    }

    private void cancelLoad() {
        if (loader != null) {
            loader.cancel(true);
        }
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(int resId) {
        this.headerView = View.inflate(getContext(), resId, null);
        addHeaderView();
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        addHeaderView();
    }

    private void addHeaderView() {
        AppBarLayout appBarLayout = (AppBarLayout) loadingView.findViewById(R.id.header_view);
        appBarLayout.removeAllViews();
        appBarLayout.addView(headerView, 0);
    }

    protected LoadingMaster getLoadingView() {
        return loadingView;
    }

//    protected abstract int getFragmentLayoutId();

    protected abstract FactorySmartItemView getViewFactory();
    protected abstract List<T> fetchMoreDataAsync(int startPage, int count);
    protected abstract void onOperationFailed();

    protected void preExecute(boolean refresh) {
        if (refresh) {
            loadingView.loadingStart();
        }
        recyclerView.showMoreProgress();
    }

    protected void postExecute(List<T> response, boolean refresh) {
        if (response == null) {
            if (list.size() == 0) {
                loadingView.loadingFailed(false);
                onOperationFailed();
            } else {
                adapter.notifyDataSetChanged();
                loadingView.loadingEnd();
            }
            reachedEnd = true;
            return;
        }

        if (refresh) {
            list.clear();
        }

        if (response.size() != ITEM_PER_PAGE) {
            reachedEnd = true;
        }

        list.addAll(response);
        loadingView.loadingEnd();
        adapter.notifyDataSetChanged();
    }

    private class ListLoader extends AsyncTask<Void, Void, List<T>> {
        int count;
        int startPage;
        boolean refresh;

        public ListLoader(int startPage, int count, boolean refresh) {
            this.count = count;
            this.startPage = startPage;
            this.refresh = refresh;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            preExecute(refresh);
        }

        @Override
        protected List<T> doInBackground(Void... voids) {
            return fetchMoreDataAsync(startPage, count);
        }

        @Override
        protected void onPostExecute(List<T> response) {
            super.onPostExecute(response);
            postExecute(response, refresh);
        }
    }
}
