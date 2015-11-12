package com.example.sasha.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class NotesAdapter extends ArrayAdapter<Person> {

    private Context context;
    private List<Person> persons;


    public NotesAdapter(Context context, int notes_item, List<Person> persons) {
        super(context, R.layout.notes_item, persons);
        this.context = context;
        this.persons = persons;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.notes_item, parent, false);

        ListView listView = (ListView) parent.findViewById(R.id.list);
        TextView phoneView = (TextView) view.findViewById(R.id.tel);
        TextView namesView = (TextView) view.findViewById(R.id.name);

        namesView.setText((persons.get(position)).getName());
        phoneView.setText((persons.get(position)).getNumberTelefon());

        if (listView.isItemChecked(position)) {
            view.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.blue));
        }

        return view;
    }


}
