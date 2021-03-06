package com.attosec.galgeleg_v400b;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


public class Spil_Frag extends Fragment implements View.OnClickListener{

    private NumberPicker charPicker;
    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "æ", "ø", "å"};
    public static HangmanLogic game;
    private Button guessButton;
    private Button playagain;
    private TextView wordText;
    private TextView guessedWords;
    private ImageView galgeImg;
    private Integer[] wrongImg;
    private TextView gættilbage;
    private TextView normTekst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Galgeleg");
        View rod = inflater.inflate(R.layout.spil_frag, container, false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(game == null){
            game = new HangmanLogic();
        }
        charPicker = (NumberPicker) rod.findViewById(R.id.charPicker);
        playagain = (Button) rod.findViewById(R.id.btnPlayAgain);
        galgeImg = (ImageView) rod.findViewById(R.id.imageView);
        wordText = (TextView) rod.findViewById(R.id.wordText);
        guessedWords = (TextView) rod.findViewById(R.id.guessedWords);
        gættilbage = (TextView) rod.findViewById(R.id.guessBackNumber);
        guessButton = (Button) rod.findViewById(R.id.btnGuess);
        normTekst = (TextView) rod.findViewById(R.id.guessBackTitle);
        charPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        charPicker.setMaxValue(28);
        charPicker.setDisplayedValues(alphabet);
        wordText.setText("Loading...");
        playagain.setOnClickListener(this);
        DrAsync g = new DrAsync();
        g.execute();
        game.nulstil();

        return rod;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        //finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
        getActivity().finish();
        return true;
    }

    public void setVisibleView(){
        playagain.setVisibility(View.VISIBLE);
        charPicker.setVisibility(View.INVISIBLE);
        guessButton.setVisibility(View.INVISIBLE);
        guessedWords.setVisibility(View.INVISIBLE);
        gættilbage.setVisibility(View.INVISIBLE);
        normTekst.setVisibility(View.INVISIBLE);
    }

    public void restartSpil(){
        wordText.setText(game.getSynligtOrd());
        gættilbage.setText("7");
        galgeImg.setImageResource(R.drawable.galge);
        wrongImg = new Integer[]{
                R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3, R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6
        };
        guessButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == guessButton) {
            if (!game.erSpilletSlut()) {
                game.gætBogstav(alphabet[charPicker.getValue()]);
                wordText.setText(game.getSynligtOrd());
                gættilbage.setText(String.valueOf(7 - game.getAntalForkerteBogstaver()));
                if(!String.valueOf(guessedWords.getText()).contains(" " + alphabet[charPicker.getValue()] + " ")){
                    guessedWords.append(alphabet[charPicker.getValue()] + " ");
                }
                if(!game.erSidsteBogstavKorrekt()) {
                    if (!game.erSpilletSlut()) {
                        galgeImg.setImageResource(wrongImg[game.getAntalForkerteBogstaver() - 1]);
                    } else {
                        //Kan gøres til en metode for at spare kode men nu lavede jeg det i uden lige at tænke på det så fuck det (Y)
                        if (game.erSpilletVundet()) {
                            galgeImg.setImageResource(R.drawable.vundet);
                            wordText.setText("Du har vundet! Ordet var: " + game.getOrdet());
                            setVisibleView();
                        } else {
                            galgeImg.setImageResource(R.drawable.tabt);
                            wordText.setText("Du har tabt! Ordet var: " + game.getOrdet());
                            setVisibleView();
                        }
                    }
                }
                else if(game.erSpilletSlut())
                    if (game.erSpilletVundet()) {
                        galgeImg.setImageResource(R.drawable.vundet);
                        wordText.setText("Du har vundet! Ordet var: " + game.getOrdet());
                        setVisibleView();
                    } else {
                        galgeImg.setImageResource(R.drawable.tabt);
                        wordText.setText("Du har tabt! Ordet var: " + game.getOrdet());
                        setVisibleView();
                    }
            }
        }
        else if(v == playagain){
            playagain.setVisibility(View.GONE);
            charPicker.setVisibility(View.VISIBLE);
            guessButton.setVisibility(View.VISIBLE);
            guessedWords.setVisibility(View.VISIBLE);
            gættilbage.setVisibility(View.VISIBLE);
            normTekst.setVisibility(View.VISIBLE);
            galgeImg.setImageResource(R.drawable.galge);
            game.nulstil();
            wordText.setText(game.getSynligtOrd());
            gættilbage.setText("7");
            guessedWords.setText("Brugte bogstaver: ");
            //getActivity().finish();
            //android.os.Process.killProcess(android.os.Process.myPid());

        }
    }

    private class DrAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (game.getAllWords().size() == 8) {
                try {
                    game.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){restartSpil();}

    }

}
