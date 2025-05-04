import javax.swing.JFrame;


public class App {
    public static void main(String[] args) throws Exception {
    // 19 cloumns and 21 rows
    // each tile is 32*32 pixels
    int rowCount=21;
    int colCount=19;
    int tileSize=32;
    int boardwidth= colCount*tileSize;
    int boardheight= rowCount*tileSize;

    // new window
    JFrame frame= new JFrame("Pac Man");
    frame.setVisible(true);
    //size of the window
    frame.setSize(boardwidth, boardheight);
    //no specific location can move around
    frame.setLocationRelativeTo(null);
    // can't minimize
    frame.setResizable(false);
    //post closing operation 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Pacman pacmanGame= new Pacman();
    //adding panel to the window frame
    frame.add(pacmanGame);
    //full size of jpanel in the window frame
    frame.pack();
    pacmanGame.requestFocus();
    frame.setVisible(true);
    }
}
