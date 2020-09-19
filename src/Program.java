import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Program
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Ahp ahp = new Ahp("src/criteria.txt","src/alternatives.txt");
        ahp.runAhp();
    }
}
