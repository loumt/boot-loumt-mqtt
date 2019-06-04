
import java.util.ArrayList;
import java.util.List;

class Student{
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}



public class StudyTest {

    private static final Object[] EMPTY_ELEMENTDATA = {};

    public static void main(String[] args) {

        Student student = new Student();
        System.out.println(student.getAge());

        Object[] EMPTY = EMPTY_ELEMENTDATA;

        System.out.println(Math.max(1,2));

        System.out.println(EMPTY == EMPTY_ELEMENTDATA);

        System.out.println(7 >> 1);


        List<String> arr = new ArrayList<String>();


    }
}
