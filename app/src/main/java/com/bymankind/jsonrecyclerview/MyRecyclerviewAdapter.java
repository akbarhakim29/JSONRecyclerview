package com.bymankind.jsonrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bymankind.jsonrecyclerview.helper.ItemTouchHelperAdapter;
import com.bymankind.jsonrecyclerview.helper.ItemTouchHelperViewHolder;
import com.bymankind.jsonrecyclerview.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by Server-Panduit on 4/12/2017.
 */

public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.MyViewHolder> implements ItemTouchHelperAdapter {
        private List<SetterGetter> feedsList;
        private Context context;
        private LayoutInflater inflater;

        private final OnStartDragListener mDragStartListener;

    public MyRecyclerviewAdapter(Context context,OnStartDragListener dragStartListener, List<SetterGetter> feedsList){
        this.context = context;
        this.feedsList = feedsList;
        mDragStartListener = dragStartListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.singleitem_recyclerview,parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SetterGetter feeds = feedsList.get(position);

    //    final int order_id = feeds.getOrder_id();

        holder.title.setText(feeds.getName());
        holder.content.setText(feeds.getOrder_invoice_number());
        holder.content2.setText(feeds.getItem());
        //when using bitmap Lrucache (setImageUrl) must using NetworkImageView
        //holder.imageView.setImageUrl(feeds.getImgURL(),NetworkController.getInstance(context).getImageLoader());

        Glide.with(context).load(feeds.getPicture()).placeholder(R.drawable.yin_yang).into(holder.imageView);

       /* holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"id = "+ order_id,Toast.LENGTH_SHORT).show();
            }
        });*/

        //holder.ratingBar.setProgress(feeds.getRating());

        /*holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(feedsList,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        feedsList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
        private TextView content,content2,title;
        private ImageView imageView;
        private ProgressBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_view);
            content = (TextView) itemView.findViewById(R.id.content_view);
            content2 = (TextView) itemView.findViewById(R.id.content_view2);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            /*ratingBar = (ProgressBar) itemView.findViewById(R.id.ratingbar_view);
            ratingBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Rated by User : "+feedsList.get(getAdapterPosition()).getRating(),Toast.LENGTH_LONG).show();
                }
            });*/
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.CYAN);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }


    }
}
