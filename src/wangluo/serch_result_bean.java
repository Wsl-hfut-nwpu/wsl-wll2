package wangluo;

import java.util.Vector;

public class serch_result_bean {
		Vector <String>a;
		Vector <String>b;
		Vector <String>c;
		Vector <String>d;
		Vector <String>e;
		Vector <String>f;
		Vector <String>g;
		Vector <String>h;
		
		Vector <String>i;
		Vector <String>j;
		Vector <String>k;
		Vector <String>l;
		Vector <String>m;
		
		public void All_bean()
		{
			a=new Vector<String>(1);
			b=new Vector<String>(1);
			c=new Vector<String>(1);
			d=new Vector<String>(1);
			e=new Vector<String>(1);
			f=new Vector<String>(1);
			g=new Vector<String>(1);
			h=new Vector<String>(1);

		}
		
		public void All_bean1()
		{
			i=new Vector<String>(1);
			j=new Vector<String>(1);
			k=new Vector<String>(1);
			l=new Vector<String>(1);
			m=new Vector<String>(1);
		}
		
		
		public void set(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8){
			a.add(str1);
			b.add(str2);
			c.add(str3);
			d.add(str4);
			e.add(str5);
			f.add(str6);
			g.add(str7);
			h.add(str8);
		}
		
		public void set1(String str1,String str2,String str3,String str4,String str5){
			i.add(str1);
			j.add(str2);
			k.add(str3);
			l.add(str4);
			m.add(str5);
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
		public Vector<String> getj(){
			return j;
		}
		public Vector<String> getk(){
			return k;
		}
		public Vector<String> getl(){
			return l;
		}
		public Vector<String> getm(){
			return m;
		}

}
