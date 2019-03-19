package ca.nait.adrantiev1.simplepong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.youcode.nait.games.Screen;
import ca.youcode.nait.games.impl.AndroidGame;

public class GameActivity extends AndroidGame{
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
