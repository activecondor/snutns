package snu.talkandshow;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by Seungyong on 2015-11-20.
 */
public class EventFragment extends Fragment {
    private ArrayList<Integer> indexList;
    private String fragmentName;
    private RVAdapter mRVAdapter;
    private ArrayList<Event> events;
    private EventDBAdapter eventDB;
    protected Handler handler;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean ALLITEMSHOWN = false;

    public static final EventFragment newInstance(ArrayList<Integer> indexList, String fragmentName) {
        EventFragment eventFragment = new EventFragment();
        Bundle bdl = new Bundle();
        bdl.putIntegerArrayList("indexList", indexList);
        bdl.putString("fragmentName", fragmentName);
        eventFragment.setArguments(bdl);
        return eventFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventDB = new EventDBAdapter(getActivity(), "EventDB.db");
        eventDB = eventDB.createDatabase();
        eventDB = eventDB.open();
        indexList = new ArrayList<Integer>();
        indexList= getArguments().getIntegerArrayList("indexList");
        fragmentName = getArguments().getString("fragmentName");
        events = new ArrayList<Event>();
        if(indexList.size()<5){
            for(int i = 0; i<indexList.size();i++){
                events.add(eventDB.loadEvent(indexList.get(i)));
            }
            ALLITEMSHOWN = true;
        }
        else{
            for (int i = 0; i < 5; i++) {
                events.add(eventDB.loadEvent(indexList.get(i)));
            }
        }
        eventDB.close();
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.content_main, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRVAdapter = new RVAdapter(events, eventDB, mRecyclerView, fragmentName);
        mRecyclerView.setAdapter(mRVAdapter);

        mRVAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (ALLITEMSHOWN == false) {
                    events.add(null);
                    mRVAdapter.notifyItemInserted(events.size() - 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            events.remove(events.size() - 1);
                            mRVAdapter.notifyItemRemoved(events.size());
                            int start = events.size();
                            int end = start + 5;
                            if (end > indexList.size()) {
                                end = indexList.size();
                                ALLITEMSHOWN = true;
                            }
                            eventDB = eventDB.open();
                            for (int i = start; i < end; i++) {
                                events.add(eventDB.loadEvent(indexList.get(i)));
                                mRVAdapter.notifyItemInserted(events.size() - 1);
                            }
                            eventDB.close();
                            mRVAdapter.setLoaded();
                        }
                    }, 1500);
                } else
                    Toast.makeText(getContext(), "더이상 불러올 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
