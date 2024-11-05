package DATABASE_EXP2;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GradientPanel extends JPanel {

    private Color startColor;
    private Color midColor;
    private Color endColor;

    public GradientPanel(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

 

	public GradientPanel(Color color, Color color2, Color color3) {
		 this.startColor = color;
		 this.midColor=color2;
	     this.endColor = color3;
	}



	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int height = getHeight();

        // Create a gradient paint
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);

        // Apply the gradient paint to the panel
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}