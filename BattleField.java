/**
  *This program is to play a game of Battleship against the computer.
  */-----------------------------------
import java.util.Scanner;
public class BattleField 
{
      private BattleShip[][] grid;//Declare a private array of BattleShip type
      private BattleShip[][] copyGrid;//Declare a private array of BattleShip type,it should be copy the grid[][] at a proper time.
      //Defines a enumerated type coordinates to limit the bound of coordinates so that Computer can use these coordinates after random a number 
      enum coordinates {A1,A2,A3,A4,A5,A6,A7,A8,
    		            B1,B2,B3,B4,B5,B6,B7,B8,
    		            C1,C2,C3,C4,C5,C6,C7,C8,
    		            D1,D2,D3,D4,D5,D6,D7,D8,
    		            E1,E2,E3,E4,E5,E6,E7,E8,
    		            F1,F2,F3,F4,F5,F6,F7,F8,
    		            G1,G2,G3,G4,G5,G6,G7,G8,
    		            H1,H2,H3,H4,H5,H6,H7,H8};
      //All the following variables should be static because they should belong to all the objects.
      //Declares the following private static instance variables to track the number of ships or grenades exist. 	            
      private static int countShip_H;
      private static int countGrenade_H;
      private static int countShip_C;
      private static int countGrenade_C;
      //Declares the following private static instance variables to track the number of hitting grenades by human or computer.
      private static int countHitGrenade_H;
      private static int countHitGrenade_C;
      boolean[] exist=new boolean[64];//Creates an array object to track if the coordinate was positioned or not.
	  boolean bool_outofbound=false;//demonstrate an boolean type variable for loop method.
      public BattleField()//Initialize 64 objects, constructor should be set as public 
      {
    	  grid=new BattleShip[8][8];
    	  for(int i=0;i<8;i++)
    	      for(int j=0;j<8;j++)
    		     grid[i][j]=new BattleShip(); 
      }           
      //output the layout of grid.The method should be public because it will be invoked in other class.
      public void showGrid()
      {
         System.out.println("  A B C D E F G H");
         for(int i=0;i<8;i++)
          {
    	    System.out.print(i+1+" ");
 	        for(int j=0;j<8;j++)
 	        {
 	        	 //If the position was not called,outputs "_" accordingly.
 	        	 if(!grid[i][j].getCalled())
    	        	System.out.print("_ ");
 	        	 //If the position was called,outputs all the elements of positions.
    	         else   	        	 
    	    	    System.out.print(grid[i][j].getElement()+" "); 	    	        
 	        }
            System.out.println();
          }
      }
      //when the game is over, output the all positions of ships and grenades.
      //the copy of grid[][] should be used.The method should be public because it will be invoked in other class.
      public void showGrid_final()
      {
         System.out.println("  A B C D E F G H");
         for(int i=0;i<8;i++)
          {
    	    System.out.print(i+1+" ");
 	        for(int j=0;j<8;j++)
 	        {
    	    	    System.out.print(copyGrid[i][j].getElement()+" "); 	    	        
 	        }
            System.out.println();
          }
      }
      //sets the position of human's ship.The method should be public because it will be invoked in other class.
      public void setShip_H()
      {
    	  System.out.println("Hi, let's play Battleship!");
    	  Scanner keyboard=new Scanner(System.in);
    	  for(int i=1;i<=6;i++)
    	  { 
    		  String upper="";
    		  //using do-while loop to generate different positions of ships.
    		  do
    		  {
    			  bool_outofbound=false;
    			  System.out.print("Enter the coordinate of your ship #"+i+": ");
    	    	  String position=keyboard.nextLine();
    	    	  upper=position.toUpperCase();   
    	    	  //if the coordinate the user inputed outside the grid bound, bool_outofbound=true,do-while loop run.
    	    	  if(!checkGridBound(upper))
   	    	        {
   	    		     System.out.println("Sorry, the coordinate outside the grid. Try again.");
   	    		     bool_outofbound=true;
   	    	        }
    	    	  //if exist[coordinate]=true, it means that the coordinate the user inputed was already used, do-while loop run.
   	    	      else if(exist[coordinates.valueOf(upper).ordinal()])
    	    		 System.out.println("Sorry, the coordinate already used. Try again.");
    		  }while(bool_outofbound||exist[coordinates.valueOf(upper).ordinal()]);
    		  exist[coordinates.valueOf(upper).ordinal()]=true;//the value of exist[coordinate] is changed to true when the coordinate was already used    		  
    		  grid[row_point(upper)][col_point(upper)].setElement("s");//if the coordinate was legible, set the positioned successfully.
    		  countShip_H++;//if the position was set successfully, track the count.
    	  }
    	  setGrenade_H();//when all the ships were set, go to set grenade of human.
      }
      //sets the position of human's grenade. Similar codes with the method setShip_H()
      //the method will just be used inside the class and is helping method,so it is private.
      private void setGrenade_H()
      {
    	  Scanner keyboard=new Scanner(System.in);
    	  for(int i=1;i<=4;i++)
    	  { 
    		  String upper="";
    		  do
    		  {
    			  bool_outofbound=false;
    			  System.out.print("Enter the coordinate of your grenade #"+i+": ");
    	    	  String position=keyboard.nextLine();
    	    	  upper=position.toUpperCase();
    	    	  if(!checkGridBound(upper))
    	    	   {
    	    		  System.out.println("Sorry, the coordinate outside the grid. Try again.");
    	    		  bool_outofbound=true;
    	    	   }
    	    	  else if(exist[coordinates.valueOf(upper).ordinal()])
     	    		 System.out.println("Sorry, the coordinate already used. Try again.");
    		  }while(bool_outofbound||exist[coordinates.valueOf(upper).ordinal()]);
    		  exist[coordinates.valueOf(upper).ordinal()]=true;
    		  grid[row_point(upper)][col_point(upper)].setElement("g");
    		  countGrenade_H++;
    	  }
    	  setShip_C();
      }
      //it is helping method in this class, it sets the position of computer's ship.
      private void setShip_C()
      {
    	  for(int i=1;i<=6;i++)
    	  { 
    		  String upper="";
    		  do
    		  {   			 
    			  //demonstrates the array of enumerated type to control the bound of elements according to the random number.
    			  coordinates[] cd=coordinates.values();
    	          int n=(int)(Math.random()*64);
    	    	  upper=cd[n].toString(); //convert the enumerated type to String type so that the value of upper can be pass to method row_point(String upper)...	    	      	    	 
    		  }while(exist[coordinates.valueOf(upper).ordinal()]);//if the random number was used, do-while loop run.
    		  //use the method of enumerated type to locate the index, and then exist[index] is changed to true,is means that the position was used.
    		  exist[coordinates.valueOf(upper).ordinal()]=true; 
    		  grid[row_point(upper)][col_point(upper)].setElement("S");//if the coordinate was legible, set the positioned successfully.
    		  countShip_C++;//if the position was set successfully, track the count.
    	  }
    	  setGrenade_C();//when all the ships were set, go to set grenade of human.
      }
      //it is helping method in this class, it sets the position of computer's grenade. Similar codes with the method setShip_C(),it is helping method.
      private void setGrenade_C()
      {
    	  for(int i=1;i<=4;i++)
    	  { 
    		  String upper="";
    		  do
    		  {
    			  coordinates[] cd=coordinates.values();
    	          int n=(int)(Math.random()*64);
    	    	  upper=cd[n].toString();   	    	  
    		  }while(exist[coordinates.valueOf(upper).ordinal()]);
    		  exist[coordinates.valueOf(upper).ordinal()]=true;
    		  grid[row_point(upper)][col_point(upper)].setElement("G");
    		  countGrenade_C++;
    	  }
    	  System.out.println();
    	  System.out.println("OK, the computer placed its ships and grenades at random. Let's play.");
    	  copyGrid=new BattleShip[8][8];
    	  for(int i=0;i<8;i++)
    	      for(int j=0;j<8;j++)
    		     copyGrid[i][j]=new BattleShip(grid[i][j]); 
    	  System.out.println();
    	  setRocket_H();
      }
      //it is helping method in this class, it checks if the user's input is out of bound or not.
      private boolean checkGridBound(String position)
      {
    	  //use static Integer method to convert string to int so that expression can compare with them.
    	  if(Integer.parseInt(position.substring(1))>8||Integer.parseInt(position.substring(1))<1||position.charAt(0)>72||position.charAt(0)<65)
    		  return false;
    	  else 
              return true;
      }
      //it is helping method in this class, it gets the int type of horizontal coordinate,so that it can be used in grid[][].
	  private int row_point(String position)
	  {
		switch(position.toUpperCase().charAt(1))
		{
		  case '1':return 0;
		  case '2':return 1;
		  case '3':return 2;
		  case '4':return 3;
		  case '5':return 4;
		  case '6':return 5;
		  case '7':return 6;
		  case '8':return 7;
		  /*just makes compiler happy.It can not happen at all because the method 
		   *checkGridBound(String ps)will be used firstly, so all the "position" will be
		   *inside the bound of grid.
		   */
		  default: return 0;
		}
	  }
	  //it is helping method in this class, it gets the int type of vertical coordinate,similar with the row_point(String position)
	  private int col_point(String position)
	  {
		switch(position.toUpperCase().charAt(0))
		{
		  case 'A':return 0;
		  case 'B':return 1;
		  case 'C':return 2;
		  case 'D':return 3;
		  case 'E':return 4;
		  case 'F':return 5;
		  case 'G':return 6;
		  case 'H':return 7;
		  default: return 0;//makes compiler happy.
		}
	  }	  
	  /**the method is to place rocket.
	   * */
	  public void setRocket_H()
	  {
		  String upper="";
		  //the do-while loop has the same logic with that in the method setShip_H();
		  do
		  {
			bool_outofbound=false;
		    System.out.print("Position of your rocket: ");
		    Scanner keyboard=new Scanner(System.in);
		    String position=keyboard.nextLine();
    	    upper=position.toUpperCase();
    	    if(!checkGridBound(upper))
    	    {
    	    	System.out.println("The coordinate you chose is outside the grid, please try again: ");
   	    		bool_outofbound=true;
    	    }
		  }while(bool_outofbound);
		  //if the position has never been called and is a human's ship,the ship sinks.
		  if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
			/*invoke mutator method to change the boolean value of the object. That means this coordinate was called.
			 * Then we can use the method showGrid() to output correct grid as requirement.
			 */			
		    grid[row_point(upper)][col_point(upper)].setCalled(true);
		    countShip_H--;//track the number of sinked ships 
		    //when countShip_H--, it is imperative to check if it is 0 or not.
		    if(countShip_H==0)
		      {
		    	/*when all the human's ships sunk,computer won and display the position of all ships and grenades.
		    	 * Then display the number of missed turns due to hitting a grenade. Finally, game is over here.
		    	 */
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
		    //if there are human's ships exist, game continues.Display the grid and computer's turn.
		    showGrid();
		    setRocket_C();
		  }
		  //if human hit a ship,but this ship was hit already,nothing happy and computer's turn.
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
		    System.out.println("Nothing.");
			showGrid();
		    setRocket_C();
		  }
		  //similar with above comments.
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countShip_C--;			    
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_C();
		  }
		  //similar with above comments.
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		    setRocket_C();
		  }
		  //similar with above comments,but if human hit a grenade,then computer shall play twice.
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_H--;
			countHitGrenade_H++;//track the number of hitting grenades.
			if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			/*it is important to use a new method setRocket_C_twice().Although the method's code is similar with the method setRocket_C(),
			 * it is totally different method. Because it can not use the method setRocket_C() to let computer to play twice, setRocket_C()
			 * just be made to run once, then is human's turn.
			 */
			setRocket_C_twice();		    
		  }
		  //the following else-if expressions list all the possibilities of each play.The same logic as above comments.
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		    setRocket_C();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
		    grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_H--;
			countHitGrenade_H++;
			if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_C_twice();			  
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		    setRocket_C();
		  }
		  //a position that has been called and did not hit anything should be displayed with the "*"
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("_"))
		  {
			System.out.println("Nothing.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			grid[row_point(upper)][col_point(upper)].setElement("*");
			showGrid();
			setRocket_C();
		  }	
		  //a position "*" that has been called should be displayed the same 
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("*"))
		  {
			System.out.println("Nothing.");
			showGrid();
			setRocket_C();
		  }	
	  }
	  //this is just helping method. The logic is the same with the method setRocket_H()
	  private void setRocket_C()
	  {
		  String upper="";
		  System.out.println("Position of my rocket: ");
		  coordinates[] cd=coordinates.values();
	      int n=(int)(Math.random()*64);
	      upper=cd[n].toString();   	    	  
	      if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
		    grid[row_point(upper)][col_point(upper)].setCalled(true);
		    countShip_H--;		        
		    if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
		    showGrid();
		    setRocket_H();
		  }
	      else if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
	    	System.out.println("Nothing.");
	    	showGrid();
		    setRocket_H();
	      }	     
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countShip_C--;			    
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_H();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
			System.out.println("Nothing.");
	    	showGrid();
		    setRocket_H();
	      }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
		    grid[row_point(upper)][col_point(upper)].setCalled(true);		    		    
			countGrenade_C--;
			countHitGrenade_C++;
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_H_twice();		    
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
			System.out.println("Nothing.");
	    	showGrid();
		    setRocket_H();
	      }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_C--;
			countHitGrenade_C++;
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_H_twice();			    
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
			System.out.println("Nothing.");
	    	showGrid();
		    setRocket_H();
	      }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("_"))
		  {
			System.out.println("Nothing.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			grid[row_point(upper)][col_point(upper)].setElement("*");
			showGrid();
			setRocket_H();
		  }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("*"))
		  {
			System.out.println("Nothing.");
			showGrid();
			setRocket_H();
		  }	
	  }
	  //it is helping method in this class for human to play twice
	  private void setRocket_H_twice()
	  {
		for(int i=0;i<2;i++)//play twice
		{
		  String upper="";
		  //do-while loop check the input string is legible or not.Finally, the legible input can will be used for the following codes.
		  do
		  {
			bool_outofbound=false;
		    System.out.print("Position of your rocket: ");
		    Scanner keyboard=new Scanner(System.in);
		    String position=keyboard.nextLine();
    	    upper=position.toUpperCase();
    	    if(!checkGridBound(upper))
    	    {
    	    	System.out.println("The coordinate you chose is outside the grid, please try again: ");
   	    		bool_outofbound=true;
    	    }
		  }while(bool_outofbound);
		  /*the following if-else expressions are similar with that in method setRocket_H().The difference is that method setRocket_C() is deleted
		   * in them(so that for loop can run twice-play twice,after that computer will play) except that human hit a grenade, if so, human will 
		   * lose the second chance and computer will play directly.
		   */
		  if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
		    grid[row_point(upper)][col_point(upper)].setCalled(true);
		    countShip_H--;		        
		    if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
		    showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countShip_C--;			    
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_H--;
			countHitGrenade_H++;
			if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();	
			setRocket_C();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_H--;
			countHitGrenade_H++;
			if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();	
			setRocket_C();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
		  {
			System.out.println("Nothing.");
			showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("_"))
		  {
			System.out.println("Nothing.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			grid[row_point(upper)][col_point(upper)].setElement("*");
			showGrid();
		  }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("*"))
		  {
			System.out.println("Nothing.");
			showGrid();
		  }	
		}
		setRocket_C();
	  }
	  //it is helping method in this class for computer to play twice, it is the same logic as method setRocket_H_twice()
	  private void setRocket_C_twice()
	  {
		for(int i=0;i<2;i++)
		{
		  String upper="";
		  System.out.println("Position of my rocket: ");
		  coordinates[] cd=coordinates.values();
	      int n=(int)(Math.random()*64);
	      upper=cd[n].toString();   	    	  
	      if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
		    grid[row_point(upper)][col_point(upper)].setCalled(true);
		    countShip_H--;		        
		    if(countShip_H==0)
		      {
		        System.out.println("I win!");
		        showGrid_final();
		        System.out.println("I missed "+countHitGrenade_C+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
		    showGrid();
		  }
	      else if(grid[row_point(upper)][col_point(upper)].getElement().equals("s")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
	    	System.out.println("Nothing.");
	    	showGrid();
	      }	     
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("ship hit.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countShip_C--;			    
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("S")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
			System.out.println("Nothing.");
	    	showGrid();
	      }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_C--;
			countHitGrenade_C++;
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_H();			    
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("g")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
			System.out.println("Nothing.");
	    	showGrid();
	      }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==false)
		  {
			System.out.println("Boom! Grenade");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			countGrenade_C--;
			countHitGrenade_C++;
			if(countShip_C==0)
		      {
		        System.out.println("You win!");
		        showGrid_final();
		        System.out.println("You missed "+countHitGrenade_H+" turns due to hitting a grenade.");
		        System.exit(0);
		      }
			showGrid();
			setRocket_H();			    
		  }
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("G")&&grid[row_point(upper)][col_point(upper)].getCalled()==true)
	      {
			System.out.println("Nothing.");
	    	showGrid();
	      }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("_"))
		  {
			System.out.println("Nothing.");
			grid[row_point(upper)][col_point(upper)].setCalled(true);
			grid[row_point(upper)][col_point(upper)].setElement("*");
			showGrid();
		  }	
		  else if(grid[row_point(upper)][col_point(upper)].getElement().equals("*"))
		  {
			System.out.println("Nothing.");
			showGrid();
		  }	
		}
		setRocket_H();
	  }
}
