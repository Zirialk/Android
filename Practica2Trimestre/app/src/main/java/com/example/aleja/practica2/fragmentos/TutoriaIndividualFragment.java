package com.example.aleja.practica2.fragmentos;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.adaptadores.CachedFragmentPagerAdapter;
import com.example.aleja.practica2.modelos.Alumno;


public class TutoriaIndividualFragment extends Fragment{
    private static final String ARG_ALUMNO = "alumno";
    private Alumno mAlumno;
    private ViewPager viewPager;

    public static TutoriaIndividualFragment newInstance(Alumno alumno) {
        Bundle args = new Bundle();
        TutoriaIndividualFragment fragment = new TutoriaIndividualFragment();
        args.putParcelable(ARG_ALUMNO, alumno);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutoria_individual,container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlumno = getArguments().getParcelable(ARG_ALUMNO);
        initViews();
    }

    private void initViews() {
        configViewPager();
    }

    private void configViewPager() {
        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        final SectionsPagerAdapter vpAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);
        viewPager.setAdapter(vpAdapter);

        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        moverFab(-420 , R.drawable.ic_photo_camera_white_24dp);
                        break;
                    case 1:
                        moverFab(0, R.drawable.ic_add);
                        break;
                }
                //Coloca como tab actual la presionada
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public int getCurrentPage(){
        return viewPager.getCurrentItem();
    }
    public int getIdAlumno(){
        return mAlumno.getId();
    }

    class SectionsPagerAdapter extends CachedFragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        //Especifica que fragmento irá en cada página del viewPager
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return EditorFragment.newInstance(mAlumno);
                case 1:
                    return VisitasFragment.newInstance(mAlumno);
            }
            return null;
        }



        @Override
        public int getCount() {
            return 2;
        }

        //Establece los títulos de los tabs.
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getActivity().getString(R.string.tabAlumno);
                case 1:
                    return getActivity().getString(R.string.tabTutorias);
            }
            return null;
        }

    }

    private void moverFab(int y, final int idDrawable){
        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.animate().translationY(y).setInterpolator(new AccelerateInterpolator(2)).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab.setImageResource(idDrawable);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
