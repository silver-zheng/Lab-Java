import java.util.Stack;
import java.util.NoSuchElementException;
public class Queue<E> extends Stack<E>
{
    /**
	 * �Զ���ķ��Ͷ���
	 */
	private static final long serialVersionUID = -535350814358605791L;
	public final int dump = 10;
    private  Stack<E> stk;
    public Queue( )
    {
    	stk = new Stack<E>();
    }
    /**
     *����һ��Ԫ�� 
     *����������������׳�һ��IllegalStateException�쳣
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
   *  ���һ��Ԫ�ز�����true       
   *  ��������������򷵻�false
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
     *  �Ƴ������ض���ͷ����Ԫ��   
     *  �������Ϊ�գ����׳�һ��NoSuchElementException�쳣
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
     *    �Ƴ������ʶ���ͷ����Ԫ��    
     *    �������Ϊ�գ��򷵻�null
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
     * ���ض���ͷ����Ԫ��             
     * �������Ϊ�գ��򷵻�null
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
     *   ���ض���ͷ����Ԫ��            
     *    �������Ϊ�գ����׳�һ��NoSuchElementException�쳣
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
