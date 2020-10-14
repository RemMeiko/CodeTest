package Mysql;

public class TeamWorker {
    private int id;
    private char name;
    private int sex;
    private double degree;


    public int getId() {
        return id;
    }

    public char getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public double getDegree() {
        return degree;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(char name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}
