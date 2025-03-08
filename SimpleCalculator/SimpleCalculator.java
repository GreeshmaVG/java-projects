import java.awt.*; // swing components JFrame, JTextField, JButton, JPanel (mostly for gui)
import java.awt.event.*; // for layout managers, fonts and colours
import javax.swing.*;
// can use KeyListener for using keyboard  events ig (that's for later)

public class SimpleCalculator extends JFrame implements ActionListener 
{
    private JTextField display; // to display the text to the user ( like the answer or what we type)
    private String num1=""; // for number 1
    private String num2=""; // for number 2
    private String operator=""; // for operator
    // they are all declared as strings because we don't know what the user will type yet
    // we will convert them to numbers later when we need to do the calculation
    // they are all declared globally so that we can access them anywhere in this program

    public SimpleCalculator()
    {
        //setting up the window
        setTitle("calculator!"); // title of the window
        setSize(400, 400); //dimensions of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default close operation mean what happens if we close the window
        // we want to exit the program so we set it to exit on close
        setLayout(new BorderLayout()); //organizing components

        // setting up the display field
        display = new JTextField(); // creating a text field to show the numbers and the result
        display.setEditable(false); // we don't want the user to be able to edit the display field hence making it false will prevents manual typing
        display.setFont(new Font("Monospaced", Font.BOLD, 40)); //sets the font styl and all by creating an instance of the Font()
        add(display, BorderLayout.NORTH); // adding the display field to the top of the window

        //setting up buttons
        JPanel panel = new JPanel(); // creating a panel to hold the buttons
        panel.setLayout(new GridLayout(4,4,3,3)); //organizing the buttons in a 4x4 grid layout with 5px gaps between them
        String buttons[] =
        {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        // dynamically define an array of buttons
        // we will use a loop to create the buttons and add them to the panel
        for (String text: buttons)
        {
            JButton button = new JButton(text); // creating a button with the text
            button.setFont(new Font("Monospaced", Font.BOLD, 20)); // setting the font of the button
            button.addActionListener(this); // adding an action listener to the button so that we can handle the button
            panel.add(button); //adds the buttons to the panel one by one
        }

        add(panel, BorderLayout.CENTER); //adds the buttons in the center of the panel
        setVisible(true);
    }

    @Override
    //this is an event handler method from the ActionListener interface
    public void actionPerformed(ActionEvent e) //called when a button is clicked
    {
        String input = e.getActionCommand(); // getting the text of the button that was clicked and is stored in the input variable
        if(input.matches("[0-9.]")) // checks if number or decimal "." is clicked
        // the matches() method checks if the string follows a regex pattern
        {
            if(operator.isEmpty()) // if no operator is selected priorly
            {
                num1 += input; // store in num1
            }
            else                   // if an operator is selected already, meaning num1 has a value stored already
            {
                num2 += input; //  store in num2
            }
            display.setText(num1 + operator + num2); //it shows whatever we have in num1, operator, and num2
        }
        else if(input.matches("[+*/-]")) // if operator is clicked
        {
            if(!num1.isEmpty()) // store the operator only after num1 is set
            {
                operator= input; // store the operator in the operator variable
            }
            display.setText(num1 + operator);
        }
        else if(input.equals("=")) // if "=" is clicked, perfoms the calculation
        {
            if(!num1.isEmpty() && !num2.isEmpty()) // checks if we have both num1 and num2 before proceeding for the calculation
            {
                double result= calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator); // converts num1 and num2 to double(from String type)
                // calls calculate() which is an user defined method in the later part of the code to perform mathematical operations
                display.setText(String.valueOf(result)); // updates the display with the result by converting the datatype of result from double back to String
                num1= String.valueOf(result); // updates num1 with the result
                num2= ""; // resets for the next calculation
                operator= ""; // resets for the next calculation

            }
        }

    }
    private double calculate(double a, double b, String op) // basic method which takes in 2 double parameters and a String operator
    {
        switch(op) // uses switch to choose from different operation
        {
            case "+": return a+b;
            case "-": return a-b;
            case "*": return a*b;
            case "/": return (b!=0) ? a/b : 0; // conditional statement prevents division by zero
            default: return 0; 
        }
    }
    public static void main( String[] args)
    {
        new SimpleCalculator();
    }
    
}
