package judgement.mobile.ktane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MorseModule extends AppCompatActivity {
    private static final int[] possibleFrequencies = new int[] {
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
    private String answer;
    public Button freqLeftShiftButton, freqRightShiftButton;
    public SeekBar freqSlider;
    ViewPager2 frequencyViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_module);
        generateNewAnswer();

    }

    private void generateNewAnswer() {
        List<String> keysAsArray = new ArrayList<>(answerKey.keySet());
        answer = keysAsArray.get(MainActivity.random.nextInt(keysAsArray.size()));
    }

    public void frequencyLeft() {

    }
}