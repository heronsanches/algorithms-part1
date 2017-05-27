package algorithms.part1.unionfind.percolation;

import algorithms.part1.unionfind.TxtUtils;
import edu.princeton.cs.algs4.WeightedQuickUnionUF; 
import java.util.Scanner;


/**
 * <p align="justify">The model. We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. 
 * A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down)
 * open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill 
 * all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials
 * example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with
 * full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so 
 * that a system that percolates lets water fill open sites, flowing from top to bottom.)</p>
 * <p align="justify">By convention, the row and column indices are integers from 1 to n, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException 
 * if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0. </p>
 * @author Heron Sanches
 */
public class Percolation {

   private final WeightedQuickUnionUF wqu;
   private final int n;
   
   /**It indicates sites opened and blocked. 0: blocked, 1:opened.*/
   private int sites[];
   private int qttOpenSites;
   private final int bottomStart;
   private final int upperStart;
   private final int topVirtualSite;
   private final int bottomVirtualSite; //n*n+1
   
   
   public Percolation(int n){ // create n-by-n grid, with all sites blocked
      
      if(n <= 0)
         throw new IllegalArgumentException();
      
      this.n = n;
      this.upperStart = 1;
      this.bottomStart = calculatesIndex(n, 1);
      this.wqu = new WeightedQuickUnionUF(n * n + 2); //added 2 virtual sites, so the algorithm has to work from 1 to n*n real indexes, 0 and n*n+1 are the virtual sites
      this.sites = new int[n * n + 2];
      this.topVirtualSite = 0;
      this.bottomVirtualSite = sites.length - 1;
   
   }
   
   
   /**Calculates the correct index of the primitive int Java Array (with one dimension) - index from 0 to n+1, based on a nxn grid, where row and column indices are integers from 1 to n.*/
   private int calculatesIndex(int row, int col){
      return n*(row - 1) + col;
   }
   
   
   public void open(int row, int col){ // open site (row, col) if it is not open already
      
      if(row < 1 || col < 1 || row > n || col > n)
         throw new IndexOutOfBoundsException();
            
      int site = calculatesIndex(row, col);
      //System.out.println("site: "+site);
      
      if(sites[site] == 0){ //not opened
         
         sites[site] = 1; //open
         qttOpenSites++;

         
         if(site >= bottomStart){ //bottom sites
            
            wqu.union(site, bottomVirtualSite); //connect to the virtual bottom site
            
            if(n == 1)
               wqu.union(site, topVirtualSite);
            
         }else if(site >= upperStart && site <= n){ //upper sites
            wqu.union(site, topVirtualSite); //connect to the virtual upper site
         }
         
         //*****verify if the there are neighborhhoods opened and so connect to them*****
         if(col+1 <= n){ //right neighboor
            
            int right = calculatesIndex(row, col+1);
            
            if(sites[right] == 1)
               wqu.union(site, right);
         
         }
         
         if(col-1 >= 1){ //left neighboor
            
            int left = calculatesIndex(row, col-1);
            
            if(sites[left] == 1)
               wqu.union(site, left);
         
         }
         
         if(row-1 >= 1){ //up neighboor
            
            int up = calculatesIndex(row-1, col);
            
            if(sites[up] == 1)
               wqu.union(site, up);
         
         }
         
         if(row+1 <= n){ //bottom neighboor
            
            int bottom = calculatesIndex(row+1, col);
            
            if(sites[bottom] == 1)
               wqu.union(site, bottom);
         
         }
         //*****END*****verify if the there are neighborhhoods opened and so connect to them*****
         
      }
      
   }
   
   
   public boolean isOpen(int row, int col){ // is site (row, col) open?
      
      if(row < 1 || col < 1 || row > n || col > n)
         throw new IndexOutOfBoundsException();
      
      return sites[calculatesIndex(row, col)] == 1;
   
   }
   
   
   /**A full site is an open site that can be connected to an open site in the top row via a chain of
    * neighboring (left, right, up, down) open sites */
   public boolean isFull(int row, int col){  // is site (row, col) full?
      
      if(row < 1 || col < 1 || row > n || col > n)
         throw new IndexOutOfBoundsException();
      
      if(isOpen(row, col))
         if(wqu.connected(calculatesIndex(row, col), topVirtualSite))
            return true;
      
      return false;
   
   }
   
   
   public int numberOfOpenSites(){ // number of open sites
      return qttOpenSites;
   }
   
   
   /**The system percolates if there is a path from a site located on the most bottom to the most top.*/
   public boolean percolates(){ // does the system percolate?
      
      if(wqu.connected(topVirtualSite, bottomVirtualSite))
         return true;
      
      return false;
      
   }
   
   
   public static void main(String[] args){   // test client (optional)
      
      TxtUtils txtUtils = new TxtUtils(args[0]);
      Scanner input = txtUtils.getInput();
      
      Percolation perc = new Percolation(input.nextInt());
      int row;
      int col;
      
      while(input.hasNext()){
         
         row = input.nextInt();
         col = input.nextInt();
         perc.open(row, col);
         System.out.println("row: "+row+" column: "+col);
         System.out.println("isopen? "+perc.isOpen(row, col));
         System.out.println("percolates? "+perc.percolates());
         System.out.println("n opened sites: "+perc.numberOfOpenSites());
         System.out.println("isFull? "+perc.isFull(row, col));
         System.out.println();

      }
            
   }


}