package com.example.aleja.practica2.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.adaptadores.CachedFragmentPagerAdapter;
import com.example.aleja.practica2.modelos.Alumno;


public class TutoriaIndividualFragment extends Fragment{
    private static final String ARG_ALUMNO = "alumno";
    private FragmentManager mGestor;
    private Alumno mAlumno;

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
        SectionsPagerAdapter vpAdapter = new SectionsPagerAdapter(getChildFragmentManager(), mAlumno);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.container);
        viewPager.setAdapter(vpAdapter);

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SectionsPagerAdapter extends CachedFragmentPagerAdapter {

        private final Alumno alumno;

        public SectionsPagerAdapter(FragmentManager fm, Alumno alumno) {
            super(fm);
            this.alumno = alumno;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return EditorFragment.newInstance(alumno);
                case 1:
                    return VisitasFragment.newInstance(mAlumno);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ALUMNO";
                case 1:
                    return "TUTORÍAS";
            }
            return null;
        }

    }


}