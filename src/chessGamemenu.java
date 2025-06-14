import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class chessGamemenu extends JFrame {
    private JButton playVsfriend, playVsAI, designers, Exit;

    public chessGamemenu() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        // Chessboard panel (background)
        ChessBoardPanel chessBoard = new ChessBoardPanel();
        chessBoard.setBounds(0, 0, getWidth(), getHeight() ); // Adjust the chessboard size and position
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER); // Add chessboard to the background

        // Panel for buttons (on top of the chessboard)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setPreferredSize(new Dimension(800, 200)); // Set button panel size
        buttonPanel.setBounds(0, 0, getWidth(), 200); // Position the button panel at the top
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER); // Add button panel above the chessboard

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create and add buttons to the buttonPanel
        playVsfriend = createHoverButton("Play Vs Friend");
        gbc.gridy = 0;
        buttonPanel.add(playVsfriend, gbc);

        playVsAI = createHoverButton("Play Vs AI");
        gbc.gridy = 1;
        buttonPanel.add(playVsAI, gbc);

        designers = createHoverButton("Designers");
        gbc.gridy = 2;
        buttonPanel.add(designers, gbc);

        Exit = createHoverButton("Exit");
        gbc.gridy = 3;
        buttonPanel.add(Exit, gbc);

        // Add component listener to adjust board size when the window is resized
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                chessBoard.setBounds(0, 0, getWidth(), getHeight() ); // Adjust chessboard size dynamically
                buttonPanel.setBounds(0, 0, getWidth(), 800); // Ensure button panel stays on top
                chessBoard.revalidate();
                chessBoard.repaint();
            }
        });

        setVisible(true);
    }

    private JButton createHoverButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.setFont(new Font("Arial Black", Font.ITALIC, 24));
        button.setForeground(Color.black);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 2));
        button.setPreferredSize(new Dimension(200, 50));

        // Add MouseListener for hover effect
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(button.getFont().deriveFont(28f)); // Increase font size
                button.setPreferredSize(new Dimension(220, 60)); // Increase button size
                button.revalidate(); // Repaint and revalidate for proper rendering
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(button.getFont().deriveFont(24f)); // Reset font size
                button.setPreferredSize(new Dimension(200, 50)); // Reset button size
                button.revalidate(); // Repaint and revalidate for proper rendering
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Object source = e.getSource();
                if (source == playVsfriend) {
                    System.out.println("Play Vs Friend button clicked");
                    dispose();
                   ChessGame.startgame();
                } else if (source == playVsAI) {
                    System.out.println("Play Vs AI button clicked");
                    dispose();
                    ChessGame.startgameAi();
                } else if (source == designers) {
                    System.out.println("Designers button clicked");
                    JOptionPane.showMessageDialog(chessGamemenu.this,
                            String.format("%38s\n%38s\n%38s\n%38s\n%38s\n%37s",
                                    "Yousab Ashraf Makram",
                                    "Gaber AbdElRazek Tabl",
                                    "Toaa Hosny Mahmoud",
                                    "Raneem Ashraf Metwally",
                                    "Youssef Khaled Hussein",
                                    "Mohamed Akmal AbdElhakeem"),
                            "Designers", JOptionPane.PLAIN_MESSAGE);
                } else if (source == Exit) {
                    int confirm = JOptionPane.showConfirmDialog(chessGamemenu.this, "Are you sure you want to exit?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        chessGamemenu.this.dispose();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        return button;
    }

    public static void main(String[] args) {
        // Launch the game frame
        new chessGamemenu();
    }
}

class ChessBoardPanel extends JPanel {
    private final int boardSize = 8; // Standard chessboard size (8x8)

    @Override
    public Dimension getPreferredSize() {
        // Ensure the panel resizes dynamically to fill available space
        return new Dimension(getParent().getWidth(), getParent().getHeight() ); // Subtract space for buttons
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get updated width and height of the panel
        int tileSize = getWidth() / boardSize; // Calculate tile size dynamically based on panel width
        drawBoard(g, tileSize);
    }

    // Draw the chessboard
    private void drawBoard(Graphics g, int tileSize) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boolean isWhite = (row + col) % 2 == 0;
                g.setColor(isWhite ? Color.WHITE : Color.LIGHT_GRAY.darker());
                g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

}


