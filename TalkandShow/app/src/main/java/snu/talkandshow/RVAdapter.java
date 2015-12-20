package snu.talkandshow;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Seungyong on 2015-11-18.
 */
public class RVAdapter extends RecyclerView.Adapter{
    private final int VIEW_EVENT = 1;
    private final int VIEW_PROG = 0;
    private ArrayList<Event> event;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private static EventDBAdapter eventDB;
    private static myDBAdapter myDB;
    private static ArrayList<Integer> list;
    private static String fragmentName;

    public RVAdapter(ArrayList<Event> event, EventDBAdapter eventDB, RecyclerView recyclerView, String fragmentName){
        this.event=event;
        this.eventDB = eventDB;
        this.fragmentName = fragmentName;
        this.myDB = new myDBAdapter(recyclerView.getContext(), "MyDB.db");
        myDB= myDB.createDatabase();
        this.list = new ArrayList<Integer>();
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                    if(onLoadMoreListener != null){
                        onLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView title;
        public TextView place;
        public TextView date;
        public ImageView imageView;
        public TextView host;
        public TextView host1;
        public TextView fee;
        public TextView fee1;
        public TextView contact;
        public TextView contact1;
        public TextView info;
        public TextView info1;
        public LinearLayout linearLayout1;
        public LinearLayout linearLayout2;
        public LinearLayout linearLayout3;
        public ImageView like_image;
        public TextView like;
        public ImageView bookmark_image;
        public TextView bookmark;
        public ImageView attend_image;
        public TextView attend;

        public EventViewHolder(View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            title = (TextView) v.findViewById(R.id.title);
            place= (TextView) v.findViewById(R.id.place);
            date = (TextView) v.findViewById(R.id.date);
            host = (TextView) v.findViewById(R.id.host);
            host1 = (TextView) v.findViewById(R.id.host1);
            fee = (TextView) v.findViewById(R.id.fee);
            fee1 = (TextView) v.findViewById(R.id.fee1);
            contact = (TextView) v.findViewById(R.id.contact);
            contact1 = (TextView) v.findViewById(R.id.contact1);
            info = (TextView) v.findViewById(R.id.info);
            info1 = (TextView) v.findViewById(R.id.info1);

            linearLayout1 = (LinearLayout) v.findViewById(R.id.linearLayout1);
            like_image = (ImageView) v.findViewById(R.id.like_image);
            like = (TextView) v.findViewById(R.id.like);
            linearLayout1.setTag(0);
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer) linearLayout1.getTag() == 0) {
                        myDB=myDB.open();
                        int id = myDB.getTableCount("like");
                        int event_id = (Integer) cardView.getTag();
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int date = (year-2000)*10000+month*100+day;
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int time = hour*100+minute;
                        myDB.saveData(id, event_id, date, time, "like");
                        myDB.close();
                        like_image.setImageResource(R.drawable.heart_teal_24dp);
                        like.setTextColor(Color.parseColor("#50bcb6"));
                        linearLayout1.setTag(1);
                    } else {
                        myDB=myDB.open();
                        int event_id = (Integer) cardView.getTag();
                        myDB.removeData(event_id, "like");
                        myDB.close();
                        like_image.setImageResource(R.drawable.heart_outline_lightgrey_24dp);
                        like.setTextColor(Color.parseColor("#FF727272"));
                        linearLayout1.setTag(0);
                    }
                }
            });

            linearLayout2 = (LinearLayout) v.findViewById(R.id.linearLayout2);
            bookmark_image = (ImageView) v.findViewById(R.id.bookmark_image);
            bookmark = (TextView) v.findViewById(R.id.bookmark);
            linearLayout2.setTag(0);
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer) linearLayout2.getTag() == 0) {
                        myDB=myDB.open();
                        int id = myDB.getTableCount("bookmark");
                        int event_id = (Integer) cardView.getTag();
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int date = (year-2000)*10000+month*100+day;
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int time = hour*100+minute;
                        myDB.saveData(id, event_id, date, time, "bookmark");
                        myDB.close();
                        bookmark_image.setImageResource(R.drawable.bookmark_teal_24dp);
                        bookmark.setTextColor(Color.parseColor("#50bcb6"));
                        linearLayout2.setTag(1);
                    } else {
                        myDB=myDB.open();
                        int event_id = (Integer) cardView.getTag();
                        myDB.removeData(event_id, "bookmark");
                        myDB.close();
                        bookmark_image.setImageResource(R.drawable.bookmark_outline_lightgrey_24dp);
                        bookmark.setTextColor(Color.parseColor("#FF727272"));
                        linearLayout2.setTag(0);
                    }
                }
            });

            linearLayout3 = (LinearLayout) v.findViewById(R.id.linearLayout3);
            attend_image = (ImageView) v.findViewById(R.id.attend_image);
            attend = (TextView) v.findViewById(R.id.attend);
            linearLayout3.setTag(0);
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer) linearLayout3.getTag() == 0) {
                        myDB=myDB.open();
                        int id = myDB.getTableCount("attend");
                        int event_id = (Integer) cardView.getTag();
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int date = (year-2000)*10000+month*100+day;
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int time = hour*100+minute;
                        myDB.saveData(id, event_id, date, time, "attend");
                        myDB.close();
                        attend_image.setImageResource(R.drawable.people_teal_24dp);
                        attend.setTextColor(Color.parseColor("#50bcb6"));
                        linearLayout3.setTag(1);
                    } else {
                        myDB=myDB.open();
                        int event_id = (Integer) cardView.getTag();
                        myDB.removeData(event_id, "attend");
                        myDB.close();
                        attend_image.setImageResource(R.drawable.people_outline_lightgrey_24dp);
                        attend.setTextColor(Color.parseColor("#FF727272"));
                        linearLayout3.setTag(0);
                    }
                }
            });

            cardView = (CardView) v.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!list.contains((Integer)cardView.getTag())){
                        eventDB = eventDB.open();
                        Event event = eventDB.loadEventDetail((Integer)cardView.getTag());
                        eventDB.close();
                        host.setText(event.getHost());
                        if (event.getFee() == 0) {
                            fee.setText("무료");
                        } else {
                            fee.setText("" + event.getHost() + "원");
                        }
                        contact.setText(event.getContact());
                        info.setText(event.getInfo());
                        host.setVisibility(View.VISIBLE);
                        host1.setVisibility(View.VISIBLE);
                        fee.setVisibility(View.VISIBLE);
                        fee1.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);
                        contact1.setVisibility(View.VISIBLE);
                        info.setVisibility(View.VISIBLE);
                        info1.setVisibility(View.VISIBLE);
                        list.add((Integer)cardView.getTag());
                    }
                    else {
                        host.setVisibility(View.GONE);
                        host1.setVisibility(View.GONE);
                        fee.setVisibility(View.GONE);
                        fee1.setVisibility(View.GONE);
                        contact.setVisibility(View.GONE);
                        contact1.setVisibility(View.GONE);
                        info.setVisibility(View.GONE);
                        info1.setVisibility(View.GONE);
                        list.remove((Integer)cardView.getTag());
                    }
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(viewType==VIEW_EVENT){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event,parent,false);
            return new EventViewHolder(v);
        }
        else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item,parent,false);
            return new ProgressViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        if(holder instanceof EventViewHolder){
            Event singleEvent= event.get(position);
            ((EventViewHolder) holder).cardView.setTag(singleEvent.getId());
            ((EventViewHolder) holder).title.setText(singleEvent.getTitle());
            ((EventViewHolder) holder).place.setText(singleEvent.getPlace());
            int date=singleEvent.getDate();
            int year=2000+date/10000;
            int month=(date%10000)/100;
            int day=(date%10000)%100;
            ((EventViewHolder) holder).date.setText(""+year+"년 "+month+"월 "+day+"일");
            ((EventViewHolder) holder).imageView.setImageResource(R.drawable.pic);
            myDB = myDB.open();
            if(myDB.isInTable(singleEvent.getId(), "like")){
                ((EventViewHolder) holder).like_image.setImageResource(R.drawable.heart_teal_24dp);
                ((EventViewHolder) holder).like.setTextColor(Color.parseColor("#50bcb6"));
                ((EventViewHolder) holder).linearLayout1.setTag(1);
            }
            else{
                ((EventViewHolder) holder).like_image.setImageResource(R.drawable.heart_outline_lightgrey_24dp);
                ((EventViewHolder) holder).like.setTextColor(Color.parseColor("#FF727272"));
                ((EventViewHolder) holder).linearLayout1.setTag(0);
            }
            if(myDB.isInTable(singleEvent.getId(), "bookmark")){
                ((EventViewHolder) holder).bookmark_image.setImageResource(R.drawable.bookmark_teal_24dp);
                ((EventViewHolder) holder).bookmark.setTextColor(Color.parseColor("#50bcb6"));
                ((EventViewHolder) holder).linearLayout2.setTag(1);
            }
            else{
                ((EventViewHolder) holder).bookmark_image.setImageResource(R.drawable.bookmark_outline_lightgrey_24dp);
                ((EventViewHolder) holder).bookmark.setTextColor(Color.parseColor("#FF727272"));
                ((EventViewHolder) holder).linearLayout2.setTag(0);
            }
            if(myDB.isInTable(singleEvent.getId(), "attend")){
                ((EventViewHolder) holder).attend_image.setImageResource(R.drawable.people_teal_24dp);
                ((EventViewHolder) holder).attend.setTextColor(Color.parseColor("#50bcb6"));
                ((EventViewHolder) holder).linearLayout3.setTag(1);
            }
            else{
                ((EventViewHolder) holder).attend_image.setImageResource(R.drawable.people_outline_lightgrey_24dp);
                ((EventViewHolder) holder).attend.setTextColor(Color.parseColor("#FF727272"));
                ((EventViewHolder) holder).linearLayout3.setTag(0);
            }
            if(list.contains((Integer)singleEvent.getId())){
                eventDB = eventDB.open();
                Event eventDetail = eventDB.loadEventDetail(singleEvent.getId());
                eventDB.close();
                ((EventViewHolder) holder).host.setText(eventDetail.getHost());
                if (singleEvent.getFee() == 0) {
                    ((EventViewHolder) holder).fee.setText("무료");
                } else {
                    ((EventViewHolder) holder).fee.setText("" + eventDetail.getHost() + "원");
                }
                ((EventViewHolder) holder).contact.setText(eventDetail.getContact());
                ((EventViewHolder) holder).info.setText(eventDetail.getInfo());
                ((EventViewHolder) holder).host.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).host1.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).fee.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).fee1.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).contact.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).contact1.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).info.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).info1.setVisibility(View.VISIBLE);
            }
            else{
                ((EventViewHolder) holder).host.setVisibility(View.GONE);
                ((EventViewHolder) holder).host1.setVisibility(View.GONE);
                ((EventViewHolder) holder).fee.setVisibility(View.GONE);
                ((EventViewHolder) holder).fee1.setVisibility(View.GONE);
                ((EventViewHolder) holder).contact.setVisibility(View.GONE);
                ((EventViewHolder) holder).contact1.setVisibility(View.GONE);
                ((EventViewHolder) holder).info.setVisibility(View.GONE);
                ((EventViewHolder) holder).info1.setVisibility(View.GONE);
            }
        }
        else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount(){
        return event.size();
    }

    @Override
    public int getItemViewType(int position){
        return event.get(position) != null ? VIEW_EVENT : VIEW_PROG;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public void setLoaded(){
        loading = false;
    }
}
