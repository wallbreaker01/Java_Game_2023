package flappyBird;

import javax.swing.*;
import java.awt.*;


//game draw design
public class Renderer extends JPanel {
    private static final long serialVersionUID = 1L;
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }

}
