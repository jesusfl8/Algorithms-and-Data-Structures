package aed.treesearch;

import java.util.Iterator;
import java.util.Spliterator;

import es.upm.aedlib.Position;
import es.upm.aedlib.set.*;
import es.upm.aedlib.positionlist.*;
import es.upm.aedlib.tree.*;

public class TreeSearch {

	public static Set<Position<String>> search(Tree<String> t, PositionList<String> searchExprs) {

		Position<String> treeCursor = t.root();
		Position<String> searchCursor = searchExprs.first();
		PositionListSet<Position<String>> res = new PositionListSet<Position<String>>();
		
		if (!treeCursor.element().equals(searchCursor.element()) && !searchCursor.element().equals("*")) {
			return res;
		}
		if (searchExprs.size() == 1) {
			if ((treeCursor.element().equals(searchCursor.element()) || searchCursor.element().equals("*"))) {
				res.add(treeCursor);
			}
		} else {
			res = search(treeCursor, searchCursor, t, searchExprs, res);
		}
		return res;

	}

	private static PositionListSet<Position<String>> search(Position<String> treeCursor, Position<String> searchCursor,
			Tree<String> t, PositionList<String> searchExprs, PositionListSet<Position<String>> res) {

		Iterator<Position<String>> it = t.children(treeCursor).iterator();
		searchCursor = searchExprs.next(searchCursor);
		
		while (it.hasNext()) {
			treeCursor = it.next();

			if (treeCursor.element().equals(searchCursor.element()) || searchCursor.element().equals("*")) {
				if (searchExprs.next(searchCursor) != null) {
					search(treeCursor, searchCursor, t, searchExprs, res);
				} else {
					res.add(treeCursor);
				}
			}
		}

		return res;
	}

	public static Tree<String> constructDeterministicTree(Set<PositionList<String>> paths) {

        GeneralTree<String> res=new LinkedGeneralTree<>();

        if(paths.isEmpty()) {
            return res;
        }

        Iterator<PositionList<String>> it=paths.iterator();
        PositionList<String> currentPath = it.next();

        Position<String> currentCursor= currentPath.first();


        res.addRoot(currentPath.first().element());
       
        res=treeMaker(paths, currentPath , currentCursor, it, res);
        return res;

    }

    private static GeneralTree<String> treeMaker(Set<PositionList<String>> paths, PositionList<String> currentPath,
    	Position<String> currentCursor,Iterator<PositionList<String>> it,GeneralTree<String> res){
    	currentCursor= currentPath.first();
    	res=treeAux(currentPath, currentCursor, res);
        if(it.hasNext()) {                   	
        	currentPath=it.next();
             res=treeMaker(paths, currentPath, currentCursor, it, res);

        }
        
         return res;
        }
    private static GeneralTree<String> treeAux ( PositionList<String> currentPath, Position<String> currentCursor,
    		GeneralTree<String> res){
    	 Position<String> cursorArbol= res.root();   	
        currentCursor = currentPath.next(currentCursor);
       
        while (currentCursor!= null) {         
               if(posArbol(currentCursor, res, cursorArbol)!=null) {          
                 cursorArbol=posArbol(currentCursor, res, cursorArbol);
               }
               else {
            	   res.addChildLast(cursorArbol, currentCursor.element());
            	   cursorArbol=posArbol(currentCursor, res, cursorArbol);
               }
               currentCursor = currentPath.next(currentCursor);

             }
         return res;
    }
    private static Position<String> posArbol(Position<String> currentCursor, 
    		GeneralTree<String> res, Position<String> cursorArbol){
    	Position<String> resul=null;
    	Iterator<Position<String>> recorrido =res.children(cursorArbol).iterator();	 	 
  	  while(recorrido.hasNext()) {			  
  		  cursorArbol= recorrido.next();		  
  		  if(cursorArbol.element().equals(currentCursor.element())) {

  			 return cursorArbol;
  			 
  		  }	 	
  		  posArbol(currentCursor,res,cursorArbol);
  		    			  		  	  
  	  }
    
    	return resul;
    }
}