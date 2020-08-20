package edu.tacoma.uw.finalproject;
/**
 * display all the notes under the list view and allow the user to access to the note detail
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import edu.tacoma.uw.finalproject.model.Note;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoteDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoteListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    /**
     * contain the list of notes that have been saved by user
     */
    private List<Note> mNoteList;
    /**
     * use to display the item in list view
     */
    private RecyclerView mRecyclerView;

    /**
     * create all the interface for the UI and launch the NoteAddFragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNoteAddFragment();
            }
        });
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.item_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);
    }

    /**
     * call and launch the NoteAddFragment class for the add floating button
     */
    private void launchNoteAddFragment() {
        NoteAddFragment noteAddFragment = new NoteAddFragment();

        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, noteAddFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra(NoteDetailActivity.ADD_NOTE, true);
            startActivity(intent);
        }
    }

    /**
     * Pass in the urls to get the list from JSONObject
     */
    @Override
    protected void onResume(){
        super.onResume();
        new NotesTask().execute(getString(R.string.get_Notes));
        setupRecyclerView(mRecyclerView);
    }

    /**
     * display the data on the UI
     * @param recyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if(mNoteList != null){
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter
                    (this, mNoteList, mTwoPane));
        }

    }

    /**
     * This class display all the items under list view
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        /**
         * The class that will use recyclerview to display the list
         */
        private final NoteListActivity mParentActivity;
        /**
         * Contain the list of Note that have been saved by the user
         */
        private final List<Note> mValues;
        /**
         * Whether or not the activity is in two-pane mode, i.e. running on a tablet
         * device.
         */
        private final boolean mTwoPane;
        /**
         * access to the username that have been save
         */
        public SharedPreferences mSharedPreferences;
        /*
         * The file stores user information
         */
        public final static String SIGN_IN_FILE_PREFS = "edu.tacoma.uw.finalproject.sign_in_file_prefs";


        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note item = (Note) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(NoteDetailFragment.ARG_ITEM_ID, item);
                    NoteDetailFragment fragment = new NoteDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NoteDetailActivity.class);
                    intent.putExtra(NoteDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                }
                //mEmailList.add("Hello");
                //Toast.makeText(NoteListActivity.this,"Hello" + getEmailList(), Toast.LENGTH_SHORT).show();
            }
        };

        /**
         * COnstructor to initialize all the fields
         * @param parent
         * @param items
         * @param twoPane
         */
        SimpleItemRecyclerViewAdapter(NoteListActivity parent,
                                      List<Note> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }
        /*
        create the view for the activty
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note_content, parent, false);
            return new ViewHolder(view);
        }

        /**
         * setup the listview to display the ID and Person name
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            mSharedPreferences = getSharedPreferences(SIGN_IN_FILE_PREFS, Context.MODE_PRIVATE);
            String username = mSharedPreferences.getString("username",null);
            if(mValues.get(position).getUsername().equalsIgnoreCase(username)){
                holder.mIdView.setText(mValues.get(position).getNoteId());
                holder.mContentView.setText(mValues.get(position).getNoteWho());

            }

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        /**
         * count the items in mvalues list
         * @return int size
         */
        @Override
        public int getItemCount() {
            return mValues.size();
        }


        private boolean differentDays(String start) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try{
                Date from = sdf.parse(start);
                Date current = new Date();
                long diffInMillies = Math.abs(current.getTime() - from.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff < 14){
                    return true;
                }
            }catch (ParseException e){

            }
            return false;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
    /**
     * class that take the urls string, read the urls and return the json array.
     */
    private class NotesTask extends AsyncTask<String, Void, String> {
        /**
         * take and read the urls to text string
         * @param urls
         * @return string
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        /**
         * take the string that have been read from urls and add to the note list
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mNoteList = Note.parseNoteJson(
                            jsonObject.getString("notes"));
                    if(!mNoteList.isEmpty()){
                        setupRecyclerView((RecyclerView) mRecyclerView);
                    }
                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }





}