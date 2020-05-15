import java.math.BigInteger;
import java.util.Random;

// 
// Decompiled by Procyon v0.5.36
// 

public class cliFermat
{
    private String newline;
    private StringBuilder textArea;
    Random rnd;
    
    public cliFermat() {
        this.newline = "\n";
        this.rnd = new Random();
        this.textArea = new StringBuilder();
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
    
    public static void main(final String[] args) {
        final cliFermat f = new cliFermat();
        if( args.length == 0 ) {
            System.out.println( "Input number argument missing!" );
        } else {
            f.runTest(args[0]);;
            System.out.print( f.textArea );
        }
    }
}
