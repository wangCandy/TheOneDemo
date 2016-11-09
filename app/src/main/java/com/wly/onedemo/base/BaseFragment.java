package com.wly.onedemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wly.onedemo.R;
import com.wly.onedemo.utils.FileUtil;

/**
 * Created by Candy on 2016/10/20.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = BaseFragment.class.getSimpleName();

    public View convertView;
    public Context mContext;
    public FileUtil fileUtil = new FileUtil();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(getLayoutId() , null);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findView();
        setOnListener();
        initialize();
    }

    protected abstract int getLayoutId();

    protected abstract void findView();

    protected abstract void setOnListener();

    protected abstract void initialize();

    public void setTitle(String title){

        if(TextUtils.isEmpty(title)){
            return;
        }else{
            TextView mTitle = (TextView) convertView.findViewById(R.id.tv_main_title);
            mTitle.setText(title);
        }
    }
}
