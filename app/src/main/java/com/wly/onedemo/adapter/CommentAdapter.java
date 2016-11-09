package com.wly.onedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.wly.onedemo.R;
import com.wly.onedemo.bean.CommentDetailBean;
import com.wly.onedemo.bean.UserBean;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.utils.DisplayUtil;
import com.wly.onedemo.utils.ImageUtil;
import com.wly.onedemo.view.CircleImageView;
import com.wly.onedemo.view.TextViewExpandable;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Candy on 2016/10/28.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private static final String TAG = CommentAdapter.class.getSimpleName();


    private List<Object> mComments;
    private Context mContext;

    public CommentAdapter(List<Object> comments , Context context){
        mComments = comments;
        mContext = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_layout , null);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentViewHolder holder, int position) {
        CommentDetailBean item = (CommentDetailBean) mComments.get(position);

        UserBean userInfo = item.getUser();

        holder.mFavoriteCount.setText(String.valueOf(item.getPraisenum()));

        Log.d(TAG, "onBindViewHolder: content=" + item.getContent());
        holder.mComment.setText(item.getContent());

        Date createTime = DisplayUtil.String2Date(item.getCreated_at());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd.yyyy" , Locale.ENGLISH);
        holder.mEssayDate.setText(dateFormat.format(createTime));

        //设置评论者信息
        Bitmap bitmap = DownLoadBitmapCache.getInstance().LoadBitmap(userInfo.getWeb_url());
        if(bitmap != null){
            holder.mAvatar.setImageBitmap(bitmap);
        }else{
            DownLoadBitmapCache.getInstance().DownLoadBitmap(mContext , userInfo.getWeb_url() , holder.mAvatar);
        }
//        ImageUtil.loadOriginalImage(mContext, userInfo.getWeb_url(), new FutureCallback<Bitmap>() {
//            @Override
//            public void onCompleted(Exception e, Bitmap result) {
//                holder.mAvatar.setImageBitmap(result);
//            }
//        });
        holder.mAuthorName.setText(userInfo.getUser_name());

        //设置答复者。
        if (item.getTouser() == null){
            holder.mTouser.setVisibility(View.GONE);
        }else{
            UserBean touser = item.getTouser();
            holder.mTouser.setVisibility(View.VISIBLE);
            holder.mTouserName.setText(touser.getUser_name() + ":");
            holder.mTouserContent.setText(item.getQuote());
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mAvatar;
        TextView mAuthorName;
        TextView mEssayDate;
        TextView mFavoriteCount;
        TextViewExpandable mComment;

        LinearLayout mTouser;
        TextView mTouserName;
        TextView mTouserContent;

        public CommentViewHolder(View itemView) {
            super(itemView);

            mAvatar = (CircleImageView) itemView.findViewById(R.id.iv_author_avatar);
            mAuthorName = (TextView) itemView.findViewById(R.id.tv_author_name);
            mEssayDate = (TextView) itemView.findViewById(R.id.tv_essay_date);
            mFavoriteCount = (TextView) itemView.findViewById(R.id.tv_essay_favorite);

            mComment = (TextViewExpandable) itemView.findViewById(R.id.tv_comment_contain);

            mTouser = (LinearLayout) itemView.findViewById(R.id.ll_touser);
            mTouserName = (TextView) itemView.findViewById(R.id.tv_touser_name);
            mTouserContent = (TextView) itemView.findViewById(R.id.tv_touser_content);
        }
    }
}
