package co.simil.androidtictactoe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        final ListPreference difficultyLevelPref = (ListPreference) findPreference("difficulty_level");
        String initDifficulty = prefs.getString("difficulty_level", getResources().getString(R.string.difficulty_expert));
        difficultyLevelPref.setSummary((CharSequence) initDifficulty);

        difficultyLevelPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                // Display the selected difficulty
                difficultyLevelPref.setSummary((CharSequence) newValue);

                // Since we are handling the pref, we must save it
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("difficulty_level", newValue.toString());
                ed.commit();

                return true;
            }
        });


        final EditTextPreference victoryMessagePref = (EditTextPreference) findPreference("victory_message");
        String initVictoryMessage = prefs.getString("victory_message", getResources().getString(R.string.no_victory_yet));
        victoryMessagePref.setText(initVictoryMessage);
        victoryMessagePref.setSummary((CharSequence) initVictoryMessage);

        victoryMessagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                // Since we are handling the pref, we must save it
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("victory_message", newValue.toString());
                ed.commit();

                // Display the typed victory message
                victoryMessagePref.setText(newValue.toString());
                victoryMessagePref.setSummary((CharSequence) newValue.toString());

                return true;
            }
        });
    }
}
