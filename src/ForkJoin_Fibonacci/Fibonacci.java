package ForkJoin_Fibonacci;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class Fibonacci extends RecursiveTask<Long>  {
    private int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        final int FIBONACCI_NUM = 30;
        long start = System.currentTimeMillis();
        long res = ForkJoinPool.commonPool().invoke(new Fibonacci(FIBONACCI_NUM));
        System.out.println("Fibonacci = " + res + " time "
                + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        res = fib(FIBONACCI_NUM);
        System.out.println("Fibonacci = " + res + " time "
                + (System.currentTimeMillis() - start));

    }

    private static long fib(int n) {
        Long[] fibArr = new Long[n+1];
        if (n == 0)
            return 0L;
        if (n == 1)
            return 1L;
        fibArr[0] = 0L;
        fibArr[1] = 1l;

        for (int i = 2; i < fibArr.length; i++) {
            fibArr[i] = fibArr[i-1] + fibArr[i-2];
        }
        return fibArr[n];
    }

    @Override
    protected Long compute() {
        if (n == 0)
            return 0L;
        if (n == 1)
            return 1L;

        Fibonacci left = new Fibonacci(n - 1);
        left.fork();
        long right = new Fibonacci(n - 2).compute();

        return left.join() + right;
    }
}
