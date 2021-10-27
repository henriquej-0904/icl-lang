import java.util.*;
import util.*;

public class Environment implements Cloneable{
  private Stack<List<Pair<String,Integer>>> scopeStack;
    public Environment(){
       scopeStack = new Stack<List<Pair<String,Integer>>>();
    }

    private Environment(Stack<List<Pair<String,Integer>>> scopeStack){
      this.scopeStack = scopeStack;
    }

    public Environment beginScope(){
     Environment e = (Environment)this.clone();
     e.scopeStack.push(new LinkedList<>());
     return e;
    }

    public Environment endScope(){
      Environment e = (Environment)this.clone();
      e.scopeStack.pop();
      return e;
     }


    public void assoc(String id, int value){
      List<Pair<String,Integer>> scope = scopeStack.peek();
      for (Pair<String,Integer> pair : scope) {
        if(pair.getLeft().equals(id))
          throw  new IllegalArgumentException();
      }
      scope.add(new Pair<String,Integer>(id,value));
     }

    public int find(String id){
     ListIterator< List<Pair<String,Integer>>> ite =  scopeStack.listIterator(scopeStack.size());
     boolean found = false;
     Pair<String,Integer> pair = null;
     while(ite.hasPrevious() && ! found){
      List<Pair<String,Integer>> scope = ite.previous();
      Iterator<Pair<String,Integer>> it = scope.iterator();
      while(it.hasNext() && !found){
        pair = it.next();
        if(pair.getLeft().equals(id))
          found = true;
      }
     }
     if(found)
      return pair.getRight();
    else 
     throw new NoSuchElementException ();
    }
 

  @Override
  @SuppressWarnings("unchecked")
  public Object clone(){
    return new Environment((Stack<List<Pair<String,Integer>>>)scopeStack.clone());
  }

    
}