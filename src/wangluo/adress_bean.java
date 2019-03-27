package wangluo;

import java.util.Vector;
public class adress_bean {	
			Vector <String>a;
			Vector <String>b;
			Vector <String>c;
			Vector <String>d;			
			
			public void All_bean()
			{
				a=new Vector<String>(1);
				b=new Vector<String>(1);
				c=new Vector<String>(1);
				d=new Vector<String>(1);				
			}												
			public void set(String str1,String str2,String str3,String str4){
				a.add(str1);
				b.add(str2);
				c.add(str3);
				d.add(str4);				
			}			
			public Vector<String> geta(){
				return a;
			}
			public Vector<String> getb(){
				return b;
			}
			public Vector<String> getc(){
				return c;
			}
			public Vector<String> getd(){
				return d;
			}

}
