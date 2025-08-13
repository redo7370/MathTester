import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class MathTester {

    private final static List<String> operators = Arrays.asList("+", "-", "*", "/");
    private static Random rand = new Random();
    private static String currentTask;
    private static int currentResult;
    private final static int amountPanels = 4;
    private static List<Color> colors = Arrays.asList(Color.WHITE, Color.GREEN, Color.YELLOW, Color.RED);
    private static int currentMode = 1;

    public static void GUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("MathTester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setResizable(false);

        // Create Panel for Components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(amountPanels, 1));
        frame.add(mainPanel);

        // Create SubPanels for different use cases
        JPanel[][] subPanels = new JPanel[amountPanels][1];
        JPanel menuPanel = subPanels[0][0] = new JPanel();
        JPanel outputPanel = subPanels[1][0] = new JPanel();
        JPanel buttonPanel = subPanels[2][0] = new JPanel();
        JPanel textPanel = subPanels[3][0] = new JPanel();

        buttonPanel.setLayout(new GridLayout(1, 2));
        textPanel.setLayout(new GridLayout(1, 1));
        outputPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(menuPanel);
        mainPanel.add(outputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(textPanel);

        // Create Menu Bar
        Border menuBorder = new LineBorder(Color.black);
        JMenuBar modeMenu = new JMenuBar();
        modeMenu.setBorder(menuBorder);
        menuPanel.add(modeMenu);

        // Create Items and separators for Menu
        JMenuItem easyMode = new JMenuItem("Easy");
        JMenuItem mediumMode = new JMenuItem("Medium");
        JMenuItem hardMode = new JMenuItem("Hard");
        easyMode.setBackground(colors.get(1));
        menuPanel.setBackground(colors.get(1));
        modeMenu.add(easyMode);
        modeMenu.add(mediumMode);
        modeMenu.add(hardMode);

        // Create Buttons
        JButton[][] buttons = new JButton[1][2];
        JButton submitButton = buttons[0][0] = new JButton("Submit");
        JButton quitButton = buttons[0][1] = new JButton("Quit");
        buttonPanel.add(submitButton);
        buttonPanel.add(quitButton);

        // Create Entry Textfield
        JTextField[][] textFields = new JTextField[2][1];
        JTextField entry = textFields[0][0] = new JTextField("");
        textPanel.add(entry);

        // Create Output Label
        JLabel[][] labels = new JLabel[1][2];
        JLabel calcLabel = labels[0][0] = new JLabel(currentTask);
        JLabel outputLabel = labels[0][1] = new JLabel("Solve the equation.");
        outputPanel.add(calcLabel);
        outputPanel.add(outputLabel);

        // Add Action Listeners to Buttons
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                List<String> comp = compareResult(entry.getText());
                calcLabel.setText(comp.get(0));
                outputLabel.setText(comp.get(1));
                entry.setText("");
            }
        });

        // Add Action Listeners to MenuBar
        easyMode.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                menuPanel.setBackground(colors.get(1));
                easyMode.setBackground(colors.get(1));
                mediumMode.setBackground(colors.get(0));
                hardMode.setBackground(colors.get(0));
                modeSelect("easy");
                calcLabel.setText(currentTask);
                outputLabel.setText("Switched to Easy-Mode");
            }
        });

        mediumMode.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                menuPanel.setBackground(colors.get(2));
                easyMode.setBackground(colors.get(0));
                mediumMode.setBackground(colors.get(2));
                hardMode.setBackground(colors.get(0));
                modeSelect("medium");
                calcLabel.setText(currentTask);
                outputLabel.setText("Switched to Medium-Mode");
            }
        });

        hardMode.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                menuPanel.setBackground(colors.get(3));
                easyMode.setBackground(colors.get(0));
                mediumMode.setBackground(colors.get(0));
                hardMode.setBackground(colors.get(3));
                modeSelect("hard");
                calcLabel.setText(currentTask);
                outputLabel.setText("Switched to Hard-Mode");
            }
        });

        // Display the window.
        frame.setVisible(true);
    }

    private static void modeSelect(String mode) {
        switch (mode) {
            case "easy":
                currentMode = 1;
                break;

            case "medium":
                currentMode = 2;
                break;

            case "hard":
                currentMode = 3;
                break;

            default:
                System.exit(50);
                break;
        }

        taskHandler();
    }

    // Generate new Tasks to display and store the current answer
    private static void taskHandler() {
        int firstNumber = rand.nextInt(100 * currentMode);
        int secondNumber = rand.nextInt(100 * currentMode);
        String operator;

        if (firstNumber % secondNumber == 0 && secondNumber != 0) {
            operator = operators.get(rand.nextInt(4));
        } else {
            operator = operators.get(rand.nextInt(3));
        }

        if ((operator == "*" || operator == "/") && (firstNumber > 15 || secondNumber > 15)) {
            String newFirstNumber = String.valueOf(firstNumber);
            String newSecondNumber = String.valueOf(secondNumber);
            if (newFirstNumber.length() > currentMode || newSecondNumber.length() > currentMode) {
                firstNumber = Integer.parseInt(newFirstNumber.substring(0, currentMode));
                secondNumber = Integer.parseInt(newSecondNumber.substring(0, currentMode));
            }
        }

        currentTask = String.valueOf(firstNumber).concat(" ")
                .concat(operator)
                .concat(" ")
                .concat(String.valueOf(secondNumber))
                .concat(" = ");

        switch (operator) {
            case "+":
                currentResult = firstNumber + secondNumber;
                break;

            case "-":
                currentResult = firstNumber - secondNumber;
                break;

            case "*":
                currentResult = firstNumber * secondNumber;
                break;

            case "/":
                currentResult = firstNumber / secondNumber;
                break;

            default:
                currentResult = 0;
                System.exit(50);
                break;
        }
    }

    // Compare User Input to exercise result and return output to the Action
    // Listener
    private static List<String> compareResult(String number) {
        int userResult;
        try {
            userResult = Integer.valueOf(number);
        } catch (Exception ValueError) {
            List<String> misinputError = Arrays.asList(currentTask, "Error: Input could not be parsed as an Integer.");
            return misinputError;
        }

        if (userResult == currentResult) {
            String lastResult = String.format("Your Last answer %d was correct.",
                    Integer.parseInt(String.valueOf(currentResult)));
            taskHandler();
            List<String> checkAssist = Arrays.asList(currentTask, lastResult);
            return checkAssist;
        } else {
            List<String> checkAssist = Arrays.asList(currentTask, "Wrong Answer. Try Again.");
            return checkAssist;
        }
    }

    public static void main(String[] args) {
        // Evoke first exercise
        taskHandler();
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI();
            }
        });
    }
}