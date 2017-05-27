package algorithms.part1.unionfind.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author Heron Sanches
 */
public class PercolationStats {
    
   private final int trials;
   private final int n;
   private final int qttSites;
   private double percTValues[];
   private int ptvActualIndex;
   
   
   public PercolationStats(int n, int trials){ //perform trials independent experiments on an n-by-n grid

      if(n <= 0 || trials <=0)
         throw new IllegalArgumentException();
      
      this.n = n;
      this.qttSites = n*n;
      this.trials = trials;
      this.percTValues = new double[trials];
      
      for(int i=0; i<trials; i++)
         monteCarloSimulation();
      
   }
   
   
   /**Calculates row and column for a determined site.
    @return row and column on the Percolation pattern, [1, n]. int[0]: row; int[1]: column*/
   private int[] calculatesRowColumn(int site){
      
      int res[] = new int[2];
      
      if((site % n) == 0)
         res[0] = site / n;
      else
         res[0] = Math.floorDiv(site, n) + 1; //row on the Percolation pattern, [1,n]
      
      res[1] = site - (n * (res[0] - 1)); //column on the Percolation pattern, [1,n]
      return res;
      
   }
   
   
   private void monteCarloSimulation(){
      
      Percolation perc = new Percolation(n);
      int site;
      int rowColumn[];
      
      do{
         
         site = StdRandom.uniform(1, qttSites+1);
         rowColumn = calculatesRowColumn(site);
         
         while(perc.isOpen(rowColumn[0], rowColumn[1])){
            
            site = StdRandom.uniform(1, qttSites+1);
            rowColumn = calculatesRowColumn(site);
            
         }

         perc.open(rowColumn[0], rowColumn[1]);

         if(perc.percolates())
            break;
         
      }while(true);
      
      percTValues[ptvActualIndex++] = Double.valueOf(String.valueOf(perc.numberOfOpenSites())) / Double.valueOf(String.valueOf(qttSites));
      
   }
   
   
   public double mean(){ //sample mean of percolation threshold
      return StdStats.mean(percTValues);
   }
   
   
   public double stddev(){ //sample standard deviation of percolation threshold
      return StdStats.stddev(percTValues);
   }
   
   
   public double confidenceLo(){ // low  endpoint of 95% confidence interval
      return mean() - (1.96 * stddev() / Math.sqrt(trials));
   }
   
   
   public double confidenceHi(){ // high endpoint of 95% confidence interval
      return mean() + (1.96 * stddev() / Math.sqrt(trials));
   }
   
   
   /**<p align="justify">Two command-line arguments n and T, performs T independent computational experiments (discussed above)
    * on an n-by-n grid, and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold.</p>*/
   public static void main(String[] args){ // test client (described below)

      PercolationStats ps = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
      System.out.println("mean                    = "+ps.mean());
      System.out.println("stddev                  = "+ps.stddev());
      System.out.println("95% confidence interval = ["+ps.confidenceLo()+", "+ps.confidenceHi()+"]\n");
      
   }
   
   
}