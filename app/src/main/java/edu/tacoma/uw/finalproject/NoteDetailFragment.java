package edu.tacoma.uw.finalproject;
/**
 * This class will display the detail data of each notes on the UI
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import edu.tacoma.uw.finalproject.model.Note;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link NoteListActivity}
 * in two-pane mode (on tablets) or a {@link NoteDetailActivity}
 * on handsets.
 */
public class NoteDetailFragment extends Fragment {


    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * references to Note class to get and access the note value and variable
     */
    private Note mNote;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteDetailFragment() {
    }

    /**
     * Before running, get the UI interface setup
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mNote = (Note) getArguments().getSerializable(ARG_ITEM_ID);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null){
                appBarLayout.setTitle(mNote.getNoteEmail());
            }
        }
    }

    /**
     * create the view of the note detail bu display the data in specific place
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        if (mNote != null) {
            ((TextView) rootView.findViewById(R.id.item_who)).setText(mNote.getNoteWho());
            ((TextView) rootView.findViewById(R.id.item_phone)).setText(mNote.getNotePhone());
            ((TextView) rootView.findViewById(R.id.item_email)).setText(mNote.getNoteEmail());
            ((TextView) rootView.findViewById(R.id.item_date)).setText(mNote.getNoteDate());
            ((TextView) rootView.findViewById(R.id.item_location)).setText(mNote.getNoteLocation());
        }

        return rootView;
    }
}