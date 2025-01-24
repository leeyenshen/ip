import java.util.LinkedList;

public class List{
    private LinkedList<String> list;

    public List(LinkedList<String> list) {
        this.list = list;
    }

    public List() {
        this.list = new LinkedList<>();
    }

    public void add(String item){
        System.out.println("added: " + item);
        this.list.add(item);
    }

    public void display() {
        System.out.println(Event.line);
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        System.out.println(Event.line);
    }

    public LinkedList<String> getList() {
        return this.list;
    }
}
