import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuizApplication2 {
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton option1RadioButton;
    private JRadioButton option2RadioButton;
    private JRadioButton option3RadioButton;
    private JRadioButton option4RadioButton;
    private JButton submitButton;
    public int id;
    private int currentQuestionIndex;
    private int score;

    private Question[] questions = {
            new Question("Question 1: What does an RDBMS consist of?",
                    new String[]{"Collection of Records", "Collection of Keys", "Collection of Tables", "Collection of Fields"}, 2),
            new Question("Question 2: The ability to query data, as well as insert, delete, and alter tuples, is offered by ____________",
                    new String[]{"TCL (Transaction Control Language)", "DCL (Data Control Language)", "DDL (Data Definition Langauge)", "DML (Data Manipulation Langauge)"}, 3),
            new Question("Question 3: Which of the following command is correct to delete the values in the relation teaches?",
                    new String[]{"Delete from teaches;", "Delete from teaches where Id =’Null’;", "Remove table teaches;", "Drop table teaches;"}, 0),
            new Question("Question 4: Which one of the following given statements possibly contains the error?",
                    new String[]{"select * from emp where empid = 10003;", "select empid from emp where empid = 10006;", "select empid from emp;", "select empid where empid = 1009 and Lastname = 'GELLER';"}, 3),
            new Question("Question 5: What do you mean by one to many relationships?",
                    new String[]{"One class may have many teachers", "One teacher can have many classes", "Many classes may have many teachers", "Many teachers may have many classes"}, 1)
    };

    public QuizApplication2(int x) {
    	id=x;
        currentQuestionIndex = 0;
        score = 0;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Quiz 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        questionLabel = new JLabel();
        panel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));

        option1RadioButton = new JRadioButton();
        optionsPanel.add(option1RadioButton);

        option2RadioButton = new JRadioButton();
        optionsPanel.add(option2RadioButton);

        option3RadioButton = new JRadioButton();
        optionsPanel.add(option3RadioButton);

        option4RadioButton = new JRadioButton();
        optionsPanel.add(option4RadioButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(option1RadioButton);
        buttonGroup.add(option2RadioButton);
        buttonGroup.add(option3RadioButton);
        buttonGroup.add(option4RadioButton);

        panel.add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });
        panel.add(submitButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        displayQuestion();
        frame.setVisible(true);
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];
            questionLabel.setText(currentQuestion.getQuestion());
            String[] options = currentQuestion.getOptions();
            option1RadioButton.setText(options[0]);
            option2RadioButton.setText(options[1]);
            option3RadioButton.setText(options[2]);
            option4RadioButton.setText(options[3]);
        } else {
        	frame.setVisible(false);
            showResult();
        }
    }

    private void submitAnswer() {
        Question currentQuestion = questions[currentQuestionIndex];
        int selectedOption = getSelectedOption();

        if (selectedOption == currentQuestion.getCorrectOption()) {
            score++;
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private int getSelectedOption() {
        if (option1RadioButton.isSelected()) {
            return 0;
        } else if (option2RadioButton.isSelected()) {
            return 1;
        } else if (option3RadioButton.isSelected()) {
            return 2;
        } else if (option4RadioButton.isSelected()) {
            return 3;
        } else {
            return -1;
        }
    }

    private void showResult() {
    	String driverClassName = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "bagawan";
        String pass = "laddu";

try {
    // Load the JDBC driver
    Class.forName(driverClassName);

    // Establish a connection to the database
    Connection con = DriverManager.getConnection(url, username, pass);

    // Perform database operations using the connection
Statement stmt=con.createStatement();  
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
Date date = new Date(); 
int a=stmt.executeUpdate("insert into scores values("+id+","+score+",2,'"+formatter.format(date)+"')");
if(a>0)
JOptionPane.showMessageDialog(null, "Attempted Quiz Successfully", "Error", JOptionPane.INFORMATION_MESSAGE);
else
JOptionPane.showMessageDialog(null, "Fail", "Error", JOptionPane.ERROR_MESSAGE);
    // Close the connection
    con.close();

    //System.out.println("Connection closed successfully.");
} catch (ClassNotFoundException s) {
    System.err.println("Failed to load JDBC driver: " + s.getMessage());
} catch (SQLException s) {
    System.err.println("Failed to connect to the database: " + s.getMessage());
}

    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizApplication2();
            }
        });
    }*/

    private class Question {
        private String question;
        private String[] options;
        private int correctOption;

        public Question(String question, String[] options, int correctOption) {
            this.question = question;
            this.options = options;
            this.correctOption = correctOption;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectOption() {
            return correctOption;
        }
    }
}
