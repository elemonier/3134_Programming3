import java.util.Iterator;
import java.lang.Math.*;
import java.io.*;

/**
 * W3134: Programming Assignment 1
 * 
 * @author emilylemonier
 * @uni eql2001 Goal: Implement a MyCountingLinkedLists class which holds a list
 *      of nodes. Functionality: This list has the capability to insert, find,
 *      and delete nodes. The list itself can be sorted, printed, and reversed.
 *      Note: Extra Credit completed in bubbleSort() function.
 */

public class MyList implements Serializable, Iterable{

	private ENode head;
	private ENode tail;
	int size;

	/**
	 * Constructor Method 
	 * Initializes instance variables: 
	 * head & tail nodes set to null 
	 * size & total elements set to 0
	 */
	public MyList() {
		head = tail = null;
		size = 0;
	}

	/**
	 * Method inserts node into list.
	 * 
	 * @param s
	 *            - string which contains data for node
	 */
	public void insert(String s, double w) {
		ENode newNode = new ENode(s, w);

		if (size == 0) {
			head = newNode;
			tail = newNode;
		}
		else{
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;
	}

	public String getSize(){
		return Integer.toString(size);
	}

	public int getIntSize(){
		return size;
	}

	/**
	 * Methods deletes an instance of String s in list by either decrementing
	 * its counter or deleting the node itself by calling remove(). 
	 * Size is also decremented.
	 * If IP not found in list MYLLException is thrown
	 * 
	 * @param s
	 *            - Node's data to match.
	 * @throws MYLLException
	 */
	public void delete(String s) throws MYLLException {
		int check = find(s);

		if (check >= 0) {
			ENode n = getNode(check);
			remove(n);
			size--;
		} else
			throw new MYLLException("ENode not found.");
	}

	/**
	 * Method: Removes node from linked list. Checks if node is head, tail, or
	 * in the middle and reassigns node pointers accordingly.
	 * 
	 * @param n
	 *            - node to remove
	 */
	private void remove(ENode n) {
		if (n.prev == null) {
			head = n.next;
			head.prev = null;
			return;
		}
		if (n.next == null) {
			tail = n.prev;
			n.prev.next = null;
			return;
		}
			n.prev.next = n.next;
			n.next.prev = n.prev;
			size--;
	}

	/**
	 * Sort method.
	 * Calls bubbleSort on list.
	 */
	public void sort() {
		this.bubbleSort();
	}

	/**
	 * Method: returns a node at a specific index in the list.
	 * Returns null of element is not in list.
	 * @param index
	 *            - index of node.
	 * @return Node type.
	 */
	public ENode getNode(int index) {
		ENode n = head;
		int count = 0;

		for (Iterator it = this.iterator(); it.hasNext();) {
			if (index == count) {
				return n;
			}
			n = (ENode) it.next();
			count++;
		}
		return null;
	}

	/**
	 * Locates a string in the list according to its data.
	 * Returns index or -1 if not in list.
	 * 
	 * @param s
	 *            - String of data
	 * @return integer index of string location
	 */
	public int find(String s) {
		int index = 0;
		ENode curr = head;
		for (Iterator it = this.iterator(); it.hasNext();) {
			if (curr.getData().equals(s)) {
				return index;
			}
			curr = (ENode) it.next();
			index++;
		}

		return -1;
	}


	/**
	 * Method Reverses the order of the list.
	 */
	public void reverse() {
		ENode temp = null;
		ENode curr = head;
		while (curr != null) {
			temp = curr.prev;
			curr.prev = curr.next;
			curr.next = temp;
			curr = curr.prev;
			tail = curr;
		}
		head = temp.prev;
	}

	/**
	 * Method alias for MyCountingLinkedListsIterator class
	 * 
	 * @return MyCountingLinkedListsIterator instance
	 */
	//public java.util.Iterator iterator() {
	public Iterator iterator(){
		return new MyListIterator();
	}

	/**
	 * Method: Sorts list using O(n^2) bubble sort
	 */
	private void bubbleSort() {
		boolean swapped = false;
		do {
			swapped = false;
			ENode curr = head;
			while (curr.next != null) {
				if (curr.next.getWeight() > curr.getWeight()) {
					swapped = true;
					String temp = curr.getData();
					double tempNum = curr.getWeight();

					curr.setData(curr.next.getData());
					curr.setWeight(curr.next.getWeight());
					curr.next.setData(temp);
					curr.next.setWeight(tempNum);
				}
				curr = curr.next;
			}
		} while (swapped);
	}


	public String toString(){
		String info = "";
		int i = 0;
		ENode curr = head;
		while(curr != null){
			info += "Edge " + Integer.toString(i) + ": " + curr.toString() + "\n";
			curr = curr.next;
			i++;
		}
		return info;
	}

	/**
	 * MyCountingLinkedListsIterator class
	 * 
	 * @author emilylemonier
	 * @uni eql2001
	 * 
	 */
	private class MyListIterator implements java.util.Iterator {
		private ENode current = head;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			//1return (current.next != null);
			return(current != null);
		}

		@Override
		public ENode next() {
			// TODO Auto-generated method stub
			//1current = (ENode) current.next;
			//1return current;
			ENode temp = current;
			current = temp.next;
			return temp;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
		}
	}
}
