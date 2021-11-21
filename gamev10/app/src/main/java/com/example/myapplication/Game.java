package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.CountDownTimer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;


public class Game extends AppCompatActivity {
    private int computerScore;
    private int playerScore;
    private TextView computerScoreView;
    private TextView playerScoreView;
    private int counter;
    private CountDownTimer countDownTimer;
    private boolean finishGame = false;

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(18);
    }

    public void openWinActivity() {
        finishGame = true;
        countDownTimer.cancel();
        Intent intent = new Intent(this, WinActivity.class);
        Game.this.finish();
        startActivity(intent);
        Game.this.finish();
    }

    public void openLoseActivity() {
        finishGame = true;
        countDownTimer.cancel();
        Intent intent = new Intent(this, LoseActivity.class);
        Game.this.finish();
        startActivity(intent);
        Game.this.finish();
    }

    public Karten setCardImage(ImageView image) {
        Karten card = Karten.values()[getRandomNumber()];
        //Log.d("TAG", card.getPath());
        switch (card.getPath()) {
            case "cerberus10.jpg":
                image.setImageResource(R.drawable.cerberus10);
                break;
            case "charmander6.jpg":
                image.setImageResource(R.drawable.charmander6);
                break;
            case "deathwing12.jpg":
                image.setImageResource(R.drawable.deathwing12);
                break;
            case "delfinu10.jpg":
                image.setImageResource(R.drawable.delfinu10);
                break;
            case "fox5.jpg":
                image.setImageResource(R.drawable.fox5);
                break;
            case "gorilla12.jpg":
                image.setImageResource(R.drawable.gorilla12);
                break;
            case "penguin6.jpg":
                image.setImageResource(R.drawable.penguin6);
                break;
            case "rechin12.jpg":
                image.setImageResource(R.drawable.rechin12);
                break;
            case "tiger10.jpg":
                image.setImageResource(R.drawable.tiger10);
                break;
            case "apocalypse7.jpg":
                image.setImageResource(R.drawable.apocalypse7);
                break;
            case "cinder8.jpg":
                image.setImageResource(R.drawable.cinder8);
                break;
            case "crocodile9.jpg":
                image.setImageResource(R.drawable.crocodile9);
                break;
            case "fish7.jpg":
                image.setImageResource(R.drawable.fish7);
                break;
            case "hydra11.jpg":
                image.setImageResource(R.drawable.hydra11);
                break;
            case "kraken9.jpg":
                image.setImageResource(R.drawable.kraken9);
                break;
            case "mamut9.jpg":
                image.setImageResource(R.drawable.mamut9);
                break;
            case "phoenix11.jpg":
                image.setImageResource(R.drawable.phoenix11);
                break;
            case "snake11.jpg":
                image.setImageResource(R.drawable.snake11);
                break;
        }
        return card;
    }

    public ArrayList<ImageView> getPlayerDeck() {
        ArrayList<ImageView> playerCards = new ArrayList<ImageView>();

        playerCards.add((ImageView) findViewById(R.id.lowercard3));
        playerCards.add((ImageView) findViewById(R.id.lowercard4));
        playerCards.add((ImageView) findViewById(R.id.lowercard5));
        playerCards.add((ImageView) findViewById(R.id.lowercard6));
        playerCards.add((ImageView) findViewById(R.id.lowercard7));

        return playerCards;
    }

    public int changeScore(ImageView playerCard, Karten computerChosenCard, ArrayList<Karten> playerAssignedCards, ArrayList<ImageView> playerCards) {
        int i;
        for (i = 0; i < playerCards.size(); i++) {
            String s1 = playerCard.getDrawable().toString();
            String s2 = playerCards.get(i).getDrawable().toString();
            if (s1.equals(s2)) {
//                System.out.println(computerChosenCard.getPath());
//                System.out.println(playerAssignedCards.get(i).getPath());
                if (computerChosenCard.getPath().equals(playerAssignedCards.get(i).getPath())) {
                    Toast.makeText(Game.this, "DRAW", Toast.LENGTH_SHORT).show();
                    return i;
                }
                boolean playerWin = compareCards(computerChosenCard, playerAssignedCards.get(i));
//                System.out.println(playerWin);
                if (playerWin)
                    playerScore++;
                else
                    computerScore++;
                break;
            }

        }
        computerScoreView.setText("" + computerScore);
        playerScoreView.setText("" + playerScore);
        return i;
    }

    public boolean compareCards(Karten computerChosenCard, Karten playerChosenCard) {
        if (computerChosenCard.getType().equals(playerChosenCard.getType()))
            return computerChosenCard.getPower() < playerChosenCard.getPower();
        if (playerChosenCard.getType().equals("Fire"))
            return computerChosenCard.getType().equals("Grass");
        if (playerChosenCard.getType().equals("Grass"))
            return computerChosenCard.getType().equals("Water");
        return computerChosenCard.getType().equals("Fire");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final ImageView chosenCard = findViewById(R.id.activePlayerCard);
        final ImageView computerCard = findViewById(R.id.activeComputerCard);
        computerScoreView = findViewById(R.id.scoreComputer);
        playerScoreView = findViewById(R.id.scorePlayer);
        final TextView textView = findViewById(R.id.score);

        computerScore = Integer.parseInt(computerScoreView.getText().toString());
        playerScore = Integer.parseInt(playerScoreView.getText().toString());

        final ArrayList<ImageView> playerCards = getPlayerDeck();
        final ArrayList<Karten> playerAssignedCards = new ArrayList<>();

        for (int i = 0; i < playerCards.size(); i++) {
            final ImageView card = playerCards.get(i);
            Karten assignedCard = setCardImage(card);
            playerAssignedCards.add(assignedCard);

            //add onClickListener
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //System.out.println(card.getDrawable());
                    chosenCard.setImageDrawable(card.getDrawable());
                    Karten computerChosenCard = setCardImage(computerCard);
                    int chosenPosition = changeScore(chosenCard, computerChosenCard, playerAssignedCards, playerCards);
                    Karten newDrawnCard = setCardImage(card);
                    playerAssignedCards.remove(chosenPosition);
                    playerAssignedCards.add(chosenPosition, newDrawnCard);

                    if (playerScore == 10) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference("User");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                try {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        String username = ds.child("username").getValue(String.class);

                                        if (getIntent().getStringExtra("USER_NAME").equals(username)) {
                                            Integer nrWins = ds.child("nr_wins").getValue(Integer.class);

                                            ds.child("nr_wins").getRef().setValue(++nrWins);
                                        }
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                        openWinActivity();
                    }
                    if (computerScore == 10) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference("User");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                try {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        String username = ds.child("username").getValue(String.class);

                                        if (getIntent().getStringExtra("USER_NAME").equals(username)) {
                                            Integer nrLosses = ds.child("nr_losses").getValue(Integer.class);

                                            ds.child("nr_losses").getRef().setValue(++nrLosses);
                                        }
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                        openLoseActivity();
                    }
                    if(!finishGame)
                        initialiseCounter(textView, playerCards);
                }
            });
        }

        counter = 15;

        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                playerCards.get(getRandomNumber() % 5).performClick();
            }
        };

        countDownTimer.start();
    }

    private void initialiseCounter(final TextView textView, final ArrayList<ImageView> playerCards) {
        counter = 15;

        countDownTimer.cancel();

        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                playerCards.get(getRandomNumber() % 5).performClick();
            }
        };

        countDownTimer.start();
    }
}