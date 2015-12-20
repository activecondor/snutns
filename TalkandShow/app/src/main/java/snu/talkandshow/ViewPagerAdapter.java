package snu.talkandshow;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Seungyong on 2015-11-20.
 */
class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<EventFragment> eventFragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public EventFragment getItem(int position) {
        return eventFragments.get(position);
    }
    @Override
    public int getCount() {
        return eventFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    public void addFragment(EventFragment fragment, String title) {
        eventFragments.add(fragment);
        titles.add(title);
    }
}