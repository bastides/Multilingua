package fr.oc.multilingua.multilingua;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.List;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;
import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;
import fr.oc.multilingua.multilingua.sqlite.Quiz;
import fr.oc.multilingua.multilingua.sqlite.User;

public class QuizActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager _viewPager;

    public class QuizAdapter extends PagerAdapter {

        private List<Quiz> _quizList;

        public QuizAdapter(List<Quiz> quizList) { this._quizList = quizList; }

        @Override
        public int getCount() {
            return _quizList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.quiz_item_pager, container, false);

            final TextView question = (TextView) rootView.findViewById(R.id.question);
            final Button previous = (Button) rootView.findViewById(R.id.previous);
            final Button next = (Button) rootView.findViewById(R.id.next);
            final Button finish = (Button) rootView.findViewById(R.id.finish);
            final RadioGroup radioChoice = (RadioGroup) rootView.findViewById(R.id.btn_radio);
            final RadioButton radioTrue = (RadioButton) rootView.findViewById(R.id.radio_true);
            final RadioButton radioFalse = (RadioButton) rootView.findViewById(R.id.radio_false);
            final Button answer = (Button) rootView.findViewById(R.id.answer);

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _viewPager.setCurrentItem(position - 1, true);
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _viewPager.setCurrentItem(position + 1, true);
                }
            });

            answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int valueOfRadioChoice = radioChoice.getCheckedRadioButtonId();
                    Quiz quiz = _quizList.get(position);
                    if (valueOfRadioChoice != radioTrue.getId() && valueOfRadioChoice != radioFalse.getId()) {
                        AlertDialog.Builder error = new AlertDialog.Builder(QuizActivity.this);
                        error.setTitle("Attention");
                        error.setMessage("Vous devez choisir Vrai ou Faux avant de voir la réponse.");
                        error.setPositiveButton(android.R.string.ok, null);
                        error.show();
                    } else {
                        if (valueOfRadioChoice == radioTrue.getId()) {
                            if (quiz.get_answer() == 1) {
                                alertGoodAnswer();
                            } else {
                                alertWrongAnswer();
                            }
                        } else {
                            if (quiz.get_answer() == 0) {
                                alertGoodAnswer();
                            } else {
                                alertWrongAnswer();
                            }
                        }
                    }

                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder error = new AlertDialog.Builder(QuizActivity.this);
                    error.setTitle("Bravo");
                    error.setMessage("Vous avez terminé votre quiz du jour. Revenez demain tester vos connaissances !");
                    error.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), CoursesActivity.class);
                            startActivity(intent);
                        }
                    });
                    error.show();
                }
            });

            final Quiz quiz = _quizList.get(position);
            question.setText(quiz.get_question());

            if (position == 0) {
                previous.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                finish.setVisibility(View.GONE);
            } else if (position == getCount() - 1) {
                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                finish.setVisibility(View.VISIBLE);
            } else {
                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                finish.setVisibility(View.GONE);
            }

            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("close"));

        int idCourses[];

        DBHelper db = new DBHelper(this);
        List<Course> completeCourses = db.selectCompleteCourses();
        idCourses = new int[completeCourses.size()];
        for (int i = 0; i < completeCourses.size(); i++) {
            int id = completeCourses.get(i).get_id();
            idCourses[i] = id;
        }
        SecureRandom random = new SecureRandom();
        int randomQuiz = random.nextInt(idCourses.length);
        List<Quiz> _quizList = db.selectQuizByCourseId(idCourses[randomQuiz]);

        if (_quizList != null) {
            _viewPager = (ViewPager) findViewById(R.id.quir_pager);
            _viewPager.setOffscreenPageLimit(_quizList.size());
            _viewPager.setAdapter(new QuizAdapter(_quizList));
        } else {
            AlertDialog.Builder error = new AlertDialog.Builder(QuizActivity.this);
            error.setTitle("Attention");
            error.setMessage("Vous devez avoir fait au moins un cours pour faire les quiz.");
            error.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(QuizActivity.this, CoursesActivity.class);
                    startActivity(intent);
                }
            });
            error.show();
        }

        User user = db.selectUser(UserPreferencesManager.getInstance(this).loadEmail());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setChecked(true);

        View headerView = navigationView.getHeaderView(0);
        TextView heaerFirstname = (TextView) headerView.findViewById(R.id.header_firstname);
        heaerFirstname.setText(user.get_firstName());
        TextView headerEmail = (TextView) headerView.findViewById(R.id.header_email);
        headerEmail.setText(user.get_email());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_courses) {
            Intent intent = new Intent(QuizActivity.this, CoursesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_quiz) {

        } else if (id == R.id.nav_appointment) {
            Intent intent = new Intent(QuizActivity.this, AppointmentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mail) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "info@multilingua.fr", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'information");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Votre message..");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } else if (id == R.id.nav_disconnection) {
            UserPreferencesManager.getInstance(QuizActivity.this).saveId(0);
            UserPreferencesManager.getInstance(QuizActivity.this).saveEmail(null);
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_true:
                if (checked)
                    // true rules
                    break;
            case R.id.radio_false:
                if (checked)
                    // false rules
                    break;
        }
    }

    public void alertGoodAnswer() {
        AlertDialog.Builder error = new AlertDialog.Builder(QuizActivity.this);
        error.setTitle("Bravo !");
        error.setMessage("C'est la bonne réponse !");
        error.setPositiveButton(android.R.string.ok, null);
        error.show();
    }

    public void alertWrongAnswer() {
        AlertDialog.Builder error = new AlertDialog.Builder(QuizActivity.this);
        error.setTitle("Dommage");
        error.setMessage("Mauvaise réponse !");
        error.setPositiveButton(android.R.string.ok, null);
        error.show();
    }
}
