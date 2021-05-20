//Written By Damien King
//takes in a given file name and reads through a graph file creating vertexes and edges between them
//then returns a graph object to the main
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GraFileReader {

    public Graph read(String fn){
        try {


            File fi = new File(fn);
            Scanner f = new Scanner(fi);

            int vertCount;
            int edgeCount;

            String in [] = f.nextLine().split(" ");

            vertCount = Integer.parseInt(in[0]);
            edgeCount = Integer.parseInt(in[1]);

            Graph g = new Graph(vertCount);

            for(int i = 0; i < vertCount; i++){
                in = f.nextLine().split(" ");
               in[1] = in[1].replace(",","");
                g.addVertex(in[0],Double.parseDouble(in[1]), Double.parseDouble(in[2]));
            }

            for(int i = 0; i < edgeCount; i++){
                in = f.nextLine().split(" ");
                //WILL NEED TO ADD PATH NAME HERE ONCE CHANGED TO ADJACENCY LIST
                g.addEdge(Integer.parseInt(in[0]),Integer.parseInt(in[1]));
            }

            return g;
        }

        catch(IOException i){
            System.out.println("File Not Found!");
            System.exit(0);
        }
        catch(NumberFormatException n){
            System.out.println("There is an error in the input file!");
        }

        /*
        catch(FileNotFoundException e){
            System.out.println("File Not Found!");
            System.exit(0);
        }

         */

        return null;

    }
}
