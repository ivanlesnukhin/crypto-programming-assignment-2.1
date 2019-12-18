package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import static com.company.CubeRoot.*;

public class AttackRSA {

    public static void main(String[] args) {
        String filename = "input.txt";
        BigInteger[] N = new BigInteger[3];
        BigInteger[] e = new BigInteger[3];
        BigInteger[] c = new BigInteger[3];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            for (int i = 0; i < 3; i++) {
                String line = br.readLine();
                String[] elem = line.split(",");
                N[i] = new BigInteger(elem[0].split("=")[1]);
                e[i] = new BigInteger(elem[1].split("=")[1]);
                c[i] = new BigInteger(elem[2].split("=")[1]);
            }
            br.close();
        } catch (Exception err) {
            System.err.println("Error handling file.");
            err.printStackTrace();
        }
        BigInteger m = recoverMessage(N, e, c);
        System.out.println("Recovered message: " + m);
        System.out.println("Decoded text: " + decodeMessage(m));
    }

    public static String decodeMessage(BigInteger m) {
        return new String(m.toByteArray());
    }

    /**
     * Tries to recover the message based on the three intercepted cipher texts.
     * In each array the same index refers to same receiver. I.e. receiver 0 has
     * modulus N[0], public key d[0] and received message c[0], etc.
     *
     * @param N
     *            The modulus of each receiver.
     * @param e
     *            The public key of each receiver (should all be 3).
     * @param c
     *            The cipher text received by each receiver.
     * @return The same message that was sent to each receiver.
     */
    private static BigInteger recoverMessage(BigInteger[] N, BigInteger[] e,
                                             BigInteger[] c) {
        //create secret variable s
        BigInteger s = BigInteger.ZERO;

        //create an array n, such that each n_i in n is n_i = N_a * N_b, where a!=b!=i and N_a, N_b ∈ N,
        // i.e. n_1 = N_2*N_3, n_2 = N_1*N_3 and n_3 = N_1*N_2
        BigInteger[] n = new BigInteger[3];
        //fill the empty array with ones, so that the program will not fall later on
        for (int i = 0; i<3; i++){
            n[i] = BigInteger.ONE;
        }
        //fill n with proper values
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (j != i){
                    n[i] = n[i].multiply(N[j]);
                }
            }
        }

        //create an array of inverses x, so that each x_i = (n_i)^-1 (mod N_i), where n_i ∈ n[i], N_i ∈ N[i]
        BigInteger[] x = new BigInteger[3];
        for (int i = 0; i<3; i++){
            x[i] = n[i].modInverse(N[i]);
        }

        //create an array m, where each m_i = c_i*n_i*x_i, where c_i, n_i, x_i belong to c, n and x respectively
        BigInteger[] m = new BigInteger[3];
        BigInteger a1, a2;
        for (int i = 0; i<3; i++){
            a1 = c[i].multiply(n[i]);
            a2 = a1.multiply(x[i]); //to avoid complex and ugly method chaining we split it into two variables
            m[i] = a2;
        }

        //now we find secret s; in order to do that we need to count the sum  Σ(m_i), where i={0,1,2} and m_i ∈ m[i]
        //we also need to take a third root from the final s, since public key, e, is 3.
        for (int i = 0; i<3; i++){
            s = s.add(m[i]);
        }
        s = cbrt(s);


        return s;
    }

}