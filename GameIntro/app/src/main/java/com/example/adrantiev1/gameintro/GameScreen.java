package com.example.adrantiev1.gameintro;

import android.graphics.Color;

import java.util.List;
import java.util.Random;

import ca.youcode.nait.games.Game;
import ca.youcode.nait.games.Graphics;
import ca.youcode.nait.games.Input;
import ca.youcode.nait.games.Screen;

/**
 * Created by adrantiev1 on 3/13/2019.
 */

public class GameScreen extends Screen {

    public final static String TAG = "GameScreen";
    int x = 20,y = 20, xInc = 5, yInc = 4 ;

    public GameScreen(Game game) {
        super(game);
    }
    @Override
    public void update(float v) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int nEvents = touchEvents.size();
        xInc += nEvents;
        if (xInc > 20)
        {
            xInc = 0;
        }
        x += xInc;
        y += yInc;
        if (x < 10 || x > 310)
        {
            xInc *= -1;
        }
        if (y < 10 || y > 470)
        {
            yInc *= -1;
        }
    }

    @Override
    public void present(float v) {
        Graphics graphics = game.getGraphics();
        graphics.clear(5);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));



        graphics.drawCircle(x,y,10, color);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
