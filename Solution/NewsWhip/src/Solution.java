import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.CSVWriter;
public class Solution
{
	public static String data[][] = new String[1000][2];
	public static int count;
	public static int sizeofarray;
	public static int validvalue;
	
	public Solution()
	{
		count =0;
		sizeofarray =1;
		validvalue =0;
	}
	
	public static void exportDataToExcel(String fileName) throws FileNotFoundException, IOException
    {
        File file = new File(fileName);
        if (!file.isFile())
            file.createNewFile();

        CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        
        String domain[]= new String[count];
        String U[] = new String[count];
        int score[] = new int[count];
        int urls[]= new int[count];
        int stat[] = new int[count];
        int c =0;
        //System.out.println("Size of array: "+sizeofarray);
		//System.out.println(data[0][0]);
        for (int i = 0; i < count; i++)
        {
        	if(!(data[i][0].equals(null)))
        	{
        		String d[] = data[i][0].trim().split("\\//");
    			String d2[] = d[1].trim().split("\\/");
    			String d3[] = d2[0].trim().split("\\.");
    			domain[c] = d3[1]+"."+d3[2];
    			U[c] = data[i][0];
    			score[c] = Integer.parseInt(data[i][1]);
    			c++;
        	}
        }
        Map<String, String> map = new HashMap<String, String>();
        
        for(int i=0;i<count;i++)
        {
        	String value = "0"+" 0";
        	map.put(domain[i],value);
        }
        
        for(int i =0;i<domain.length;i++)
        {
        	for(Map.Entry<String,String> entry : map.entrySet())
        	{
        		//System.out.println("Key :"+entry.getKey()+" value: "+entry.getValue());
        		if(entry.getKey().equals(domain[i]))
        		{
        			String val = entry.getValue();
        			String v[] = val.split(" ");
        			System.out.println("value:"+val);
        			int urlcount = Integer.parseInt(v[0]);
        			urlcount = urlcount+1;
        			int fscore = Integer.parseInt(v[1]);
        			fscore = fscore+score[i];
        			String newval = urlcount+" "+fscore;
        		
        			map.replace(domain[i], newval);
        		}
        	}
        }
        //int rowCount = map.size();
        //int columnCount =3;
        String header[]= {"Domains", "URLs", "Social_Score"};
        csvWriter.writeNext(header);
        for (Map.Entry<String,String> entry : map.entrySet())
        {
        	String key = entry.getKey();
        	String value = entry.getValue();
        	String v[] = value.split(" ");
            String[] values = new String[3];
            values[0] = key+" ";
            values[1] = v[0]+" ";
            values[2] = v[1]+" ";
          // String values = key+","+v[0]+","+v[1];
            csvWriter.writeNext(values);
        }

        csvWriter.flush();
        csvWriter.close();
    }
	
	public int remove(String url)
	{
		int flag =0;
		for(int i =0; i<count; i++)
		{
			if(data[i][0].equals(url))
			{
				data[i][0] = null;
				data[i][1] =null;
				flag =1;
				break;
			}
		}
		return flag;
		//System.out.println("Sucessfully removed");
	}
	public void add(String url, String score)
	{
		
		
		data[count][0]= url;
		data[count][1] = score;
		count++;
		sizeofarray++;
		validvalue++;
		//System.out.println(data[count-1][0]+" "+data[count-1][1]);
		
	}
	public String userInput(int ch)throws IOException
	{
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		if(ch == 1)
		{
			System.out.println("Enter the url and score");
			String line = br.readLine();
			return line;
		}
		else if(ch == 2)
		{
			System.out.println("Enter the url");
			String line = br.readLine();
			return line;
		}
		else
		{
			return null;
		}
		
	}
	public int menu()throws IOException
	{
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Please enter your choice");
		System.out.println("1- ADD a URL");
		System.out.println("2- REMOVE a URL");
		System.out.println("3- EXPORT statistics");
		int ch = Integer.parseInt(br.readLine());
		return ch;
	}
	public static void main (String args[])throws IOException
	{
		
		Solution g = new Solution();
		
		int ch = g.menu();
		g.add("http://www.rte.ie/news/politics/2018/1004/1001034-cso/", "20");
		g.add("https://www.rte.ie/news/ulster/2018/1004/1000952-moanghan-mine/", "30");
		g.add("http://www.bbc.com/news/world-europe-45746837", "10");
		count =3;
		if(ch == 1)
		{
			String line = g.userInput(ch);
			String us[] = line.trim().split("\\s+");
			String url = us[0];
			String score = us[1];
			g.add(url, score);
		}
		else if(ch ==2)
		{
			
			String url = g.userInput(ch);
			int f = g.remove(url);
			if(f ==0 )
			{
				System.out.println("No such URL present in the database");
			}
		}
		else if(ch ==3)
		{
			//String filename = "Statistical";
			exportDataToExcel("D:/Stats.csv");
		}
		else
		{
			System.out.println("Wrong choice entered");
		}
	}
}
