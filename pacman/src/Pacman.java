import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.ColorUIResource;


public class Pacman extends JPanel implements ActionListener, KeyListener {

    class Block
    {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        int direction= 'U'; // U D L R
        int velocityX= 0;
        int velocityY= 0;

        Block (Image image, int x, int y, int width, int height)
        {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height=height;
            this.startX=x;
            this.startY=y;
        }
        void updateDirection(char direction)
        {
            this.direction= direction;
            updateVelocity();
        }

        void updateVelocity()
        {
            if(this.direction== 'U')
            {
                this.velocityX=0;
                this.velocityY= -tileSize/4;
            }
            else if(this.direction== 'D')
            {
                this.velocityX=0;
                this.velocityY= tileSize/4;
            }
            else if(this.direction== 'L')
            {
                this.velocityY=0;
                this.velocityX= -tileSize/4;
            }
            else if(this.direction== 'R')
            {
                this.velocityY=0;
                this.velocityX= tileSize/4;
            }
        }
    }
            //X = wall, O = skip, P = pac man, ' ' = food
            //Ghosts: b = blue, o = orange, p = pink, r = red
           private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX" 
        };
       
            // hash set to store the details of the particular items/ lists
            // array can also be used but, hash set is used here for better performance (storing and retrival)
        
            HashSet<Block> walls;
            HashSet<Block> foods;
            HashSet<Block> ghosts;
            Block pacman;
            Timer gameLoop;
        
    
    private int rowCount=21;
    private int colCount=19;
    private int tileSize=32;
    private int boardwidth= colCount*tileSize;
    private int boardheight= rowCount*tileSize;

    //images for the ghosts
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    //images for the movements
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    public Pacman() 
    {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        //loading the images in the src folder to the variables
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage(); 

        // the "./foldername.type" in the file location string means that the file is in the same folder as the java file(this one) here it is the source folder
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage(); 
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage(); 
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage(); 
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage(); 

        pacmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage(); 
        pacmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage(); 
        pacmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage(); 
        pacmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage(); 

        loadMap();
        /*System.out.println(walls.size());
        System.out.println(foods.size());
        System.out.println(ghosts.size());
        */
        
        gameLoop= new Timer(50, this); //20fps == 1000/50 sec
        gameLoop.start();

    }
    public void loadMap() {
        walls= new HashSet<Block>();
        foods= new HashSet<Block>();
        ghosts= new HashSet<Block>();
        
        for( int r =0; r<rowCount; r++) 
        {
            for(int c =0; c<colCount; c++ )
            {
                String row = tileMap[r];
                char tileMapChar= row.charAt(c);

                int x=c*tileSize;
                int y=r*tileSize;

                if( tileMapChar=='X') // block wall
                {
                    Block wall = new Block(wallImage, x, y, tileSize,tileSize);
                    walls.add(wall);
                }
                else if(tileMapChar=='b') // blue ghost
                {
                    Block ghost= new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar=='o') // orange ghost
                {
                    Block ghost= new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar=='p') // pink ghost
                {
                    Block ghost= new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar=='r') // red ghost
                {
                    Block ghost= new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar=='P') // pacman, default face right side
                {
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if(tileMapChar==' ') // food by scratch, no image
                {
                    Block food= new Block(null, x+14, y+14, 4, 4);
                    foods.add(food);
                }
               
            }
        }
    }
    public void paintComponent( Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for(Block ghost: ghosts)
        {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);   
        }
        
        for(Block wall: walls)
        {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(ColorUIResource.WHITE);
        for(Block food: foods)
        {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }
    public void move()
    {
        pacman.x+=pacman.velocityX;
        pacman.y+=pacman.velocityY;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) 
    {
        System.out.println("KeyEvent:" +e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            pacman.updateDirection('U');
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            pacman.updateDirection('D');
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            pacman.updateDirection('L');
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            pacman.updateDirection('R');
        }
    }
}

