package snu.talkandshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CalendarPickerView calendarPickerView;
    private FloatingActionButton fab;
    private ScrollView scrollView;
    private EventFragment eventFragment_bookmark;
    private EventFragment eventFragment_attend;
    private EventFragment eventFragment_add;

    private final String[] type = {"전체", "강연", "공연", "기타"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
                final EditText date_edit = (EditText) findViewById(R.id.date_edit1);
                EditText time_edit = (EditText) findViewById(R.id.date_edit2);
                Button moreButton = (Button) findViewById(R.id.moreOption);
                Button cancel = (Button) findViewById(R.id.cancel);
                Button add = (Button) findViewById(R.id.add);
                final CheckBox lecture = (CheckBox) findViewById(R.id.lecture);
                final CheckBox play = (CheckBox) findViewById(R.id.play);
                final CheckBox other = (CheckBox) findViewById(R.id.other);
                final ArrayList<String> options = new ArrayList<String>();
                options.add("주최");
                options.add("참가비");
                options.add("문의");
                options.add("내용");
                lecture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (play.isChecked())
                            play.setChecked(false);
                        else if (other.isChecked())
                            other.setChecked(false);
                    }
                });
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lecture.isChecked())
                            lecture.setChecked(false);
                        else if (other.isChecked())
                            other.setChecked(false);
                    }
                });
                other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (play.isChecked())
                            play.setChecked(false);
                        else if (lecture.isChecked())
                            lecture.setChecked(false);
                    }
                });

                date_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerFragment dialogFragment = new DatePickerFragment();
                        dialogFragment.show(getFragmentManager(), "Date Picker");
                    }
                });
                time_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerFragment dialogFragment = new TimePickerFragment();
                        dialogFragment.show(getFragmentManager(), "Time Picker");
                    }
                });
                moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setMessage("취소 하시겠습니까?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        tabLayout.setVisibility(View.VISIBLE);
                                        viewPager.setVisibility(View.VISIBLE);
                                        scrollView.setVisibility(View.GONE);
                                        fab.setVisibility(View.VISIBLE);
                                    }
                                });
                        alertDialog.show();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setMessage("추가 하시겠습니까?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        EventDBAdapter eventDB = new EventDBAdapter(getApplicationContext(), "EventDB.db");
                                        eventDB = eventDB.createDatabase();
                                        eventDB = eventDB.open();
                                        EditText title_edit = (EditText) findViewById(R.id.title_edit1);
                                        EditText place_edit = (EditText) findViewById(R.id.place_edit1);
                                        Event event = new Event();
                                        event.setId(eventDB.getCount());
                                        if(lecture.isChecked())
                                            event.setType(1);
                                        else if(play.isChecked())
                                            event.setType(2);
                                        else if(other.isChecked())
                                            event.setType(3);
                                        event.setTitle(title_edit.getText().toString());
                                        if(date_edit!=null){
                                            int a = date_edit.getText().toString().indexOf("년");
                                            int b = date_edit.getText().toString().indexOf("월");
                                            int c = date_edit.getText().toString().indexOf("일");
                                            int year = Integer.parseInt(date_edit.getText().toString().substring(a - 2, a));
                                            int month = Integer.parseInt(date_edit.getText().toString().substring(a + 2, b));
                                            int day = Integer.parseInt(date_edit.getText().toString().substring(b + 2, c));
                                            event.setDate(year * 10000 + month * 100 + day);
                                        }
                                        event.setPlace(place_edit.getText().toString());
                                        eventDB.saveEvent(event);
                                        eventDB.close();
                                        myDBAdapter myDB = new myDBAdapter(getApplication(), "MyDB.db");
                                        myDB = myDB.createDatabase();
                                        myDB = myDB.open();
                                        int id = myDB.getTableCount("added");
                                        int event_id = event.getId();
                                        Calendar calendar = Calendar.getInstance();
                                        int year = calendar.get(Calendar.YEAR);
                                        int month = calendar.get(Calendar.MONTH);
                                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                                        int date = (year-2000)*10000+month*100+day;
                                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                        int minute = calendar.get(Calendar.MINUTE);
                                        int time = hour*100+minute;
                                        myDB.saveData(id, event_id, date, time, "added");
                                        myDB.close();
                                        setupViewPager(viewPager);
                                        tabLayout.setupWithViewPager(viewPager);
                                        tabLayout.setVisibility(View.VISIBLE);
                                        viewPager.setVisibility(View.VISIBLE);
                                        scrollView.setVisibility(View.INVISIBLE);
                                        fab.setVisibility(View.VISIBLE);
                                    }
                                });
                        alertDialog.show();

                    }
                });

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendarPickerView.init(today, nextYear.getTime()).withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.RANGE);
        calendarPickerView.setVisibility(View.INVISIBLE);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setVisibility(View.INVISIBLE);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
   }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        EventDBAdapter eventDB = new EventDBAdapter(getApplicationContext(), "EventDB.db");
        eventDB = eventDB.createDatabase();
        eventDB = eventDB.open();
        for(int i=0; i<4; i++){
            ArrayList list = eventDB.getIndex(i);
            Collections.reverse(list);
            EventFragment eventFragment = EventFragment.newInstance(list, type[i]);
            adapter.addFragment(eventFragment, type[i]);
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(tabLayout.getVisibility()==View.GONE){
            if(calendarPickerView.getVisibility()==View.VISIBLE){
                viewPager.setVisibility(View.VISIBLE);
                calendarPickerView.setVisibility(View.INVISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            else if(scrollView.getVisibility()==View.VISIBLE){
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
            }

        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter) {
            calendarPickerView.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.INVISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        myDBAdapter myDB = new myDBAdapter(getApplicationContext(), "MyDB.db");
        myDB = myDB.createDatabase();
        myDB = myDB.open();
        if (id == R.id.nav_main){
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.nav_bookmark) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            ArrayList list =myDB.getIndex("bookmark");
            myDB.close();
            Collections.reverse(list);
            eventFragment_bookmark = EventFragment.newInstance(list, "bookmark");
            adapter.addFragment(eventFragment_bookmark, "bookmark");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.nav_attend) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            ArrayList list =myDB.getIndex("attend");
            myDB.close();
            Collections.reverse(list);
            eventFragment_attend = EventFragment.newInstance(list, "attend");
            adapter.addFragment(eventFragment_attend, "attend");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_add) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            ArrayList list =myDB.getIndex("added");
            myDB.close();
            Collections.reverse(list);
            eventFragment_add = EventFragment.newInstance(list, "added");
            adapter.addFragment(eventFragment_add, "add");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}