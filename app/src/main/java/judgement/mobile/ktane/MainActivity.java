package judgement.mobile.ktane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static Random random;
    public static final String TAG = "judgement.mobile.ktane";
    public Map<String,  Class> nameMapping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameMapping = new HashMap<String, Class>();
        nameMapping.put(getString(R.string.set_module_name), SetModule.class);
        nameMapping.put(getString(R.string.morse_module_name), MorseModule.class);
        random = new Random();
        setContentView(R.layout.activity_main);
    }

    public void loadModule(View view) {
        String text = ((Button)view).getText().toString();
        if (nameMapping.containsKey(text)) {
            startActivity(new Intent(this, nameMapping.get(text)));
        }
    }
}