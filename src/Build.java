import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<Vertex<String>> visited = new HashSet<>();
    printShortWordsHelper(vertex, k, visited);
  }
  
  public static void printShortWordsHelper(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    //base case
    if (vertex == null) return;
    if (visited.contains(vertex)) return;

    //current vertex as visited
    visited.add(vertex);

    //check is word is shorter than k
    String word = vertex.data; 
    if (word.length() < k) {
      System.out.println(word);
    }

    //recursively visit adjacent vertices
    for (Vertex<String> neighbor : vertex.neighbors) {
      printShortWordsHelper(neighbor, k, visited);
    }
  }
  


  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if (vertex == null) return "";

    Set<Vertex<String>> visited = new HashSet<>();
    return longestWordHelper(vertex, visited, vertex.data);
  }
  public static String longestWordHelper(Vertex<String> vertex, 
  Set<Vertex<String>> visited, String longWord) {
    /*
    state base case
    current vertex as visited
    check and update if the current word is the longest
    recurisvely visit adjecent vertices
     */

    if (vertex == null || visited.contains(vertex)) return longWord;

    visited.add(vertex);

    String str = vertex.data;
    if (str.length() > longWord.length()) {
      longWord = str;
    }

    for(Vertex<String> neighbor : vertex.neighbors) {
      longWord = longestWordHelper(neighbor, visited, longWord);
    }
    
    return longWord;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if (vertex == null) return;

    Set<Vertex<T>> visited = new HashSet<>();
    printSelfLoopersHelper(vertex, visited);
  }

  public static <T> void printSelfLoopersHelper(Vertex<T> vertex, Set<Vertex<T>> visited) {
    /*
    state base case
    current vertex as visited 
    check if vertex has a self-loop 
    recursively visit adjacent vertices
     */ 
    if (vertex == null || visited.contains(vertex)) return;

    visited.add(vertex);

    if (vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }

    for (Vertex<T> neighbor : vertex.neighbors) {
      printSelfLoopersHelper(neighbor, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if (start == null || destination == null) return false;
    if (start == destination) return true;

    Set<Airport> visited = new HashSet<>();
    return canReachHelperMethod(start, destination, visited);
  }

  public static boolean canReachHelperMethod(Airport start, Airport destination, Set<Airport> visited) {
    /*
    base case
    current vertex as visited
    check if any neighbor leads to destination return true
    recursively visit adjacent verticies or neighboring flights to visit
     */

    if (start == destination) return true;

    visited.add(start);

    for (Airport neighbor : start.getOutboundFlights()) {
      if(!visited.contains(neighbor)) {
        if(canReachHelperMethod(neighbor, destination, visited)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> visited = new HashSet<>();

    dfs(graph, starting, visited);

    Set<T> unreachable = new HashSet<>();

    for (T vertex : graph.keySet()) {
      if (!visited.contains(vertex)) {
        unreachable.add(vertex);
      }
    }

    return unreachable;
  }

  public static <T> void dfs(Map<T, List<T>> graph, T current, Set<T> visited) {
    if (visited.contains(current)) {
      return;
    }

    visited.add(current);

    for (T neighbor : graph.getOrDefault(current, Collections.emptyList())) {
      dfs(graph, neighbor, visited);
    }
  }
}
