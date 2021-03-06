package com.mobileapp.classmate.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.mobileapp.classmate.R;
import com.mobileapp.classmate.viewmodel.MainViewModel;

// Need to implement tab layout for tabs
public class ViewPagerMainActivity extends FragmentActivity {
    private String CHANNEL_ID = "REMINDERS";

    // handles animation and swiping
    private ViewPager viewPager;

    // Floating action button for adding classes on class selection fragment
    private FloatingActionButton mFab;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        CreateNotificationChannel();
        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPager) findViewById(R.id.class_selection_view_pager);
        // sets up a tab layout for viewpagers
        TabLayout tablayout = (TabLayout) findViewById(R.id.class_selection_tabLayout);

        // Set up viewPager tabs
        // provides pages to view pager widget
        TabAdapter mTabAdapter = new TabAdapter(getSupportFragmentManager());
        ClassSelectionPageFragment classSelectionFragment = new ClassSelectionPageFragment();
        DailyPageFragment dailyPageFragment = new DailyPageFragment();
        mTabAdapter.addFragment(classSelectionFragment, "Class Selection");
        mTabAdapter.addFragment(dailyPageFragment, "Daily");

        viewPager.setAdapter(mTabAdapter);
        tablayout.setupWithViewPager(viewPager);

        // Add Class Floating action button
        mFab = (FloatingActionButton) findViewById(R.id.fab_add_class);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mFab.show();
                        break;
                    default:
                        mFab.hide();
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        mFab.setOnClickListener(v -> classSelectionFragment.showAddCourseDialog(this));
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private void CreateNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "reminder_channel";
            String description = "Channel for all assignment reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
