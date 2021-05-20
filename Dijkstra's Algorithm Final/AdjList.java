//written by Damien King
//implements an adjacency list that stores linked lists of weights and indexes
public class AdjList {

   public weightList[] wl;

   //constructor for the adjacency list
    public AdjList(int length){
        wl = new weightList[length];
    }

    //takes in two indexes and returns the weight value stored at the given point
    public double retrieve(int b, int e){
        LNode c = wl[b].head;
       // boolean found = false;
        double weight = 1000000;

        while(c!= null){
            if(c.index == e){
               // found = true;
                weight = c.weight;
                break;
            }
            c = c.next;
        }
        return weight;
    }

    //loops through the array and creates a new linked list for each entry
    public void initialize(){
        for(int i = 0; i < wl.length; i++){
            wl[i] = new weightList();
        }
    }

    //adds a edge as a node in a given linked list
    public void edgeAdd(int b, int e, double w){
        wl[b].add(w,e);
    }


    //creates a linked list of weight nodes
    class weightList {
        LNode head;
        public void add(double w, int i) {
            if (head == null)
                head = new LNode(w, i);
            else {
                LNode n = new LNode(w, i);
                n.next = head;
                head = n;
            }
        }
    }

    //creates a node that holds an index and weight
    class LNode{
        double weight;
         int index;
         LNode next;
         public LNode(double w, int i){
             weight = w;
             index = i;
         }
    }
}
