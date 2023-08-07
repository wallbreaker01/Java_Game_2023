package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener
{
    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 800;
    public Renderer renderer;
    public Rectangle bird;
    public int ticks,yMotion,score;
    public ArrayList<Rectangle>columns;
    public Random rand;
    public boolean gameOver,started;

    public FlappyBird()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20,this); //game speed

        renderer = new Renderer();
        rand = new Random();

        jframe.add(renderer);
        jframe.setTitle("Flappy Bird");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close app
        jframe.setSize(WIDTH,HEIGHT);

        jframe.addMouseListener(this); //mouse inout
        jframe.addKeyListener(this); //key input

        jframe.setResizable(false); //window size change
        jframe.setVisible(true);

        bird = new Rectangle(WIDTH/2 - 10,HEIGHT/2 - 10,20,20); //bird size
        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }
    public void addColumn(boolean start)
    {
        int space = 380;
        int width = 100;
        int height = 50 + rand.nextInt(300);

        if(start)
        {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else
        {
            columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(columns.get(columns.size()-1).x , 0, width, HEIGHT - height - space));
        }
    }
    public void paintColumn(Graphics g, Rectangle column)
    {
        g.setColor(Color.GREEN.darker());
        g.fillRect(column.x, column.y, column.width,column.height);
    }

    //here the jump code will go
    public void jump()
    {
        if(gameOver)
        {
            bird = new Rectangle(WIDTH/2 - 10,HEIGHT/2 - 10,20,20);
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            gameOver = false;
        }
        if(!started)
        {
            started = true;
        }
        else if(!gameOver)
        {
            if(yMotion > 0)
            {
                yMotion = 0;
            }
            yMotion -=10;
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        int speed = 10; //game speed

        ticks++;
        if(started)
        {

            for (int i = 0; i < columns.size(); i++)
            {
                Rectangle colum = columns.get(i);
                colum.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15)
            {
                yMotion += 2;
            }

            for (int i = 0; i < columns.size(); i++)
            {
                Rectangle colum = columns.get(i);

                if (colum.x + colum.width < 0)
                {
                    columns.remove(colum);
                    if (colum.y == 0)
                    {
                        addColumn(false);
                    }
                }
            }
            bird.y += yMotion;

            for (Rectangle column : columns)
            {
                if(column.y == 0 && bird.x + bird.width/2 > column.x + column.width/2 - 10 && bird.x + bird.width/2 < column.x + column.width/2 + 10)
                {
                    score++;
                }

                if (column.intersects(bird))
                {
                    gameOver = true;

                    if(bird.x <= column.x)
                    {
                        bird.x = column.x - bird.width;
                    }
                    else
                    {
                        if(column.y != 0)
                        {
                            bird.y = column.y - bird.height;
                        }
                        else if(bird.y < column.height)
                        {
                            bird.y = column.height;

                        }
                    }
                }
            }

            if (bird.y > HEIGHT - 120 || bird.y < 0)
            {
                gameOver = true;
            }
            if(bird.y + yMotion >= HEIGHT - 120)
            {
                bird.y = HEIGHT - 120 - bird.height;
            }
        }

        renderer.repaint();
    }

    public void repaint(Graphics g)
    {
        g.setColor(Color.cyan);
        g.fillRect(0,0,WIDTH,HEIGHT);

        g.setColor(Color.ORANGE);
        g.fillRect(0,HEIGHT-120,WIDTH,120);

        g.setColor(Color.GREEN);
        g.fillRect(0,HEIGHT-120,WIDTH,20);

        g.setColor(Color.RED);
        g.fillRect(bird.x,bird.y,bird.width,bird.height);

        for(Rectangle column : columns)
        {
            paintColumn(g,column);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman",1,100));

        if(!started)
        {
            g.drawString("Click to Start!",75,HEIGHT/2 - 50);
        }

        if(gameOver)
        {
            g.drawString("Game Over!",100,HEIGHT/2 - 50);
        }

        if(!gameOver)
        {
            g.drawString(String.valueOf(score),WIDTH/2 - 25,100);
        }

    }

    public static void main(String[] arg)
    {
        flappyBird = new FlappyBird();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            jump();
        }

    }
}
