package Checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by CianC on 08/11/2018.
 */

class CheckerBoardGUI extends JFrame implements ActionListener, MouseListener {
    private JMenu gameMenu, playerMenu;
    /*http://www.cs.princeton.edu/courses/archive/fall13/cos432/homework/exampleCode/StaticInitializationDemo.java
    Used as an example for initializing and populating an array list. */
    private static ArrayList<Player> players;
    /*Ensures player 1 will move first. */
    private boolean player1Turn = true;
    private boolean player2Turn = false;
    /*https://stackoverflow.com/questions/20601670/initializing-two-dimensional-jpanel-arrays-using-a-for-loop
    Example for creating a 2d array of JPanels for the layout of the board. */
    private final JPanel[][] square = new JPanel[8][8];
    /*Creating a label for the players to display their names and scores in the container. */
    private final JLabel player1 = new JLabel();
    private final JLabel player2 = new JLabel();
    private Point moveFrom;
    private Point moveTo;
    private final JPanel board = new JPanel(new GridLayout(8, 8));
    private final ImageIcon whitePiece = new ImageIcon(System.getProperty("user.dir") + "/images/white_piece.png");
    JLabel white = new JLabel(whitePiece);
    private final ImageIcon blackPiece = new ImageIcon(System.getProperty("user.dir") + "/images/black_piece.png");
    JLabel black = new JLabel(blackPiece);
    private boolean moveSelection=false;

    /*
     * Sets the starting location for all white and black pieces
     * as well as setting the permanently invalid squares.
     */
    private final String[][] strBoard = new String[][]{
            {"WP","IV","WP","IV","WP","IV","WP","IV"},
            {"IV","WP","IV","WP","IV","WP","IV","WP"},
            {"WP","IV","WP","IV","WP","IV","WP","IV"},
            {"IV","","IV","","IV","","IV",""},
            {"","IV","","IV","","IV","","IV"},
            {"IV","BP","IV","BP","IV","BP","IV","BP"},
            {"BP","IV","BP","IV","BP","IV","BP","IV"},
            {"IV","BP","IV","BP","IV","BP","IV","BP"} };

    CheckerBoardGUI() {
        setTitle("Checkers");
        setBounds(100, 100, 535, 615);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createGameMenu();
        createPlayerMenu();

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        menu.add(gameMenu);
        menu.add(playerMenu);

        Container c = getContentPane();
        c.setLayout(null);
        board.setBounds(5, 25, 500, 500);
        player2.setBounds(15,-10,500,50);
        player2.setText("Player 2");
        c.add(player2);
        c.add(board);
        player1.setBounds(15,510,500,50);
        player1.setText("Player 1");
        c.add(player1);
        this.createBoard();

        this.setPieces();
    }

    /* https://mukarrammukhtar.wordpress.com/chess-board-in-java/
    Used this as a reference for creating the board and finding the
    initial location of the pieces on the board using for loops to
    iterate through the 2d array.*/
    private void createBoard() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                square[i][j] = new JPanel(new BorderLayout());
                square[i][j].addMouseListener(this);
                board.add(square[i][j]);
                if (i % 2 == 0) {

                    if (j % 2 != 0) {
                        square[i][j].setBackground(Color.DARK_GRAY);
                    }
                    else
                        square[i][j].setBackground(Color.WHITE);
                }
                else {
                    if (j % 2 == 0) {
                        square[i][j].setBackground(Color.DARK_GRAY);
                    }
                    else {
                        square[i][j].setBackground(Color.WHITE);
                    }
                }
            }
    }

    /*Sets the initial location of the white pieces as any
    * square marked with "WP" and the location of the black
    * pieces as any sqaure marked "BP".*/
    private JLabel getPieceLocations(String strPieceName){
        JLabel piece;
        players = new ArrayList<>();
        switch (strPieceName) {
            case "WP":
                piece = new JLabel(this.whitePiece);
                break;
            case "BP":
                piece = new JLabel(this.blackPiece);
                break;
            default:
                piece = new JLabel();
                break;
        }

        return piece;
    }
    /*https://mukarrammukhtar.wordpress.com/chess-board-in-java/
    Used arrangeChessPieces() method to iterate through the board and
    set the starting locations of the pieces. */
    private void setPieces()
    {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
            {
                this.square[i][j].add(this.getPieceLocations(strBoard[i][j]), BorderLayout.CENTER);
                this.square[i][j].validate();
            }
    }

    /*Sets the Game menu and the game options as
    * well as a listener for each option.*/
    private void createGameMenu(){
        gameMenu = new JMenu("Game");
        JMenuItem item;
        item = new JMenuItem("New");
        item.addActionListener(this);
        gameMenu.add(item);
        item = new JMenuItem("Save");
        item.addActionListener(this);
        gameMenu.add(item);
        item = new JMenuItem("Open");
        item.addActionListener(this);
        gameMenu.add(item);
        item = new JMenuItem("Quit");
        item.addActionListener(this);
        gameMenu.add(item);
    }

    /*Sets the player menu and the game options as
    * well as a listener for each option.*/
    private void createPlayerMenu(){
        playerMenu = new JMenu("Players");
        JMenuItem item;
        item = new JMenuItem("Add");
        item.addActionListener(this);
        playerMenu.add(item);
        item = new JMenuItem("Show");
        item.addActionListener(this);
        playerMenu.add(item);
        /*item = new JMenuItem("Reset");
        item.addActionListener(this);
        playerMenu.add(item);*/
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add":
                addPlayers();
                break;
            case "Show":
                showPlayers();
                break;
        /*else if(e.getActionCommand().equals("Reset")){
            resetPlayers();
        }*/
            case "New":
                newGame();
            case "Save":
                try{
                    saveGame();
                    JOptionPane.showMessageDialog(null, "Saving game.");
                    break;
                }catch (IOException e1){
                    JOptionPane.showMessageDialog(null, "Error saving game.");
            }
            case "Open":
                open();
                JOptionPane.showMessageDialog(null, "Opening game.");
                break;
        }
    }

    private void newGame() {
        CheckerBoardGUI game = new CheckerBoardGUI();
        game.setVisible(true);
    }

    /*This method allows the players to select a piece to be moved and then
    * select where to move the piece. This also ensures each move is valid
    * as well as passing the move onto the next player.
    * https://mukarrammukhtar.wordpress.com/chess-board-in-java/
    * Used as a reference for creating this method.*/
    public void mouseClicked(MouseEvent e){
        if(player1Turn){
            Object source = e.getSource();
            JPanel temp = (JPanel)source;
            int x = (temp.getX()/57);
            int y = (temp.getY()/57);

            System.out.print("Blacks Turn.");

            System.out.print(moveFrom);

            System.out.print(moveTo);

            this.moveSelection = !this.moveSelection;
            if(this.moveSelection)
            {
                moveFrom = new Point(x,y);

                if(this.strBoard[moveFrom.y][moveFrom.x].trim().equals(""))
                    this.moveSelection = !this.moveSelection;
            }
            else
            {
                this.moveTo = new Point(x,y);
                if(!moveFrom.equals(this.moveTo))
                {
                    /*if(moveTo.y<moveFrom.y){
                        if(moveFrom.y-moveTo.y==1) {
                            JOptionPane.showMessageDialog(null, "Valid");
                            this.movePiece();
                        }else
                            JOptionPane.showMessageDialog(null,"Invalid. Piece can only be moved forward 1 space.");

                    }else
                        JOptionPane.showMessageDialog(null,"Player 1 must move a black piece forward.");*/



                    /*Ensures the first point selected isn't blank*/
                    if(!this.strBoard[moveFrom.y][moveFrom.x].trim().equals("")){
                        /*Prevents a piece from moving to any square marked IV*/
                        if(!strBoard[this.moveTo.y][this.moveTo.x].trim().equals("IV")) {
                            this.strBoard[this.moveTo.y][this.moveTo.x] = this.strBoard[moveFrom.y][moveFrom.x];
                            this.strBoard[moveFrom.y][moveFrom.x] = " ";
                            if (moveFrom.y < moveTo.y) {
                                JOptionPane.showMessageDialog(null, "Invalid. Must move piece forward.");

                            }else if (this.strBoard[this.moveTo.y][this.moveTo.x].equals(this.strBoard[moveFrom.y][moveFrom.x])) {
                                JOptionPane.showMessageDialog(null, "Invalid.Piece must be moved from starting point.");

                            }else if(moveTo.y <= moveFrom.y-2) {
                                JOptionPane.showMessageDialog(null, "Invalid. Can't not move piece forward 2 spaces.");
                            }else
                                movePiece();

                        }else
                            JOptionPane.showMessageDialog(null, "Invalid Move");
                    }else
                        JOptionPane.showMessageDialog(null,"Piece must be moved from starting point.");
                }
            }
        }else if(player2Turn){
            Object source = e.getSource();
            JPanel temp = (JPanel)source;
            int x = (temp.getX()/57);
            int y = (temp.getY()/57);

            System.out.print(x + " " + y);

            System.out.print("Whites Turn.");

            this.moveSelection = !this.moveSelection;
            if(this.moveSelection)
            {
                moveFrom = new Point(x,y);

                System.out.print(moveFrom);

                if(this.strBoard[moveFrom.y][moveFrom.x].trim().equals(""))
                    this.moveSelection = !this.moveSelection;
            }
            else
            {
                this.moveTo = new Point(x,y);
                if(!moveFrom.equals(this.moveTo))
                {
                    /*if(moveTo.y<moveFrom.y){
                        if(moveFrom.y-moveTo.y==1) {
                            JOptionPane.showMessageDialog(null, "Valid");
                            this.movePiece();
                        }else
                            JOptionPane.showMessageDialog(null,"Invalid. Piece can only be moved forward 1 space.");

                    }else
                        JOptionPane.showMessageDialog(null,"Player 1 must move a black piece forward.");



                    /*Ensures the first point selected isn't blank*/
                    if(!this.strBoard[moveFrom.y][moveFrom.x].trim().equals("")){
                        if(!strBoard[this.moveTo.y][this.moveTo.x].trim().equals("IV")) {
                            this.strBoard[this.moveTo.y][this.moveTo.x] = this.strBoard[moveFrom.y][moveFrom.x];
                            this.strBoard[moveFrom.y][moveFrom.x] = " ";
                            if (moveFrom.y > moveTo.y) {
                                JOptionPane.showMessageDialog(null, "Invalid. Must move piece forward.");
                                this.strBoard[this.moveTo.y][this.moveTo.x] = this.strBoard[moveFrom.y][moveFrom.x];
                            }else if (strBoard[this.moveTo.y][this.moveTo.x].equals(this.strBoard[moveFrom.y][moveFrom.x])) {
                                JOptionPane.showMessageDialog(null, "Piece must be moved from starting point.");


                            }else if(moveTo.y >= moveFrom.y+2){
                                JOptionPane.showMessageDialog(null, "Invalid. Can't move piece forward 2 spaces.");

                            }else
                                this.movePiece();

                        }else
                            JOptionPane.showMessageDialog(null, "Invalid Move");

                    }else
                        JOptionPane.showMessageDialog(null,"Piece must be moved from starting point.");

                }
            }
        }
    }
    /*This method executes on moving the piece from one square to the other
    using the variables moveFrom and moveTo.
    https://mukarrammukhtar.wordpress.com/chess-board-in-java/*/
    private void movePiece(){
        for(int z = 0; z < this.square[this.moveTo.y][this.moveTo.x].getComponentCount(); z++)
            if(this.square[this.moveTo.y][this.moveTo.x].getComponent(z).getClass().toString().contains("JLabel")) {
                this.square[this.moveTo.y][this.moveTo.x].remove(z);
                this.square[this.moveTo.y][this.moveTo.x].repaint();
            }

        for(int z = 0; z < this.square[moveFrom.y][moveFrom.x].getComponentCount(); z++)
            if(this.square[moveFrom.y][moveFrom.x].getComponent(z).getClass().toString().contains("JLabel"))
            {
                this.square[moveFrom.y][moveFrom.x].remove(z);
                this.square[moveFrom.y][moveFrom.x].repaint();
                player1Turn=!player1Turn;
                player2Turn=!player2Turn;
            }

        this.square[this.moveTo.y][this.moveTo.x].add(this.getPieceLocations(this.strBoard[this.moveTo.y][this.moveTo.x]), BorderLayout.CENTER);
        this.square[this.moveTo.y][this.moveTo.x].validate();
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

    private void addPlayers(){
        for(int i=1;i<=2;i++){
            String name = JOptionPane.showInputDialog("Enter name of player " + i + ": ");
            players.add(new Player(name));
        }
        int player2PiecesTaken = 0;
        if(player2Turn) {
            player2.setText("Player 2: " + players.get(1).getName() + "      Pieces taken: " + player2PiecesTaken + "*");
        }else player2.setText("Player 2: " + players.get(1).getName() + "      Pieces taken: " + player2PiecesTaken);
        int player1PiecesTaken = 0;
        if(player1Turn){
            player1.setText("Player 1: " + players.get(0).getName() + "      Pieces taken: " + player1PiecesTaken + "*");
        }else player1.setText("Player 1: " + players.get(0).getName() + "      Pieces taken: " + player1PiecesTaken);
    }
            /*if(getName().length()>0) {
                if(players.size()==1) {
                    player1.setText((String) players.get(0));
                }else if(players.size()==2){
                    player2.setText((String) players.get(1));
                }else
                    JOptionPane.showMessageDialog(null, "Two players already added. Select Reset to add new players.");
        }else{
                JOptionPane.showMessageDialog(null, "Invalid name. Please re-enter your name.");
            }*/
    private void showPlayers(){
        StringBuilder playerInfo = new StringBuilder();
        for(Player p : players){
            playerInfo.append("Name: ").append(p.getName()).append("\nPieces left: ").append(p.getPiecesLeft()).append("\nPieces taken: ").append(p.getPiecesTaken()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, playerInfo + "\n\n");
        /*if(players.size()<1){
            JOptionPane.showMessageDialog(null,"No Players Added");
        }else if(players.size()==1)
            JOptionPane.showMessageDialog(null, "Checkers.Player 1: " + players.get(0));
        else
            JOptionPane.showMessageDialog(null, "Checkers.Player 1: " + players.get(0) + "\nCheckers.Player 2: " + players.get(1)); */
    }
    /*private void resetPlayers(){

        if(players.size()<1){
            JOptionPane.showMessageDialog(null, "There are no players added.");
        }else if(players.size()>=1){
            players.removeAll(players);
            player1.setText("Checkers.Player 1");
            player2.setText("Checkers.Player 2");
            JOptionPane.showMessageDialog(null, "Players have been reset.");
        }else
            players.remove(0);
            JOptionPane.showMessageDialog(null, "Players have been reset");
    }*/


    /*http://beginwithjava.blogspot.com/2011/04/java-file-save-and-file-load-objects.html
    Used as a reference for creating a save state and being able to reopen a saved game. */
    private void saveGame() throws IOException {
        File saveFile = new File("saveFile.data");
        FileOutputStream outFileStream = new FileOutputStream(saveFile);
        ObjectOutputStream os = new ObjectOutputStream(outFileStream);

        os.writeObject(players);

        os.close();
    }

    private void open(){
        try{
            File inFile = new File("saveFile.data");
            FileInputStream saveFile = new FileInputStream(inFile);
            ObjectInputStream in = new ObjectInputStream(saveFile);

            ArrayList<Player> players = (ArrayList<Player>) in.readObject();

            int player2PiecesTaken = 0;
            if(player2Turn) {
                player2.setText("Player 2: " + players.get(1).getName() + "      Pieces taken: " + player2PiecesTaken + "*");
            }else player2.setText("Player 2: " + players.get(1).getName() + "      Pieces taken: " + player2PiecesTaken);
            int player1PiecesTaken = 0;
            if(player1Turn){
                player1.setText("Player 1: " + players.get(0).getName() + "      Pieces taken: " + player1PiecesTaken + "*");
            }else player1.setText("Player 1: " + players.get(0).getName() + "      Pieces taken: " + player1PiecesTaken);

            JOptionPane.showMessageDialog(null, players);
            in.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error opening game.");
        }
    }
}