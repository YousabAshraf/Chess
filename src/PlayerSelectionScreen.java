import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PlayerSelectionScreen {

    private static Player player1;
    private static Player player2;
    private static String currentPlayer;
    private static ArrayList<String> moveHistory;

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static void showPlayerSelectionScreen() {

        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel player1Label = new JLabel("Player 1 Name:");
        JTextField player1NameField = new JTextField(15);
        JLabel player1ColorLabel = new JLabel("Choose color:");
        JComboBox<String> player1ColorBox = new JComboBox<>(new String[]{"White", "Black"});

        JLabel player2Label = new JLabel("Player 2 Name:");
        JTextField player2NameField = new JTextField(15);
        JLabel player2ColorLabel = new JLabel("Choose color:");
        JComboBox<String> player2ColorBox = new JComboBox<>(new String[]{"Black", "White"});

        Font font = new Font("Arial", Font.PLAIN, 18);
        player1Label.setFont(font);
        player2Label.setFont(font);
        player1ColorLabel.setFont(font);
        player2ColorLabel.setFont(font);
        player1NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        player2NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        player1ColorBox.setFont(new Font("Arial", Font.PLAIN, 16));
        player2ColorBox.setFont(new Font("Arial", Font.PLAIN, 16));


        player1ColorBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1ColorBox.getSelectedItem().equals(player2ColorBox.getSelectedItem())) {
                    player2ColorBox.setSelectedItem(player1ColorBox.getSelectedItem().equals("White") ? "Black" : "White");
                }
            }
        });

        player2ColorBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player2ColorBox.getSelectedItem().equals(player1ColorBox.getSelectedItem())) {
                    player1ColorBox.setSelectedItem(player2ColorBox.getSelectedItem().equals("White") ? "Black" : "White");
                }
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 0;
        playerSelectionPanel.add(player1Label, gbc);

        gbc.gridx = 1;
        playerSelectionPanel.add(player1NameField, gbc);

        gbc.gridx = 2;
        playerSelectionPanel.add(player1ColorLabel, gbc);

        gbc.gridx = 3;
        playerSelectionPanel.add(player1ColorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        playerSelectionPanel.add(player2Label, gbc);

        gbc.gridx = 1;
        playerSelectionPanel.add(player2NameField, gbc);

        gbc.gridx = 2;
        playerSelectionPanel.add(player2ColorLabel, gbc);

        gbc.gridx = 3;
        playerSelectionPanel.add(player2ColorBox, gbc);

        int option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
        while (option == JOptionPane.DEFAULT_OPTION)
        {
            JOptionPane.showMessageDialog(null, "You can not exit!", "Error", JOptionPane.ERROR_MESSAGE);
            option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
        }
        while (option == JOptionPane.OK_OPTION) {
            String player1Name = player1NameField.getText();
            String player2Name = player2NameField.getText();
            String player1Color = player1ColorBox.getSelectedItem().toString();
            String player2Color = player2ColorBox.getSelectedItem().toString();

            if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                if (player1Color.equals(player2Color)) {
                    JOptionPane.showMessageDialog(null, "Players cannot choose the same color. Please choose different colors.", "Error", JOptionPane.PLAIN_MESSAGE);
                } else {
                    player1 = new Player(player1Name, player1Color);
                    player2 = new Player(player2Name, player2Color);

                    currentPlayer = "white";
                    moveHistory = new ArrayList<>();

                    JOptionPane.getRootFrame().dispose();
                    break;
                }
            } else {

                JOptionPane.showMessageDialog(null, "Please enter both player names.", "Error", JOptionPane.ERROR_MESSAGE);
                option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
                while (option == JOptionPane.DEFAULT_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "You can not exit!", "Error", JOptionPane.ERROR_MESSAGE);
                    option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    public static void showPlayerSelectionScreenAi() {

        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel player1Label = new JLabel("Player 1 Name:");
        JTextField player1NameField = new JTextField(15);
        JLabel player1ColorLabel = new JLabel("Choose color:");
        JComboBox<String> player1ColorBox = new JComboBox<>(new String[]{"White", "Black"});


        Font font = new Font("Arial", Font.PLAIN, 18);
        player1Label.setFont(font);
        player1ColorLabel.setFont(font);
        player1NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        player1ColorBox.setFont(new Font("Arial", Font.PLAIN, 16));


        gbc.gridx = 0;
        gbc.gridy = 0;
        playerSelectionPanel.add(player1Label, gbc);

        gbc.gridx = 1;
        playerSelectionPanel.add(player1NameField, gbc);

        gbc.gridx = 2;
        playerSelectionPanel.add(player1ColorLabel, gbc);

        gbc.gridx = 3;
        playerSelectionPanel.add(player1ColorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        int option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
        while (option == JOptionPane.DEFAULT_OPTION)
        {
            JOptionPane.showMessageDialog(null, "You can not exit!", "Error", JOptionPane.ERROR_MESSAGE);
            option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
        }
        while (option == JOptionPane.OK_OPTION) {
            String player1Name = player1NameField.getText();
            String player1Color = player1ColorBox.getSelectedItem().toString();

            if (!player1Name.isEmpty() ) {

                    player1 = new Player(player1Name, player1Color);
                    player2 = new Player("AI",(player1Color.equals("White") ? "Black" : "White") );

                    currentPlayer = "white";
                    moveHistory = new ArrayList<>();

                    JOptionPane.getRootFrame().dispose();
                    break;

            } else {

                JOptionPane.showMessageDialog(null, "Please enter both player names.", "Error", JOptionPane.ERROR_MESSAGE);
                option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
                while (option == JOptionPane.DEFAULT_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "You can not exit!", "Error", JOptionPane.ERROR_MESSAGE);
                    option = JOptionPane.showConfirmDialog(null, playerSelectionPanel, "Enter Player Names and Colors", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

}
