package com.example.amicoli.zhihudemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amicoli.zhihudemo.R;
import com.example.amicoli.zhihudemo.activities.ArticleDetailActivity;
import com.example.amicoli.zhihudemo.bean.ContextBean;
import com.example.amicoli.zhihudemo.bean.StoriesBean;
import com.example.amicoli.zhihudemo.interfaces.OnItemClickListener;
import com.example.amicoli.zhihudemo.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/2/9.
 */

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder>{

    public static final int ITEM_VIEW_TYPE_HEADER = 0;//带有header
    public static final int ITEM_VIEW_TYPE_ITEM = 1;//带有footer
    public static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private OnItemClickListener mOnItemClickListener;
    private boolean canLoadMore = true ; //是否可以加载更多  默认 true
    private ViewGroup header;
    private ViewGroup footer;
    Context mContext;
    public List<StoriesBean> storiesBeen;
    public ContextBean mContextbean;
    private GridLayoutManager mLayoutManager;
    private boolean loading = false;
    private boolean isLoadMore = true;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private int headerCount=0;
    private int footerCount=0;
    private boolean isLoading ;
    private OnLoadMoreListener mOnLoadMoreListener;
    private TextView today_date,before_date;

    public StoriesAdapter(View header, View footer, Context context,List<StoriesBean> storiesBeenlist, GridLayoutManager gridLayoutManager,ContextBean contextbean) {
        headerCount=header == null?0:1;
        footerCount=footer == null?0:1;
        this.header = (ViewGroup) header;
        this.footer = (ViewGroup) footer;
        this.storiesBeen = storiesBeenlist;
        this.mContextbean = contextbean;
        this.mLayoutManager = gridLayoutManager;
        this.mContext = context;
    }

    public void setmOnLoadMoreListener(OnLoadMoreListener loadMoreListener){
        this.mOnLoadMoreListener = loadMoreListener;
    }

    public void setData(List<StoriesBean> mStoriesBeen){
        if (storiesBeen != null){
            storiesBeen.clear();
        }
        storiesBeen = mStoriesBeen;
        notifyDataSetChanged();
    }

    public synchronized void addDatas(List<StoriesBean> add_storiesbean){
        if(storiesBeen == null){
            storiesBeen = add_storiesbean;
        }else {
            storiesBeen.addAll(add_storiesbean);
            notifyDataSetChanged();
        }
    }

    public StoriesBean getItem(int position){
        if(storiesBeen!=null && position <storiesBeen.size()){
            return storiesBeen.get(position);
        }
        return null;
    }

    public void setCanLoadMore(boolean canLoadMore){
        this.canLoadMore = canLoadMore;
    }

    public boolean isLoading(){
        return isLoading;
    }

    /***显示加载中*/
    public void loading(){
        if(mOnLoadMoreListener != null )
            mOnLoadMoreListener.loadMore();
        isLoading = true ;
        showLoadingFooter();
    }

    public void loadOver(){
        isLoading = false;
        //if(footer)

    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        if (isFooter(position)) {
            return ITEM_VIEW_TYPE_FOOTER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                return new ViewHolder(header, viewType);
            case ITEM_VIEW_TYPE_FOOTER:
                return new ViewHolder(footer, viewType);
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                return new ViewHolder(view, viewType);
        }

    }
    //绑定数据
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //如果时Header和Footer的时候直接返回，Header和Footer的操作，可以在Activity中进行操作
        if (isHeader(position) || isFooter(position)) {
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            return ;
        }

        //holder.today_date.setText("123");
        holder.tv_title.setText(storiesBeen.get(position-headerCount).getTitle());
            holder.tv_from.setText(R.string.tv_from);
            Glide.with(mContext)
                    .load(storiesBeen.get(position-headerCount).getImages().get(0))
                    .placeholder(R.drawable.gril)
                    .crossFade()
                    .into(holder.iv_item_image);
            holder.getmContentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                    intent.putExtra("id",storiesBeen.get(position-headerCount).getId());
                    mContext.startActivity(intent);
                }
            });


        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }
    }

    private void showLoadingFooter(){
        if(footer != null ){
            footer.getChildAt(0).setVisibility(View.VISIBLE);
            before_date.setText("1231");
        }

    }


    @Override
    public int getItemCount(){
        return storiesBeen == null ? 0: storiesBeen.size()+2;
    }

    /**
     * 在position位置插入item
     * @param position:指的是item在beans中的位置，而不是在Adapter中的位置；
     * 所以在notifyItemInserted的时候需要算上header
     * @param storiesBeen
     */

    public void addItemAt(int position,List<StoriesBean> storiesBeen){
        storiesBeen.add(position, (StoriesBean) storiesBeen);
        notifyItemInserted(position+headerCount);
    }

    /**
     * 在底部添加Items
     * @param items
     */
    public void addItemsAtLast(ArrayList<StoriesBean> items) {
        int size=storiesBeen.size();
        storiesBeen.addAll(items);
        notifyItemRangeInserted(size+headerCount, items.size());
    }


    public void setLoadMore(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
    }

    /**
     * reset loading status
     */
    public void setLoaded() {
        loading = false;
    }

    public void addOnLoadMoreListener(OnLoadMoreListener mLoadMoreListener) {
        this.mOnLoadMoreListener = mLoadMoreListener;
    }

    public boolean isHeader(int position) {
        if(headerCount == 0){
            return false;
        }
        return position == 0;
    }

    public boolean isFooter(int position){
        if (footerCount == 0){
            return false;
        }
        return position == (storiesBeen.size()+headerCount);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        ImageView iv_item_image;
        TextView tv_from;
        View mContentView;
        SparseArray<View> mViews;

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            initView(itemView,viewType);
        }

       public void initView(View view,int viewType){
           switch (viewType){
               case ITEM_VIEW_TYPE_HEADER:
                   today_date = (TextView) itemView.findViewById(R.id.today_date);
                   break;
               case ITEM_VIEW_TYPE_FOOTER:
                   before_date = (TextView) itemView.findViewById(R.id.date_before);
                   break;
               case ITEM_VIEW_TYPE_ITEM:
                   tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                   tv_from = (TextView) itemView.findViewById(R.id.tv_from);
                   iv_item_image = (ImageView) itemView.findViewById(R.id.iv_item_image);
                   mContentView = itemView;
                   break;
               default:
                   break;
           }

       }

        public View getmContentView(){
            return mContentView;
        }
        /**
         * 通过viewId获取控件
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId)
        {
            View view = mViews.get(viewId);
            if (view == null)
            {
                view = mContentView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }
}
