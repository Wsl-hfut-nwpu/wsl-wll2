package wangluo;
import java.util.Vector;
public class copy_bean {
	
				Vector <String>a;
				Vector <String>b;
				Vector <String>c;
				Vector <String>d;
				Vector <String>e;
				Vector <String>f;
				Vector <String>g;
				Vector <String>h;	
				Vector <String>i;
				
				public void All_bean(){
					a=new Vector<String>(1);
					b=new Vector<String>(1);
					c=new Vector<String>(1);
					d=new Vector<String>(1);
					e=new Vector<String>(1);
					f=new Vector<String>(1);
					g=new Vector<String>(1);
					h=new Vector<String>(1);
					i=new Vector<String>(1);
				}						
				
				public void set_1(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9){
					a.add(str1);
					b.add(str2);
					c.add(str3);
					d.add(str4);
					e.add(str5);											
				    f.add(str6);
				    g.add(str7);
				    h.add(str8);
				    i.add(str9);
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
				public Vector<String> gete(){
					return e;
				}
				public Vector<String> getf(){
					return f;
				}
				public Vector<String> getg(){
					return g;
				}
				public Vector<String> geth(){
					return h;
				}	
				public Vector<String> geti(){
					return i;
				}		

}
