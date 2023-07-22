package com.abhi.java2048;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Gui{

    Game game;


    int frameheight=393;
    int framewidth=328;
    int gameboardSize=296;
    int marginSize=16;
    Color backgroundColor=new Color(250,248,239,255);

    Font largeFeedbackFont= new Font("SansSerif", 0, 40);
    Font smallFeedbackFont= new Font("SansSerif", 0, 20);

    JLabel scoreLabel;

    Hashtable <Integer, ImageIcon> numberTiles;
    GameBoard gb;
    MyFrame frame;
    

    public Gui(){
        game=new Game();
        frame= new MyFrame(); //just a window
        frame.setFocusable(true);
        frame.addKeyListener(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadNumberTiles();

        gb=new GameBoard();
        // gb.setFocusable(true);

        //north
        JPanel northPanel=new JPanel();
        northPanel.setLayout(new GridLayout());
        northPanel.setPreferredSize(new Dimension(framewidth, 82));

        JLabel gameLabel= new JLabel("<html>Goal:<br>2048</html>", SwingConstants.CENTER);
        gameLabel.setFont(new Font("serif", Font.BOLD, 20));
        northPanel.add(gameLabel);
        // scoreLabel=new JLabel("<html>Score:<br>00</html>", SwingConstants.CENTER);
        // northPanel.add(scoreLabel);
        scoreLabel=new JLabel("<html>Your Score:<br>00</html>", SwingConstants.CENTER);
        northPanel.add(scoreLabel);
        scoreLabel.setFont(new Font("serif", Font.BOLD, 20));
        northPanel.setBackground(backgroundColor);


        JPanel westBuffer= new JPanel();
        westBuffer.setPreferredSize(new Dimension(marginSize, gameboardSize));
        westBuffer.setBackground(backgroundColor);

        JPanel eastBuffer= new JPanel();
        eastBuffer.setPreferredSize(new Dimension(marginSize, gameboardSize));
        eastBuffer.setBackground(backgroundColor);

        JPanel southBuffer= new JPanel();
        southBuffer.setPreferredSize(new Dimension(framewidth, marginSize));
        southBuffer.setBackground(backgroundColor);


        //adding panels to frm
        frame.getContentPane().add(northPanel, BorderLayout.NORTH);
        frame.getContentPane().add(westBuffer, BorderLayout.WEST);
        frame.getContentPane().add(eastBuffer, BorderLayout.EAST);
        frame.getContentPane().add(southBuffer, BorderLayout.SOUTH);
        frame.getContentPane().add(gb, BorderLayout.CENTER);

        frame.getContentPane().setPreferredSize(new Dimension(framewidth, frameheight));
        frame.pack();
        frame.setVisible(true);
    }

    private void loadNumberTiles() {
        numberTiles = new Hashtable<>();
        ClassLoader cldr = this.getClass().getClassLoader();
        URL url0000 = cldr.getResource("images/00.png");
        URL url0002 = cldr.getResource("images/2.png");
        URL url0004 = cldr.getResource("images/4.png");
        URL url0008 = cldr.getResource("images/8.png");
        URL url0016 = cldr.getResource("images/16.png");
        URL url0032 = cldr.getResource("images/32.png");
        URL url0064 = cldr.getResource("images/64.png");
        URL url0128 = cldr.getResource("images/128.png");
        URL url0256 = cldr.getResource("images/256.png");
        URL url0512 = cldr.getResource("images/512.png");
        URL url1024 = cldr.getResource("images/1024.png");
        URL url2048 = cldr.getResource("images/2048.png");
        
        numberTiles.put(0, new ImageIcon(url0000));
        numberTiles.put(2, new ImageIcon(url0002));
        numberTiles.put(4, new ImageIcon(url0004));
        numberTiles.put(8, new ImageIcon(url0008));
        numberTiles.put(16, new ImageIcon(url0016));
        numberTiles.put(32, new ImageIcon(url0032));
        numberTiles.put(64, new ImageIcon(url0064));
        numberTiles.put(128, new ImageIcon(url0128));
        numberTiles.put(256, new ImageIcon(url0256));
        numberTiles.put(512, new ImageIcon(url0512));
        numberTiles.put(1024, new ImageIcon(url1024));
        numberTiles.put(2048, new ImageIcon(url2048));
    }

    public class GameBoard extends JPanel {
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(187, 173, 160, 255));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            int[][] board = game.getGameBoard();

            for (int y = 1; y < 5; y++) {
                for (int x = 1; x < 5; x++) {
                    int X = (8 * x) + (64 * (x - 1));
                    int Y = (8 * y) + (64 * (y - 1));
                    int thisNumber = board[y - 1][x - 1];

                    if (numberTiles.containsKey(thisNumber)) {
                        ImageIcon thisTile = numberTiles.get(thisNumber);
                        thisTile.paintIcon(this, g, X, Y);
                    }
                }
            }
        }
    }

    class WinBoard extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            g.setColor(new Color(187, 173, 160, 255));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setFont(largeFeedbackFont);
            g.setColor(new Color(0, 80, 0));
            g.drawString("YOU WIN!", 20, 40);
            g.setFont(smallFeedbackFont);
            g.setColor(new Color(225, 225, 225));
            g.drawString("Press ENTER to play again...", 20, 70);
        }
    }

    class LoseBoard extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            g.setColor(new Color(187, 173, 160, 255));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setFont(largeFeedbackFont);
            g.setColor(new Color(200, 0, 0));
            g.drawString("YOU LOSE!", 20, 40);
            g.setFont(smallFeedbackFont);
            g.setColor(new Color(225, 225, 225));
            g.drawString("Press ENTER to try again...", 20, 70);
        }
    }

    class MyFrame extends JFrame implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {
            int key=e.getKeyCode();
            if(game.getState()==GameState.CONTINUE){
                if (key==KeyEvent.VK_UP || key==KeyEvent.VK_W) {
                    System.out.println("up key pressed...");
                    game.pushUP();
                    game.addNewNumber();
                    game.checkState();
                    gb.repaint();
                    updateScore();
                }
                else if (key==KeyEvent.VK_DOWN || key==KeyEvent.VK_S) {
                    System.out.println("down key pressed...");
                    game.pushDOWN();
                    game.addNewNumber();
                    game.checkState();
                    gb.repaint();
                    updateScore();
                }
                else if (key==KeyEvent.VK_LEFT || key==KeyEvent.VK_A) {
                    System.out.println("left key pressed...");
                    game.pushLEFT();
                    game.addNewNumber();
                    game.checkState();
                    gb.repaint();
                    updateScore();
                }
                else if (key==KeyEvent.VK_RIGHT || key==KeyEvent.VK_D) {
                    System.out.println("right key pressed...");
                    game.pushRIGHT();
                    game.addNewNumber();
                    game.checkState();
                    gb.repaint();
                    updateScore();
                }

                GameState gs= game.getState();
                if (gs==GameState.LOSE){
                    frame.getContentPane().remove(gb);
                    frame.getContentPane().add(new LoseBoard(), BorderLayout.CENTER);
                    frame.getContentPane().invalidate();
                    frame.getContentPane().validate();
                    System.out.println("Game Over...");
                }
                else if(gs==GameState.WIN){
                    frame.getContentPane().add(new WinBoard(), BorderLayout.CENTER);
                    frame.getContentPane().invalidate();
                    frame.getContentPane().validate();
                    System.out.println("Game Over, ITS A WIN...");
                }
            }
            else{
                if (key==KeyEvent.VK_ENTER){
                    game= new Game();
                    frame.getContentPane().remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER));
                    frame.getContentPane().add(gb);
                    gb.repaint();
                    frame.getContentPane().invalidate();
                    frame.getContentPane().validate();

                }
            }
            
        }
        @Override
        public void keyTyped(KeyEvent e) {}
    }

    public void updateScore(){
        scoreLabel.setText("<html>Score:<br>"+game.getScore()+"</html>");
    }
    
}
