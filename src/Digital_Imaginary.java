import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Digital_Imaginary extends JFrame {
    private JTextField signUpPlayerIdField;
    private JTextField signUpPlayerNameField;
    private JTextField signUpPositionField;
    private JPasswordField signUpPasswordField;
    private JTextField loginPlayerIdField;
    private JPasswordField loginPasswordField;

    // Database to store player information (In-memory storage for simplicity)
    //private Map<String, Player> playerDatabase;

    public Digital_Imaginary() {
        // Initialize the database
        //playerDatabase = new HashMap<>();
    	
        setTitle("Sign Up/Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create panels for sign up and login components
        JPanel signUpPanel = createSignUpPanel();
        JPanel loginPanel = createLoginPanel();

        // Create tabbed pane to switch between sign up and login
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sign Up", signUpPanel);
        tabbedPane.addTab("Login", loginPanel);

        // Add tabbed pane to the frame
        getContentPane().add(tabbedPane);
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Sign Up components
        JLabel signUpPlayerIdLabel = new JLabel("Student ID:");
        signUpPlayerIdField = new JTextField(20);
        JLabel signUpPlayerNameLabel = new JLabel("Student Name:");
        signUpPlayerNameField = new JTextField(20);
        JLabel signUpPositionLabel = new JLabel("Class:");
        signUpPositionField = new JTextField(20);
        JLabel signUpPasswordLabel = new JLabel("Password:");
        signUpPasswordField = new JPasswordField(20);

        JButton signUpButton = new JButton("Sign Up");

        // Sign Up button action listener
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerId = signUpPlayerIdField.getText();
                String playerName = signUpPlayerNameField.getText();
                String position = signUpPositionField.getText();
                String password = new String(signUpPasswordField.getPassword());

                if (playerId.isEmpty() || playerName.isEmpty() || position.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else
                {
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
                				int a=stmt.executeUpdate("insert into students values("+playerId+",'"+playerName+"','"+position+"','"+password+"')");  
                				if(a>0)
                					JOptionPane.showMessageDialog(null, "Sign Up Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                				else
                					JOptionPane.showMessageDialog(null, "Enter Valid Details", "Error", JOptionPane.ERROR_MESSAGE);
                	            // Close the connection
                	            con.close();
                	        } catch (ClassNotFoundException c) {
                	            System.err.println("Failed to load JDBC driver: " + c.getMessage());
                	        } catch (SQLException c) {
                	            System.err.println("Failed to connect to the database: " + c.getMessage());
                	        }
                	    }
         
         
                //JOptionPane.showMessageDialog(null, "Sign In Successful", "Success", JOptionPane.INFORMATION_MESSAGE);


                // Clear the sign up fields
                signUpPlayerIdField.setText("");
                signUpPlayerNameField.setText("");
                signUpPositionField.setText("");
                //signUpContactInfoField.setText("");
                signUpPasswordField.setText("");

                //JOptionPane.showMessageDialog(null, "Sign up successful!");
            }
        });

        // Add components to the sign up panel
        panel.add(signUpPlayerIdLabel);
        panel.add(signUpPlayerIdField);
        panel.add(signUpPlayerNameLabel);
        panel.add(signUpPlayerNameField);
        panel.add(signUpPositionLabel);
        panel.add(signUpPositionField);
        panel.add(signUpPasswordLabel);
        panel.add(signUpPasswordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(signUpButton);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Login components
        JLabel loginPlayerIdLabel = new JLabel("Student ID:");
        loginPlayerIdField = new JTextField(20);
        JLabel loginPasswordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerId = loginPlayerIdField.getText();
                String password = new String(loginPasswordField.getPassword());

                // Retrieve player object from the database
                if (playerId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter ID and password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
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
        			ResultSet rs=stmt.executeQuery("select password from students where student_id="+playerId+"and password='"+password+"'");  
        			if(rs.next())
        			{
        					
        				JOptionPane.showMessageDialog(null, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        				setVisible(false);
        				new Home(Integer.parseInt(playerId));
        			}
        			else
        				JOptionPane.showMessageDialog(null, "Enter Valid Details", "Error", JOptionPane.ERROR_MESSAGE);
                    // Close the connection
                    con.close();

                } catch (ClassNotFoundException c) {
                    System.err.println("Failed to load JDBC driver: " + c.getMessage());
                } catch (SQLException c) {
                    System.err.println("Failed to connect to the database: " + c.getMessage());
                }
            }

                // Clear the login fields
                loginPlayerIdField.setText("");
                loginPasswordField.setText("");
            }
        });

        // Add components to the login panel
        panel.add(loginPlayerIdLabel);
        panel.add(loginPlayerIdField);
        panel.add(loginPasswordLabel);
        panel.add(loginPasswordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Digital_Imaginary().setVisible(true);
            }
        });
    }
}
/*
class Player {
    private String playerId;
    private String playerName;
    private String position;
    private String contactInfo;
    private String password;

    public Player(String playerId, String playerName, String position, String contactInfo, String password) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.position = position;
        this.contactInfo = contactInfo;
        this.password = password;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPosition() {
        return position;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getPassword() {
        return password;
    }
*/