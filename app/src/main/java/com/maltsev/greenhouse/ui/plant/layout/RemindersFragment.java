package com.maltsev.greenhouse.ui.plant.layout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.model.Note;
import com.maltsev.greenhouse.ui.note.adapter.RecyclerViewAdapterNotes;
import com.maltsev.greenhouse.ui.plant.adapter.RecyclerViewAdapterReminders;

import java.util.ArrayList;

public class RemindersFragment extends Fragment {

    private RecyclerViewAdapterReminders recyclerViewAdapterReminders;
    private ArrayList<Integer> notes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_reminders, container, false);

        for (int i = 0; i < 10; i++) {
            notes.add(i);
        }
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_reminders);
        recyclerViewAdapterReminders = new RecyclerViewAdapterReminders(getContext(), notes);
        recyclerView.setAdapter(recyclerViewAdapterReminders);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_notes));

        recyclerView.addItemDecoration(divider);

        return root;
    }
}
