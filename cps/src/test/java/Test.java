import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.duanrong.authentication.service.GetMenu;
import com.duanrong.authentication.service.impl.GetMenuImpl;
import com.duanrong.authentication.utils.JDBCConnectionImpl;
import com.duanrong.business.permission.model.SysType;
import com.duanrong.cps.business.platform.model.DemandBill;


public class Test extends JDBCConnectionImpl<SysType>{

	public static void main(String[] args) throws SQLException, IOException{
		GetMenu permissionBean = new GetMenuImpl();	
		//List<String>list = (List<String>) permissionBean.hasActivePermission("13261356043ppfh");
		//System.out.println(list.size());
		
//       SimpleDateFormat aa= (SimpleDateFormat) DateFormat.getInstance();		
//	
//		Calendar calendar=Calendar.getInstance();
//		TimeZone timeZone=calendar.getTimeZone();
//		System.out.println(timeZone.getDisplayName(Locale.US));
//		
//		ObjectOutputStream out=new ObjectOutputStream(out);
//		 for (int i = 0; i < args.length; i++) {  
//	            System.out.println("args[" + i + "] is <" + args[i] + ">");  
//	        }  
		
//		DemandBill dd=new DemandBill();
//		System.out.println(dd);
		
		
		
		File file=new File("test.txt");
//		System.out.println(file.exists());
//		if(!file.exists()){
//			file.createNewFile();
//		}
//		System.out.println(file.exists());
//		System.out.println(file.getAbsolutePath());
//		
//		FileInputStream fileIn=new FileInputStream(file);
//		byte[] buffer=new byte[1024]; 
//		if(fileIn.read(buffer, 0, 1024)!=-1){
//			System.out.println(new String(buffer,"utf-8"));
//		}
//		fileIn.close();
		
		
		FileOutputStream fileOut=new FileOutputStream(file);
		
		BufferedInputStream buffterin=new BufferedInputStream(new FileInputStream(file));
		InputStreamReader rr=new InputStreamReader(new FileInputStream(file));
		BufferedReader bufferRed=new BufferedReader(rr);
		
		String aa="maying";
		byte[] bb=aa.getBytes();
		fileOut.write(bb, 0, bb.length);
		fileOut.close();
	}
	
	
	
}
