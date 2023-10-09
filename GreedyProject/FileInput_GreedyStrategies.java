
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
    
public class GreedyStrategies { 
    
    static class Pair{
    int index;
    Bag bag;

    Pair(int index,Bag bag)
    {
        this.index = index;
        this.bag = bag;
    }
}
   static class Bag {
        int numWorking;
        int total;
        
        //constructor to initialize the bag value
        public Bag(int numWorking, int total)
        {
            this.numWorking=numWorking;
            this.total=total;
        }
    }
 
   static class BagAddStrategy1 implements Comparator<Pair> {
        //Override the compare method of the priority queue to compare by numWorking only.
        @Override
        public int compare(Pair pair1, Pair pair2)
        {
           Bag bag1 = pair1.bag;
           Bag bag2 = pair2.bag;
           if(bag1.numWorking == bag2.numWorking)
           {
            return Integer.compare(pair1.index,pair2.index);
           }
           return Integer.compare(bag1.numWorking, bag2.numWorking);
        }
    }

   static class BagAddStrategy2 implements Comparator<Pair> {
        //Override the compare method of the priority queue to compare by the formula only.
        @Override
        public int compare(Pair pair1, Pair pair2)
        {
           Bag bag1 = pair1.bag;
           Bag bag2 = pair2.bag;
           if(bag1.total==0 || bag2.total==0 )
           {
            
           }
           if((double) bag1.numWorking/bag1.total == (double) bag2.numWorking/bag2.total)
           {
            return Integer.compare(pair1.index,pair2.index);
           }
           return Double.compare( (double) bag1.numWorking/bag1.total, (double) bag2.numWorking/bag2.total);
        }
    }

    static class BagAddStrategy3 implements Comparator<Pair> {
        //Override the compare method of the priority queue to compare by total only.
        @Override
        public int compare(Pair pair1, Pair pair2)
        {
            Bag bag1 = pair1.bag;
            Bag bag2 = pair2.bag;
            if(bag1.total == bag2.total)
            {
             return Integer.compare(pair1.index,pair2.index);
            }
            return Integer.compare(bag1.total, bag2.total);
        }
    }
    
    static class BagAddStrategy4 implements Comparator<Pair> {
        //Override the compare method of the priority queue to compare by the formula only.
        @Override
        public int compare(Pair pair1, Pair pair2)
        {
            Bag bag1 = pair1.bag;
            Bag bag2 = pair2.bag; 
            if((double) (bag2.numWorking+1)/(bag2.total+1) - (double) bag2.numWorking/bag2.total == 
                (double) (bag1.numWorking+1)/(bag1.total+1) - (double) bag1.numWorking/bag1.total)
            {
             return Integer.compare(pair1.index,pair2.index);
            }          
            return Double.compare(
                (double) (bag2.numWorking+1)/(bag2.total+1) - (double) bag2.numWorking/bag2.total, 
                (double) (bag1.numWorking+1)/(bag1.total+1) - (double) bag1.numWorking/bag1.total
            );
        }
    }

    public static void maximiseAvgPercentage(int k, Bag[] bags, int strategy)
    {
        PriorityQueue<Pair> pq;
        switch(strategy)
        {
            case 1: 
                pq = new PriorityQueue<Pair>(new BagAddStrategy1());
                break;
            case 2:
                pq = new PriorityQueue<Pair>(new BagAddStrategy2());
                break;
            case 3:
                pq = new PriorityQueue<Pair>(new BagAddStrategy3());
                break;
            case 4:
                pq = new PriorityQueue<Pair>(new BagAddStrategy4());
                break;
            default:
                pq = new PriorityQueue<Pair>(new BagAddStrategy1());
        }

        int indexIterator = 0;
        for(Bag bagIterator : bags)
        {
            Pair pairToBeAdded = new Pair(indexIterator++,bagIterator);
            pq.add(pairToBeAdded);
        }
        
        for(int i=0; i<k; i++)
        {
            Pair pairPicked = pq.poll();
            Bag bagPicked = pairPicked.bag;
            bagPicked.numWorking+=1;
            bagPicked.total+=1;
            pq.add(pairPicked);
            // System.out.print(pairPicked.index+" ");
        }
    }
    
    public static void calculateAveragePercentage(int n,Bag[] bags)
    {
        float average = 0;
        for(Bag bagIterator : bags)
        {
          float averageForBagIterator = (float)bagIterator.numWorking/bagIterator.total; 
          average = average+averageForBagIterator;
        }
        System.out.println(average/n*100+"%");
    }
    
    
    
     public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("input5.txt"));
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            Bag[] bags = new Bag[n];

            if (n == 0) {
                return; // No bags to process
            }

            for (int i = 0; i < n; i++) {
                int numWorking = scanner.nextInt();
                int total = scanner.nextInt();
                bags[i] = new Bag(numWorking, total);
            }

            int strategy = Integer.parseInt(args[0]);
            maximiseAvgPercentage(k, bags, strategy);
            calculateAveragePercentage(n, bags);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}