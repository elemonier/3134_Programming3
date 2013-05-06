import java.io.Serializable;
import java.util.*;
import java.lang.Math.*;
import java.lang.Comparable;

public class VNode implements Serializable, Comparable<VNode>{
	private String city_name;	//name of city
	//private String state_name;
	private double minDistance;
	private VNode previous;
	private double latitude;
	private double longitude;
	private int inCount;	//counts for in degree
	private int outCount;	//counts for out degree
	private boolean visited;
	private MyList inList;
	private MyList outList;
	private int id;
	private String path;
	public VNode(String n, double lon, double lat, int value){
		if(n.contains(","))
			city_name = n;
		else
			city_name = n + ", " + n;
		latitude = lat;
		longitude = lon;
		inCount = 0;
		outCount = 0;
		visited = false;
		inList = new MyList();	
		outList = new MyList();
		id = value;
		minDistance = Double.POSITIVE_INFINITY;
		previous = null;
		path = null;
	}

	public String getCityName(){
			return this.city_name;
	}

	public String getPrevious(){
		return path;
	}

	public String getOutList(){
		return outList.toString();
	}

	public MyList getList(){
		MyList l = outList;
		return l;
	}

	public String toString(){
		String str = "City: " + city_name +
			"; InCount: " + getInCount() + 
			"; OutCount: " + getOutCount() + 
			"; Unique ID: " + id; 

		return str;
	}
	
	public boolean addEdge(VNode X, double weight){
		//check if it's the same city
		if(X.getCityName() == this.getCityName()){
			return false;
		}
		//check if node X already has a connection
		else if(X.hasConnection(this) || this.hasConnection(X)){
			return false;
		}
		
		outList.insert(X.getCityName(), weight);
		X.plusInCount();
		return true;
			
	}

	public void plusInCount(){
		inCount++;
	}

	public String getInCount(){
		return Double.toString(inCount);
	}

	public String getOutCount(){
		return outList.getSize();
	}

	public double getLat(){
		return latitude;
	}

	public double getLon(){
		return longitude;
	}

	private boolean hasConnection(VNode N){
		//determines if another node already has connection
		int index = N.outList.find(this.getCityName());
		if(index > 0){return true;}
		else {return false;}
	}


	public String getID(){
		return Integer.toString(id);
	}
	public void setMinDistance(double num){
		minDistance = num;
	}

	public double calculateDistance(VNode v){
		double x = 69.1 *(v.getLat() - this.getLat());
		double y = 69.1 *(v.getLon() - this.getLon()) * Math.cos(this.getLat()/57.3);
		double dist = Math.hypot(x, y);
		return dist;
	}

	public double getMinDistance(){
		return minDistance;
	}

	public int compareTo(VNode v){
		if(minDistance < v.getMinDistance())
			return 1;
		else if(minDistance > v.getMinDistance())
			return -1;
		else
			return 0;

	}

	public boolean getVisited(){return visited;}

	public void setVisited(boolean b){visited = b;}

	//public void printPath(VNode v){
	//
	//}
	public void decrease(String key, double newMin){
		minDistance = newMin;
		path = key;
	}
}

class VNodeComparator implements Comparator {
	public int compare(Object a, Object b){
		VNode v1 = (VNode) a;
		VNode v2 = (VNode) b;
		if( v1.getMinDistance() > v2.getMinDistance())
			return 1;
		else if(v1.getMinDistance() < v2.getMinDistance())
			return -1;
		else
			return 0;
	}
}