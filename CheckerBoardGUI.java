import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by CianC on 08/11/2018.
 */

public class CheckerBoardGUI extends JFrame implements ActionListener, MouseListener {
    private JMenu gameMenu, playerMenu;
    private ArrayList<Player> players;
    private boolean validMove = true;
    private JPanel[][] square = new JPanel[8][8];
    private JLabel player1 = new JLabel();
    private JLabel player2 = new JLabel();
    private Point moveFrom, moveTo;
    private JPanel board = new JPanel(new GridLayout(8, 8));
    private ImageIcon whitePiece = new ImageIcon(System.getProperty("user.dir") + "/images/white_piece.png");
    JLabel white = new JLabel(whitePiece);
    private ImageIcon blackPiece = new ImageIcon(System.getProperty("user.dir") + "/images/black_piece.png");
    JLabel black = new JLabel(blackPiece);
    private boolean moveSelection=false;

    /**
     * Sets the starting location for all white and black pieces
     * as well as setting the permanently invalid squares.
     */
    private String[][] strBoard = new String[][]{
            {"WP","IV","WP","IV","WP","IV","WP","IV"},
            {"IV","WP","IV","WP","IV","WP","IV","WP"},
            {"WP","IV","WP","IV","WP","IV","WP","IV"},
            {"IV","","IV","","IV","","IV",""},
            {"","IV","","IV","","IV","","IV"},
            {"IV","BP","IV","BP","IV","BP","IV","BP"},
            {"BP","IV","BP","IV","BP","IV","BP","IV"},
            {"IV","BP","IV","BP","IV","BP","IV","BP"} };

    CheckerBoardGUI() {
        setTitle("Chess");
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

    // https://mukarrammukhtar.wordpress.com/chess-board-in-java/
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
                        validMove=true;
                }
                else {
                    if (j % 2 == 0) {
                        square[i][j].setBackground(Color.DARK_GRAY);
                    }
                    else {
                        square[i][j].setBackground(Color.WHITE);
                        validMove = true;
                    }
                }
            }
    }

    private JLabel getPieceLocations(String strPieceName){
        JLabel piece;
        players = new ArrayList<>();
        if(strPieceName.equals("WP")){
            piece = new JLabel(this.whitePiece);
        }
        else if(strPieceName.equals("BP")) {
            piece = new JLabel(this.blackPiece);
        }
        else
            piece = new JLabel();

        return piece;
    }

    private void setPieces()

    {

        for(int i = 0; i < 8; i++)

            for(int j = 0; j < 8; j++)

            {
                this.square[i][j].add(this.getPieceLocations(strBoard[i][j]), BorderLayout.CENTER);

                this.square[i][j].validate();
            }
    }
    private void createGameMenu(){
        gameMenu = new JMenu("Game");
        JMenuItem item;
        item = new JMenuItem("New");
        item.addActionListener(this);
        gameMenu.add(item);
        item = new JMenuItem("Save");
        gameMenu.add(item);
        item = new JMenuItem("Load");
        gameMenu.add(item);
        item = new JMenuItem("Quit");
        gameMenu.add(item);
    }

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
        if(e.getActionCommand().equals("Add")){
            addPlayers();
        }
        else if(e.getActionCommand().equals("Show")){
            showPlayers();
        }
        /*else if(e.getActionCommand().equals("Reset")){
            resetPlayers();
        }*/
        else if(e.getActionCommand().equals("New")){
            setPieces();
        }
    }

    public void mouseClicked(MouseEvent e){
        boolean player1Turn = true;
        boolean player2Turn = false;
        if(player1Turn){
            Object source = e.getSource();
            JPanel temp = (JPanel)source;
            int x = (temp.getX()/57);
            int y = (temp.getY()/57);

            this.moveSelection = !this.moveSelection;
            if(this.moveSelection)
            {
                this.moveFrom = new Point(x,y);

                if(this.strBoard[this.moveFrom.y][this.moveFrom.x].trim().equals(""))
                    this.moveSelection = !this.moveSelection;


                if(!this.strBoard[this.moveFrom.y][this.moveFrom.x].trim().equals("") && this.strBoard[this.moveFrom.y][this.moveFrom.x].charAt(1) == 'W') {
                    this.moveSelection = !this.moveSelection;
                }
            }
            else
            {
                this.moveTo = new Point(x,y);
                if(!this.moveFrom.equals(this.moveTo))
                {
                    if(this.strBoard[this.moveFrom.y][this.moveFrom.x].trim() != "")
                        if(strBoard[this.moveTo.y][this.moveTo.x].trim() != "IV") {
                            this.strBoard[this.moveTo.y][this.moveTo.x] = this.strBoard[this.moveFrom.y][this.moveFrom.x];
                            this.strBoard[this.moveFrom.y][this.moveFrom.x] = " ";
                            this.movePiece();

                        }else if(strBoard[this.moveTo.y][this.moveTo.x].equals(this.strBoard[this.moveFrom.y][this.moveFrom.x])){
                            JOptionPane.showMessageDialog(null, "Invalid move");
                        }else {
                            JOptionPane.showMessageDialog(null, "Invalid move");
                        }
                }
            }
        }
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
            String name = JOptionPane.showInputDialog("Enter name of player" + i + ": ");
            players.add(new Player(name));
        }
        player2.setText("Player 2: " + players.get(0).getName() + "      Pieces left: " + players.get(0).getPiecesLeft() + "      Pieces taken: " + players.get(0).getPiecesTaken());
        player1.setText("Player 1: " + players.get(1).getName() + "      Pieces left: " + players.get(1).getPiecesLeft() + "      Pieces taken: " + players.get(1).getPiecesTaken());
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
        String playerInfo = "";
        for(Player p : players){
            playerInfo += ("Name: " + p.getName() + "\nPieces left: " + p.getPiecesLeft() + "\nPieces taken: " + p.getPiecesTaken() + "\n\n");
        }
        JOptionPane.showMessageDialog(null, playerInfo + "\n\n");
        /*if(players.size()<1){
            JOptionPane.showMessageDialog(null,"No Players Added");
        }else if(players.size()==1)
            JOptionPane.showMessageDialog(null, "Player 1: " + players.get(0));
        else
            JOptionPane.showMessageDialog(null, "Player 1: " + players.get(0) + "\nPlayer 2: " + players.get(1)); */
    }
    /*private void resetPlayers(){

        if(players.size()<1){
            JOptionPane.showMessageDialog(null, "There are no players added.");
        }else if(players.size()>=1){
            players.removeAll(players);
            player1.setText("Player 1");
            player2.setText("Player 2");
            JOptionPane.showMessageDialog(null, "Players have been reset.");
        }else
            players.remove(0);
            JOptionPane.showMessageDialog(null, "Players have been reset");
    }*/

    public void selectPiece(){
    }

    public void movePiece(){
        for(int z = 0; z < this.square[this.moveTo.y][this.moveTo.x].getComponentCount(); z++)
            if(this.square[this.moveTo.y][this.moveTo.x].getComponent(z).getClass().toString().contains("JLabel"))
            {
                this.square[this.moveTo.y][this.moveTo.x].remove(z);
                this.square[this.moveTo.y][this.moveTo.x].repaint();
            }

        for(int z = 0; z < this.square[this.moveFrom.y][this.moveFrom.x].getComponentCount(); z++)
            if(this.square[this.moveFrom.y][this.moveFrom.x].getComponent(z).getClass().toString().contains("JLabel"))
            {
                this.square[this.moveFrom.y][this.moveFrom.x].remove(z);
                this.square[this.moveFrom.y][this.moveFrom.x].repaint();
            }

            this.square[this.moveTo.y][this.moveTo.x].add(this.getPieceLocations(this.strBoard[this.moveTo.y][this.moveTo.x]), BorderLayout.CENTER);
        this.square[this.moveTo.y][this.moveTo.x].validate();
    }


}