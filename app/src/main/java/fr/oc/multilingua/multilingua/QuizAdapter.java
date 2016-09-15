package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.oc.multilingua.multilingua.sqlite.Course;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Course> _coursesList;

    public QuizAdapter (List<Course> coursesList) {
        this._coursesList = coursesList;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        holder.setCourse(_coursesList.get(position));
    }

    @Override
    public int getItemCount() {
        return _coursesList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {

        private final TextView _title;
        private Course _currentCourse;

        public QuizViewHolder(View itemView) {
            super(itemView);
            _title = ((TextView) itemView.findViewById(R.id.course_title));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), QuizExerciseActivity.class);
                    intent.putExtra(CourseActivity.COURSE_TITLE, _currentCourse.get_title());
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setCourse(Course course) {
            this._currentCourse = course;
            _title.setText(this._currentCourse.get_title());
        }
    }
}
