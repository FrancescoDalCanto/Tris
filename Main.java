import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private char[][] matrix;
    private char currentPlayer;
    private JLabel statusLabel;

    public Main() {
        setTitle("Tris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        matrix = new char[3][3];
        currentPlayer = 'X';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(this);
                gridPanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Turno del giocatore X");
        add(gridPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = -1, col = -1;

        // Trova la posizione del pulsante cliccato
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Se la casella è vuota, assegna il simbolo del giocatore corrente
        if (matrix[row][col] == '\u0000') {
            matrix[row][col] = currentPlayer;
            button.setText(String.valueOf(currentPlayer));
            if (controllaVittoria()) {
                JOptionPane.showMessageDialog(this, "Ha vinto il giocatore " + currentPlayer);
                disabilitaPulsanti();
            } else if (controllaPareggio()) {
                statusLabel.setText("Pareggio!");
                disabilitaPulsanti();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Turno del giocatore " + currentPlayer);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mossa non valida. Riprova.");
        }
    }

    private boolean controllaVittoria() {
        // Controlla le righe e le colonne
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0] == currentPlayer && matrix[i][1] == currentPlayer && matrix[i][2] == currentPlayer) {
                return true; // Vittoria sulla riga i
            }
            if (matrix[0][i] == currentPlayer && matrix[1][i] == currentPlayer && matrix[2][i] == currentPlayer) {
                return true; // Vittoria sulla colonna i
            }
        }

        // Controlla le diagonali
        if ((matrix[0][0] == currentPlayer && matrix[1][1] == currentPlayer && matrix[2][2] == currentPlayer) ||
                (matrix[0][2] == currentPlayer && matrix[1][1] == currentPlayer && matrix[2][0] == currentPlayer)) {
            return true; // Vittoria sulla diagonale
        }

        return false;
    }

    private boolean controllaPareggio() {
        // Controlla se tutte le caselle sono piene
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == '\u0000') {
                    return false; // Ci sono caselle vuote, il gioco continua
                }
            }
        }
        return true; // Tutte le caselle sono piene, pareggio
    }

    private void disabilitaPulsanti() {
        // Disabilita tutti i pulsanti dopo la fine del gioco
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
