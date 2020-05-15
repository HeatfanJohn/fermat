import javax.swing.SwingUtilities;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

public class Fermat extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String newline;
    protected static final String text = "n";
    protected static final String buttonString = "test";
    JTextField input;
    JTextArea textArea;
    protected JLabel actionLabel;
    Random rnd;
    
    public Fermat() {
        this.newline = "\n";
        this.rnd = new Random();
        this.setLayout(new BorderLayout());
        (this.input = new JTextField()).setActionCommand("n");
        this.input.addActionListener(this);
        final JLabel textFieldLabel = new JLabel("n: ");
        textFieldLabel.setLabelFor(this.input);
        (this.actionLabel = new JLabel("Input a number greater than 1 and then press the test button.")).setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        final JButton button = new JButton();
        button.setText("test");
        button.setActionCommand("test");
        button.addActionListener(this);
        final JPanel textControlsPane = new JPanel();
        final GridBagLayout gridbag = new GridBagLayout();
        final GridBagConstraints c = new GridBagConstraints();
        textControlsPane.setLayout(gridbag);
        final JLabel[] labels = { textFieldLabel };
        final JTextField[] textFields = { this.input };
        this.addLabelTextRows(labels, textFields, gridbag, textControlsPane);
        c.gridwidth = 0;
        c.anchor = 17;
        c.weightx = 1.0;
        textControlsPane.add(this.actionLabel, c);
        textControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        c.anchor = 13;
        c.weightx = 1.0;
        textControlsPane.add(button, c);
        (this.textArea = new JTextArea()).setFont(new Font("Serif", 0, 16));
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        final JScrollPane areaScrollPane = new JScrollPane(this.textArea);
        areaScrollPane.setVerticalScrollBarPolicy(22);
        areaScrollPane.setPreferredSize(new Dimension(500, 500));
        areaScrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Output"), BorderFactory.createEmptyBorder(5, 5, 5, 5)), areaScrollPane.getBorder()));
        this.add(textControlsPane, "First");
        this.add(areaScrollPane, "Center");
    }
    
    private void addLabelTextRows(final JLabel[] labels, final JTextField[] textFields, final GridBagLayout gridbag, final Container container) {
        final GridBagConstraints c = new GridBagConstraints();
        c.anchor = 13;
        for (int numLabels = labels.length, i = 0; i < numLabels; ++i) {
            c.gridwidth = -1;
            c.fill = 0;
            c.weightx = 0.0;
            container.add(labels[i], c);
            c.gridwidth = 0;
            c.fill = 2;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }
    
    private void createAndShowGUI() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        this.setOpaque(true);
        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void actionPerformed(final ActionEvent e) {
        if ("test".equals(e.getActionCommand())) {
            this.getInput();
        }
    }
    
    private void getInput() {
        final String n = this.input.getText();
        this.textArea.append("n = " + n + this.newline);
        this.runTest(n);
    }
    
    private void runTest(final String input) {
        BigInteger n;
        try {
            n = new BigInteger(input);
        }
        catch (NumberFormatException e) {
            this.output(String.valueOf(input) + " is not a valid integer!");
            this.output("");
            return;
        }
        if (n.compareTo(BigInteger.ONE) <= 0) {
            this.output("n must greater than 1!");
            this.output("");
            return;
        }
        final BigInteger nMOne = n.subtract(BigInteger.ONE);
        if (nMOne.compareTo(BigInteger.TEN) <= 0) {
            for (BigInteger a = nMOne; a.compareTo(BigInteger.ZERO) > 0; a = a.subtract(BigInteger.ONE)) {
                final boolean pass = this.fermatTest(n, a, nMOne);
                if (!pass) {
                    this.output("n is not a prime number");
                    this.output("");
                    return;
                }
            }
            this.output("n is a prime number");
            this.output("");
            return;
        }
        final BigInteger[] as = this.generateTestNumbers(n);
        for (int i = 0; i < 10; ++i) {
            final boolean pass = this.fermatTest(n, as[i], nMOne);
            if (!pass) {
                this.output("n is not a prime number");
                this.output("");
                return;
            }
        }
        this.output("n is probably a prime number");
        this.output("");
    }
    
    private BigInteger[] generateTestNumbers(final BigInteger n) {
        final BigInteger[] as = new BigInteger[10];
        for (int i = 0; i < 10; ++i) {
            boolean done = false;
            do {
                BigInteger a = new BigInteger(n.bitLength(), this.rnd);
                a = a.mod(n);
                if (this.inRange(a) && !this.hasBeenChosen(a, as)) {
                    as[i] = a;
                    done = true;
                }
            } while (!done);
        }
        return as;
    }
    
    private boolean inRange(final BigInteger a) {
        return !a.equals(BigInteger.ZERO) && !a.equals(BigInteger.ONE);
    }
    
    private boolean hasBeenChosen(final BigInteger a, final BigInteger[] as) {
        for (final BigInteger e : as) {
            if (a.equals(e)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean fermatTest(final BigInteger n, final BigInteger a, final BigInteger nMOne) {
        this.output("a = " + a.toString());
        final BigInteger result = a.modPow(nMOne, n);
        this.output("a^(n-1) = " + result.toString() + " mod n");
        return result.equals(BigInteger.ONE);
    }
    
    private void output(final String s) {
        this.textArea.append(String.valueOf(s) + this.newline);
    }
    
    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Fermat.this.createAndShowGUI();
            }
        });
    }
    
    public static void main(final String[] args) {
        final Fermat f = new Fermat();
        f.start();
    }
}
