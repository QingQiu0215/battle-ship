public class BattleShipDriver 
{
	public static void main(String[] args) 
	{
		BattleField a=new BattleField();//Creates an object BattleField and assign its address to "a".
		a.showGrid();//Invokes method showGrid() to show the grid firstly.
		a.setShip_H();//Invokes method setShip_H() to start the game.
	}
}
