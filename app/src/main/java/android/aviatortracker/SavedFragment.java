package android.aviatortracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.aviatortracker.databinding.ActivitySavedFragmentBinding;

public class SavedFragment extends Fragment {
    private Button deleteBtn;
    private Aviator selectedFlight;
    public SavedFragment(Aviator aviator){
        selectedFlight = aviator;
    }

    String selectedStatus;
    String selectedSrcCode;
    String selectedSrcAirport;
    String selectedSrcTerminal;
    String selectedSrcGate;
    String selectedSrcSched;
    String selectedSrcDelay;
    String selectedDstCode;
    String selectedDstAirport;
    String selectedDstTerminal;
    String selectedDstGate;
    String selectedDstSched;
    String selectedDstDelay;
    String selectedFlightCode;
    String selectedAirline;
    AviatorViewModel aviatorModel;
    AviatorAdapter adapter;
    ArrayList<Aviator> flights;

    private AviatorDAO aDAO;

    public SavedFragment(Aviator aviator, AviatorDAO aDAO, AviatorAdapter adapter) {
        selectedFlight = aviator;
        this.aDAO = aDAO;
        this.adapter = adapter;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ActivitySavedFragmentBinding binding = ActivitySavedFragmentBinding.inflate(inflater, container, false);

        aviatorModel = new ViewModelProvider(this).get(AviatorViewModel.class);
        flights = aviatorModel.aviators.getValue();
        if(flights == null){
            aviatorModel.aviators.postValue( flights = new ArrayList<Aviator>());
        }

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            List<Aviator> allFlights = aDAO.getAllFlights();
            flights.addAll(allFlights);
        });


        binding.detailFlight.setText(selectedFlight.getFlightCode());
        deleteBtn = binding.deleteDetailBtn;

        deleteBtn.setOnClickListener( c ->  {
            deleteSelectedFlight(aDAO);
        });

        selectedStatus = selectedFlight.getStatus();
        selectedSrcCode = selectedFlight.getSrcCode();
        selectedSrcAirport = selectedFlight.getSrcName();
        selectedSrcTerminal = selectedFlight.getSrcTerminal();
        selectedSrcGate = selectedFlight.getSrcGate();
        selectedSrcSched = selectedFlight.getSrcSched();
        selectedSrcDelay =selectedFlight.getSrcSched();
        selectedDstCode = selectedFlight.getDstCode();
        selectedDstAirport = selectedFlight.getDstName();
        selectedDstTerminal = selectedFlight.getDstTerminal();
        selectedDstGate = selectedFlight.getDstGate();
        selectedDstSched = selectedFlight.getDstSched();
        selectedDstDelay = selectedFlight.getDstDelay();
        selectedFlightCode = selectedFlight.getFlightCode();
        selectedAirline = selectedFlight.getAirline();

        binding.srcAirport.setText("Airport: "+selectedSrcAirport);
        binding.srcTerminal.setText("Terminal: "+selectedSrcTerminal);
        binding.srcGate.setText("Gate: "+selectedSrcGate);
        binding.srcSchduled.setText("Scheduled: "+selectedSrcSched);
        binding.srcDelay.setText("Delay: "+selectedSrcDelay);

        binding.dstAirport.setText("Airport: "+selectedDstAirport);
        binding.dstTerminal.setText("Terminal: "+selectedDstTerminal);
        binding.dstGate.setText("Gate: "+selectedDstGate);
        binding.dstSchduled.setText("Scheduled: "+selectedDstSched);

        return binding.getRoot();
    }
    /****** << OnClickEvent to delete selected flight >> ******************************************************************/
    private void deleteSelectedFlight(AviatorDAO aDAO) {
        System.out.println("[SYSTEM] CLICKED BTN: deleteSelectedFlight()");

        Executor threadDelete = Executors.newSingleThreadExecutor();
        threadDelete.execute(() -> {
            flights.remove(selectedFlight);
            aDAO.deleteFlight(selectedFlight);
            adapter.setAviators(flights);
//                adapter.notifyDataSetChanged();
        });

        Snackbar.make(requireView(), "Deleted the Flight", Snackbar.LENGTH_SHORT).show();

        // Close the fragment and return to the RecyclerView
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(this).commit();
    }
}

