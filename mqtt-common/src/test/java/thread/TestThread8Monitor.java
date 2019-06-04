package thread;

public class TestThread8Monitor {


    public static void main(String[] args) {
        final Number number1 = new Number();
        final Number number2 = new Number();

        new Thread(new Runnable(){
            public void run(){
                number1.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number2.getTwo();
            }
        }).start();
    }
}


class Number{
    public synchronized void getOne(){
        try{
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("One...........");
    }

    public  synchronized void getTwo(){
        System.out.println("Two.....");
    }

    public void getThree(){
        System.out.println("Three...........");
    }
}