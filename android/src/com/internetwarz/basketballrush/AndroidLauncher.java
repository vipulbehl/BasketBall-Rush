package com.internetwarz.basketballrush;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements PlayServices {
    private GameHelper gameHelper;
    private final static int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new BasketBallRush(this), config);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(true);
        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
            }

            @Override
            public void onSignInSucceeded() {
            }
        };
        gameHelper.setup(gameHelperListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        String str = "https://play.google.com/store/apps/details?id=com.internetwarz.basketballrush";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void blueBallsCounter(int blueBalls){
        if(isSignedIn() && blueBalls>0){
            Games.Achievements.increment(gameHelper.getApiClient(),
                    getString(R.string.achievement_basket_1000_blue_balls),blueBalls);
            Games.Achievements.increment(gameHelper.getApiClient(),
                    getString(R.string.achievement_basket_2000_blue_balls),blueBalls);
        }
    }

    @Override
    public void greenBallsCounter(int greenBalls){
        if(isSignedIn() && greenBalls>0){
            Games.Achievements.increment(gameHelper.getApiClient(),
                    getString(R.string.achievement_basket_500_green_balls),greenBalls);
            Games.Achievements.increment(gameHelper.getApiClient(),
                    getString(R.string.achievement_basket_1000_green_balls),greenBalls);
        }
    }

    @Override
    public void gamesPlayedAchievements(String gameType,int score){
        if(isSignedIn()){
            if(gameType.equals("two color mode")){
                Games.Achievements.increment(gameHelper.getApiClient(),
                        getString(R.string.achievement_play_25_games_easy_mode),1);
                Games.Achievements.increment(gameHelper.getApiClient(),
                        getString(R.string.achievement_play_50_games_easy_mode),1);
            }
            else if(gameType.equals("three color mode")){
                Games.Achievements.increment(gameHelper.getApiClient(),
                        getString(R.string.achievement_play_50_games_medium_mode),1);
                Games.Achievements.increment(gameHelper.getApiClient(),
                        getString(R.string.achievement_play_100_games_medium_mode),1);
            }
            else if(gameType.equals("four color mode")){
                Games.Achievements.increment(gameHelper.getApiClient(),
                        getString(R.string.achievement_play_100_games_hard_mode),1);
                Games.Achievements.increment(gameHelper.getApiClient(),
                        getString(R.string.achievement_play_200_games_hard_mode),1);
            }
        }
    }

    @Override
    public void unlockAchievement(int score, String gameType) {
        if (isSignedIn()) {
            if (gameType.equals("two color mode")) {
                if (score == 25) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_25_in_easy));
                } else if (score == 50) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_50_in_easy));
                } else if (score == 100) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_100_in_easy));
                }
            } else if (gameType.equals("three color mode")) {
                if (score == 25) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_25_in_medium));
                } else if (score == 50) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_50_in_medium));
                } else if (score == 100) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_100_in_medium));
                }
            } else if (gameType.equals("four color mode")) {
                if (score == 25) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_25_in_hard));
                } else if (score == 50) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_50_in_hard));
                } else if (score == 80){
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_80_in_hard));
                } else if (score == 100) {
                    Games.Achievements.unlock(gameHelper.getApiClient(),
                            getString(R.string.achievement_score_100_in_hard));
                }
            }
        }
    }

    @Override
    public void submitScore(int highScore, String gameType) {
        if (isSignedIn()) {
            if (gameType.equals("two color mode")) {
                Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                        getString(R.string.leaderboard_easy), highScore);
            } else if (gameType.equals("three color mode")) {
                Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                        getString(R.string.leaderboard_medium), highScore);
            } else if (gameType.equals("four color mode")) {
                Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                        getString(R.string.leaderboard_hard), highScore);
            }
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

}
