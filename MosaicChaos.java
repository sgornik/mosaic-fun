package Mosaic;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.round;

/**
 * Created by sgornik on 28.2.2015..
 * Package : com.example.learning
 * Project Name : Test
 */
public class MosaicChaos {

    private static final int DISTURB_VALUE = 1;
    // max number of columns and rows
    private static int COLS = 20;
    private static int ROWS = 20;

    // max movement on each axis
    private static int DX = 1;
    private static int DY = 1;

    // size of the square
    private static int SIZE = 3;

    // limit with colors
    private static int LIMIT = 255;

    // array holding the squares
    private int[][] squares;

    // array of buttons which are changing colors
    private JButton[][] buttons;

    // disturbance model
    private Point disturbance;
    // var showing if one square reached the LIMIT
    private boolean done;

    // METHODS

    // INIT
    public MosaicChaos() {
        this(new Point(ROWS / 2, COLS / 2));
    }

    public MosaicChaos(Point point) {
        setDisturbance(point);

        // initialization of the squares
        int[][] temp = new int[ROWS][COLS];

        // setting done to false
        setDone(false);

        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                temp[i][j] = 0;
            }

        setSquares(temp);
    }
    // END INIT

    public void moveDisturbance() {
        int dx, dy;

        dx = (int) (round((Math.random() * 2 * DX)) - DX);
        dy = (int) (round((Math.random() * 2 * DY)) - DY);

        // Limit DX
        while (!((dx + disturbance.getX()) < COLS && (disturbance.getX() + dx) >= 0)) {
            dx = round((int) (Math.random() * 2 * DX)) - DX;
        }

        // Limit DY on right side
        while (!((dy + disturbance.getY()) < ROWS && (disturbance.getY() + dy) >= 0)) {
            dy = round((int) (Math.random() * 2 * DY)) - DY;
        }

        setDisturbance(new Point((int) disturbance.getX() + dx, (int) disturbance.getY() + dy));

        //System.out.println("moveDisturbance : "+disturbance.getLocation().toString());

        // disturb the mosaic
        //this.disturb();
    }

    public void disturb() {
        int[][] temp = getSquares();
        Point dist = getDisturbance();
        temp[(int) dist.getX()][(int) dist.getY()] += DISTURB_VALUE;
        setSquares(temp);
        editButton((int) dist.getX(), (int) dist.getY());
    }

    public int[][] getSquares() {
        return squares;
    }

    public void setSquares(int[][] squares) {
        this.squares = squares;
    }

    public Point getDisturbance() {
        return disturbance;
    }

    public void setDisturbance(Point disturbance) {
        this.disturbance = disturbance;
    }

    @Override
    public String toString() {
        String text = "MosaicChaos{" + "squares=[\n";
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                text += (squares[i][j] + "|");
            text += "\n";
        }
        text += "]" + ", disturbance=" + disturbance.toString() + '}';
        return text;
    }

    public void makeWorld() {

        //Create and set up the window.
        JFrame frame = new JFrame("Mosaic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        doButtons(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    private void doButtons(JFrame frame) {
        // create the grid layout
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(ROWS, COLS));

        buttons = new JButton[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setSize(SIZE, SIZE);
                buttons[i][j].setText("" + squares[i][j]);
                buttons[i][j].setBackground(new Color(squares[i][j], 0, 0));
                contentPane.add(buttons[i][j]);
            }
        }
    }

    private void editButton(int x, int y) {
        Color color = buttons[x][y].getBackground();
        buttons[x][y].setBackground(new Color(squares[x][y], color.getGreen(), color.getBlue()));
        buttons[x][y].setText("" + squares[x][y]);
        if (buttons[x][y].getBackground().getRed() >= 255)
            setDone(true);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
