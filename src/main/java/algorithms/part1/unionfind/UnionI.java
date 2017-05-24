package algorithms.part1.unionfind;

/**
 *
 * @author Heron Sanches
 */
public interface UnionI {
   
   int find(int p);
   boolean connected(int p, int q);
   void union(int p, int q);
   void printComponents();
   
}