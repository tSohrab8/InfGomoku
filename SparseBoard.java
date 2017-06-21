import java.util.*;

// An implementation of an ExpandableBoard intended to favor reduced
// memory over speed of operations.  Most operations require O(E) time
// to complete where E is the number of non-fill elements that have
// been set on the board.  Internally, elements are stored in several
// lists sorted in different orders to facilitate the calculation of
// the longest sequence.
// 
//  Space Complexity: O(E)
//  E: The number of elements that have been set on the board
public class SparseBoard<T> implements ExpandableBoard<T>{
  
  T fillElem;                                                                        
  int minRow, maxRow, minCol, maxCol;
  AdditiveList<RowColElem<T>> board = new AdditiveList<RowColElem<T>>();
  
  
  /* Workhorse constructor. Since our board only tracks set elements, creating
   * an empty board is a simple matter of storing our min/max row/col, along with
   * the fill element, in the object's fields. This gives us a runtime of O(1) as there are
   * only 5 operations every single time this constructor is run. */
  
  public SparseBoard(int minRow, int maxRow, int minCol, int maxCol, T fillElem){
    this.minRow = minRow;
    this.maxRow = maxRow;
    this.minCol = minCol;
    this.maxCol = maxCol;
    setFillElem(fillElem);
  }
  
  // Convenience 1-arg constructor, creates a single cell board with
  // given fill element. The initial extent of the board is a single
  // element at 0,0.
  
  public SparseBoard(T fillElem){
    this(0,0,0,0,fillElem);
  }
  
  // Convenience 2-arg constructor, creates a board with given fill
  // element and copies elements from T 2-D array. Assumes upper left
  // is coordinate 0,0 and lower right is size of 2-D array.  The
  // board should not have any undo/redo history but should have a
  // longest sequence calculated from the contents of 2-D array.
  //
  // There is no target complexity for this method. Use of repeated
  // set() calls is suggested to simplify it.
  public SparseBoard(T[][] x, T fillElem){
    
    this(0,x.length - 1, 0, x[0].length - 1, fillElem);     // Call workhorse constructor to create proper sized board
    
    for (int i = 0; i < x.length; i++){
      for (int n = 0; n < x[i].length; n++){                // Loop through 2d board and call set method for each
        this.set(i,n,x[i][n]);                              // cell, set method will handle keeping track of the
      }                                                     // longest sequence as well as identifying fill element.
    }
  }

  // The following 5 methods simply return the respective internal field's value, they are all O(1) complexity 
  // as they are just one operation every time.  
  
  public int getMinRow(){
    return this.minRow;
  }
  
  public int getMaxRow(){
    return this.maxRow;
  }
  
  public int getMinCol(){
    return this.minCol;
  }
  
  public int getMaxCol(){
    return this.maxCol;
  }
  
  public T getFillElem(){
    return this.fillElem;
  }
  
  // Change the fill element for the board. To make this efficient,
  // only change an internal field which dictates what should be
  // returned when an element that has not been explicitly set is
  // requested via a call to get().
  //
  // Target complexity: O(1) (worst-case)
  public void setFillElem(T f){
    
    if (f == null)
      throw new RuntimeException("Cannot set elements to null");
    this.fillElem = f;
    
  }
  
  // Retrieve the longest sequence present on the board. If there is a
  // tie, the earliest longest sequence to appear on the board should
  // be returned.  The list returned should be independent of any
  // internal board data structures so that the list can be changed
  // and not affect the board.  This implies a copy of any internal
  // board lists should be made and returned.  The longest sequence on
  // a board that is filled with only the fill element is the empty
  // list [].
  //
  // Target Complexity: O(L) (worst case)
  // L: length of the longest sequence
  public List< RowColElem<T> > getLongestSequence(){
    return null;
  }
  
  // Retrieve an element at virtual row/col specified. Any row/col may
  // be requested. If it is beyond the extent of the board determined
  // by min/max row/col, the fill element is returned.  If the element
  // has not been explicitly set, the fill element is returned.
  // 
  // Complexity: O(E)
  //  E: The number of elements that have been set on the board
  public T get(int row, int col){
    
    if (row < minRow || row > maxRow || col < minCol || col > maxCol)
      return fillElem;
    
    ListIterator<RowColElem<T>> iter = board.listIterator();

    while(iter.hasNext()){
      RowColElem<T> piece = iter.next();
      if (piece.getRow() == row && piece.getCol() == col)
        return piece.getElem();
    }
    
    return fillElem; 
    
  }
  
  // Update internals to reflect an increase in the board extents by
  // one row on the bottom.  This method should not change the memory
  // footprint of the SparseBoard.
  // 
  // Target Complexity: O(1) (worst-case)
  public void addRowBottom(){
    this.maxRow += 1;
  }
  
  // Update internals to reflect an increase in the board extents by
  // one column on the right.  This method should not change the
  // memory footprint of the SparseBoard.
  //
  // Target Complexity: O(1) (worst-case)
  public void addColRight(){
    this.maxCol += 1;
  }
  
  // Perform expansion for the board. Adjust any internal fields so
  // that the board tells the world it is large enough to include the
  // (row,col) position specified.  No new memory should be allocated.
  // Always return 0.
  //
  // Target Complexity: O(1) (worst-case)
  public int expandToInclude(int row, int col){
    
    if (row < minRow)
      minRow = row;
    else if (row > maxRow)
      maxRow = row;
    
    if (col < minCol)
      minCol = col;
    else if (col > maxCol)
      maxCol = col;
      
    return 0;
  }
  
  // Set element at row/col position to be x. Update internals to
  // reflect that the set may have created a new longest sequence.
  // Also update internals to allow undoSet() to be used and disable
  // redoSet() until a set has been undone.  Once an element is set,
  // it cannot be set again; attempts to do so raise a runtime
  // exception with the message: "Element 4 -2 already set to XX"
  // where the row/col indices and string representation of the
  // element are adjusted to match the call made.  Setting an element
  // to the fill element of board has no effect on the board.  It is
  // not allowed to set elements of the board to be null. Attempting
  // to do so will generate a RuntimeException with the message
  // "Cannot set elements to null"
  //
  // Target Complexity: O(E)
  //  E: The number of elements that have been set on the board
  public void set(int row, int col, T x){
  
  
  }
  
  // Produce copies of the internal lists of the explicitly set
  // elements on the board.  Only elements that have been explictily
  // set should be included. Each method produces a list that is
  // sorted in an order dictated by the method name. The returned
  // lists should be copies so that subsequenent modification to the
  // lists does not affect the board.
  //
  // Target Complexity: O(E) (worst-case)
  public List<RowColElem<T>> elementsInRowColOrder(){
  
    ListIterator<RowColElem<T>> iter = board.listIterator();
    
    while (iter.hasNext()){
      RowColElem<T> piece = iter.next();
      
      
    return null;
  
  }
  
  public List<RowColElem<T>> elementsInColRowOrder(){return null;}
  
  public List<RowColElem<T>> elementsInDiagRowOrder(){return null;}
  
  public List<RowColElem<T>> elementsInADiagReverseRowOrder(){return null;}
  
  // Undo an explicit set(row,col,x) operation by changing an element
  // to its previous state.  Repeated calls to undoSet() can be made
  // to restore the board to an earlier state.  Each call to undoSet()
  // enables a call to redoSet() to be made to move forward in the
  // history of the board state. Calls to undoSet() do not change the
  // extent of boards: they do not shrink to a smaller size once grown
  // even after an undo call.  If there are no sets to undo, this
  // method throws a runtime exception with the message
  // "Undo history is empty"
  //
  // Target Complexity: O(1) (worst case)
  public void undoSet(){}
  
  // Redo a set that was undone via undoSet().  Every call to
  // undoSet() moves backward in the history of the board state and
  // enables a corresponding call to redoSet() which will move forward
  // in the history.  At any point, a call to set(row,col,x) will
  // erase 'future' history that can be redone via redoSet().  If
  // there are no moves that can be redone because of a call to set()
  // or undoSet() has not been called, this method generates a
  // RuntimeException with the message "Redo history is empty".
  //
  // Target Complexity: O(1)
  public void redoSet(){}
  
  // toString() - create a pretty representation of board.
  //
  // Examples:
  //   |  1|  2|  3|
  //   +---+---+---+
  // 1 |   |   |   |
  //   +---+---+---+
  // 2 |   |   |   |
  //   +---+---+---+
  // 3 |   |   |   |
  //   +---+---+---+
  //
  //    | -4| -3| -2| -1|  0|  1|  2|  3|
  //    +---+---+---+---+---+---+---+---+
  // -2 |  A|   |   |   |   |   |   |   |
  //    +---+---+---+---+---+---+---+---+
  // -1 |   |   |  B|   |   |   |   |   |
  //    +---+---+---+---+---+---+---+---+
  //  0 |   |   |   |   |   |   |   |   |
  //    +---+---+---+---+---+---+---+---+
  //  1 |   |   |   |   |   |  A|   |   |
  //    +---+---+---+---+---+---+---+---+
  //  2 |   |   |   |   |   |   |   |   |
  //    +---+---+---+---+---+---+---+---+
  //  3 |   |   |   |   |   |   |   |   |
  //    +---+---+---+---+---+---+---+---+
  //
  // Target Complexity: O(R*C)
  //   R: number of rows
  //   C: number of columns
  // 
  // Constraint: No array or arraylist allocation is allowed in this
  // method.
  // 
  // Note: to adhere to target runtime complexity employ a
  // StringBuilder and an iterator over an internal list of the boards
  // set elements.
  public String toString(){return null;}
  
}