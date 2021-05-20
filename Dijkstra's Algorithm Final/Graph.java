//written by Damien King Implements Dijkstra's algorithm using an adjacency list
//Implements code from the Data Structures and Algorithms textbook by Robert Lafore

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Graph {
    private final int INFINITY = 1000000;
    private Vertex[] vertexList; // list of vertices
    private int nVerts; // current number of vertices
    private int nTree; // number of verts in tree
    private DistPar[] sPath; // array for shortest-path data
    private int currentVert; // current vertex
    private double startToCurrent; // distance to currentVert
    int startTree;

    private VertexLinkedList vl = new VertexLinkedList();

    private int vCount;
    private int eCount;

    private AdjList list;

    // -------------------------------------------------------------

    //constructor for the graph uses is called from the file reader
    public Graph(int vertC) // constructor
    {
        vertexList = new Vertex[vertC];
        // adjacency matrix
        //adjMat = new double[6000][6000];
        list = new AdjList(vertC);
        nVerts = 0;
        nTree = 0;

        list.initialize();
        sPath = new DistPar[vertC]; // shortest paths
    }

    //adds a vertex into the array of vertexes
    public void addVertex(String lab, double lat, double lo)
    {
        vertexList[nVerts++] = new Vertex(lab, lat, lo);
    }
    // -------------------------------------------------------------

    //adds an edge into the adjacency list
    public void addEdge(int start, int end)
    {
        ConvertGPSCoordinantes gp = new ConvertGPSCoordinantes();
        Vertex s = vertexList[start];
        Vertex e = vertexList[end];
        double weight = gp.calcDist(s.latitude,s.longitude,e.latitude,e.longitude);
        list.edgeAdd(start,end,weight);
        list.edgeAdd(end,start,weight);
       // adjMat[start][end] = weight; // (directed)
       // adjMat[end][start] = weight;
    }
    // -------------------------------------------------------------


    //calculates the shortest possible path between all points form a given starting point
    public void path(int start) // find all shortest paths
    {
       // System.out.println(start);
        startTree = start; // start at vertex 0
        vertexList[startTree].isInTree = true;
        nTree = 0; // put it in tree
        // transfer row of distances from adjMat to sPath
        for(int j=0; j<nVerts; j++)
        {
            //double tempDist = adjMat[startTree][j];
            double tempDist = list.retrieve(startTree,j);
            sPath[j] = new DistPar(startTree, tempDist);
        }
        // until all vertices are in the tree
        //System.out.println(nVerts);
        while(nTree < nVerts) {
            //System.out.println(nTree);
            if (nTree != start) {
                int indexMin = getMin(); // get minimum from sPath
                double minDist = sPath[indexMin].distance;
                if (minDist == INFINITY) // if all infinite
                { // or in tree,
                    System.out.println("There are unreachable vertices");
                    break; // sPath is complete
                    //nTree++;
                }
                else { // reset currentVert
                    currentVert = indexMin; // to closest vert
                    startToCurrent = sPath[indexMin].distance;
            // minimum distance from startTree is
            // to currentVert, and is startToCurrent
                }
            // put current vertex in tree
                vertexList[currentVert].isInTree = true;
            }
                nTree++;
                adjust_sPath(); // update sPath[] array
            }
           // end while(nTree<nVerts)
            //displayPaths(); // display sPath[] contents
            nTree = 0; // clear tree
            for (int j = 0; j < nVerts; j++)
                vertexList[j].isInTree = false;
    }

    //gets the next destination with the smallest weight
    public int getMin() // get entry from sPath
    {
        // with minimum distance
        double minDist = INFINITY; // assume minimum
        int indexMin = 0;
        for(int j=1; j<nVerts; j++) // for each vertex,
        {
            if( !vertexList[j].isInTree && // smaller than old one
                    sPath[j].distance < minDist )
            {
                minDist = sPath[j].distance;
                indexMin = j; // update minimum
            }
        } // end for
        return indexMin; // return index of minimum
    }

    // adjust values in shortest-path array sPath
    public void adjust_sPath() {
        int column = 0; // skip starting vertex
        while(column < nVerts) // go across columns
        {
                // if this column’s vertex already in tree, skip it
                if (vertexList[column].isInTree) {
                    column++;
                    continue;
                }
            // calculate distance for one sPath entry
            // get edge from currentVert to column
            //double currentToFringe = adjMat[currentVert][column];
            double currentToFringe = list.retrieve(currentVert,column);
            // add distance from start
            double startToFringe = startToCurrent + currentToFringe;
            // get distance of current sPath entry
            double sPathDist = sPath[column].distance;
            // compare distance from start with sPath entry
            if (startToFringe < sPathDist) // if shorter,
            { // update sPath
                sPath[column].parentVert = currentVert;
                sPath[column].distance = startToFringe;
            }

            column++;
        } // end while(column < nVerts)
    }

    //prints out all connecting paths, was used primarily for testing purposes
    public void displayPaths(){
        for(int j=0; j<nVerts; j++) // display contents of sPath[]
        {
            System.out.print(vertexList[j].label + "="); // B=
            if(sPath[j].distance == INFINITY)
                System.out.print("inf"); // inf
            else
                System.out.print(sPath[j].distance); // 50
            String parent = vertexList[sPath[j].parentVert ].label;
            System.out.print("(" + parent + ") "); // (A)
            System.out.println();
        }
        System.out.println();
    }

    //this method takes a given end city and backtracks through the path to find the shortest path from the end point to the beginning
    //also creates a linked list of vertexes to be used in the file writer
    public void findRoute(String b, String e){
        int i = 0;
        boolean complete = false;

        if(b.equals(e)) {
            System.out.println("Cannot create route where beginning and end are equal");
            return;
        }

        while(vertexList[i] != null){
            if(vertexList[i].label.equals(e)){
                complete = true;
                break;
            }

            else
                i++;
        }

        if(!complete){
            System.out.println("Input End Vertex Not Found!");
            return;
        }


        complete = false;
        String current = vertexList[i].label;
        int place = i;
        int vertCount = 0;
        int edgeCount = 0;
        while(!complete){
            //System.out.println("hello");
            //if(next == vertexList[sPath[place].parentVert].label){
            if(current.equals(b)){
                vl.addToBeginning(vertexList[place]);
                //vl.changeHeadIndex(place);
                vertCount++;
                complete = true;
            }
            else if(current.equals(vertexList[sPath[place].parentVert].label)){
                System.out.println("Specified end vertex was not able to be found");
               // return;
                break;
            }
            else {
                vl.addToBeginning(vertexList[place]);
                //vl.changeHeadIndex(place);
                vertCount++;
                edgeCount++;
                place = sPath[place].parentVert;
                current = vertexList[place].label;
            }

        }

        vl.printList();
        System.out.println("Vert Count = " + vertCount);
        System.out.println("Edge Count = " + edgeCount);

        vCount = vertCount;
        eCount = edgeCount;
        vl.reIndex();
    }


    //after finding the path, loops through the linked list of vertexes and writes them to a text file
    public void graphFileWriter(String fn) {
        try {
            File f = new File(fn);
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else {
                System.out.println("File already exists.");
            }

            FileWriter fw = new FileWriter(fn);

            fw.write(vCount + " " + eCount+ "\n");

            Node c = vl.head;
            while(c !=null){
                fw.write(c.data.label+ " " + c.data.latitude + ", " + c.data.longitude+ "\n");
                c = c.next;
            }

            if(vl.head == null || vl.head.next == null){
                System.out.println("An invalid path was passed to print");
                return;
            }
            c = vl.head.next;
            Node prev = vl.head;
            int counter = 0;
            while(c !=null){
                counter++;
                //will need to change counter to the route names
                fw.write(prev.index+ " " + c.index + " I-" + counter + "\n");
                prev = c;
                c = c.next;
            }
            fw.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    //takes in a string name of a city and returns its index in the verts array
    int getIndex(String s){
        int i;
        for(i = 0; i < vertexList.length; i++){
            if(vertexList[i].label.equals(s))
                break;
        }
        return i;
    }

}