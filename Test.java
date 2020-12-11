import java.util.*;
import java.time.*;
import java.io.*;
class Myexception extends Exception
{
	String str;
	Myexception(String str)
	{
		this.str=str;
	}
	public String toString()
	{
		return (str);
	}	
}
abstract class VehicleType
{
	private String vehicle_type;
	VehicleType()
	{}
	VehicleType(String vehicle_type)
	{
		this.vehicle_type=vehicle_type;
	}
	String get()
	{
		return vehicle_type;
	}
	void set()
	{
		try{
		Scanner scnr=new Scanner(System.in);
		System.out.println("Enter Vehicle Type:");
		vehicle_type=scnr.nextLine();

		if(!vehicle_type.equals("car") && !vehicle_type.equals("CAR") && !vehicle_type.equals("bus") && !vehicle_type.equals("BUS")&& !vehicle_type.equals("scooter") && !vehicle_type.equals("aeroplane") && !vehicle_type.equals("AEROPLANE") && !vehicle_type.equals("auto") && !vehicle_type.equals("AUTO") && !vehicle_type.equals("motorbike") && !vehicle_type.equals("MOTORBIKE"))
		{
			throw new Myexception("Invalid vehicle type\n");
		}
		}
		catch(Myexception excep)
		{
			System.out.println("Exception: "+excep);
			System.exit(0);
		}

	}
}
interface Color
{
	default public String getColor()
	{
		return "white";
	}
}
interface MfgDate
{
	LocalDate getMfgDate();
}
class Vehicle extends VehicleType implements Serializable,Color,MfgDate
{
	private LocalDate mfg_date;
	Vehicle(){}
	Vehicle(LocalDate date)
	{
		mfg_date=date;	
	}
	void setMfgDate()
	{
		try{
		Scanner scnr=new Scanner(System.in);
		System.out.println("Enter Manufacturing Date:");
		String date=scnr.nextLine();
		mfg_date=LocalDate.parse(date);
		if(mfg_date.getYear()>2020) throw new Myexception("Date has not completed or reached");
		}
		catch(Myexception excep)
		{
			System.out.println("Exception:"+excep);
		}
	}
	public LocalDate getMfgDate()
	{
		return mfg_date;
	}
	
}
public class Test
{
	static void serializeVehicles(ArrayList<Vehicle> v)throws IOException
	{
		FileOutputStream fout=null;
		ObjectOutputStream oos=null;
		try
		{
			fout=new FileOutputStream("Records.txt",true);
			oos=new ObjectOutputStream(fout);
			for(int i=0;i<v.size();i++) oos.writeObject(v.get(i));
		}
		catch(IOException e)
		{
			System.out.println("Error while writing object....");
		}
		finally
		{
			if(oos!=null) oos.close();
			if(fout!=null) fout.close();
		}
	}
	static void deserializeVehicles(int n)throws IOException
	{
		FileInputStream fin=null;
		ObjectInputStream ois=null;
		try
		{
			fin=new FileInputStream("Records.txt");
			ois=new ObjectInputStream(fin);
			int count=0;
			for(int i=0;i<n;i++)
			{
				Vehicle v=(Vehicle)ois.readObject();
				LocalDate d=v.getMfgDate();
				if(d.getYear()>2018)
				{
					count++;
					System.out.println("Vechile:"+count);
					System.out.println("Vehicle Manufacturing Date:"+v.getMfgDate());
					System.out.println("Vehicle Color:"+v.getColor());
				}
			}
			if(count==0)
			System.out.println("No Vehicle has manufacturing date >2018");

		}
		catch(Exception e)
		{
			System.out.println("Error while writing object....");
		}
		finally
		{
			if(ois!=null) ois.close();
			if(fin!=null) fin.close();
		}
		
	}
	public static void main(String args[])throws IOException
	{
		ArrayList<Vehicle> V=new ArrayList<Vehicle>(5);
		int n;
		try
		{
		System.out.println("Enter no of Vehicles:");
		Scanner scnr=new Scanner(System.in);
		n=scnr.nextInt();
		if(n<=0) throw new Myexception("invalid entry(no of vehicles must be greater than o)");
		for(int ik=0;ik<n;ik++)
		{
			Vehicle obj=new Vehicle();
			obj.set();
			obj.setMfgDate();
			V.add(obj);
		}
		}
		catch(Myexception excep)
		{
			System.out.println("Exception: "+excep);
		}
		
		serializeVehicles(V);
		System.out.println("Vehicles with Manufacturing Date >2018");
		deserializeVehicles(V.size());
	}
}