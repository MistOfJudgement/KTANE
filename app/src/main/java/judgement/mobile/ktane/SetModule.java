package judgement.mobile.ktane;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class SetModule extends AppCompatActivity {


    public ImageButton[] _cards;
    private List<Integer> _selected;
    private SetSet _solution;
    private SetCard[] _displayedCards;
    private boolean _isSolved;
    private int[] Symbols;
    private int[] SymbolsSelected;
    public TextView solveCount;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);

        sharedPreferences = getSharedPreferences(MainActivity.TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        solveCount = findViewById(R.id.solveCount);

        _cards = new ImageButton[9];
        for(int i = 0; i < _cards.length; i++) {
            _cards[i] = findViewById(this.getResources().getIdentifier("imageButton" + i, "id", this.getPackageName()));
            _cards[i].setOnClickListener(new SetButton(i));
        }
        Symbols = new int[81];
        SymbolsSelected = new int[81];
        for (int i = 0; i < 81; i++) {
            Symbols[i] = this.getResources().getIdentifier(String.format("icon%1$c%2$c%3$c%4$c", (char) ('a' + (i % 3)), (char) ('1' + ((i / 3) % 3)), (char) ('a' + ((i / 9) % 3)), (char) ('1' + ((i / 27) % 3))), "drawable", this.getPackageName());
            SymbolsSelected[i] = this.getResources().getIdentifier(String.format("iconsel%1$c%2$c%3$c%4$c", (char) ('a' + (i % 3)), (char) ('1' + ((i / 3) % 3)), (char) ('a' + ((i / 9) % 3)), (char) ('1' + ((i / 27) % 3))), "drawable", this.getPackageName());
        }
        start();
    }
    private void solveModule() {
        editor.putInt("solves", sharedPreferences.getInt("solves", 0) + 1);
        editor.apply();
        solveCount.setText("" + sharedPreferences.getInt("solves", 0));

        //wait method

        start(); //regenerate puzzle
        _isSolved = false;
    }
    private void strike() {
        editor.putInt("solves", 0);
        editor.apply();
        solveCount.setText("0");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText(getApplicationContext(), "Activity Paused: Regenerating Puzzle", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        start();
    }
    public void start() {
        _selected = new ArrayList<>();
        _solution = SetSet.getRandom(true);
        HashSet<SetCard> cards = new HashSet<>(_solution.getCards());

            int iter = 0;
            while (cards.size() < 9) {
                SetCard newCard = SetCard.getRandom();
                if (cards.contains(newCard) || any(cards, (c) -> cards.contains(newCard.get3rdInSet(c))))
                {
                    iter++;
                    if (iter > 1000) {
                        _solution = SetSet.getRandom(true);
                        cards.clear();
                        cards.addAll(_solution.getCards());
                    }
                    continue;
                }
                cards.add(newCard);
            }


        _displayedCards = cards.toArray(new SetCard[0]);
        Collections.shuffle(Arrays.asList(_displayedCards));

        String[] fillings = new String[] {"filled", "wavy", "empty"};

        for (int i = 0; i < _displayedCards.length; i++) {
//            Log.i("aaaa", _displayedCards[i].getFilling() + "");
            Log.i("ktane-set",String.format("[S.E.T.] Icon at (module) %1$c%2$c is (manual) %3$c%4$c, %5$s, %6$d dots.",
                    (char) ('A' + i % 3), (char) ('1' + i / 3),
                    (char) ('A' + _displayedCards[i].getX()), (char) ('1' + _displayedCards[i].getY()),
                    fillings[_displayedCards[i].getFilling()], _displayedCards[i].numDots()));
            _cards[i].setImageDrawable(getDrawable(Symbols[_displayedCards[i].getIndex()]));
        }
        Log.i("ktane-set", String.format("[S.E.T.] Solution: %1$s, %2$s, %3$s.",
                coords(Arrays.asList(_displayedCards).indexOf(_solution.getOne())),
                coords(Arrays.asList(_displayedCards).indexOf(_solution.getTwo())),
                coords(Arrays.asList(_displayedCards).indexOf(_solution.getThree()))));


    }
    private boolean any(Collection<SetCard> cards, Predicate<SetCard> func) {
        for (SetCard element: cards) {
            if (func.test(element)) {
                return true;
            }
        }
        return false;
    }
    private boolean all(Collection<Integer> list, Predicate<Integer> func) {
        for (Integer i : list) {
            if (!func.test(i)) {
                return false;
            }
        }
        return true;
    }
    private String coords(int i)
    {
        return (char) ('a' + i % 3) + "" + (char) ('1' + i / 3);
    }


    class SetButton implements View.OnClickListener {
        int i;
        public SetButton(int ind) {
            i = ind;
        }
        @Override
        public void onClick(View view) {
            if (_isSolved)
            {
                return;
            }

            if (_selected.contains(i))
            {
                //Audio.PlaySoundAtTransform("Deselect", Cards[i].transform);
                //_cardSelections[i].material = CardUnselected;
                //_cardImages[i].material.mainTexture = Symbols[_displayedCards[i].Index];
                _cards[i].setImageDrawable(getDrawable(Symbols[_displayedCards[i].getIndex()]));
                _selected.remove(new Integer(i));
            }
            else
            {
                _selected.add(new Integer(i));
                if (_selected.size() == 3 && all(_selected, (s) -> _solution.getCards().contains(_displayedCards[s])))
                {
                    Log.i("ktane-set", "[S.E.T.] Module solved.");
                    //Module.HandlePass();
                    //_cardSelections[i].material = CardSelected;
                    _cards[i].setImageDrawable(getDrawable(SymbolsSelected[_displayedCards[i].getIndex()]));
                    _isSolved = true;
                    //Audio.PlaySoundAtTransform("Chime", Cards[i].transform);
                    solveModule();
                }
                else if (_selected.size() == 3)
                {
                    Log.i("[S.E.T.]", String.format("Incorrect set selected: %1$s, %2$s, %3$s.", coords(_selected.get(0)), coords(_selected.get(1)), coords(_selected.get(2))));
                    //Module.HandleStrike();
                    _selected.remove(new Integer(i));
                    strike();
                }
                else
                {
                    //_cardSelections[i].material = CardSelected;
                    _cards[i].setImageDrawable(getDrawable(SymbolsSelected[_displayedCards[i].getIndex()]));
                    //Audio.PlaySoundAtTransform("Stamp", Cards[i].transform);
                }
            }
        }
    }
}

class SetCard {

    private final int index;
    public int getIndex() {return index;}
    public int getX() {return index%3;}
    public int getY() {return (index/3)%3;}
    public int numDots() {return (index/9)%3;}
    public int getFilling() {return index/27;}

    public SetCard(int x, int y, int numDots, int filling) {index = x + 3 * (y + 3 * (numDots + 3 * filling));}
    public SetCard(int index) {this.index = index;}

    private static int get3rdValue(int one, int two) { return (6 - one - two) % 3; }
    public SetCard get3rdInSet(SetCard other) { return new SetCard(get3rdValue(this.getX(), other.getX()), get3rdValue(this.getY(), other.getY()), get3rdValue(this.numDots(), other.numDots()), get3rdValue(this.getFilling(), other.getFilling())); }
    public static SetCard getRandom() { return new SetCard(MainActivity.random.nextInt(81)); }
    public boolean equals(SetCard other) { return other.getIndex() == this.index; }
    public int getHashCode() { return this.index; }
    public boolean equals(Object obj) { return obj instanceof SetCard && equals((SetCard) obj); }
}

final class SetSet {
    private final SetCard one;
    public SetCard getOne() {return one;}
    private final SetCard two;
    public SetCard getTwo() {return two;}
    private final SetCard three;
    public SetCard getThree() {return three;}

    public List<SetCard> getCards() {
        List<SetCard> temp = new ArrayList<>();
        temp.add(one);
        temp.add(two);
        temp.add(three);
        return temp;
    }
    public SetSet(SetCard one, SetCard two, SetCard three)
    {
        this.one = one;
        this.two = two;
        this.three = three;
    }
    @Override
    public int hashCode()
    {
        // The hash function needs to be invariant under reordering of the three cards
        return (one.getHashCode() + two.getHashCode() + three.getHashCode()) ^ (one.getHashCode() * two.getHashCode() * three.getHashCode());
    }
    public boolean equals(SetSet other) { return other != null && other.getCards().contains(one) && other.getCards().contains(two) && other.getCards().contains(three); }
    public boolean equals(Object obj) { return obj instanceof SetSet && equals((SetSet) obj); }

    public static SetSet getRandom(boolean avoidSameSymbol)
    {
        SetCard one = SetCard.getRandom();
        SetCard two = SetCard.getRandom();
        while (two.equals(one) || (avoidSameSymbol && one.getX() == two.getX() && one.getY() == two.getY())) {
            two = SetCard.getRandom();
        }
        return new SetSet(one, two, one.get3rdInSet(two));
    }
    public static SetSet getRandom() {
        return getRandom(false);
    }
}