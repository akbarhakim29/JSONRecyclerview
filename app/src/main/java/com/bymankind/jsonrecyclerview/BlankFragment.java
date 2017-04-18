package com.bymankind.jsonrecyclerview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.bymankind.jsonrecyclerview.helper.SimpleItemTouchHelperCallback;
import com.bymankind.jsonrecyclerview.helper.OnStartDragListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    RequestQueue queue;
    String url = "http://192.168.1.143/laosposapi/api/getStatusQueueList";
   // https://api.myjson.com/bins/w86a
    RecyclerView recyclerView;
    List<SetterGetter> feedsList = new ArrayList<SetterGetter>();
    MyRecyclerviewAdapter adapter;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter = new MyRecyclerviewAdapter(getActivity(),this, feedsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        queue = NetworkController.getInstance(getContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonChildObject = jsonResponse.getJSONArray("data");
                    int jsonLenght = jsonChildObject.length();

                    for (int i=0;i<jsonLenght;i++){
                        try {
                            JSONObject jsonObject = jsonChildObject.getJSONObject(i);
                            SetterGetter setterGetter = new SetterGetter(jsonObject.getInt("order_id"),jsonObject.getString("order_invoice_number"),jsonObject.getString("name"),jsonObject.getString("item"),jsonObject.getString("picture"));
                            feedsList.add(setterGetter);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }finally {
                            adapter.notifyItemChanged(i);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        queue.add(stringRequest);

    }




    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
