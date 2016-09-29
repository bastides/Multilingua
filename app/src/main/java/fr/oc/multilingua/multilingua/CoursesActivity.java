package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;
import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;
import fr.oc.multilingua.multilingua.sqlite.User;

public class CoursesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("close"));

        DBHelper db = new DBHelper(this);
        List<Course> coursesList = db.selectAllCourses();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseAdapter adapter = new CourseAdapter(coursesList);
        recyclerView.setAdapter(adapter);

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
        navigationView.getMenu().getItem(0).setChecked(true);

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
        getMenuInflater().inflate(R.menu.courses, menu);
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

        } else if (id == R.id.nav_quiz) {
            Intent intent = new Intent(CoursesActivity.this, QuizActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_appointment) {
            Intent intent = new Intent(CoursesActivity.this, AppointmentsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mail) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "info@multilingua.fr", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'information");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Votre message..");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } else if (id == R.id.nav_disconnection) {
            UserPreferencesManager.getInstance(CoursesActivity.this).saveId(0);
            UserPreferencesManager.getInstance(CoursesActivity.this).saveEmail(null);
            Intent intent = new Intent(CoursesActivity.this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
