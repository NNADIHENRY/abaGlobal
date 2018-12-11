//package com.example.henry.abaglobal;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
///**
// * Created by prime on 11/28/18.
// */
//
//public class TabsAccessorAdapter extends FragmentPagerAdapter {
//
//    public TabsAccessorAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
//        switch (position){
//            case 0:
//                RegFragment regFragment = new RegFragment();
//                return regFragment;
//            case 1:
//                LoginFragment loginFragment = new LoginFragment();
//                return loginFragment;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position){
//            case 0:
//              return "Register";
//            case 1:
//                return "Log In";
//            default:
//                return null;
//        }
//    }
//}
