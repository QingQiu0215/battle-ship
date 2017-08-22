public class BattleShip 
{	
  //Declare the following instance variables
  private String element;
  private boolean called;
  
  public BattleShip()//Initialize all the instance variables
  {
	  element="_";
	  called=false;
  }
  //Initialize all the instance variables with supplied values accordingly
  public BattleShip(String newElement,boolean newCalled)
  {
	  element=newElement;
	  called=newCalled;
  }
  /**All the value of instance variable will be changed in the game.However, 
   * after positioning all the ships and grenades,their values should be recorded
   * so that they can be output when the game is over as per requirement.
   */
  public BattleShip(BattleShip other)
  {
	  element=other.element;
	  called=other.called;
  }
  //The following get methods return the values accordingly.
  public String getElement()
  {
	  return element;
  }
  public boolean getCalled()
  {
	  return called;
  }
  //The following set methods mutator the values accordingly.
  public void setElement(String newElement)
  {
	  element=newElement;
  }
  public void setCalled(boolean newCalled)
  {
	  called=newCalled;
  }
}
