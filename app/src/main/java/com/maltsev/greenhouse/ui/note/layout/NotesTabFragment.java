package com.maltsev.greenhouse.ui.note.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.note.Menu;
import com.maltsev.greenhouse.ui.note.adapter.RecyclerViewAdapterNotes;
import com.maltsev.greenhouse.ui.greenhouse.model.Note;
import com.maltsev.greenhouse.ui.plant.database.PlantDatabaseQueryClass;

import java.util.ArrayList;

public class NotesTabFragment extends Fragment {

    private PlantDatabaseQueryClass databaseQueryClass;

    private RecyclerViewAdapterNotes recyclerViewAdapterNotes;
    private ArrayList<Note> notes = new ArrayList<>();
    protected Button addNoteBtn;

    private Menu menu;

    public NotesTabFragment(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        databaseQueryClass = new PlantDatabaseQueryClass(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.notes_tab_fragment, container, false);

        if (menu == Menu.PLANT) {
            notes.addAll(databaseQueryClass.getAllNoteByPlantId(Long.parseLong(getActivity().getIntent().getExtras().get("plantId").toString())));
        } else {

        }
        addNoteBtn = getActivity().findViewById(R.id.button_add_note);
        Intent intent = new Intent(getContext(), CreateNoteActivity.class);
        intent.putExtra("name", getActivity().getIntent().getExtras().get("name").toString());
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Note note = new Note();
                        note.setDate(data.getExtras().getString("date"));
                        note.setText(data.getExtras().getString("text"));
                        note.setId(-1);

                        if (menu == Menu.PLANT) {
                            PlantDatabaseQueryClass databaseQueryClass = new PlantDatabaseQueryClass(getContext());
                            long id = databaseQueryClass.insertNote(note, Long.parseLong(getActivity().getIntent().getExtras().get("plantId").toString()));
                            if (id > 0) {
                                note.setId(id);
                            }
                        } else {

                        }
                        recyclerViewAdapterNotes.update(note);
                        recyclerViewAdapterNotes.notifyDataSetChanged();
                    }
                });
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someActivityResultLauncher.launch(intent);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_notes);
        recyclerViewAdapterNotes = new RecyclerViewAdapterNotes(getContext(), notes);
        recyclerView.setAdapter(recyclerViewAdapterNotes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_notes));

        recyclerView.addItemDecoration(divider);

        return root;
    }

}
