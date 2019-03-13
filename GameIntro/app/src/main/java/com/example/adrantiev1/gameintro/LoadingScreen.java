package com.example.adrantiev1.gameintro;

import ca.youcode.nait.games.Game;
import ca.youcode.nait.games.Screen;

/**
 * Created by adrantiev1 on 3/13/2019.
 */

public class LoadingScreen extends Screen {


    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float v) {

    }

    @Override
    public void present(float v) {
    game.setScreen(new GameScreen(game));
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
