//package  testProgram;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Main 
{
	public static void main (String[] args)throws 
	IllegalStateException,NullPointerException,NoSuchElementException
	{
		Queue<Integer> queue = new Queue<Integer>();
		Scanner scanner = new Scanner(System.in);
		int op,para,ret;
		boolean exit = true;
		while(exit)
		{
			System.out.print(
				"\nTest Queue\n"+
				"0 exit\t   1 add\n"+
				"2 offer\t   3 remove\n"+
				"4 poll\t   5 peek\n"+
				"6 element\n"+
				"input: "
			);
			op = scanner.nextInt();
			switch(op)
			{
				case 0:
					exit = false;
					break;
				case 1:
					para = scanner.nextInt();
					try
					{
						for(int i=0;i<para;++i)
						{
							int rand = (int)(Math.random()*100);
							queue.add(rand);
							System.out.println("i = "+i+" rand = "+rand);
						}
					}
					catch(IllegalStateException ae)
					{
						System.out.println("The queue is full.");
					}
					break;
				case 2:
					para = scanner.nextInt();
					for(int i=0;i<para;++i)
					{
						int rand = (int)(Math.random()*100);
						boolean retb = queue.offer(rand);
						if(retb==false)
						{
							System.out.println("The queue is full.");
							break;
						}
						System.out.println("i = "+i+" rand = "+rand);
					}
					break;
				case 3:
					para = scanner.nextInt();
					try
					{
						for(int i=0;i<para;++i)
						{
							ret = queue.remove();
							System.out.println("i = "+i+" ret = "+ret);
						}
					}
					catch(NoSuchElementException ae)
					{
						System.out.println("The queue is empty.");
					}
					break;
				case 4:
					para = scanner.nextInt();
					try
					{
						for(int i=0;i<para;++i)
						{
							ret = queue.poll();
							System.out.println("i = "+i+" ret = "+ret);
						}
					}
					catch(NullPointerException ae)
					{
						System.out.println("The queue is empty.");
					}
					break;
				case 5:
					try
					{
						ret = queue.peek();
						System.out.println(" ret = "+ret);
					}
					catch(NullPointerException ae)
					{
						System.out.println("The queue is empty.");
					}
					break;
				case 6:
					try
					{
						System.out.println("ret = "+queue.element());
					}
					catch(NoSuchElementException ae)
					{
						System.out.println("The queue is empty.");
					}
					break;
			}
		}
		scanner.close();
	}
}
