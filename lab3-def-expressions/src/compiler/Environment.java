package compiler;

import java.util.*;
import util.*;

public class Environment implements Cloneable{
  private Stack<List<Pair<String,Coordinates>>> scopeStack;
    public Environment(){
       scopeStack = new Stack<List<Pair<String,Coordinates>>>();
    }

    private Environment(Stack<List<Pair<String,Coordinates>>> scopeStack){
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


    public void assoc(String id, Coordinates coordinates){
      List<Pair<String,Coordinates>> scope = scopeStack.peek();
      for (Pair<String,Coordinates> pair : scope) {
        if(pair.getLeft().equals(id))
          throw  new IllegalArgumentException();
      }
      scope.add(new Pair<String,Coordinates>(id,coordinates));
     }

    public Coordinates find(String id){
     ListIterator< List<Pair<String,Coordinates>>> ite =  scopeStack.listIterator(scopeStack.size());
     boolean found = false;
     Pair<String,Coordinates> pair = null;
     while(ite.hasPrevious() && ! found){
      List<Pair<String,Coordinates>> scope = ite.previous();
      Iterator<Pair<String,Coordinates>> it = scope.iterator();
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
    
    public int depth(){
        return scopeStack.size();
    }

  @Override
  @SuppressWarnings("unchecked")
  public Object clone(){
    return new Environment((Stack<List<Pair<String,Coordinates>>>)scopeStack.clone());
  }

    
}
