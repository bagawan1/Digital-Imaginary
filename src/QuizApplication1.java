import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuizApplication1 extends JFrame {
    private JLabel[] questionLabels;
    private JRadioButton[][] options;
    private JButton submitButton;
    private int totalQuestions;
    private int score;
    int id;
    // Quiz questions and answers
    private String[] questions = {
    	"Question 1: What does an RDBMS consist of?",
    	"Question 2: The ability to query data, as well as insert, delete, and alter tuples, is offered by ____________",
    	"Question 3: Which of the following command is correct to delete the values in the relation teaches?",
    	"Question 4: Which one of the following given statements possibly contains the error?",
    	"Question 5: What do you mean by one to many relationships?"
    };

    private String[][] optionsList = {
        {"Collection of Records", "Collection of Keys", "Collection of Tables", "Collection of Fields"},
        {"TCL (Transaction Control Language)", "DCL (Data Control Language)", "DDL (Data Definition Langauge)", "DML (Data Manipulation Langauge)"},
        {"Delete from teaches;", "Delete from teaches where Id =’Null’;", "Remove table teaches;", "Drop table teaches;"},
        {"select * from emp where empid = 10003;", "select empid from emp where empid = 10006;", "select empid from emp;", "select empid where empid = 1009 and Lastname = 'GELLER';"},
        {"One class may have many teachers", "One teacher can have many classes", "Many classes may have many teachers", "Many teachers may have many classes"}
    };

    private int[] answers = {2,3,0,3,1}; // Index of the correct answer for each question

    public QuizApplication1(int id) {
    	this.id=id;
        setTitle("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        questionLabels = new JLabel[questions.length];
        options = new JRadioButton[questions.length][4];
        ButtonGroup[] groups = new ButtonGroup[questions.length];
        setSize(900, 600);
        setVisible(true);
        for (int i = 0; i < questions.length; i++) {
            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

            questionLabels[i] = new JLabel();
            questionPanel.add(questionLabels[i], BorderLayout.NORTH);

            JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
            groups[i] = new ButtonGroup();

            for (int j = 0; j < 4; j++) {
                options[i][j] = new JRadioButton();
                optionsPanel.add(options[i][j]);
                groups[i].add(options[i][j]);
            }

            questionPanel.add(optionsPanel, BorderLayout.CENTER);
            contentPanel.add(questionPanel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 600));

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswers();
                setVisible(false);
                showResult();
            }
        });

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(submitButton);

        totalQuestions = questions.length;
        score = 0;

        showQuestions();

        add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void showQuestions() {
        for (int i = 0; i < totalQuestions; i++) {
            questionLabels[i].setText(questions[i]);

            for (int j = 0; j < 4; j++) {
                options[i][j].setText(optionsList[i][j]);
                options[i][j].setSelected(false);
            }
        }
    }

    private void checkAnswers() {
        for (int i = 0; i < totalQuestions; i++) {
            for (int j = 0; j < 4; j++) {
                if (options[i][j].isSelected() && j == answers[i]) {
                    score++;
                    break;
                }
            }
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
JOptionPane.showMessageDialog(null, "Attempted Quiz Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QuizApplication1 quizApplication = new QuizApplication1(1);
                quizApplication.setSize(800, 600); // Increase the size of the frame
                quizApplication.setVisible(true);
            }
        });
    }
}
