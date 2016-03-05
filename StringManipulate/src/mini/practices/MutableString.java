package mini.practices;


//should I use Generics or use Character for parameter here? 
public class MutableString {
	private Node root;
	private char[] charArr;
	
	private class Node{
		private Node left;
		private Node right;
		private int count;
		private char value;
		
		Node(){
			left = null;
			right = null;
			count = 1;
		}
		
		Node(char value){
			this();
			this.value = value;
		}

	}
	

	MutableString(String exp){
		charArr = exp.toCharArray();
		buildTree();
	}
	
	/**
	 * build a tree from char[]. 
	 */
	private void buildTree(){
		int head = 0;
		int end = charArr.length-1;
		
		root = buildTree(charArr,head,end);
	}
	
	private int size(Node x){
		if(x == null) return 0;
		else return x.count;
	}
	
	private Node buildTree(char[] charArr, int head, int end){
		if(head > end) return null;
		
		int mid = head + (end-head)/2;
		
		Node x = new Node(charArr[mid]);
		
		x.left = buildTree(charArr,head,mid-1);
		x.right = buildTree(charArr,mid+1,end);
		
		x.count = size(x.left) + size(x.right) + 1;
		
		return x;	
	}
	
	
	public char get(int i) throws Exception{
		return get(root,i);
	}
	
	private char get(Node node, int i) throws Exception{
		if(node == null) throw new Exception();
		
		if(size(node.left) == i) return node.value;
		
		if(size(node.left) < i) return get(node.right,i-(size(node.left)+1));
		else return get(node.left,i);
		
	}
	
	public void insert(int i, char c){
		root = insert(root,i+1,c);
	}
	
	private Node insert(Node node, int i, char c){		
		if(node == null){
			return new Node(c);
		}
		
		if(size(node.left)>= i) node.left = insert(node.left,i,c);
		else node.right = insert(node.right,i- (size(node.left)+1),c);
		
		return node;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		inOrderTra(sb,root);
		
		return sb.toString();
	}
	
	private void inOrderTra(StringBuilder sb, Node node){
		if(node == null) return;
		
		if(node.left != null) inOrderTra(sb,node.left);
		
		sb.append(node.value);
		
		if(node.right != null) inOrderTra(sb,node.right);
	}
	
	public void reverse(){
		reverse(root);
	}
	
	private void reverse(Node x){
		if(x == null) return;
		
		reverse(x.left);
		reverse(x.right);
		
		Node temp = x.left;
		x.left = x.right;
		x.right = temp;
		
	}
	
	public static void main(String[] args) {
		MutableString ms = new MutableString("Handsome");
		ms.insert(7, 'I');
		
		try {
			System.out.println(ms.toString()+ms.get(2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
