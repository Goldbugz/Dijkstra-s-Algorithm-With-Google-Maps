class Vertex
{
    public String label; // label (e.g. ‘A’)
    public boolean isInTree;
    double latitude;
    double longitude;
    // -------------------------------------------------------------
    public Vertex(String lab,double lat, double lo) // constructor
    {
        label = lab;
        isInTree = false;
        latitude = lat;
        longitude = lo;
    }
// -------------------------------------------------------------
}