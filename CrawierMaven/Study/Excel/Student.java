package Study.Excel;

public class Student {
    private int age;
    private String name;
    private String address;

    public Student(int a,String b,String c) {
        this.address = c;
        this.age = a;
        this.name = b;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
