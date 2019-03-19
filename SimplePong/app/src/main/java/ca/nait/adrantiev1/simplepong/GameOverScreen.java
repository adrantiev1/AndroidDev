package ca.nait.adrantiev1.simplepong;

import ca.youcode.nait.games.Game;
import ca.youcode.nait.games.Graphics;
import ca.youcode.nait.games.Screen;

/**
 * Created by adrantiev1 on 3/19/2019.
 */

public class GameOverScreen extends Screen {
    public GameOverScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float v)
    {

    }

    @Override
    public void present(float v)
    {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.gameover, 100,50);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Assets.background.dispose();
        Assets.ball.dispose();
        Assets.gameover.dispose();
        Assets.paddle.dispose();
    }
}
