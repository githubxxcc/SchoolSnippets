package sg.edu.nus.cs2020;

public class BinarySearch {

	/**
	 * Returns the first index of occurrence of x in a sorted array. E.g.
	 * search({1, 2, 3, 3, 3}, 3) should return 2.
	 * 
	 * @param arr
	 *            Sorted integer array.
	 * @param x
	 *            The key to be searched in arr.
	 */
	public static int search (int[] arr, int x) {
		// set the two ends of the searching, and the mid element
		int high = arr.length -1;
		int low = 0;
		
		// binary search through the array
		while(low<=high){
			int mid = low+(high-low)/2;
			//return low if the low element is equal to x
			if(arr[low] == x) return low;
			
			//search the right side of the mid if mid element is smaller than x.
			if(arr[mid]<x){
				low = mid+1;
			}
			//search the left side of the mid if mid element is greater than x.
			else if(arr[mid]>x) high = mid-1;
			else return mid;
		}
		return -1;
	}
	
	/**
	 * Returns the range in which x occurs in arr 
	 * E.g. search({1, 2, 3, 3, 3}, 3) should return [2, 4].
	 * 
	 * @param arr
	 *            Sorted integer array
	 * @param x
	 *            The key to be searched in arr.
	 * @return An integer array of size 2, in which the first index is the index
	 *         of first occurrence of x, while the second index is the index of
	 *         last occurrence of x. x is guaranteed to be in arr.
	 */
	public static int[] searchRange(int[] arr, int x) {
		//initiate the first and last index which will point to x.
		int first,last;

		//conduct binary search and find the index of x, set both first and last equal to it
		first = search(arr,x);
		last = first;
		
		//search backwards to find the value of first
		while(first>0 && arr[first-1] == x) first--;
		
		//search forward to find the value of last
		while(last<arr.length-1 && arr[last+1] == x) last++;
		
		return new int[]{first,last};
	}
	
	public static int findPos(int[] arr){
		//from both ends
		// search for local peak 
		
		int begin, end, mid;
		begin = 0;
		end = arr.length-1;
		mid = 0;

		
		while(end-begin != 1){
			mid = begin + (end-begin)/2;
			
			if(arr[begin]<=arr[mid]) begin = mid;
			else end = mid;
		}
		
		return end;
	}
}
