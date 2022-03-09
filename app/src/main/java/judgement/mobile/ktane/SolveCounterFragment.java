package judgement.mobile.ktane;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Use the {@link SolveCounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolveCounterFragment extends Fragment {


    int solvesWithoutFail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView solveCount;
    public SolveCounterFragment() {
        // Required empty public constructor
    }

    public static SolveCounterFragment newInstance() {
        SolveCounterFragment fragment = new SolveCounterFragment();
        fragment.solvesWithoutFail = 0;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solve_counter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        solveCount = view.findViewById(R.id.solveCount3);
        solveCount.setText("" + sharedPreferences.getInt("solves", 0));

    }

    public void solveModule() {
        editor.putInt("solves", sharedPreferences.getInt("solves", 0) + 1);
        editor.apply();
        solveCount.setText("" + sharedPreferences.getInt("solves", 0));
    }
    public void strike() {
        editor.putInt("solves", 0);
        editor.apply();
        solveCount.setText("0");
    }
}