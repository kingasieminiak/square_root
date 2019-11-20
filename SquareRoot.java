import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.math.BigDecimal;

// SOURCE: https://math.mit.edu/~stevenj/18.335/newton-sqrt.pdf

// Newton's method:
// 1) equation: n = n - ((n * n - num) / (2 * n))
// 2) convergence proof: n = 0.5(n + (num/n))

public class SquareRoot {
  public static void main(String args[]) {
    SquareRoot newSquare = new SquareRoot();
    newSquare.getSquareRootBigDecimal(2, 12);
    newSquare.getSquareRootDouble(2, 12);
  }

  void getSquareRootBigDecimal(int num, int k) {
    validateInput(num, k);
    k++;

    MathContext mc = new MathContext(k*2, RoundingMode.FLOOR);
    BigDecimal precision = new BigDecimal(BigInteger.ONE, k);

    BigDecimal x = BigDecimal.valueOf(num);
    BigDecimal prev = BigDecimal.ZERO;
    BigDecimal n = BigDecimal.ONE;

    while (abs(n.subtract(prev)).compareTo(precision) == 1) {
      prev = n;
      n = x.divide(n, mc).add(n).divide(BigDecimal.valueOf(2), mc);
    }

    k--;
    System.out.println(n.setScale(k, RoundingMode.FLOOR).toPlainString());
  }

  // partial solution, will not work for high precision
  void getSquareRootDouble(int num, int k) {
    validateInput(num, k);

    double n = 1;
    double prev = 0;
    double power = getPower10(k);

    double precision = k == 0 ? 0 : 1 / power;

    while (true) {
      prev = n;
      n = (n + num/n) / 2;

      double abs = ((prev - n) <= 0.0F) ? 0.0F - (prev - n) : (prev - n);

      if (abs <= precision) {
        break;
      }
    }

    double result = (long)(n * power) / power;

    if(k == 0) {
      System.out.println((long)result);
    } else {
      System.out.println(result);
    }
  }

  private void validateInput(int num, int k) {
    if ((num > 10000000 || num < 0) || (k > 10000 || k < 0)) {
      throw new Error(" invalid parameters");
    }
  }

  private double getPower10(int k) {
    double precision = 1;

    for(int i = 0; i < k; i++) {
      precision = precision * 10;
    }

    return precision;
  }

  private BigDecimal abs(BigDecimal num) {
    if(num.compareTo(BigDecimal.ZERO) == 1) {
      return num;
    } else {
      return num.negate();
    }
  }
}
