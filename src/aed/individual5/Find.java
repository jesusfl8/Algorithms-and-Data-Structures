package aed.individual5;

import es.upm.aedlib.tree.Tree;

import java.util.Iterator;

import es.upm.aedlib.Position;


public class Find {

  /**
   * Busca ficheros con nombre igual que fileName dentro el arbol directory,
   * y devuelve un PositionList con el nombre completo de los ficheros
   * (incluyendo el camino).
   */
  public static void find(String fileName, Tree<String> directory) {  
    // Cambio el cuerpo de este metodo

	  Position<String> cursor=directory.root();
	  String currentPath="/"+cursor.element();
	  findInPos(cursor,currentPath,fileName,directory);
  }
  
  private static void findInPos(Position<String> cursor, String currentPath, String fileName, Tree<String> directory) {
	
	  Iterator<Position<String>> it=directory.children(cursor).iterator();
	  
	  while(it.hasNext()) {
		  cursor=it.next();
		  if(cursor.element().equals(fileName)) {
			  currentPath=currentPath+"/"+fileName;
			  Printer.println(currentPath);
		  }
		  findInPos(cursor,currentPath+"/"+cursor.element(),fileName,directory);
	  }
	
	  
  }
}
