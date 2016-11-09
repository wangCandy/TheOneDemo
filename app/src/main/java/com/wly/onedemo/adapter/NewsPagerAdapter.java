package com.wly.onedemo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wly.onedemo.R;
import com.wly.onedemo.bean.NewsBean;
import com.wly.onedemo.fragment.NewsFragment;
import com.wly.onedemo.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/10/21.
 */
public class NewsPagerAdapter extends PagerAdapter {

    private FragmentManager manger;
    public List<Fragment> fragments = new ArrayList<>();
    public List<String> lists = new ArrayList<String>();

    public NewsPagerAdapter(FragmentManager fm , List<String> lists) {
        manger = fm;
        this.lists = lists;
        for (int i = 0; i < lists.size(); i++) {
            fragments.add(getItem(i));
        }
    }

    public Fragment getItem(int position) {
//        Bundle args = new Bundle();
//        args.putString("position", lists.get(position));
        NewsFragment fragment = NewsFragment.newsInstance(lists.get(position));
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 移出viewpager两边之外的page布局
        container.removeView(fragments.get(position).getView());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);
        // 如果fragment还没有added
        if (!fragment.isAdded()) {
            FragmentTransaction ft = manger.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            manger.executePendingTransactions();
        }

        if (fragment.getView().getParent() == null) {
            // 为viewpager增加布局
            container.addView(fragment.getView());
        }

        return fragment.getView();
    }
}
