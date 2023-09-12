package android.aviatortracker;

import android.aviatortracker.databinding.ActivitySavedBinding;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SavedActivity extends AppCompatActivity implements AviatorAdapter.OnItemClickListener{
    private ActivitySavedBinding binding;
    AviatorViewModel aviatorViewModel;
    ArrayList<Aviator> flights = new ArrayList<>();
    private AviatorDAO aDAO;

    private RecyclerView recyclerSaved;
    private AviatorAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerSaved = binding.recyclerSaved;

        /****** << GET SAVED DATA >> ****************************************************************/
        aDAO = Room.databaseBuilder(getApplicationContext(),AviatorDB.class, "database-flights").build().aDAO();
        aviatorViewModel = new ViewModelProvider(this).get(AviatorViewModel.class);

        Executor threadAll = Executors.newSingleThreadExecutor();
        threadAll.execute(() -> {
            System.out.println("[SYSTEM] List saved flights");
            List<Aviator> allFlights = aDAO.getAllFlights();
            flights.addAll(allFlights);
        });
        /****** << END OF GET SAVED DATA >> ****************************************************************/

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter =new AviatorAdapter(flights, this::onAviatorSelected, aDAO);
        recyclerSaved.setLayoutManager(linearLayoutManager);
        recyclerSaved.setAdapter(adapter); // Notify the adapter about the data change (adapter.notifyDataSetChanged();)

        /****** << FRAGMENT STARTS HERE >> ****************************************************************/
        aviatorViewModel.selectedAviators.observe(this,(newValue) -> {
            System.out.println("[SYSTEM] INIT FRAGMENT for SAVED");
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            SavedFragment savedFragment = new SavedFragment(newValue, aDAO, adapter);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentSaved, savedFragment)
                    .commit();
        });
        /****** << END OF FRAGMENT >> ***********************************************************************/
    }

    public void onAviatorSelected(Aviator selectedAviator) {
        aviatorViewModel.selectedAviators.postValue(selectedAviator);
    }
}
