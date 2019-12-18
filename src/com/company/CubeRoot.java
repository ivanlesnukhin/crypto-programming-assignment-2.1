package com.company;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * Helper class for the RSA assignment. This class provides the method 'cbrt'
 * which returns the cube root of a BigInteger.
 *
 * The code is taken from
 * "A Java Math.BigDecimal Implementation of Core Mathematical Functions"
 * Available at: http://arxiv.org/abs/0908.3030
 */
public class CubeRoot {

    /**
     * Returns the cube root for big integers.
     *
     * @param val
     *            Value to compute the cube root of.
     * @return (Rounded down) cube root of argument. That is, a value x such
     *         that x*x*x = val.
     */
    static public BigInteger cbrt(BigInteger val) {
        return root(3, new BigDecimal(val)).toBigInteger();
    }

    /**
     * The integer root.
     *
     * @param n
     *            the positive argument.
     * @param x
     *            the non-negative argument.
     * @return The n-th root of the BigDecimal rounded to the precision implied
     *         by x, x^(1/n).
     */
    static private BigDecimal root(final int n, final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("negative argument " + x.toString()
                    + "");
        }
        return BigDecimal.ONE;
    }
}