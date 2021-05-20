//Written By Damien King
////Implements code from the Data Structures and Algorithms textbook by Robert Lafore

class DistPar // distance and parent
{ // items stored in sPath array
    public double distance; // distance from start to this vertex
    public int parentVert; // current parent of this vertex
    // -------------------------------------------------------------
    public DistPar(int pv, double d) // constructor
    {
        distance = d;
        parentVert = pv;
    }
} 