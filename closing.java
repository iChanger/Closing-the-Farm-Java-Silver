//By Ian, and here I will actually provide GOOD commentation unlike you know who...
import java.io.*;
import java.util.*;
class closing {
    public static void main (String [] args) throws IOException {
        //WARNING: Do not read the problem statement wrong like I did! The reason why I was getting it wrong because I didn't know that the barns gets closed and STAYS closed. The problem asks for KEEPING THE BARNS CLOSED, NOT ONLY CLOSING ONE BARN AT A TIME. Watch out for this!
        BufferedReader f = new BufferedReader(new FileReader("closing.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("closing.out")));
        StringTokenizer st = new StringTokenizer(f.readLine());    
        int N = Integer.parseInt(st.nextToken()); //The number of barns you have
        int M = Integer.parseInt(st.nextToken()); //The number of bidirectional paths you have
        ArrayList<LinkedList<Integer>> adj_list = new ArrayList<LinkedList<Integer>>(); //We keep this to create an Adjacency List. The adjacency list is just a way to store nodes, and I did this the simple way
        boolean[] ignore_list = new boolean[N + 1]; //We have an ignore array (you will see why we use a boolean array later on). This array will provide all of the barns we ignore (all of the closed barns) in our Depth-First-Search. It is simply storing all of the numbers at the end of the input file
        LinkedList<Integer> good_list = new LinkedList<Integer>(); //This is just a list of all of the barns that are open. We can start only in an open barn in our Depth-First-Search, otherwise DFS will not work. Thus, we just select the first index to get a guaranteed open barn.
        for(int i = 0; i <= N; i++)
            adj_list.add(new LinkedList<Integer>()); //We ignore the index 0, and we produce an extra index to prevent confusion
        for(int i = 1; i <= N; i++)
            good_list.add(i); //Initially, all of the barns will be open
        for(int i = 0; i < M; i++){ //This loop just adds all of the barns in adjacency list form
            StringTokenizer tempSt = new StringTokenizer(f.readLine());
            int i1 = Integer.parseInt(tempSt.nextToken());
            int i2 = Integer.parseInt(tempSt.nextToken());
            adj_list.get(i1).add(i2);
            adj_list.get(i2).add(i1);
        }       
        if(dfs(adj_list, new boolean[N + 1], good_list.get(0), 1, ignore_list) == N) //We easily check if the intial barn is open at first or not. The ignore list is empty, so  by default none of the barns are closed.
            out.println("YES");
        else
            out.println("NO");
        for(int i = 0; i < N - 1; i++){
            int ignore = Integer.parseInt(f.readLine());
            ignore_list[ignore] = true; //We let the barn be an index. We then set the index of the barn to be true to be ignored. You will see later why we do this
            good_list.remove(good_list.indexOf(ignore)); //Because that is the INDEX we remove, NOT the value we want to remove is removed. This removes the closed barn from the "open barn" list.
            if(dfs(adj_list, new boolean[N + 1], good_list.get(0), 1, ignore_list) == N - (i + 1)) //Alright, we start the DFS search. Why do we do N - (i + 1)? Because each iteration, we close a barn. Thus, you MUST visit all barns other than the number of barns you close, which is just N - (number of iterations). Think of it like this, the first iteration means you must visit N - 1 barns, and so forth...
                out.println("YES");
            else
                out.println("NO");
        }
        out.close();
    }
    private static int dfs(ArrayList<LinkedList<Integer>> adjLists, boolean[] visited, int v, int count, boolean[] ignore_list){
            visited[v] = true; //There will be a visited array to make sure we do not repeat over the same barn we visited
            //System.out.print(v + " "); //Just some lovely debugging
            for(int w : adjLists.get(v)){ //Here is the recursion part. Visit all of the barns in each index of the adjacency list. However, if we already visited the barn, we don't re-visit it.
                    if(!visited[w] && !ignore_list[w]){ //Here is why we set the ignore_list to be a boolean! We do NOT want to make it a list, because we'd have to SEARCH for whether "w" is in the ignore_list. We do NOT want that, that will take TOO MUCH TIME because it's a search. If we make it a boolean, we can check in CONSTANT TIME (just look at the index) if it is ignored. This is a very good practice and use of arrays, and I suggest you apply this method to other problems.
                        count = dfs(adjLists, visited, w, ++count, ignore_list); //And we do another recursive call. I adjusted this DFS recursion so that our method actually returns a value. I also suggest you apply this method of returning recursion to other problems.
                    }
            }
            return count;
   }
}
