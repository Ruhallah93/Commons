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
import com.ahmadian.ruhallah.commons.base.SmartAsyncTask;
import com.ahmadian.ruhallah.commons.widgets.SmartLoadingView;
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

    protected SmartLoadingView loadingView;
    protected List<T> list = new ArrayList<>(0);
    protected SmartAdapter<T> adapter;
    protected boolean reachedEnd = false;
    protected String searchPhrase = "";
    private SmartAsyncTask<List<T>> loader;
    protected int columnCount = 1;
    protected String tagName = "Smart List Fragment";
    protected View headerView;

    protected enum Type {STATIC, DYNAMIC}
    protected Type type = Type.DYNAMIC;

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
        loadingView = new SmartLoadingView(getActivity());
        loadingView.setContentView(R.layout.fragment_base);
        loadingView.setOnRetryListener(new SmartLoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                executeLoader();
            }
        });

        recyclerView = (SuperRecyclerView) loadingView.findViewById(R.id.super_recyclerview);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeLoader();
            }
        });

        recyclerView.setRefreshingColorResources(R.color.amber_A700, R.color.blue_A400
                , R.color.red_A400, R.color.light_blue_A400);

        if (type == Type.DYNAMIC)
            recyclerView.setupMoreListener(
                    new OnMoreListener() {
                        @Override
                        public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                            if (!reachedEnd) {
                                recyclerView.getMoreProgressView().setVisibility(View.VISIBLE);
                                cancelLoad();
                                loader = new SmartAsyncTask<>(getDynamicCallback((numberOfItems / numberBeforeMore) + 1, ITEM_PER_PAGE, false));
                                loader.execute();
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

    protected void setType(Type type) {
        this.type = type;
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
        executeLoader();
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

    protected SmartLoadingView getLoadingView() {
        return loadingView;
    }

    protected abstract FactorySmartItemView getViewFactory();

    protected abstract List<T> fetchMoreDataAsync(int startPage, int count);

    protected abstract List<T> fetchDataAsync();

    protected abstract void onOperationFailed();

    protected SmartAsyncTask.BaseAsyncTaskCallback getDynamicCallback(final int startPage, final int count, final boolean refresh) {
        return new SmartAsyncTask.BaseAsyncTaskCallback<List<T>>() {
            @Override
            public void onPreExecute() {
                if (refresh) {
                    loadingView.loadingStart();
                    return;
                }
                recyclerView.showMoreProgress();
            }

            @Override
            public void onPostExecute(List<T> response) {
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

            @Override
            public List<T> doInBackground() {
                return fetchMoreDataAsync(startPage, count);
            }
        };
    }

    protected SmartAsyncTask.BaseAsyncTaskCallback<List<T>> getStaticCallback() {
        return new SmartAsyncTask.BaseAsyncTaskCallback<List<T>>() {
            @Override
            public void onPreExecute() {
                loadingView.loadingStart();
            }

            @Override
            public void onPostExecute(List<T> response) {
                if (response == null) {
                    loadingView.loadingFailed(false);
                    onOperationFailed();
                    return;
                }
                list.clear();
                list.addAll(response);
                loadingView.loadingEnd();
                adapter.notifyDataSetChanged();
            }

            @Override
            public List<T> doInBackground() {
                return fetchDataAsync();
            }
        };
    }

    protected void executeLoader() {
        cancelLoad();
        if (type == Type.STATIC) {
            loader = new SmartAsyncTask<>(getStaticCallback());
        }else if (type == Type.DYNAMIC) {
            reachedEnd = false;
            loader = new SmartAsyncTask<>(getDynamicCallback(START_PAGE, ITEM_PER_PAGE, true));
        }
        loader.execute();
    }
}