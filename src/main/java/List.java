import java.util.LinkedList;

public class List{
    private LinkedList<Pair> list;

    public List(LinkedList<Pair> list) {
        this.list = list;
    }

    public List() {
        this.list = new LinkedList<>();
    }

    public void mark(int x) {
        if (x >= this.list.size() || x < 0) {
            System.out.println(Event.line);
            System.out.println("That item does not exist");
            System.out.println(Event.line);
            return;
        }
        Pair item = this.list.get(x - 1);
        if (item.isDone()) {
            System.out.println(Event.line);
            System.out.println("You have done it JERRYYYYYY!!!!");
            System.out.println(Event.line);
            return;
        }
        item.mark(true);
        System.out.println(Event.line);
        System.out.println("Nice! I've marked this task as done:\n");
        System.out.println(" [X] " + item.getItem());
        System.out.println(Event.line);
    }

    public void unmark(int x) {
        if (x >= this.list.size() || x < 0) {
            System.out.println("That item does not exist");
            return;
        }
        Pair item = this.list.get(x - 1);
        if (!item.isDone()) {
            System.out.println(Event.line);
            System.out.println("You have NOT done it JERRYYYYYY!!!!");
            System.out.println(Event.line);
            return;
        }
        item.mark(false);
        System.out.println(Event.line);
        System.out.println("OK, I've marked this task as not done:\n");
        System.out.println(" [ ] " + item.getItem());
        System.out.println(Event.line);
    }

    public void add(Pair item){
        System.out.println("added: " + item.getItem());
        this.list.add(item);
    }

    public void display() {
        System.out.println(Event.line);
        for (int i = 0; i < list.size(); i++) {
            Pair temp = list.get(i);
            System.out.print((i + 1) + ".");
            if (temp.isDone()) {
                System.out.print("[X] ");
            } else {
                System.out.print("[ ] ");
            }
            System.out.println(temp.getItem());
        }
        System.out.println(Event.line);
    }

    public LinkedList<Pair> getList() {
        return this.list;
    }
}
