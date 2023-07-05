import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.*;
public class Home {
    private JFrame frame;
    private JPanel insertQuestionPanel;
    private JTextField questionNumberTextField;
    private JTextField questionTextField;
    private JPanel insertNotesPanel;
    private JTextField chapterNumberTextField;
    private JTextField chapterNameTextField;
	private JTextArea scoresTextArea;
	public int id;
    public Home(int x) {
    	this.id=x;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Digital_Imaginary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu notesMenu = new JMenu("Notes");
        JMenuItem viewNotesItem = new JMenuItem("View Notes");
        JMenuItem insertNotesItem = new JMenuItem("Insert Notes");
        notesMenu.add(viewNotesItem);
        notesMenu.add(insertNotesItem);

        JMenu questionsMenu = new JMenu("Questions");
        JMenuItem viewQuestionsItem = new JMenuItem("View Questions");
        JMenuItem insertQuestionItem = new JMenuItem("Insert Question");
        JMenuItem updateQuestionItem = new JMenuItem("Update Question");
        JMenuItem deleteQuestionItem = new JMenuItem("Delete Question");
        questionsMenu.add(viewQuestionsItem);
        questionsMenu.add(insertQuestionItem);
        questionsMenu.add(updateQuestionItem);
        questionsMenu.add(deleteQuestionItem);

        JMenu quizMenu = new JMenu("Quiz");
        JMenuItem startQuizItem = new JMenuItem("Quiz1");
        quizMenu.add(startQuizItem);
        
        JMenuItem startQuizItem1 = new JMenuItem("Quiz2");
        quizMenu.add(startQuizItem1);
        
        JMenu scoresMenu = new JMenu("Scores");
        JMenuItem viewScoresItem = new JMenuItem("View Scores");
        scoresMenu.add(viewScoresItem);

        menuBar.add(notesMenu);
        menuBar.add(questionsMenu);
        menuBar.add(quizMenu);
        menuBar.add(scoresMenu);
        
        startQuizItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show all the questions
                new QuizApplication(id);
            }
        });

        startQuizItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show all the questions
                new QuizApplication1(id);
            }
        });
        
        viewQuestionsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show all the questions
                showQuestions();
            }
        });

        insertQuestionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the insert question panel
                createInsertQuestionPanel();
            }
        });

        updateQuestionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the update question panel
                createUpdateQuestionPanel();
            }
        });

        deleteQuestionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the delete question panel
                createDeleteQuestionPanel();
            }
        });

        insertNotesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the insert notes panel
                createInsertNotesPanel();
            }
        });
		
        viewNotesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the insert notes panel
                showNotes();
            }
        });
        
		viewScoresItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the student scores
                showStudentScores();
            }
        });
		ImageIcon img=new ImageIcon(getClass().getResource("p1.png"));
		JLabel x=new JLabel(img);
		frame.add(x);
        frame.setJMenuBar(menuBar);
        frame.setSize(600, 500); // Increased frame size
        frame.setVisible(true);
    }
    private void showNotes() {
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
			ResultSet rs=stmt.executeQuery("select * from notes"); 
			StringBuilder sb = new StringBuilder();
			while(rs.next())
			{
				int x=rs.getInt(1);
				String m=rs.getString(2);
            

                sb.append(x).append(": ").append(m).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "Notes", JOptionPane.PLAIN_MESSAGE);
        con.close();

        System.out.println("Connection closed successfully.");
    } catch (ClassNotFoundException e) {
        System.err.println("Failed to load JDBC driver: " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Failed to connect to the database: " + e.getMessage());
    }
    }

    private void showQuestions() {
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
			ResultSet rs=stmt.executeQuery("select * from questions"); 
			StringBuilder sb = new StringBuilder();
			while(rs.next())
			{
				int x=rs.getInt(1);
				String m=rs.getString(2);
            

                sb.append(x).append(": ").append(m).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "Questions", JOptionPane.PLAIN_MESSAGE);
            con.close();

    } catch (ClassNotFoundException e) {
        System.err.println("Failed to load JDBC driver: " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Failed to connect to the database: " + e.getMessage());
    }
    }

    private void createInsertQuestionPanel() {
        insertQuestionPanel = new JPanel();
        insertQuestionPanel.setLayout(new GridLayout(2, 2));

        JLabel questionNumberLabel = new JLabel("Question Number:");
        questionNumberTextField = new JTextField();
        JLabel questionLabel = new JLabel("Question:");
        questionTextField = new JTextField();

        insertQuestionPanel.add(questionNumberLabel);
        insertQuestionPanel.add(questionNumberTextField);
        insertQuestionPanel.add(questionLabel);
        insertQuestionPanel.add(questionTextField);

        int option = JOptionPane.showConfirmDialog(frame, insertQuestionPanel, "Insert Question", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Retrieve the entered question number and question text
            String questionNumber = questionNumberTextField.getText();
            String questionText = questionTextField.getText();
            if (questionNumber.isEmpty() || questionText.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
        int a=stmt.executeUpdate("insert into questions values("+questionNumber+",'"+questionText+"')");  
        if(a>0)
        	JOptionPane.showMessageDialog(null, "Inserted successfully", "Error", JOptionPane.INFORMATION_MESSAGE);
        else
        	JOptionPane.showMessageDialog(null, "Enter valid Inputs", "Error", JOptionPane.ERROR_MESSAGE);
        // Close the connection
        con.close();

    } catch (ClassNotFoundException s) {
        System.err.println("Failed to load JDBC driver: " + s.getMessage());
    } catch (SQLException s) {
        System.err.println("Failed to connect to the database: " + s.getMessage());
    }

            // Display a confirmation message
            JOptionPane.showMessageDialog(frame, "Question Inserted:\nQuestion Number: " + questionNumber + "\nQuestion: " + questionText);
        }
    }

    private void createUpdateQuestionPanel() {
        insertQuestionPanel = new JPanel();
        insertQuestionPanel.setLayout(new GridLayout(2, 2));

        JLabel questionNumberLabel = new JLabel("Question Number:");
        questionNumberTextField = new JTextField();
        JLabel questionLabel = new JLabel("Question:");
        questionTextField = new JTextField();

        insertQuestionPanel.add(questionNumberLabel);
        insertQuestionPanel.add(questionNumberTextField);
        insertQuestionPanel.add(questionLabel);
        insertQuestionPanel.add(questionTextField);

        int option = JOptionPane.showConfirmDialog(frame, insertQuestionPanel, "Update Question", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Retrieve the entered question number and question text
            String questionNumber = questionNumberTextField.getText();
            String questionText = questionTextField.getText();
            if (questionNumber.isEmpty() || questionText.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
        int a = stmt.executeUpdate("UPDATE questions SET question='" + questionText + "'WHERE question_no=" + questionNumber);
        if(a>0)
        	JOptionPane.showMessageDialog(null, "Updated successfully", "Error", JOptionPane.INFORMATION_MESSAGE);
        else
        	JOptionPane.showMessageDialog(null, "Enter valid question number", "Error", JOptionPane.ERROR_MESSAGE);
        // Close the connection
        con.close();
    } catch (ClassNotFoundException s) {
        System.err.println("Failed to load JDBC driver: " + s.getMessage());
    } catch (SQLException s) {
        System.err.println("Failed to connect to the database: " + s.getMessage());
    }

            // Display a confirmation message
            JOptionPane.showMessageDialog(frame, "Question Updated:\nQuestion Number: " + questionNumber + "\nQuestion: " + questionText);
        }
    }

    private void createDeleteQuestionPanel() {
        insertQuestionPanel = new JPanel();
        insertQuestionPanel.setLayout(new GridLayout(1, 2));

        JLabel questionNumberLabel = new JLabel("Question Number:");
        questionNumberTextField = new JTextField();

        insertQuestionPanel.add(questionNumberLabel);
        insertQuestionPanel.add(questionNumberTextField);

        int option = JOptionPane.showConfirmDialog(frame, insertQuestionPanel, "Delete Question", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Retrieve the entered question number
            String questionNumber = questionNumberTextField.getText();
            if (questionNumber.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
        int a=stmt.executeUpdate("delete from questions where question_no="+questionNumber);  
        if(a>0)
        	JOptionPane.showMessageDialog(null, "Deleted successfully", "Error", JOptionPane.INFORMATION_MESSAGE);
        else
        	JOptionPane.showMessageDialog(null, "Enter valid question number", "Error", JOptionPane.ERROR_MESSAGE);
        // Close the connection
        con.close();

    } catch (ClassNotFoundException s) {
        System.err.println("Failed to load JDBC driver: " + s.getMessage());
    } catch (SQLException s) {
        System.err.println("Failed to connect to the database: " + s.getMessage());
    }

            // Display a confirmation message
            JOptionPane.showMessageDialog(frame, "Question Deleted\nQuestion Number: " + questionNumber);
        }
    }


    private void createInsertNotesPanel() {
        insertNotesPanel = new JPanel();
        insertNotesPanel.setLayout(new GridLayout(2, 2));

        JLabel chapterNumberLabel = new JLabel("Chapter Number:");
        chapterNumberTextField = new JTextField();
        JLabel chapterNameLabel = new JLabel("Chapter Name:");
        chapterNameTextField = new JTextField();

        insertNotesPanel.add(chapterNumberLabel);
        insertNotesPanel.add(chapterNumberTextField);
        insertNotesPanel.add(chapterNameLabel);
        insertNotesPanel.add(chapterNameTextField);

        int option = JOptionPane.showConfirmDialog(frame, insertNotesPanel, "Insert Notes", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Retrieve the entered chapter number and chapter name
            String chapterNumber = chapterNumberTextField.getText();
            String chapterName = chapterNameTextField.getText();
            if (chapterNumber.isEmpty() || chapterName.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
    			int a = stmt.executeUpdate("INSERT INTO notes VALUES (" + chapterNumber + ", '" + chapterName + "')");
				if(a>0)
					JOptionPane.showMessageDialog(null, "Inserted successfully", "Error", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Enter valid chapter number and chapter name", "Error", JOptionPane.ERROR_MESSAGE);
					


            } catch (ClassNotFoundException e) {
                System.err.println("Failed to load JDBC driver: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database: " + e.getMessage());
            }

            JOptionPane.showMessageDialog(frame, "Notes Inserted:\nChapter Number: " + chapterNumber + "\nChapter Name: " + chapterName);
        }
    }

    private void showStudentScores() {
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
			ResultSet rs=stmt.executeQuery("select quiz_no,marks,time from scores where student_id="+id); 
			StringBuilder sb = new StringBuilder();
			scoresTextArea = new JTextArea();
            scoresTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(scoresTextArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
			while(rs.next())
			{
				int x=rs.getInt(1);
				int y=rs.getInt(2);
				
				sb.append("Quiz-").append(x).append(": ").append(y).append(" ").append(rs.getString(3)).append("\n");
			}

            
            scoresTextArea.setText(sb.toString());

            JOptionPane.showMessageDialog(frame, scoresTextArea, "Student Scores", JOptionPane.PLAIN_MESSAGE);
        con.close();

        //System.out.println("Connection closed successfully.");
    } catch (ClassNotFoundException s) {
        System.err.println("Failed to load JDBC driver: " + s.getMessage());
    } catch (SQLException s) {
        System.err.println("Failed to connect to the database: " + s.getMessage());
    }
    }
/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Home();
            }
        });*/
}
