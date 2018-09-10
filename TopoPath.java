//Samuel Bermudez sa167851
//COP 3503 Summer 2018
import java.io.*;
import java.util.*;
import java.lang.Object;
import java.io.Reader;
import java.io.BufferedReader;
import java.util.stream.Stream;
import java.util.HashMap;

class adjacencyList {
	int[][] ajList;
	
	//creating an adjacencyList for holding the graph
	public adjacencyList(int vertices) {
	this.ajList = new int[vertices][];
	}
	
	//listing vertice and which vertices it is adjacent to
	public void addPath(String filling, int num) {
	String []array = filling.split(" ");
	//arraylength is set by first string from the string split
	int length = Integer.parseInt(array[0]);
	int index = 0;
	this.ajList[num] = new int[length]; 
	for(int i = 0; i < length; i++) {
		this.ajList[num][i] = Integer.parseInt(array[i + 1]);
	}
	}
}

class TopoPath
{
	
	public static boolean hasTopoPath(String filename){
		BufferedReader fp = null;
		adjacencyList ConstTopoSort;
		String inputs;
		int i = 0;
		int c;
		
		try {
			
			fp = new BufferedReader(new FileReader(filename));
			//create adjacencyList by reading in first line to pass into constructor method
		ConstTopoSort = new adjacencyList(Integer.parseInt(fp.readLine()));
		//feed in remaining lines for creating the adjacencyList
		while((inputs = fp.readLine()) != null) {
			ConstTopoSort.addPath(inputs, i++);
		}
		
		if(fp != null)
			fp.close();
		
		int length = ConstTopoSort.ajList.length;
		int []tally = new int[length];
		
		//array increments for all incoming edges for each vertice
		for(i = 0; i < length; i++)
			for(int j = 0; j < ConstTopoSort.ajList[i].length; j++)
			{
				tally[ConstTopoSort.ajList[i][j] - 1]++;
			}
			
		int root = -1;
		
		//finding the 1 vertice with zero incoming edges.
		//for TopoPath to hold true there can only be 1 vertice with 0 incoming edges
		for(i = 0; i < length; i++)
		{
			if(tally[i] == 0 && root != -1)
				return false;
			
			if(tally[i] == 0 && root == -1)
				root = i;
		}
		//root is set to -1 if no edge is found return false
		if(root == -1)
			return false;
		
		int temp = 0;
		boolean flag = false;
		
		/*For each vertice -1 check if there is 1 vertice being set to 0 incoming edges so that TopoPath
		  can continue. boolean flag is set to true to find the 1 vertice. If no paths are found or if two
		  are found return false. Otherwise set flag to false and move to next root held by temp. Last node
		  will have no outgoing edges otherwise it is impossible to reach therefore it is redundant to check.
		*/
		for(i = 0; i < length - 1; i++) {
			
			for(int j = 0; j < ConstTopoSort.ajList[root].length; j++) {
			
				if(--tally[ConstTopoSort.ajList[root][j] - 1] == 0)
				{
					if(flag)
						return false;
					
					temp = ConstTopoSort.ajList[root][j] - 1;
					flag = true;
				}
			}
			root = temp;
			if(flag)
				flag = false;
			else 
				return false;
			
		}
		
		return true;
		}
		
		catch ( IOException e) {
			System.out.println("File Error");
		}
		return true;
	}
	
	public static double difficultyRating()
	{
		return 2.0;
	}
	
	public static double hoursSpent()
	{
		return 5.0;
	}
}