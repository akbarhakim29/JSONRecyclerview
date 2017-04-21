package com.bymankind.jsonrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bymankind.jsonrecyclerview.helper.ItemTouchHelperAdapter;
import com.bymankind.jsonrecyclerview.helper.ItemTouchHelperViewHolder;
import com.bymankind.jsonrecyclerview.helper.OnStartDragListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONException;
import org.json.JSONObject;

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

     //   final int order_id = feeds.getOrder_id();

        holder.title.setText(feeds.getName());
        holder.content.setText(feeds.getOrder_invoice_number());
        holder.content2.setText(feeds.getItem());
        //when using bitmap Lrucache (setImageUrl) must using NetworkImageView
        //holder.imageView.setImageUrl(feeds.getImgURL(),NetworkController.getInstance(context).getImageLoader());

        Glide.with(context).load(feeds.getPicture()).placeholder(R.drawable.yin_yang).into(holder.imageView);

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
    public long getItemId(int position) {
        return super.getItemId(position);
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
    public void onItemDismiss(final int position) {
        final SetterGetter currentData = feedsList.get(position);
        feedsList.remove(position);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");

                    if (code == 200){
                        Toast.makeText(context,"id = "+currentData.getOrder_id()+" updated !",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(context,"id = "+currentData.getOrder_id()+" not updated !",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        UpdateRequest updateRequest = new UpdateRequest(currentData.getOrder_id(),listener);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(updateRequest);

        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
        private TextView content,content2,content3,title;
        private ImageView imageView;
        private ProgressBar ratingBar;
        private ExpandableTextView expandableTextView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_view);
            content = (TextView) itemView.findViewById(R.id.content_view);
            content2 = (TextView) itemView.findViewById(R.id.content_view2);
            content3 = (TextView) itemView.findViewById(R.id.expandable_text);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.expandable_text_view);

            expandableTextView.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, " +
                    "totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. " +
                    "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, " +
                    "sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, " +
                    "qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. " +
                    "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? " +
                    "Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, " +
                    "vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");

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
