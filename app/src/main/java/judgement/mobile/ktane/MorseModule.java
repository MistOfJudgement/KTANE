package judgement.mobile.ktane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MorseModule extends AppCompatActivity {
    public static final int[] possibleFrequencies = new int[] {
            505, 515, 522, 532, 535, 542, 545, 552, 555, 565, 572, 575, 582, 592, 595, 600
    };
    private static final Map<Character, String> codeMap = new HashMap<Character, String>() {
        {
            put('A', ".-");
            put('B', "-...");
            put('C', "-.-.");
            put('D', "-..");
            put('E', ".");
            put('F', "..-.");
            put('G', "--.");
            put('H', "....");
            put('I', "..");
            put('J', ".---");
            put('K', "-.-");
            put('L', ".-..");
            put('M', "--");
            put('N', "-.");
            put('O', "---");
            put('P', ".--.");
            put('Q', "--.-");
            put('R', ".-.");
            put('S', "...");
            put('T', "-");
            put('U', "..-");
            put('V', "...-");
            put('W', ".--");
            put('X', "-..-");
            put('Y', "-.--");
            put('Z', "--..");
        }
    };
    private static final Map<String, Integer> answerKey = new HashMap<String, Integer>() {{
        put("shell", 505);
        put("halls", 515);
        put("slick", 522);
        put("trick", 532);
        put("boxes", 535);
        put("leaks", 542);
        put("strobe",545);
        put("bistro",552);
        put("flick", 555);
        put("bombs", 565);
        put("break", 572);
        put("brick", 575);
        put("steak", 582);
        put("sting", 592);
        put("vector",595);
        put("beats", 600);
    }};
    private static final int DotLength= 250; //milliseconds
    private boolean solved;
    String answer;
    Button freqLeftShiftButton, freqRightShiftButton;
    SeekBar freqSlider;
    View morseLight;
    ViewPager2 frequencyViewPager;
    SolveCounterFragment solveCount;
    Thread morseBlinkThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_module);
        freqLeftShiftButton = findViewById(R.id.freq_left_button);
        freqRightShiftButton = findViewById(R.id.freq_right_button);

        freqLeftShiftButton.setOnClickListener(view -> frequencyLeft(view));
        freqRightShiftButton.setOnClickListener(view -> frequencyRight(view));

        freqSlider = findViewById(R.id.frequency_slider);
        morseLight = findViewById(R.id.morse_light);
        frequencyViewPager = findViewById(R.id.freq_view_pager);
        solved = false;
        frequencyViewPager.setAdapter(new FrequencyFragmentStateAdapter(this));
        solveCount = (SolveCounterFragment) getSupportFragmentManager().findFragmentById(R.id.morse_solve_count);
        generateNewAnswer();

    }
    private int selectedFreqIndex() {
        return frequencyViewPager.getCurrentItem();
    }
    private void generateNewAnswer() {
        List<String> keysAsArray = new ArrayList<>(answerKey.keySet());
        answer = keysAsArray.get(MainActivity.random.nextInt(keysAsArray.size()));
        morseBlinkThread = new MorsePlayer();
        morseBlinkThread.start();
        Log.i("MorseModule", "Generated word " + answer + ". Frequency is " + answerKey.get(answer));
    }

    public void frequencyLeft(View view) {
        if(selectedFreqIndex() != 0) {
            frequencyViewPager.setCurrentItem(selectedFreqIndex() - 1, true);
        }
    }


    public void frequencyRight(View view) {
        if(selectedFreqIndex() != possibleFrequencies.length-1) {
            frequencyViewPager.setCurrentItem(selectedFreqIndex() + 1, true);
        }
    }

    public void transmit(View view) {
        if (answerKey.get(answer) == possibleFrequencies[selectedFreqIndex()]) {
            morseBlinkThread.interrupt();
            solveCount.solveModule();

            generateNewAnswer();

        } else {
            solveCount.strike();
        }
    }
    class FrequencyFragmentStateAdapter extends FragmentStateAdapter {


        public FrequencyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MorseFrequencyFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return MorseModule.possibleFrequencies.length;
        }


    }

    class MorsePlayer extends Thread{
        String word;
        public MorsePlayer() {
            word = answer;

        }
        public void run() {
            try {
                Thread.sleep(500);
                while (!isInterrupted()) {
                    for (char c : word.toUpperCase(Locale.ROOT).toCharArray()) {
                        String code = codeMap.get(c);
                        for (char symbol : code.toCharArray()) {
                            runOnUiThread(() -> morseLight.setBackgroundColor(getResources().getColor(R.color.morse_lit, getTheme())));
                            if (interrupted()) {

                            }
                            Thread.sleep(symbol == '.' ? DotLength : DotLength * 3);
                            runOnUiThread(() -> morseLight.setBackgroundColor(getResources().getColor(R.color.morse_unlit, getTheme())));
                            Thread.sleep(DotLength);
                        }
                        Thread.sleep(DotLength * 3);
                    }
                    Thread.sleep(DotLength * 6);
                }
            } catch (InterruptedException ex) {
                Log.i("Exception", "morse thread interrupted: " + ex.getLocalizedMessage());
            }
        }
    }
}