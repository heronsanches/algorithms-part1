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

   private final WeightedQuickUnionUF wqupc;
   private final int n;
   
   /**It indicates sites opened and blocked. 0: blocked, 1:opened.*/
   private int sites[];
   private int qttOpenSites;
   
   
   public Percolation(int n){ // create n-by-n grid, with all sites blocked
      
      if(n <= 0)
         throw new IllegalArgumentException();
      
      this.n = n;
      this.wqupc = new WeightedQuickUnionUF(n * n);
      this.sites = new int[n * n];
      
      for(int i=0; i<this.sites.length; i++) //initializes all sites blocked, 0:blocked, 1:open
         sites[i] = 0;
   
   }
   
   
   /**Calculates the correct index of the primitive int Java Array (with one dimension) - index from 0 to n-1, based on a virtual nxn grid, where row and column indices are integers from 1 to n.*/
   private int calculatesIndex(int row, int col){
      return n*(row - 1) + (col - 1);
   }
   
   
   /**A site is open */
   public void open(int row, int col){ // open site (row, col) if it is not open already
      
      if(row < 1 || col < 1 || row > n || col > n)
         throw new IndexOutOfBoundsException();
      
      int site = calculatesIndex(row, col);
      
      if(sites[site] == 0){ //not opened
         
         sites[site] = 1; //open
         qttOpenSites++;
         
         //*****verify if the there are neighborhhoods opened and so connect to them*****
         if(col+1 <= n){ //right neighboor
            
            int right = calculatesIndex(row, col+1);
            
            if(sites[right] == 1)
               wqupc.union(site, right);
         
         }
         
         if(col-1 >= 1){ //left neighboor
            
            int left = calculatesIndex(row, col-1);
            
            if(sites[left] == 1)
               wqupc.union(site, left);
         
         }
         
         if(row-1 >= 1){ //up neighboor
            
            int up = calculatesIndex(row-1, col);
            
            if(sites[up] == 1)
               wqupc.union(site, up);
         
         }
         
         if(row+1 <= n){ //bottom neighboor
            
            int bottom = calculatesIndex(row+1, col);
            
            if(sites[bottom] == 1)
               wqupc.union(site, bottom);
         
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
      
      if(isOpen(row, col)){
         
         int site = calculatesIndex(row, col);
         
         for(int i=0; i<n; i++) //top sites
            if(wqupc.connected(site, i))
               return true;
         
      }
      
      return false;
   
   }
   
   
   public int numberOfOpenSites(){ // number of open sites
      return qttOpenSites;
   }
   
   
   /**The system percolates if there is a path from a site located on the most bottom to the most top.*/
   public boolean percolates(){ // does the system percolate?
      
      int limit = calculatesIndex(n, n) + 1;
      
      for(int i=calculatesIndex(n, 1); i<limit; i++){ //bottom sites
         
         if(sites[i] == 0) //not opened
            continue;
         
         for(int j=0; j<n; j++) //top sites
            if( sites[j] == 1 && wqupc.connected(i, j))
               return true;
         
      }
      
      return false;
      
   }
   
   
   public static void main(String[] args){   // test client (optional)
      
      TxtUtils txtUtils = new TxtUtils(args[0]);
      Scanner input = txtUtils.getInput();
      
      Percolation perc = new Percolation(input.nextInt());
      
      while(input.hasNext())
         perc.open(input.nextInt(), input.nextInt());
      
      System.out.println("Percolates? "+perc.percolates());
      
   }


}