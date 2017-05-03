package csc214.project03.readynotes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import csc214.project03.readynotes.model.Note;
import csc214.project03.readynotes.model.NotesList;
import csc214.project03.readynotes.recycler.NoteAdapter;

public class MainActivity extends AppCompatActivity {

    private static String FRAGTAG = "FRAGMENT TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            MainFragment fragment = new MainFragment();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.frame_main_act, fragment, FRAGTAG).commit();
        }
        else {
            MainFragment fragment = (MainFragment)getFragmentManager().findFragmentByTag(FRAGTAG);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        boolean handled;
        switch (item.getItemId()) {
            case R.id.menu_main_view:
                MainFragment replace = new MainFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_main_act, replace)
                        .commit();
                handled = true;
                break;
            case R.id.menu_sort_view:
                ListFragment replace2 = new ListFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_main_act, replace2)
                        .commit();
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

}
