package APIs;

import javax.swing.border.Border;
import java.awt.*;

/**
 * create a round element in the gui
 */
public class RoundBorder implements Border {

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 5, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.gray);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);
    }
}
