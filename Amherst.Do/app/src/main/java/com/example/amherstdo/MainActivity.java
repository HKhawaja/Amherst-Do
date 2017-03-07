package com.example.amherstdo;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Task> tasks;
    private MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        System.out.println ("here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<Task>();
        listView = (ListView) findViewById(R.id.taskList);
        tasks = populate(tasks);
        System.out.println (tasks.get(3));

        //create the List Adapter and set it here
        adapter = new MyCustomAdapter(this, R.layout.task_info, tasks);
        listView.setAdapter(adapter);

        //set on item Click listener that displays details of each Task when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //when clicked, show a Toast with the data and Priority
                Task task = (Task) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Description: " + task.getDescription() + ". Priority: "+ task.getPriority(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void submit (View v) {

    }

    public void clear (View v) {
        for (Task x: tasks) {
            x.setSelected(false);
        }
        adapter = new MyCustomAdapter(this, R.layout.task_info, tasks);
        listView.setAdapter(adapter);
    }

    public static ArrayList<Task> populate (ArrayList<Task> list) {
        System.out.println ("here");
        //create Tasks
        //Task 1
        Task exam = new Task ("CS 342 Take Home", "Make take home project application", "High");
        list.add(exam);

        //Task 2
        Task paper = new Task("Phil-362 Paper", "Write Paper on Marx", "High");
        list.add(paper);

        //Task 3
        Task paper2 = new Task("Fren-207 Paper", "Write Paper on Gide's Les Caves du Vatican", "Medium");
        list.add(paper2);

        //Task 4
        Task exam2 = new Task("Fren-207 Exam", "Write Fren-207 Midterm", "High");
        list.add(exam2);

        //Task 5
        Task opt = new Task("Complete OPT Form", "Complete form I-765 and submit to UCSIS", "Medium");
        list.add(opt);

        System.out.println ("Populated tasks!");
        System.out.println (list);
        return list;
    }

    private static class Task {

        private String name;
        private String description;
        private String priority;
        private boolean selected;

        public Task (String name, String description, String priority) {
            this.name = name;
            this.description = description;
            this.priority = priority;
            selected = false;
        }

        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return description;
        }

        public String getPriority() {
            return priority;
        }

        public boolean isSelected() { return selected;}

        public void setSelected(boolean selected) { this.selected = selected;}

        public String toString() {
            return name + " " + description + " " + priority;
        }

    }

    private static class TaskViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public TaskViewHolder () {}

        public TaskViewHolder (CheckBox checkBox, TextView textView) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public TextView getTextView() {return textView;}
    }

    private class ClickBoxListener implements View.OnClickListener {

        private TaskViewHolder holder;

        public ClickBoxListener (TaskViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            CheckBox cb = (CheckBox) v;
            Task task = (Task) cb.getTag();
            task.setSelected(cb.isChecked());

            if (cb.isChecked()) {
                TextView text = holder.getTextView();
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                TextView text = holder.getTextView();
                text.setPaintFlags(text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }

        }
    }
    private class MyCustomAdapter extends ArrayAdapter<Task> {

        private ArrayList<Task> tasks;

        public MyCustomAdapter (Context context, int textViewResourceId, ArrayList<Task> taskList) {
            super(context, textViewResourceId, taskList);
            this.tasks = taskList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TaskViewHolder holder = null;
            Task task = (Task) this.getItem (position);

            // The child views in each row.
            CheckBox checkBox;
            TextView textView;

            // Create a new row view

//            System.out.println ("Null");
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_info, null);

            textView = (TextView) convertView.findViewById(R.id.taskName);
            checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            holder = new TaskViewHolder(checkBox,textView);

            //tag each row with it's child view
            convertView.setTag(holder);

            //add onCheckedListener here to each View Holder
            holder.getCheckBox().setOnClickListener(new ClickBoxListener(holder));

            //uncheck any selected checkboxes
//            holder.getCheckBox().setSelected(false);

            // Tag the CheckBox with the Task it is displaying, so that we can
            // access the task in onClick() when the CheckBox is toggled.
            checkBox.setTag(task);

            // Display planet data
            checkBox.setChecked(task.isSelected());
            textView.setText(task.getName());

            return convertView;
        }
    }
}
