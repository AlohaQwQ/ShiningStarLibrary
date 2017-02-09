package aloha.shiningstarbase.util.fragmentadapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
//FragmentStatePagerAdapter 适用于页面数量较多且需要保存Fragment状态的情况.
public class FragmentPageAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT; //tab 页数
    private List<String> tabTitles;
    private List<Fragment> fragments;
    private Context context;

    /**
     * @param fm
     * @param context
     * @param pageNumber tab页数
     * @param tabTitles tab标题
     * @param fragments tab加载fragment 集合
     */

    public FragmentPageAdapter(FragmentManager fm, Context context, int pageNumber, List<String> tabTitles, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        PAGE_COUNT = pageNumber;
        this.tabTitles = tabTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        //return PagerAdapter.POSITION_NONE;
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        // Show total pages.
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
