package judgement.mobile.ktane;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MorseFrequencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MorseFrequencyFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FREQ_INDEX = "freqInd";

    private int freqIndex;

    public MorseFrequencyFragment() {
        // Required empty public constructor
    }


    public static MorseFrequencyFragment newInstance(int freqInd) {
        MorseFrequencyFragment fragment = new MorseFrequencyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FREQ_INDEX, freqInd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            freqIndex = getArguments().getInt(ARG_FREQ_INDEX);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_morse_frequency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.frequency_text)).setText("5."+MorseModule.possibleFrequencies[freqIndex] + " MHz");
    }
}