package com.wly.onedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.wly.onedemo.R;
import com.wly.onedemo.bean.MovieBean;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.utils.CacheUtil;
import com.wly.onedemo.utils.ImageUtil;

import java.util.List;

/**
 * Created by Candy on 2016/10/25.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>{
    private static final String TAG = MovieRecyclerAdapter.class.getSimpleName();

    private List<MovieBean> mMovies;
    private Context mContext;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public MovieRecyclerAdapter(List<MovieBean> movies , Context context){

        this.mContext = context;
        this.mMovies = movies;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_movie , null);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieBean item = mMovies.get(position);
        final CacheUtil cache = CacheUtil.get(mContext);
        Bitmap bitmap = cache.getAsBitmap(item.getCover());
        if (bitmap != null) {
            Log.w(TAG, "onBindViewHolder: cache");
            holder.mMovieImage.setImageBitmap(bitmap);
        }else{
            ImageUtil.loadOriginalImage(mContext, item.getCover(), new FutureCallback<Bitmap>() {
                @Override
                public void onCompleted(Exception e, Bitmap result) {
                    if(result != null){
                        holder.mMovieImage.setImageBitmap(result);
                        cache.put(item.getCover() , result);
                    }
                }
            });
//            DownLoadBitmapCache.getInstance().DownLoadBitmap(mContext , item.getCover() , holder.mMovieImage);
        }
    }

    @Override
    public int getItemCount() {

//        Log.w(TAG, "getItemCount: count=" + mMovies.size());
        return mMovies == null ? 0 : mMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView mMovieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);

            mMovieImage = (ImageView) itemView.findViewById(R.id.iv_movie_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClicked(getLayoutPosition());
                }
            });
        }
    }
}
