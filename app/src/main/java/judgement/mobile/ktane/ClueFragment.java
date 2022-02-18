package judgement.mobile.ktane;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClueFragment newInstance(String param1, String param2) {
        ClueFragment fragment = new ClueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clue, container, false);
    }
}

/*namespace Coordinates
{
    sealed class Clue
    {
        public string Text { get; private set; }
        public bool IsCorrect { get; private set; }
        public bool IsChinese { get; private set; }
        public int FontSize { get; private set; }
        public string AltText { get; private set; }
        public int? System { get; private set; }
        public Clue(string text, bool correct, bool isChinese, int fontSize, int? system = null, string altText = null)
        {
            Text = text;
            IsCorrect = correct;
            IsChinese = isChinese;
            FontSize = fontSize;
            System = system;
            AltText = altText;
        }

        public string LoggingText
        {
            get
            {
                return (Text + (AltText == null ? null : " (" + AltText + ")")).Replace("\n", " ");
            }
        }
    }
}

 */