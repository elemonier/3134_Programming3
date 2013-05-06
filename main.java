import java.io.*;
import java.util.Scanner;
public class main{
		static Scanner sc = new Scanner(System.in);
		static String outStr;
		static AdjustListGraph graph = null;
		static MenuOpt opt = null;
		static boolean quit = false;
		static String currentCity = null;


	public static void main(String [] args){
		System.out.println("Menu:\n" +
			"a. Load a text file into the system\n" +
			"b. Load a graph from a file callled mygraph.bin\n" +
			"c. save current graph to a file called mygraph.bin\n" +
			"d. Search for state and list all cities of that state with the " +
			"in/out counts for each\n" + 
			"e. Search for city and display some information about it. " +
			"(gps & in/out count)\n" + 
			"f. Set City X as current (X is between 0-N cities)\n"+
			"g. print current city\n" +
			"h. Find n closest cities to current city using gps distances.\n" +
			"i. Find all cities from current city with directed edge costs " + 
			"less than Y\n" +
			"j. Find shortest path between current and some target city\n" +
			"l. quit\n\n");
		while(!quit){
		System.out.println("Please input a valid letter option and press return:");
		opt = MenuOpt.valueOf(sc.next());
		
		switch(opt){
			case a: 
				caseA();
				break;
			case b: 
				graph = loadGraph();
				break;
			case c:
				writeGraph(graph);
				break;
			case d:
				listCities();
				break;
			case e:
				caseE();
				break;
			case f:
				caseF();
				break;
			case g:
				caseG();
				break;
			case h:
				break;
			case i:
				break;
			case j:
				caseJ();
				break;
			case k:
				break;
			case l:
				quit = true;
				System.out.println("Goodbye.");
				break;
			default: 
				System.out.println("invalid input.");
	}
}
}
	public enum MenuOpt { a, b, c, d, e, f, g, h, i, j, k, l; }
	public static void caseA(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Input 1 if you would like to clear the graph " +
			"or 2 if you want to add information to the existing graph: ");
		int in = Integer.parseInt(scan.nextLine());
		System.out.print("Name of the textfile you want to read " +
			"into the graph: ");
		String input = scan.nextLine();
		switch(in){
			case 1: in = 1;
				graph = new AdjustListGraph(input);
				break;
			case 2: in = 2;
				System.out.println("adding cities.");
				graph.addData(input);
				break;
			default: 
				System.out.println("meow.");
		}
	}

	public static void writeGraph(AdjustListGraph gr){
		System.out.println("Writing graph to mygraph.bin");
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
			try{
				fos = new FileOutputStream("mygraph.bin");
				out = new ObjectOutputStream(fos);
				out.writeObject(gr);
				out.close();
			} catch(Exception ex){
				System.out.println("writeGraph failed. No graph to write.");
				ex.printStackTrace();
			}
		}

	public static AdjustListGraph loadGraph(){
		AdjustListGraph gr = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try{
			fis = new FileInputStream("mygraph.bin");
			in = new ObjectInputStream(fis);
			gr = (AdjustListGraph) in.readObject();
			in.close();
			return gr;
		}catch (Exception ex){
			System.out.println("loadGraph failed. mygraph.bin does not exist.");
			return null;
		}
	}

	public static void listCities(){
		Scanner scan = new Scanner(System.in);
		if(graph != null){
			System.out.println("Please enter the name of a state: ");
			String state = scan.nextLine();
			System.out.println("state: " + state);
			String str = graph.listCities(state);
			System.out.println(str);
		}
		else{
			System.out.println("Please construct a graph.");

		}

	}
	public static void caseF(){
		try{
		Scanner scan = new Scanner(System.in);
		System.out.println("Input ID number (between 0-N) to remember" +
			" as current city: ");
		int id = Integer.parseInt(scan.nextLine());
		currentCity = graph.getCity(id);
		System.out.println("Your current city is now: " + currentCity);
	}catch(ArrayIndexOutOfBoundsException ex){
		System.out.println("Not a valid ID number.");
	}catch(NullPointerException ex){
		System.out.println("Not a valid id number.");
	}

	}
	public static void caseE(){
		try{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the name of the city " +
			"you would like information about: ");
		String name = scan.nextLine();
		String info = graph.getCityInfo(name);
		System.out.println(info);
	}catch(ArrayIndexOutOfBoundsException ex){
		System.out.println("There is no city with that name.");
	}

	}
	public static void caseG(){
		try{
		String info = graph.getCityInfo(currentCity);
		System.out.println(info);
	}
	catch(NullPointerException ex){
		System.out.println("Current City is not set yet.");
	}
	}

	public static void caseI(){
		//find the n closest cities from the current city, 
		//if the current has not been set choose a random city as current, 
		//Y is a number provided by the user. You will use the randomly generated d
		//irected edges to measure cost and print all those which are less than Y away.
		Scanner scan = new Scanner(System.in);
		System.out.println("What is the maximum weight: ");
		int n = scan.nextInt();
		String closestCities = graph.findClosestCities(currentCity, n);
	}

	public static void caseJ(){
		Scanner scan = new Scanner(System.in);
		System.out.println("What is the id number of the city you would like to travel to: ");
		int id = Integer.parseInt(scan.nextLine());
		graph.dijkstra(currentCity, id);
	}

}