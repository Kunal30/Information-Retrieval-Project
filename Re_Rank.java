import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class Re_Rank {
	static double score_init_sum[]=new double[101];
	static double PF_score_sum[]=new double[101];
	
	public static void main(String args[])throws IOException
{
		HashMap map_init[]=new HashMap[101];
		HashMap map_PF[]=new HashMap[101];
		HashMap map_re_rank[]=new HashMap[101];
		initializeValues(map_init,map_PF);
		computere_rankscore(map_re_rank,map_init,map_PF);
		createRankedList(map_re_rank);
		
}
	public static void createRankedList(HashMap [] map_re_rank)throws IOException
	{
		FileOutputStream fop = null;
		File file;
		file = new File("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_re_rankresult_ZF1.res");
		fop = new FileOutputStream(file);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		for(int i=51;i<101;i++)
		{
			ValueComparator bvc = new ValueComparator(map_re_rank[i]);
	        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
	        sorted_map.putAll(map_re_rank[i]);
	        int j=0;
	        for(Entry<String, Double> entry : sorted_map.entrySet()) {
	        	  String key = entry.getKey();
	        	  Double value = entry.getValue();
	        	  String str=(i+" Q0 "+key+" "+j+" "+(value)+" In_expC2"+"\n");
	        	  byte[] contentInBytes = str.getBytes();
	  			  fop.write(contentInBytes);	
	        	  j++;	        	  
	        	}
		}
		fop.close();
		
	}
	public static void computere_rankscore(HashMap []map_re_rank,HashMap []map_init,HashMap []map_PF)
	{
		
		for(int i=51;i<101;i++)
		{
			map_re_rank[i]=new HashMap();
			Set set = map_init[i].entrySet();
		      
		      // Get an iterator
		      Iterator it = set.iterator();
		      
		      // Display elements
		      while(it.hasNext()) {
		         Map.Entry me = (Map.Entry)it.next();
		         
		         if(map_PF[i].containsKey(me.getKey())) // for documents common in init and PF
		         {
		        	 double score=(((double)me.getValue()));//+((double)map_PF[i].get(me.getKey())/PF_score_sum[i]));
		        	 map_re_rank[i].put(me.getKey(),score);
		         }
		         
		      }
		      
		      //for documents exclusively in PF
		      Set set2 = map_PF[i].entrySet();
		      Iterator it2 = set2.iterator();
		      
		     
		        while(it2.hasNext()) {            //for documents exclusively in PF
		          	 Map.Entry me = (Map.Entry)it2.next();
		          	 if(!map_init[i].containsKey(me.getKey()))
		          	 {
		        	 double score=0;//((double)me.getValue()/PF_score_sum[i]);
		        	 map_re_rank[i].put(me.getKey(),score);
		          	 }
		      }
		}
	}
	public static void initializeValues(HashMap []map_init,HashMap []map_PF)throws IOException
	{
		
		Scanner in1 = new Scanner(new FileReader("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_ZF1.res"));
		Scanner in2 = new Scanner(new FileReader("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_expanded_ZF1.res"));
		  for(int i=51;i<101;i++)
		  { map_init[i]=new HashMap();
			  for(int j=0;j<1000;j++)
			  {
				  String temp[]= in1.nextLine().split(" ");
				  double score=Double.parseDouble(temp[4]);
				  map_init[i].put(temp[2], score);
				  score_init_sum[i]+=score;			
			  }
		  }
		  for(int i=51;i<101;i++)
		  {
			  map_PF[i]=new HashMap();
			  for(int j=0;j<1000;j++)
			  {
				  String temp[]= in2.nextLine().split(" ");
				  double score=Double.parseDouble(temp[4]);
				  map_PF[i].put(temp[2],score);
				  PF_score_sum[i]+=score;
			  }
		  }  	
	}
public static void initializeValues(double score_init_sum[],double PF_score_sum[],double [][]Score_init,double [][]PF_Score,String [][]D_init,String [][]D_PF)throws IOException
{
  Scanner in1 = new Scanner(new FileReader("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result1.res"));
  Scanner in2 = new Scanner(new FileReader("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_expanded1.res"));
  for(int i=51;i<101;i++)
  {
	  for(int j=0;j<1000;j++)
	  {
		  String temp[]= in1.nextLine().split(" ");
		  double score=Double.parseDouble(temp[4]);
		  Score_init[i][j]=score;
		  D_init[i][j]=temp[2];
		  score_init_sum[i]+=score;	
	  }
  }
  for(int i=51;i<101;i++)
  {
	  for(int j=0;j<1000;j++)
	  {
		  String temp[]= in2.nextLine().split(" ");
		  double score=Double.parseDouble(temp[4]);
		  PF_Score[i][j]=score;
		  D_PF[i][j]=temp[2];
		  PF_score_sum[i]+=score;
	  }
  }  	
}
}
