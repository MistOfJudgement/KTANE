package judgement.mobile.ktane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static Random random;
    public static final String TAG = "judgement.mobile.ktane";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
    startActivity(new Intent(this, SetModule.class));
    }
}