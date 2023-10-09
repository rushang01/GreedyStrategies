
import java.util.*;
    
public class GreedyStrategies { 
    //Creating a pair class to keep track of the index of each bag.
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
   //Case in which total is 0 is handled by comapring indices in strategy 2 and 4.
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
           if(bag2.total == 0 || (double) bag1.numWorking/bag1.total == (double) bag2.numWorking/bag2.total )
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
            if(bag2.total == 0 || (double) (bag2.numWorking+1)/(bag2.total+1) - (double) bag2.numWorking/bag2.total == 
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
        //Initializing and inserting into priority queue based on the argument entered in terminal while running the program.
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

        //Corresponding index assigned to each bag just before inserting it into the priority queue.
        int indexIterator = 0;
        for(Bag bagIterator : bags)
        {
            Pair pairToBeAdded = new Pair(indexIterator++,bagIterator);
            pq.add(pairToBeAdded);
        }

         /* Picking the first Pair from priority queue, incrementing numWorking and total of the bag
            it contains and re-inserting it into the priority queue. Then print the index of the picked Pair.*/
        for(int i=0; i<k; i++)
        {
            Pair pairPicked = pq.poll();
            Bag bagPicked = pairPicked.bag;
            bagPicked.numWorking+=1;
            bagPicked.total+=1;
            pq.add(pairPicked);
            System.out.print(pairPicked.index+" ");
        }
    }
    
    /*This method calculates the average % by dividing the numWorking and total for each bag,
    summing them up, and printing them if needed by un-commenting the print line.*/
    public static void calculateAveragePercentage(int n,Bag[] bags)
    {
        float average = 0;
        for(Bag bagIterator : bags)
        {
          float averageForBagIterator = (float)bagIterator.numWorking/bagIterator.total; 
          average = average+averageForBagIterator;
        }
        // System.out.println(average/n*100+"%");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        Bag[] bags = new Bag[n];

        for(int i=0; i<n; i++)
        {
            int numWorking = scanner.nextInt();
            int total = scanner.nextInt();
            bags[i] = new Bag(numWorking,total);
        }
        // Check for the strategy number from the command line arguments
            int strategy = Integer.parseInt(args[0]);
            scanner.close();
            maximiseAvgPercentage(k,bags,strategy);
            calculateAveragePercentage(n,bags);
    }
}