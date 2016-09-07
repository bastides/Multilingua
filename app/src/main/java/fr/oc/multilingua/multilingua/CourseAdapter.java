package fr.oc.multilingua.multilingua;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
        private Course _currentCourse;

        public CourseViewHolder(View itemView) {
            super(itemView);
            _title = ((TextView) itemView.findViewById(R.id.course_title));
            _description = ((TextView) itemView.findViewById(R.id.course_description));
        }

        public void setCourse(Course course) {
            this._currentCourse = course;
            _title.setText(this._currentCourse.get_title());
            _description.setText(this._currentCourse.get_description());
        }
    }
}