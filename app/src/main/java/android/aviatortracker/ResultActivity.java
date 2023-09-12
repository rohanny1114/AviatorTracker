package android.aviatortracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.aviatortracker.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity implements AviatorAdapter.OnItemClickListener{
    private ActivityResultBinding binding;
    private TextView code;
    private RecyclerView recyclerResult;
    private LinearLayoutManager linearLayoutManager;
    private AviatorAdapter adapter;
    private ArrayList<Aviator> aviators = new ArrayList<>();
    ArrayList<Aviator> flights;
    AviatorViewModel aviatorViewModel;
    AviatorDAO aDAO;
    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        code = binding.titleResult;
        recyclerResult = binding.recyclerResult;
        aDAO = Room.databaseBuilder(getApplicationContext(),AviatorDB.class, "database-flights").build().aDAO();

        aviatorViewModel = new ViewModelProvider(this).get(AviatorViewModel.class);
        aviators = aviatorViewModel.aviators.getValue();
        if (aviators == null) {
            aviatorViewModel.aviators.postValue( aviators = new ArrayList<Aviator>());
        }


/****** << GET SHARED-REFERENCES >> ***************************************************************/
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String inputCode = bundle.getString("code");
        String inputUrl = bundle.getString("url");
        code.setText(inputCode.toUpperCase());
/****** << END OF GET SHARED-REFERENCES  >> *******************************************************/



/****** << Create RecyclerView >> *****************************************************************/
        queue = Volley.newRequestQueue(this);
        // Create the RecyclerView and Adapter inside the requestJSON success callback
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, inputUrl, null,
                (response) -> {
                    try {
                        flights = new ArrayList<>();
                        JSONArray dataArray = response.getJSONArray("data");

                        /****** << GET JSON DATA >> ***********************************************/
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject position = dataArray.getJSONObject(i);

                            String status = position.getString("flight_status");
                            JSONObject srcObject = position.getJSONObject("departure");
                            String srcName = srcObject.getString("airport");
                            String srcCode = srcObject.getString("iata");
                            String srcTerminal = srcObject.getString("terminal");
                            String srcGate = srcObject.getString("gate");
                            String srcSched = srcObject.getString("scheduled");
                            String srcDelay = srcObject.getString("delay");

                            JSONObject dstObject = position.getJSONObject("arrival");
                            String dstName = dstObject.getString("airport");
                            String dstCode = dstObject.getString("iata");
                            String dstTerminal = dstObject.getString("terminal");
                            String dstGate = dstObject.getString("gate");
                            String dstSched = dstObject.getString("scheduled");
                            String dstDelay = dstObject.getString("delay");

                            JSONObject airlineObject = position.getJSONObject("airline");
                            String airline = airlineObject.getString("name");

                            JSONObject flightObject = position.getJSONObject("flight");
                            String flightCode = flightObject.getString("iata");

                            Aviator aviatorData = new Aviator(status,
                                    srcCode, srcName, srcTerminal, srcGate, srcSched, srcDelay,
                                    dstCode, dstName, dstTerminal, dstGate, dstSched, dstDelay,
                                    flightCode, airline);
                            flights.add(aviatorData);
                            aviators = flights;
                        }
                        /****** << END OF GET JSON DATA>> *****************************************/

                        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                        adapter =new AviatorAdapter(aviators, this, aDAO);
                        recyclerResult.setLayoutManager(linearLayoutManager);
                        recyclerResult.setAdapter(adapter); // Notify the adapter about the data change (adapter.notifyDataSetChanged();)

                        /****** << FRAGMENT STARTS HERE >> ****************************************/
                        aviatorViewModel.selectedAviators.observe(this,(newValue) -> {
                            System.out.println("[SYSTEM] INIT FRAGMENT for RESULT");
                            FragmentManager fMgr = getSupportFragmentManager();
                            FragmentTransaction tx = fMgr.beginTransaction().addToBackStack("");
                            ResultFragment aviatorFragment = new ResultFragment(newValue, aDAO);
                            tx.add(R.id.fragmentResult, aviatorFragment);
                            tx.commit();
                        });
                        /****** << END OF FRAGMENT >> *********************************************/

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                (error) -> {
                    System.out.println("[SYSTEM] COULD NOT CATCH THE DATA");
                });
/****** << END OF Create RecyclerView >> *****************************************************************/
        queue.add(request);
    }


    public void onAviatorSelected(Aviator selectedAviator) {
        // Handle the selected aviator here in the activity
        // For example, update the ViewModel's selectedAviator
        aviatorViewModel.selectedAviators.postValue(selectedAviator);
    }
}