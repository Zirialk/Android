package com.example.aleja.practica2.fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.example.aleja.practica2.R;


public class PreferencesFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{
    ICambiosRealizados mListener;

    public interface ICambiosRealizados{
        void cambiosRealizados();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        actualizarSummary(findPreference(key));
    }

    private void actualizarSummary(Preference preference) {
        if(preference instanceof ListPreference){
            ListPreference pref = (ListPreference) preference;
            pref.setSummary(pref.getEntry());
            mListener.cambiosRealizados();
        }
    }
    @Override
    public void onResume() {// Se registra la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        // Se elimina la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ICambiosRealizados) context;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz necesaria.
            throw new ClassCastException(context.toString() + " must implements ICambiosRealizados");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
    public void setICambiosRealizadosListener(ICambiosRealizados listener){
        mListener = listener;
    }
}
