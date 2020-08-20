package edu.tacoma.uw.finalproject;

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
     * The dummy content this fragment is presenting.
     */
    private Note mNote;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mNote = (Note) getArguments().getSerializable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null){
                appBarLayout.setTitle(mNote.getNoteEmail());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mNote != null) {
            //((TextView) rootView.findViewById(R.id.item_detail)).setText(mNote.getNoteId());
            ((TextView) rootView.findViewById(R.id.item_who)).setText(mNote.getNoteWho());
            ((TextView) rootView.findViewById(R.id.item_phone)).setText(mNote.getNotePhone());
            ((TextView) rootView.findViewById(R.id.item_email)).setText(mNote.getNoteEmail());
            ((TextView) rootView.findViewById(R.id.item_date)).setText(mNote.getNoteDate());
            ((TextView) rootView.findViewById(R.id.item_location)).setText(mNote.getNoteLocation());
        }

        return rootView;
    }
}