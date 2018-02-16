package com.pranavpanage.webviewtester;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav on 1/17/2018.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter{


    private final List<Fragment> mFragmentList= new ArrayList<>();
    private final List<String> mFragmentTittleList= new ArrayList<>();




    public void addFragment(Fragment fragment,String title){
        mFragmentList.add(fragment);
        mFragmentTittleList.add(title);
    }


    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTittleList.get(position);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
