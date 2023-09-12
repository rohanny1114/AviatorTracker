package android.aviatortracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.aviatortracker.databinding.ActivityResultFragmentBinding;


public class ResultFragment extends Fragment {
    private Button saveBtn;

    private Aviator selectedFlight;

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
    ArrayList<Aviator> flights;

    private AviatorDAO aDAO;
    public ResultFragment(Aviator aviator){
        selectedFlight = aviator;
    }

    public ResultFragment(Aviator aviator, AviatorDAO aDAO) {
        selectedFlight = aviator;
        this.aDAO = aDAO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ActivityResultFragmentBinding binding = ActivityResultFragmentBinding.inflate(inflater, container, false);

        aviatorModel = new ViewModelProvider(this).get(AviatorViewModel.class);
        flights = aviatorModel.aviators.getValue();
        if(flights == null){
            aviatorModel.aviators.postValue( flights = new ArrayList<Aviator>());
        }

        binding.detailFlight.setText(selectedFlight.getFlightCode());
        saveBtn = binding.saveDetailBtn;

        saveBtn.setOnClickListener( c ->  {
            saveSelectedFlight(aDAO);
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
    /****** << OnClickEvent to save selected flight >> ******************************************************************/
    private void saveSelectedFlight(AviatorDAO aDAO) {
        System.out.println("[SYSTEM] CLICKED BTN: saveSelectedFlight()");

        Aviator selectedFlight = new Aviator(selectedStatus,
                selectedSrcCode, selectedSrcAirport,  selectedSrcTerminal,
                selectedSrcGate,  selectedSrcSched,  selectedSrcDelay,
                selectedDstCode,  selectedDstAirport,  selectedDstTerminal,
                selectedDstGate,  selectedDstSched,  selectedDstDelay,
                selectedFlightCode,  selectedAirline);


        Executor threadAdd = Executors.newSingleThreadExecutor();
        threadAdd.execute(() -> {
            selectedFlight.id = (int) aDAO.insertFlight(selectedFlight);
            System.out.println("[SYSTEM] Saved new flight");
        });
        flights.add(selectedFlight);
        System.out.println("[SYSTEM] SAVED FLIGHT: "+flights.get(0).flightCode);

        CharSequence text = "Saved this flight";
        Toast.makeText( getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
