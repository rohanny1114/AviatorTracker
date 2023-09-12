package android.aviatortracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.aviatortracker.databinding.ActivityMainBinding;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button btnSearch;
    private Button btnSaved;
    private EditText codeSearch;
    private ImageView imgHelp;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    private AviatorDB aDB;
    private AviatorDAO aDAO;


    ArrayList<Aviator> flights = new ArrayList<>();
    AviatorViewModel aviatorModel;
    AviatorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        btnSearch = binding.btnSearch;
        btnSaved = binding.btnSaved;
        codeSearch = binding.textSearch;
        imgHelp = binding.imgHelp;

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        prefEditor = pref.edit();
        pref.getString("code", ""); // Initiation

        // Alertdialog for help message
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        imgHelp.setOnClickListener(c -> {
            builder.setTitle("How to use Flight Tracker")
                    .setMessage("Enter a airport code and click \"Search Airport\". "
                            + "The results include only flights departing today."
                            + "You can also save a selected flight data on the detail page.")
                    .setNegativeButton("OK", (dialog, clk) -> {  })
                    .create().show();
        });


        /****** << CREATE DB>> ******************************************************************/
        aDB = Room.databaseBuilder(getApplicationContext(), AviatorDB.class, "datbase-flights").build();
        aDAO = aDB.aDAO();
        // TEST to display fragment page
        Aviator testFlight = new Aviator("test",
                "KOR", "South Korea",  "B",
                "320",  "",  null,
                "CAN", "Ottawa",  "A",
                "1",  "",  null,
                "CAN1234",  "Canada Air");

        Executor threadCreate = Executors.newSingleThreadExecutor();
        threadCreate.execute(() -> {
            testFlight.id = (int) aDAO.insertFlight(testFlight); // R: create DB and save initial test data
            System.out.println("[SYSTEM] Saved initial data");
        });

        /****** << END OF CREATE DB>> ******************************************************************/


/****** << Search button OnClickEvent >> ******************************************************************/
        String key = "3859664f5d0f0dd988e922dcda8b7fbf";

        btnSearch.setOnClickListener(c -> {
            String inputCode = String.valueOf(codeSearch.getText());
            String url = "http://api.aviationstack.com/v1/flights?access_key="+URLEncoder.encode(key)+"&dep_iata="+ URLEncoder.encode(inputCode);

            // Save input into sharedPreference
            prefEditor.putString("url", url);
            prefEditor.apply();

            System.out.println("[SENT URL] " +url);

            if (inputCode.equals("")){
                Snackbar.make(this.getCurrentFocus(), "Enter the code first", Snackbar.LENGTH_LONG).show();
            } else {
                Intent resultPage = new Intent( MainActivity.this, ResultActivity.class);
                resultPage.putExtra("code", inputCode);
                resultPage.putExtra("url", url);
                startActivity(resultPage);
            }
        });
        /****** << End of Search button OnClickEvent >> ******************************************************************/



        /****** << Saved button OnClickEvent >> ******************************************************************/
        btnSaved.setOnClickListener(c -> {
            Intent resultPage = new Intent( MainActivity.this, SavedActivity.class);
            startActivity(resultPage);
        });
        /****** << End of saved button OnClickEvent >> ******************************************************************/

    }


}