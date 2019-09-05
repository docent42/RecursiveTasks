import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args)
    {
        Node root = getRootNode();
        System.out.println("\n Start building ...\n");
        String result = new ForkJoinPool().invoke(new GetLinkMap(root));
        System.out.println("\n Complete !!!\n");
        try
        {
            FileWriter writer = new FileWriter(new File("tree\\tree.txt"),false);
            writer.write(result);
            writer.close();
        }catch (IOException ex){ex.printStackTrace();}
    }
    private static Node getRootNode()
    {
        return new Root("https://skillbox.ru","",1,new TreeSet<>(Comparator.naturalOrder()));
    }

}
