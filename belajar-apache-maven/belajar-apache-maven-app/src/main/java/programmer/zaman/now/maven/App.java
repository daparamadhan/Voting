package programmer.zaman.now.maven;

public class App {

    public boolean isActive() {
        return true;
    }

    public int sum(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println("isActive: " + app.isActive());
        System.out.println("sum(2, 3): " + app.sum(2, 3));
    }
}
