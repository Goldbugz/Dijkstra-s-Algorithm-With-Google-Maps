//written by Damien King
//This is the main class for the project
//creates the graph objects and calls its methods

class PathApp
{
    public static void main(String[] args)
    {

        if(args.length == 4) {
            GraFileReader gr = new GraFileReader();
            Graph theGraph = gr.read(args[0]);


            System.out.println("Shortest paths");

            int start = theGraph.getIndex(args[2]);
            System.out.println(start);
            theGraph.path(start);

            theGraph.findRoute(args[2], args[3]);
            //theGraph.findRoute("Atlanta","Tallahassee@I-10");
            System.out.println();

            theGraph.graphFileWriter(args[1]);
        }
    }
}
