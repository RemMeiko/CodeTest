package Study.Other;

public class ForName {
    public static void main(String[] args) {
        try {
            Class.forName(args[0]);
            System.out.println(Class.forName(args[0]));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
