package database;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ConvertBytes
{
	public static byte[] toByteArray(Object o)
	{
		byte[] myBytes = null;
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);   
			out.writeObject(o);
			myBytes = bos.toByteArray(); 

			out.close();
			bos.close();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myBytes;
		
	}
	public static Object getObjectFromBytes(byte[] bytes)
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in;
		Object o = null;
		try
		{
			in = new ObjectInputStream(bis);
			o = in.readObject();

			bis.close();
			in.close();
			
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}
}
