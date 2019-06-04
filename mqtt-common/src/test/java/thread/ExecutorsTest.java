package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i=0;i< 10;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0;i< 5;i++){
                        System.out.println(Thread.currentThread().getName() + " -> " + i);
                    }
                }
            });
        }

        executorService.shutdown();

    }

}
