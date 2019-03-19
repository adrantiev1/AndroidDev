package ca.nait.adrantiev1.simplepong;

import java.util.List;

import ca.youcode.nait.games.Game;
import ca.youcode.nait.games.Graphics;
import ca.youcode.nait.games.Input;
import ca.youcode.nait.games.Screen;

/**
 * Created by adrantiev1 on 3/19/2019.
 */

public class GameScreen extends Screen
{
    int spriteX = 6, spriteY = 6, xInc = 5, yInc = 5;
    int paddleX = 100, paddleY = 450, paddleInc = 5;
    boolean bSpriteAlive = true;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float v)
    {
        if (collision())
        {
            yInc *= -1;
        }
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int nEvents = touchEvents.size();
        for (int c = 0; c < nEvents; c++)
        {
            Input.TouchEvent event = touchEvents.get(c);
            if (event.type == Input.TouchEvent.TOUCH_DRAGGED)
            {
                paddleX = event.x - 48;
            }
        }
        spriteX += xInc;
        spriteY += yInc;

        if (spriteX < 0 || spriteX > 298)
        {
            xInc *= -1;
        }
        if (spriteY < 0)
        {
            yInc *= -1;
        }
        if (spriteY + 32 > 480)
        {
            bSpriteAlive = false;
        }
    }

    public boolean collision()
    {
        int ballDiameter = 32;
        int paddleWidth = 96;
        int paddleHight = 15;

        boolean bCollide = false;
        if (       spriteX + ballDiameter > paddleX
                && spriteX < paddleX + paddleWidth
                && spriteY + ballDiameter > paddleY
                && spriteY + ballDiameter < paddleY + paddleHight)
        {
            bCollide = true;
        }
        return bCollide;
    }

    @Override
    public void present(float v)
    {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0,0);
        g.drawPixmap(Assets.paddle, paddleX, paddleY);
        if (bSpriteAlive)
        {
            g.drawPixmap(Assets.ball, spriteX,spriteY);
        }
        else {
            game.setScreen(new GameOverScreen(game));
        }
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
