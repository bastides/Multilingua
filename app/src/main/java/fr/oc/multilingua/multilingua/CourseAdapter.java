package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;
import fr.oc.multilingua.multilingua.sqlite.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> _coursesList;

    public CourseAdapter (List<Course> coursesList) {
        this._coursesList = coursesList;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        holder.setCourse(_coursesList.get(position));
    }

    @Override
    public int getItemCount() {
        return _coursesList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView _title;
        private final TextView _description;
        private final TextView _category;
        private Course _currentCourse;

        private long _preferenceLastCourse;
        private long _currentTimestamp = System.currentTimeMillis();
        private long _24Hago = (_currentTimestamp - 86400000) / 1000;

        public CourseViewHolder(View itemView) {
            super(itemView);
            _title = ((TextView) itemView.findViewById(R.id.course_title));
            _description = ((TextView) itemView.findViewById(R.id.course_description));
            _category = ((TextView) itemView.findViewById(R.id.course_category));
            _preferenceLastCourse = UserPreferencesManager.getInstance(itemView.getContext()).loadLastCourse();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (_preferenceLastCourse < _24Hago) {
                        Intent intent = new Intent(view.getContext(), CourseActivity.class);
                        intent.putExtra(CourseActivity.COURSE_TITLE, _currentCourse.get_title());
                        intent.putExtra(CourseActivity.COURSE_DESCRIPTION, _currentCourse.get_description());
                        intent.putExtra(CourseActivity.COURSE_CATEGORY, _currentCourse.get_category());
                        intent.putExtra(CourseActivity.COURSE_COURSE, _currentCourse.get_course());
                        view.getContext().startActivity(intent);
                    } else {
                        AlertDialog.Builder error = new AlertDialog.Builder(view.getContext());
                        error.setTitle("Attention");
                        error.setMessage("Vous ne pouvez faire qu'un cours par jour.");
                        error.setPositiveButton(android.R.string.ok, null);
                        error.show();
                    }

                }
            });
        }

        public void setCourse(Course course) {
            this._currentCourse = course;
            _title.setText(this._currentCourse.get_title());
            _description.setText(this._currentCourse.get_description());
            _category.setText(this._currentCourse.get_category());
        }
    }
}
