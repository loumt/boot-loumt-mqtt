package thread;

public class ThreadTest {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1111111111111");
            }
        });

        t.start();
    }
}
