package com.example.sasha.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sasha.notes.sql.DataBaseHandler;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener,MenuItem.OnMenuItemClickListener {

    private Button addButton;
    private EditText enterName;
    private EditText enterPhone;
    private NotesAdapter notesAdapter;
    private List<Person> persons;
    private Person deletePerson;
    private Button deleteButton;
    private static ListView listView;
    private DataBaseHandler dbHandler;
    private AdapterView.AdapterContextMenuInfo contextMenuInfo;
    public static final int DELETE_ID = 1;
    public static final int REFACTOR_ID = 2;
    public static final int SENT_ID = 3;
    public static final int CALL_ID = 4;

    @Override
    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onPrepareNavigateUpTaskStack(builder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.but_add);
        listView = (ListView) findViewById(R.id.list);
        deleteButton = (Button) findViewById(R.id.but_del);

        dbHandler = new DataBaseHandler(this);
        dbHandler.deleteAll();
        persons = dbHandler.getAllPerson();
        notesAdapter = new NotesAdapter(this, R.layout.notes_item, persons);
        listView.setAdapter(notesAdapter);
        listView.setOnCreateContextMenuListener(this);
        addButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                deletePerson = (Person) parent.getItemAtPosition(position);
                listView.setItemChecked(position, true);
                listView.setSelected(true);


            }

        });


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        contextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.add(0, DELETE_ID, 0, "Delete").setOnMenuItemClickListener(this);
        menu.add(0, REFACTOR_ID, 0, "Refactor").setOnMenuItemClickListener(this);
        menu.add(0, SENT_ID, 0, "Sent").setOnMenuItemClickListener(this);
        menu.add(0, CALL_ID, 0, "Call").setOnMenuItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_add:
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (MainActivity.this).getLayoutInflater();
                View layout = inflater.inflate(R.layout.add_dialog, null);
                enterName = (EditText) layout.findViewById(R.id.name_dialog);
                enterPhone = (EditText) layout.findViewById(R.id.phone_dialog);
                alert.setView(layout)
                        .setPositiveButton("Создать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!(enterName.getText().toString().equals(""))) {
                                    dbHandler.addPerson(new Person(enterName.getText().toString(), enterPhone.getText().toString()));
                                    persons = dbHandler.getAllPerson();
                                    listView.setAdapter(new NotesAdapter(MainActivity.this, R.layout.notes_item, persons));
                                } else {
                                    Toast.makeText(MainActivity.this, "Не правильно введено", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
                alert.show();

                break;

            case R.id.but_del:
                if (deletePerson != null) {

                    dbHandler.deletePerson(deletePerson);

                    persons = dbHandler.getAllPerson();
                    listView.setAdapter(new NotesAdapter(MainActivity.this, R.layout.notes_item, persons));
                    deletePerson = null;
                } else {
                    Toast.makeText(MainActivity.this, "Выберите запись", Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Person per = persons.get(acmi.position);
        switch (item.getItemId()) {
            case DELETE_ID:
                dbHandler.deletePerson(per);
                persons.remove(acmi.position);
                listView.setAdapter(new NotesAdapter(MainActivity.this, R.layout.notes_item, persons));
                break;
            case REFACTOR_ID:

                break;

            case SENT_ID:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, per.toString());
                intent.setType("*/*");
                startActivity(intent);
                break;

            case CALL_ID:
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + per.getNumberTelefon()));
                startActivity(intentCall);
        }
        return true;
    }
}
