import java.io.*;
import java.net.URL;
public class CostEstimate
{
  public void estimate(int source,int destination,int avg_speed,int time_id)
     {
		 //System.out.println(source+" "+destination+" "+avg_speed+" "+time_id);
		 int num_nodes = 29;
		 int num_roads = 40;
		 int num_timeid = 36;
         int[][] road_id = new int[num_nodes][num_nodes];
         float[][] road_cost = new float[num_nodes][num_nodes];
		 int[][] road_flag = new int[num_nodes][num_nodes];
		 int[][] road_distance = new int[num_nodes][num_nodes];
		 int[][] road_medium = new int[num_nodes][num_nodes];
		 //float[][] road_delay = new float[num_roads][num_timeid];
		 float[][] road_delay = new float[100][100];

         int i,j,x,y,id,distance;
         float cost;
         float avgspeed = (float) 0.5;
                 
         String str;
         
		 try
         {
		     
             URL input_delay = new URL("http://localhost/btp/delay_input.txt"); //fetches the delay data from the server
             BufferedReader in = new BufferedReader(new InputStreamReader(input_delay.openStream()));
			 i=1;
             while((str = in.readLine())!= null)
             {
                 String[] numdelay = str.split(" ");
				 for(int r=0; r<num_timeid; r++)
				 {
					road_delay[i][r+1] = Float.parseFloat(numdelay[r]); 
				 }
 				 i++;
             }
             in.close();
         }
         catch (IOException | NumberFormatException e)
         {
		     System.err.println("Error in Reading: 11 *" + e.getMessage());
	     }
		 
		 
		 try
         {
		     URL input_area = new URL("http://localhost/btp/road_map.txt"); //fetches the road map information from server
             BufferedReader in = new BufferedReader(new InputStreamReader(input_area.openStream()));
             
			 for(int k=0;k<num_nodes;k++ )
             {
                 for(int p=0;p<num_nodes;p++)
                 {
                     if(k==p)
                     {
                         road_flag[k][p]=0;
                         road_cost[k][p]=0;
                         continue;
                     }
                     road_flag[k][p]=-1;
                     road_cost[k][p]=100000;
		             road_medium[k][p]=0;
                 }

             }


             while((str = in.readLine())!= null)
             {
                 String[] num = str.split(" ");
                 id = Integer.parseInt(num[0]);
                 x = Integer.parseInt(num[1]);
                 y = Integer.parseInt(num[2]);
                 distance = Integer.parseInt(num[3]);
		         cost = road_delay[id][time_id];
                 road_id[x][y] = id;
		         road_distance[x][y] = distance; 
                 road_cost[x][y] = (float)Math.round((cost+(distance/avgspeed))*100.0)/100.0f;
                 road_flag[x][y] = 1;
             }
             in.close();
         }
         catch (IOException | NumberFormatException e)
         {
		     System.err.println("Error in Reading: 12 *" + e.getMessage());
	     }
		 
		 
		 
         for(int vk=1;vk<num_nodes;vk++)
         {
             for(int vi=1;vi<num_nodes;vi++)
             {
                 for(int vj=1;vj<num_nodes;vj++)
                 {
                     if(road_cost[vi][vj]>road_cost[vi][vk]+road_cost[vk][vj])
					     {
						     road_cost[vi][vj]=road_cost[vi][vk]+road_cost[vk][vj];
							 road_medium[vi][vj]=vk;
						 }
                 }
             }
         }
	    
	    int snode= source;
		int dnode = destination;	
		int med = road_medium[snode][dnode];	
		
	 try
     {
		FileWriter fwstreamd = new FileWriter("desiredpath.txt"); //gives the better possible path for the user
                BufferedWriter outd = new BufferedWriter(fwstreamd);
		int lt;	
		String stpath ="";
		while(med>0)
		{
			stpath += (dnode+" (");
			if(road_cost[med][dnode]>=1000)
			stpath += " NO PATH EXISTS ";
			else
			stpath += (road_cost[med][dnode]+".min) ");
			dnode = med; 
			if(road_medium[snode][dnode]==0) break;
			med=road_medium[snode][dnode];	
		}
		stpath += (dnode+" (");
		
		if(road_cost[snode][dnode]>=1000)
			stpath += "NO-PATH-EXISTS) ";
		else
			stpath += (road_cost[snode][dnode]+".min) ");
		
		stpath += snode; 
		
		//System.out.println(stpath);
		String[] spath = stpath.split(" ");
		lt = spath.length;
		while(lt>0)
		{
			lt--;outd.write(spath[lt]+" "); 
		}
		outd.close();
      }
      
	  catch (Exception e)
      {
		     System.err.println("Error in Writing: 13 *" + e.getMessage());
	  }
	

     }
}