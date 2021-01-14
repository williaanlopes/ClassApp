package com.gurpster.facapemobile.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

/**
 * Created by Williaan Lopes (d3x773r) on 23/03/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class PreferenceFragmentCustom extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor spe;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        spe = sharedPref.edit();
//        bindPreferenceSummaryToValue(findPreference("defaultTimetable"));

        //Bind preference summary to value for lists and sorting list preferences
        bindPreferenceSummaryToValue();
//        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_name_default_home_state)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        // Try if the preference is one of our custom Preferences
//        DialogFragment dialogFragment = null;
//        if (preference instanceof TimePreference) {
//            // Create a new instance of TimePreferenceDialogFragment with the key of the related
//            // Preference
//            dialogFragment = TimePreferenceDialogFragmentCompat.newInstance(preference.getKey());
//        }


//        if (dialogFragment != null) {
//            // The dialog was created (it was one of our custom Preferences), show the dialog for it
//            dialogFragment.setTargetFragment(this, 0);
//            dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
//                    ".PreferenceFragment.DIALOG");
//        } else {
        // Dialog creation could not be handled here. Try with the super method.
        super.onDisplayPreferenceDialog(preference);
//        }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        if (preference.getKey().equals(PreferencesHelper.PREF_MAIN_SCREEN)) {
            spe.putString(PreferencesHelper.PREF_MAIN_SCREEN, newValue.toString()).apply();
        }

        if (preference.getKey().equals(PreferencesHelper.PREF_GRADE_NOTIFY)) {
            spe.putString(PreferencesHelper.PREF_GRADE_NOTIFY, newValue.toString()).apply();
        }

        setPreferenceSummary(preference, newValue);

        return true;
    }

    private void bindPreferenceSummaryToValue() {
        Preference pref1 = findPreference(PreferencesHelper.PREF_MAIN_SCREEN);
        pref1.setOnPreferenceChangeListener( this );
        setPreferenceSummary( pref1, PreferenceManager.getDefaultSharedPreferences( pref1.getContext()).getString(pref1.getKey(), "0") );

        Preference pref2 = findPreference( PreferencesHelper.PREF_SCHEDULE_TYPE );
        pref2.setOnPreferenceChangeListener( this );
        setPreferenceSummary( pref2, PreferenceManager.getDefaultSharedPreferences(pref2.getContext()).getString(pref2.getKey(), "0"));
    }

    private void setPreferenceSummary(Preference preference, Object value) {

        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;

            int prefIndex = listPreference.findIndexOfValue(stringValue);

//            if (prefIndex >=0){
            preference.setSummary(listPreference.getEntries()[prefIndex]);
//            }
        }

//        if (preference instanceof SwitchPreferenceCompat){
//            SwitchPreferenceCompat switchPreference = (SwitchPreferenceCompat) preference;
//            int prefIndex = listPreference.findIndexOfValue(stringValue);
//
//            if (prefIndex >=0){
//                preference.setSummary(listPreference.getEntries()[prefIndex]);
//            }
//        }

//        if (preference instanceof EditTextPreference) {
//            EditTextPreference etp = (EditTextPreference) pref;
//            preference.setSummary(etp.getText());
//        }
    }

    @Override
    public void onPause() {
        spe.commit();
        super.onPause();
    }
}
