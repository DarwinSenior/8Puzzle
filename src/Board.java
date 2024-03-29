import java.util.ArrayList;
import java.util.InputMismatchException;


public class Board  {
//public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
//// (where blocks[i][j] = block in row i, column j)
//public int dimension()                 // board dimension N
//public int hamming()                   // number of blocks out of place
//public int manhattan()                 // sum of Manhattan distances between blocks and goal
//public boolean isGoal()                // is this board the goal board?
//public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
//public boolean equals(Object y)        // does this board equal y?
//public Iterable<Board> neighbors()     // all neighboring boards
//public String toString()               // string representation of the board (in the output format specified below)
	private final int N;
	private int[][] blocks;
	private int[][] copyOfBlocks(){
		int[][] newBlocks=new int[N][N];
		for (int i = 0; i < newBlocks.length; i++) {
			for (int j = 0; j < newBlocks[i].length; j++) {
				newBlocks[i][j]=blocks[i][j];
			}
		}
		return newBlocks;
	}
	public Board(int[][] blocks){
		if (blocks[0].length!=blocks.length) throw new InputMismatchException("It is not a square");
		N=blocks.length;
		this.blocks=blocks;
		this.blocks=copyOfBlocks();
	}
	public int dimension() {
		return N;
	}
	public int hamming() {
		int count=0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if ((blocks[i][j]!=0) && (blocks[i][j]!=i*N+j+1)) {
					count++;
				}
			}
		}
		return count;
	}
	public int manhattan(){
		int count=0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (blocks[i][j]!=0) {
					int originI=(blocks[i][j]-1)/N;
					int originJ=(blocks[i][j]-1)%N;
					count+=Math.abs(i-originI)+Math.abs(j-originJ);
				}
			}
		}
		return count;
	}
	public boolean isGoal(){
		if (this==null) {
			return false;
		}
		return hamming()==0;
	}
	public Board twin(){//TODO
		int[][] blocksChange=copyOfBlocks();
		for (int i = 0; i < N; i++) {
			if (blocksChange[i][N/2]!=0 && blocksChange[i][N/2-1]!=0) {
				int tmp=blocksChange[i][N/2];
				blocksChange[i][N/2]=blocksChange[i][N/2-1];
				blocksChange[i][N/2-1]=tmp;
				break;
			}
		}
		return new Board(blocksChange);
	}
	public boolean equals(Object y){
		if (this==y) return true;
		if (y==null) return false;
		if (this.getClass()!=y.getClass()) return false;
		Board thatBoard=(Board)y;
		if (this.dimension()!=thatBoard.dimension())return false;
		boolean identical=true;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (blocks[i][j]!=thatBoard.blocks[i][j]) {
					identical=false;
					break;
				}
			}
		}
		return identical;
	}
	public Iterable<Board> neighbors(){
		ArrayList<Board> neighborsBoards=new ArrayList<Board>();
		int posI = 0,posJ = 0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (blocks[i][j]==0) {
					posI=i;
					posJ=j;
					break;
				}
			}
		}
		if (posI>0) {
			int[][] tmpBlocks=copyOfBlocks();
			tmpBlocks[posI][posJ]=tmpBlocks[posI-1][posJ];
			tmpBlocks[posI-1][posJ]=0;
			neighborsBoards.add(new Board(tmpBlocks));
		}
		if (posI<N-1) {
			int[][] tmpBlocks=copyOfBlocks();
			tmpBlocks[posI][posJ]=tmpBlocks[posI+1][posJ];
			tmpBlocks[posI+1][posJ]=0;
			neighborsBoards.add(new Board(tmpBlocks));
		}
		if (posJ>0) {
			int[][] tmpBlocks=copyOfBlocks();
			tmpBlocks[posI][posJ]=tmpBlocks[posI][posJ-1];
			tmpBlocks[posI][posJ-1]=0;
			neighborsBoards.add(new Board(tmpBlocks));
		}
		if (posJ<N-1) {
			int[][] tmpBlocks=copyOfBlocks();
			tmpBlocks[posI][posJ]=tmpBlocks[posI][posJ+1];
			tmpBlocks[posI][posJ+1]=0;
			neighborsBoards.add(new Board(tmpBlocks));
		}
		return neighborsBoards;
	}
	@Override
		public String toString() {
			StringBuilder builder=new StringBuilder();
			builder.append(N);
			builder.append('\n');
			for (int i = 0; i < blocks.length; i++) {
				for (int j = 0; j < blocks[i].length; j++) {
					builder.append(blocks[i][j]);
					builder.append(' ');
				}
				builder.append('\n');
			}
			return builder.toString();
		}
	public static void main(String[] args) {
		int N=StdIn.readInt();
		int[][] blocks1=new int[N][N];
		for (int i = 0; i < blocks1.length; i++) {
			for (int j = 0; j < blocks1[i].length; j++) {
				blocks1[i][j]=StdIn.readInt();
			}
		}
		Board board1=new Board(blocks1);
		int N2=StdIn.readInt();
		int[][] blocks2=new int[N2][N2];
		for (int i = 0; i < blocks2.length; i++) {
			for (int j = 0; j < blocks2[i].length; j++) {
				blocks2[i][j]=StdIn.readInt();
			}
		}
		Board board2=new Board(blocks2);
//		Board board=new Board(blocks);
//		//System.out.println(board.manhattan());
//		for (Board neighberBoard : board.neighbors()) {
//			System.out.println(neighberBoard);
//		}
		System.out.println(board1.equals(board2));
	}
}
