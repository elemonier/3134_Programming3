import java.io.*;
import java.util.*;
//import java.util.Random;
//import java.lang.math.random;
public class AdjustListGraph implements Serializable {
	private Hashtable<String, Integer> Lookup; //ht to map
	private VNode [] data;	//note fixed size
	private int vcount;
	private Random rand;

	public AdjustListGraph(){
		rand = new Random();
		Lookup = new Hashtable<String, Integer>();
		vcount = 0;
	}

	public AdjustListGraph(String fileName){
		rand = new Random();
		Lookup = new Hashtable<String, Integer>();	
		data = readInCities(fileName);
		fillLookup();
	}

	private VNode[] readInCities(String fileName){
		VNode [] nodeList = null;
		BufferedReader br = null;
		String name = null;
		float lat = 0;
		float lon = 0;
		int nextIndex = 0;
		try{
			String currentLine;
			br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			nodeList = new VNode[Integer.parseInt(currentLine)];
			while((currentLine = br.readLine()) != null){
				name = currentLine;
				lat = Float.parseFloat(br.readLine());
				lon = Float.parseFloat(br.readLine());
				VNode city = new VNode(name, lat, lon, nextIndex);
				nodeList[nextIndex++] = city;
			}
		} catch(FileNotFoundException e){
			System.out.println("File not found.");
		}catch(IOException e){
				e.printStackTrace();
		}
		System.out.println("got through.");
	return 	nodeList; 
	}

	private void fillLookup(){
		String city = null;
		for(int i = 0; i < data.length; i++){
			city = data[i].getCityName();
			if(!Lookup.containsKey(city)){
				addEdges(data[i]);
				Lookup.put(city, i);
		}
		}
		checkInEdges();
		//System.out.println("size of ht: " + Lookup.size());
		//System.out.println("size of data: " + data.length);
	}

	private void addEdges(VNode X){
		int i = 0;
		int edges = rand.nextInt(6) + 2;
		double weight = (double) (rand.nextInt(1900) + 100);
		int index = rand.nextInt(data.length);
		//System.out.println("Edges: " + Integer.toString(edges));
		while(i < edges){
			if(addEdge(X, data[index], weight)){
				i++;
			}
			weight = (double) (rand.nextInt(1900) + 100);
			index = rand.nextInt(data.length);
		}
		//System.out.println("Incount: " + X.getInCount() + ", Outcount: " + X.getOutCount());
		//System.out.println("OutEdges for " + X.getCityName() + ": \n" + X.getOutList());

	}
	/**
	*	VNode X - from node
	*	VNode Y - to node 
	*	w - weight to edge
	*
	**/
	private boolean addEdge(VNode X, VNode Y, double w){
		boolean added = X.addEdge(Y, w);
		return added;
	}

	private void checkInEdges(){
		for(int i = 0; i < data.length; i++){
			while(Double.parseDouble(data[i].getInCount()) == 0){
				double weight = (double) (rand.nextInt(1900) + 100);
				int index = rand.nextInt(data.length);
				addEdge(data[index], data[i], weight);
				//System.out.println("in edge added for: " + data[i].getCityName());				
			}
		}

	}
	public String listCities(String state){
		//separate variable for state?
		//System.out.println("state: " + state);
		String str = "Cities in the state of: " + state + "\n";
		Set st = Lookup.entrySet();
		Iterator itr = st.iterator();
		while(itr.hasNext()){
			Map.Entry<String, Integer> entry = (Map.Entry) itr.next();
			if(entry.getKey().contains(", " + state)){
				//System.out.println("KEY: " + entry.getKey() + "value: " + Integer.toString(entry.getValue()));
				//System.out.println("@ value: " + Integer.toString(entry.getValue()) + data[entry.getValue()].toString());
				str += (data[entry.getValue()].toString() + "\n");
			}
		}
		//System.out.println(str);
		return str;
	}

	public String getCityInfo(String cityName){
		Integer index = Lookup.get(cityName);
		return data[index].toString();
	}
	public String getCity(int index) {
		//System.out.println(data[index].getCityName());
		return data[index].getCityName();
	}

	//public void setCurrentCity(int value){
	//
	//
	//}
	public String findClosestCities(String currentCity, double maxDistance){
		System.out.println("in closest cities.");
		int num = 0;
		double currentDistance = 0;
		int startIndex;
		VNode start;
		for(VNode v : data){
			v.setVisited(false);
			v.setMinDistance(Double.POSITIVE_INFINITY);
		}
		if(currentCity == null){
			System.out.print("Your current city wasn't set; so we generated a random start city for you.");
			startIndex = rand.nextInt(data.length);
		}
		else{
			startIndex = Lookup.get(currentCity);
		}
		start = data[startIndex];
		start.setMinDistance(0);
		start.setVisited(true);
		//VNode end = data[id];
		Comparator<VNode> comparator = new VNodeComparator();
		PriorityQueue<VNode> q = new PriorityQueue<VNode>(data.length, comparator);
		q.add(start);
		VNode cur = start;
		while(cur.getMinDistance() < maxDistance){
			System.out.println(Double.toString(cur.getMinDistance()));
			currentDistance = cur.getMinDistance();
			//for(int i = 0; i < cur.getList().getIntSize(); i++){
			MyList l = cur.getList();
			Iterator i = l.iterator();
			//System.out.println(cur.toString());
			while(i.hasNext()){
				ENode e = (ENode) i.next();
				//System.out.println(e.toString());
				VNode vTemp = data[Lookup.get(e.getData())];
				double tempWeight = e.getWeight();
				if(!vTemp.getVisited()){
					vTemp.setVisited(true);
					vTemp.decrease(cur.getCityName(), e.getWeight() + currentDistance);
					q.add(vTemp);
				}
				else{
					if((cur.getMinDistance() + currentDistance) < vTemp.getMinDistance()){
						vTemp.decrease(cur.getCityName(), e.getWeight() + currentDistance);
						q.remove(vTemp);
						q.add(vTemp);
					}


				}
			}
			cur = q.poll();
		}
		System.out.println(q);
		//VNode [] anArray;
		//anArray = q.toArray();
		//for(VNode v : anArray)
		//	System.out.println(v.toString());
		//System.out.println("exited.");
		VNode list_cur = q.poll();
		System.out.println(list_cur);
		Double dist = list_cur.getMinDistance();
		System.out.println("First Dist: " + Double.toString(dist));
		String list = "";
		while(dist < maxDistance){
			list = list_cur.getCityName() + ": " + Double.toString(list_cur.getMinDistance()) + "\n" + list;
			list_cur = q.poll();
			dist = list_cur.getMinDistance();
			System.out.println("Dist: " + Double.toString(dist));
		}
		System.out.println(list);
		return list;
		//String finalPath = findPath(start, end);
		//System.out.println(finalPath);
		//System.out.println("The total distance traveled is: " + end.getMinDistance());
	}
	
	public String listCities(String cityName, int n){
		String str = "The cities with a maximum distance of " + Integer.toString(n) + " from " + cityName + ":\n";
		//get outList for city
		//search through each element, find y; then keep going until you hit y
		Integer index = Lookup.get(cityName);
		MyList cities  = data[index].getList();
		return str;

	}

	public void addData(String fileName){
		Lookup = new Hashtable<String, Integer>();	
		data = addCities(fileName);
		fillLookup();

	}

	private VNode[] addCities(String fileName){
		VNode [] nodeList = null;
		BufferedReader br = null;
		String name = null;
		double lat = 0;
		double lon = 0;
		int nextIndex = 0;
		try{
			String currentLine;
			br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			System.out.println(currentLine);
			nodeList = new VNode[data.length + Integer.parseInt(currentLine)];
			for(VNode v : data){
				nodeList[nextIndex] = data[nextIndex];
				++nextIndex; 
			}
			while((currentLine = br.readLine()) != null){
				name = currentLine;
				lat = Double.parseDouble(br.readLine());
				lon = Double.parseDouble(br.readLine());
				VNode city = new VNode(name, lat, lon, nextIndex);
				nodeList[nextIndex++] = city;
			}
		} catch(IOException e){
				e.printStackTrace();
		}
	return 	nodeList; 
	}

	public void dijkstra(String s, int id){
		double currentDistance = 0;
		int startIndex;
		VNode start;
		for(VNode v : data){
			v.setVisited(false);
			v.setMinDistance(Double.POSITIVE_INFINITY);
		}
		if(s == null){
			System.out.println("Your current city wasn't set; so we generated a random start city for you: ");
			startIndex = rand.nextInt(data.length);
		}
		else{
			startIndex = Lookup.get(s);
		}
		start = data[startIndex];
		start.setMinDistance(0);
		start.setVisited(true);
		VNode end = data[id];
		Comparator<VNode> comparator = new VNodeComparator();
		PriorityQueue<VNode> q = new PriorityQueue<VNode>(data.length, comparator);
		q.add(start);
		VNode cur = start;
		while(cur != end){
			currentDistance = cur.getMinDistance();
			cur = q.poll();
			//for(int i = 0; i < cur.getList().getIntSize(); i++){
			MyList l = cur.getList();
			Iterator i = l.iterator();
			//System.out.println(cur.toString());
			while(i.hasNext()){
				ENode e = (ENode) i.next();
				//System.out.println(e.toString());
				VNode vTemp = data[Lookup.get(e.getData())];
				double tempWeight = e.getWeight();
				if(!vTemp.getVisited()){
					vTemp.setVisited(true);
					vTemp.decrease(cur.getCityName(), e.getWeight() + currentDistance);
					q.add(vTemp);
				}
				else{
					if((cur.getMinDistance() + currentDistance) < vTemp.getMinDistance()){
						vTemp.decrease(cur.getCityName(), e.getWeight() + currentDistance);
						q.remove(vTemp);
						q.add(vTemp);
					}


				}
			}
		}
		String finalPath = findPath(start, end);
		System.out.println(finalPath);
		System.out.println("The total distance traveled is: " + end.getMinDistance());
	}

	private String findPath(VNode s, VNode e){
		VNode temp = e;
		String path = "";
		while(temp != s){
			path = temp.toString() + "\n" + path;
			temp = data[Lookup.get(temp.getPrevious())];
		}
		path = s.toString() + "\n" + path;
		return path;
	}

	public void calculateGeoDistances(String currentCity, int num){
		if(num > data.length){
			System.out.println("There are not that many cities. Please choose another number.");
		}
		VNode start = data[Lookup.get(currentCity)];
		MyList close = new MyList();
		System.out.println("start: " + start);
		for (VNode v : data){
			if(v != start){
				double d = start.calculateDistance(v);
				close.insert(v.getCityName(), d);
			}
		}
		close.sort();
		Iterator i = close.iterator();
		int check = 0;
		String listCities = "";
		while((check < num) && i.hasNext()){
			listCities += (i.next() + "\n");
			check++;
		}
		System.out.println(listCities);

	}
}















