//Written By Damien King
//creates a linked list of vertexes that stores vertex names and indexes

public class VertexLinkedList {
    Node head;

    public void addToEnd(Vertex d){
        if(head == null)
            head = new Node(d);
        else{
            Node c = head;
            while(c.next != null){
                c = c.next;
            }
            c.next = new Node(d);
        }
    }

    public void addToBeginning(Vertex d){
        if(head == null)
            head = new Node(d);
        else{
            Node t = head;
            head = new Node(d);
            head.next = t;
        }
    }

    public void changeHeadIndex(int i){
        head.index = i;
    }

    public void reIndex(){
        Node c = head;
        int counter = 0;
        while(c != null){
            c.index = counter;
            c = c.next;
            counter++;
        }
    }

    public void printList(){
        Node c = head;
        while(c != null){
            if(c.next != null)
                System.out.print(c.data.label + " -> ");
            else
                System.out.print(c.data.label);
            c = c.next;
        }
        System.out.println();
    }



}
