import java.io.*;
import java.util.Scanner;
public class Percent_Init {
public static void main(String args[])throws IOException
{
	
	double per_init_combmnz=calcPercentInit("/home/kunal/Desktop/terrier-3.6/var/results/combMNZ_eachqry.eval");
	double per_init_interpolation=calcPercentInit("/home/kunal/Desktop/terrier-3.6/var/results/interpol_eachqry.eval");
	double per_init_rerank=calcPercentInit("/home/kunal/Desktop/terrier-3.6/var/results/rerank_eachqry.eval");
	double per_init_qexp=calcPercentInit("/home/kunal/Desktop/terrier-3.6/var/results/expanded_eachqry.eval");
	System.out.println("Combmnz score="+per_init_combmnz+" Q_expanded="+per_init_qexp);
	System.out.println("Interpolation score="+per_init_interpolation+" Q_expanded="+per_init_qexp);
	System.out.println("Rerank score="+per_init_rerank+" Q_expanded="+per_init_qexp);	
}
public static double calcPercentInit(String path)throws IOException
{
	String init_path="/home/kunal/Desktop/terrier-3.6/var/results/init_eachqry.eval";
	int score_down=0;
	Scanner s1=new Scanner(new FileReader(init_path));
	Scanner s2=new Scanner(new FileReader(path));
	int total_Queries=0;
	while(s1.hasNextLine()&&s2.hasNextLine())
	{
		total_Queries++;
		String temp1[]=s1.nextLine().split(" ");
		String temp2[]=s2.nextLine().split(" ");		
		if(Double.parseDouble(temp1[1])>Double.parseDouble(temp2[1]))
			score_down++;				
	}
	return (double)(score_down/(double)total_Queries)*100;
}
}
