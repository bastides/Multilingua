package fr.oc.multilingua.multilingua;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;
import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;
import fr.oc.multilingua.multilingua.sqlite.Quiz;

public class CourseActivity extends AppCompatActivity {

    public static final String COURSE_TITLE = "title";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_COURSE = "course";

    private TextToSpeech _tts;

    private long _currentTimestamp = System.currentTimeMillis() / 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        final String title = getIntent().getStringExtra(COURSE_TITLE);
        String description = getIntent().getStringExtra(COURSE_DESCRIPTION);
        final String course = getIntent().getStringExtra(COURSE_COURSE);

        setTitle(title);

        TextView courseTitle = (TextView) findViewById(R.id.course_title);
        TextView courseDescription = (TextView) findViewById(R.id.course_description);
        final TextView courseCourse = (TextView) findViewById(R.id.course_course);

        courseTitle.setText(title);
        courseDescription.setText(description);
        courseCourse.setText(course);

        _tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = _tts.setLanguage(Locale.FRANCE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

        Button btnSpeaker = (Button) findViewById(R.id.speaker);
        btnSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak(course);
            }
        });

        Button btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseActivity.this, CoursesActivity.class);
                startActivity(intent);
                if (_tts != null) {
                    _tts.stop();
                    _tts.shutdown();
                }
            }
        });

        DBHelper db = new DBHelper(this);
        db.updateCourseComplete(title, 1);
        UserPreferencesManager.getInstance(this).saveLastCourse(_currentTimestamp);
    }

    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            _tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            _tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onDestroy() {
        if (_tts != null) {
            _tts.stop();
            _tts.shutdown();
        }
        super.onDestroy();
    }
}
