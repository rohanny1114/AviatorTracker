package android.aviatortracker;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AviatorViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Aviator>> aviators = new MutableLiveData< >();
    public MutableLiveData<Aviator> selectedAviators = new MutableLiveData< >();

}
