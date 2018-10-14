import java.util.Stack;
import java.util.NoSuchElementException;
public class Queue<E> extends Stack<E>
{
    /**
	 * 自定义的泛型队列
	 */
	private static final long serialVersionUID = -535350814358605791L;
	public final int dump = 10;
    private  Stack<E> stk;
    public Queue( )
    {
    	stk = new Stack<E>();
    }
    /**
     *增加一个元索 
     *如果队列已满，则抛出一个IllegalStateException异常
     */
    public boolean add(E e) throws IllegalStateException
    {
    	if(stk.size()<dump)
    	{
    		stk.push(e);
    		return true;
    	}
    	else if(super.empty())
    	{
    		for(;stk.empty()==false;)
    		{
    			super.push(stk.pop());
    		}
    		stk.push(e);
    		return true;
    	}
    	else
    	{
    		throw new IllegalStateException();
    	}
    }
  /**
   *  添加一个元素并返回true       
   *  如果队列已满，则返回false
   */
    public boolean offer(E e)
    {
    	if(stk.size()<dump)
    	{
    		stk.push(e);
    		return true;
    	}
    	else if(super.empty())
    	{
    		for(;stk.empty()==false;)
    		{
    			super.push(stk.pop());
    		}
    		stk.push(e);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    /**
     *  移除并返回队列头部的元素   
     *  如果队列为空，则抛出一个NoSuchElementException异常
     */
    public E remove( ) throws NoSuchElementException 
    {
    	E ret;
    	if(super.empty()==false)
    	{
    		ret = super.pop();
    	}
    	else if(stk.empty()==false)
    	{
    		for(;stk.empty()==false;)
    		{
    			super.push(stk.pop());
    		}
    		ret = super.pop();
    	}
    	else
    	{
    		throw new NoSuchElementException();
    	}
    	return ret;
    }
    /**
     *    移除并返问队列头部的元素    
     *    如果队列为空，则返回null
     */
    public E poll( ) 
    {
    	E ret;
    	if(super.empty()==false)
    	{
    		ret = super.pop();
    	}
    	else if(stk.empty()==false)
    	{
    		for(;stk.empty()==false;)
    		{
    			super.push(stk.pop());
    		}
    		ret = super.pop();
    	}
    	else
    	{
    		return null;
    	}
    	return ret;
    }
    /**
     * 返回队列头部的元素             
     * 如果队列为空，则返回null
     */
    public E peek ( ) 
    {
    	if(super.empty()==false)
    	{
    		return super.lastElement();
    	}
    	else if(stk.empty()==false)
    	{
    		return stk.firstElement();
    	}
    	return null;
    }
    /**
     *   返回队列头部的元素            
     *    如果队列为空，则抛出一个NoSuchElementException异常
     */
    public E element( ) throws NoSuchElementException 
    {
    	if(super.empty()==false)
    	{
    		return super.lastElement();
    	}
    	else if(stk.empty()==false)
    	{
    		return stk.elementAt(0);
    	}
    	else
    	{
    		throw new NoSuchElementException();
    	}
    }
}
