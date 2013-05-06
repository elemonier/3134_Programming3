/**
 * Author: Emily Quinn Lemonier
 * Uni: eql2001
 * Goal: Create a class called node, which holds an element for a linked list
 * Note: Modified DList.java and ListNode.java from Shlomo Lecture 5
 * 
 */
import java.util.*;
import java.io.*;

public class ENode implements Serializable{
	private String name;
	private double weight;
	private int count;
	ENode prev;
	ENode next;

	/**
	 * Constructor method.
	 * Instantiates all instance variables:
	 * -data - information node holds
	 * -prev & next are pointers to the previous & next nodes on the list
	 * @param s - string which contains data for node
	 */
	public ENode(String s, double w){
		name = s;
		prev = next = null;
		weight = w;
	}
	
	/**
	 * Method which returns string data 
	 * @return data
	 */
	public String getData() {return name;}
	
	/**
	 * Method which returns weight
	 * @return weight
	 */
	public double getWeight()	{return weight;}
	
	/**
	 * Method which sets the count
	 * @param count - count to be set to
	 */
	public void setWeight(double weight)	{this.weight = weight;}

	/**
	 * Method which sets the data
	 * @param data - data to be set to
	 */
	public void setData(String data) 	{name = data;}	
	
	public String toString(){
		return name + ", " + Double.toString(weight);
	}

	
}
