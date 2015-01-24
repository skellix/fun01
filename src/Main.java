import processing.core.PApplet;

import java.awt.*;

/**
 * Created by alex on 1/23/2015.
 */
public class Main extends PApplet {

	int maxIteration = 300;
	double xmod = 0, ymod = 0;
	double zoom = 1.75;
	Color[] colors = new Color[]{
			new Color(255, 0, 0),
			new Color(255, 63, 0),
			new Color(255, 122, 0),
			new Color(255, 190, 0),
			new Color(255, 255, 0),
			new Color(190, 255, 0),
			new Color(122, 255, 0),
			new Color(63, 255, 0),
			new Color(0, 255, 0),
			new Color(0, 255, 63),
			new Color(0, 255, 122),
			new Color(0, 255, 190),
			new Color(0, 255, 255),
			new Color(0, 190, 255),
			new Color(0, 122, 255),
			new Color(0, 63, 255),
			new Color(0, 0, 255),
			new Color(63, 0, 255),
			new Color(122, 0, 255),
			new Color(190, 0, 255),
			new Color(255, 0, 255),
			new Color(255, 0, 190),
			new Color(255, 0, 122),
			new Color(255, 0, 63)
	};

	@Override
	public void setup() {
		size(600, 600, P2D);
	}

	int ix = 0, iy = 0, ii = 0;
	@Override
	public void draw() {
		pushMatrix();
		//g.fill(0);
		//g.rect(0, 0, 600, 600);

		g.noStroke();
		for (int y = iy ; y < 600 ; y += 2) { // (-2.5 / 1.75) * 300
			for (int x = ix ; x < 600 ; x += 2) {
				double x1 = (((x + xmod) - 300) / 300.0) * zoom;
				double y1 = (((y + ymod) - 300) / 300.0) * zoom;
				double x2 = 0.0;
				double y2 = 0.0;

				//compute the number of iterations for the pixel
				int iteration = 0;
				while ( (x2*x2) + (y2*y2) < 4 && iteration < maxIteration ) {
					double xtemp = (x2*x2) - (y2*y2) + x1;
					y2 = (2*x2*y2) + y1;
					x2 = xtemp;
					iteration = iteration + 1;
				}

				double icolor = iteration;
				//compute the color of the pixel
				if (iteration < maxIteration) {
					double zn = Math.sqrt((x2*x2) + (y2*y2));
					double nu = Math.log(Math.log(zn) / Math.log(2)) / Math.log(2);
					icolor = iteration + 1 - nu;
				}

				icolor /= colors.length;
				Color color1 = colors[(int) Math.floor(icolor)];
				Color color2 = colors[(int) Math.floor(icolor) + 1];
				int r = lerpColor(color1.getRed(), color2.getRed(), (float) (icolor % 1));
				int g = lerpColor(color1.getGreen(), color2.getGreen(), (float) (icolor % 1));
				int b = lerpColor(color1.getBlue(), color2.getBlue(), (float) (icolor % 1));

				// draw the pixel
				fill(r, g, b);
				rect(x, y, 1, 1);
			}
		}

		switch (ii) {
			case 0: ix = iy = 0; ii ++; break;
			case 1: ix = 1; ii ++; break;
			case 2: ix = iy = 1; ii ++; break;
			case 3: ix = 0; iy = 1; ii = 0; break;
		}

		//g.noFill();
		//g.stroke(255, 0, 0);
		popMatrix();
		if (mousePressed) {
			this.mousePressed();
		}
		if (keyPressed) {
			keyPressed();
		}
	}

	@Override
	public void mousePressed() {
		double x = mouseX;
		double y = mouseY;
		switch (mouseButton) {
			case LEFT: {
				x = ((x - xmod) - 380) * ((1 - (zoom / 1.75)) * (20));
				y = ((y - ymod) - 300) * ((1 - (zoom / 1.75)) * (20));
				//xmod += x;
				//ymod += y;
				zoom *= 0.9;
				double val1 = ((((xmod) - 300) / 300.0) * 1.75);
				double val2 = ((((xmod) - 300) / 300.0) * zoom);
				xmod = ((val1 - val2) / 1.75) * 300;
				xmod -= 10 * (1.75 - zoom);
				break;}
			case CENTER: {
				break;}
			case RIGHT: {
				x = ((x - xmod) - 380) * ((1 - (zoom / 1.75)) * (20));
				y = ((y - ymod) - 300) * ((1 - (zoom / 1.75)) * (20));
				//xmod += x;
				//ymod += y;
				xmod -= 10 * (1.75 - zoom);
				zoom /= 0.9;
				break;}
		}
	}

	@Override
	public void keyPressed() {
		switch (keyCode) {
			case 'W': {
				xmod += 0;
				ymod -= 50;
				break;}
			case 'S': {
				xmod += 0;
				ymod += 50;
				break;}
			case 'A': {
				xmod -= 50;
				ymod += 0;
				break;}
			case 'D': {
				xmod += 50;
				ymod += 0;
				break;}
		}
	}

	public static void main(String[] args) {
		PApplet.main("Main", args);
	}
}
