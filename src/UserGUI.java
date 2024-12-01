import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserGUI extends Thread {
    private static Client client;


    JTextField username;
    JTextField password;
    JTextField bio;


    JButton login;
    JButton create;

    UserGUI gui;


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == login) {
                String usernameTxt = username.getText();
                String passwordTxt = password.getText();

                ArrayList<String> out = new ArrayList<String>();
                out.add(usernameTxt);
                out.add(passwordTxt);

                DataTransfer params = new DataTransfer("USER GETLOGIN", out);

                DataTransfer response = client.request(params);

                System.out.println(response.getMessage());
                System.out.println(response.getValue());
            }

            if (e.getSource() == create) {
                String usernameTxt = username.getText();
                String passwordTxt = password.getText();
                String bioTxt = bio.getText();
                ArrayList<String> out = new ArrayList<String>();
                out.add(usernameTxt);
                out.add(passwordTxt);
                out.add(bioTxt);

                DataTransfer params = new DataTransfer("USER CREATENEWUSER", out);
                DataTransfer response = client.request(params);
                System.out.println(response.getMessage());
                System.out.println(response.getValue());
            }
        }
    };

    public static void main(String[] args) {
        client = new Client("localhost", 4242);
        client.accept();

        SwingUtilities.invokeLater(new UserGUI());
    }

    public void run() {
        JFrame frame = new JFrame("Social Media Application");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel originalPanel = new JPanel();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setSize(300, 200);

        panel.add(new JLabel("Username:"));
        username = new JTextField("", 10);
        panel.add(username, BorderLayout.NORTH);

        panel.add(new JLabel("Password:"));
        password = new JTextField("", 10);
        panel.add(password, BorderLayout.SOUTH);

        panel.add(new JLabel("Bio:"));
        bio = new JTextField("", 10);
        panel.add(bio, BorderLayout.SOUTH);

        login = new JButton("Login");
        panel.add(login);
        login.addActionListener(actionListener);

        create = new JButton("Create User");
        panel.add(create);
        create.addActionListener(actionListener);

        originalPanel.add(panel);

        frame.add(originalPanel, BorderLayout.CENTER);





    }
}
