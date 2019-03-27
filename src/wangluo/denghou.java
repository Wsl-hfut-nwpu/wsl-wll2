package wangluo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class denghou
 */
@WebServlet("/denghou")
public class denghou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public static void render(HttpServletResponse response,String text) { 
    	//响应数据ajax
        response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        try {  
            if(StringUtils.isBlank(text)){  
                text="";  
            }  
            response.getWriter().write(text);  
            response.getWriter().flush();  
            response.getWriter().close();  
        } catch (IOException e) {  
         //   log.error(e.getMessage());  
            if(!"class org.apache.catalina.connector.ClientAbortException".equals(e.getClass().toString()))  
                e.printStackTrace();  
        }  
    }  
	
    public denghou() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    response.setContentType("text/html");  //下面两条必须有，一个是译码返回的方式，一个是防止出现乱码。
	    response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();

			//PrintWriter out=((ServletResponse) request).getWriter();
		    
		PreparedStatement ps=null;  //预编译sql语句的接口  2
	    Connection ct=null;    //链接数据库的接口  1
		ResultSet rs=null;   //用来接收返回查询数据库之后的结果  3
		//本部分属于第二层
		
		userId_bean bean=new userId_bean();
		serch_result_bean result_bean=new serch_result_bean();
		result_bean.All_bean();
		String option=new String (request.getParameter("option").trim().getBytes("ISO8859_1"),"utf-8");
		//返回用户名字=userId
		if(option.equals("getState"))
		{
		   System.out.println("option="+option);
		   render(response,"{\"status\":\"success\",\"userId\":"+bean.userId+"}");
		}
		/*
		 * 
		 * 1.输入数据搜索返回查询结果
		 * 
		 * 
		 */
		else if(option.equals("search"))
		{
			System.out.println("option=="+option);
			String bookname=new String (request.getParameter("bookname").trim().getBytes("ISO8859_1"),"utf-8");
			System.out.println("bookname"+bookname);
			String cha=new String (request.getParameter("bookname").trim().getBytes("ISO8859_1"),"utf-8");
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
	            ps=ct.prepareStatement("select * from the_book");
	            rs=ps.executeQuery();	
		    	  //保存该用户相关信息
	            while(rs.next())
	            {
	          	  //数据库里面数据类型必须是varchar类型，char数据类型获取出来以下比较不相等。
		            if(cha.equals(rs.getString(2)))  //该函数获取每一行的每一个属性
		            {
		            	System.out.println("搜索到");
		            	result_bean.set(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
		            }
	            }
	            //搜索结果存到result_bean里面,要按格式返回
	            String str=new String();
	            str=" {";    //str="{
	            for(int i=0;i<result_bean.geta().size();i++)
	            {
	            	str+="\""+result_bean.geta().get(i)+"\":{\"id\":\""+result_bean.geta().get(i)+"\",\"bookname\":\""+result_bean.getb().get(i)+"\",\"author\":\""+result_bean.getc().get(i)+"\",\"publishHome\":\""+result_bean.getd().get(i)+"\",\"publishTime\":\""+result_bean.gete().get(i)+"\",\"photo\":\""+result_bean.getf().get(i)+"\",\"introduce\":\""+result_bean.getg().get(i)+"\",\"price\":\""+result_bean.geth().get(i)+"\"},";
	            }
	            str = str.substring(0,str.length() - 1);
	            str+="}";   //str补右面  }"
	            System.out.println("str="+str);
	            render(response,str);
	         }catch(Exception e){} 	
		}
		/*
		 * 
		 * 
		 * 2.点击加入购物车，获取单个订单数据，插入数据表中
		 * 
		 * 
		 */
		else if(option.equals("addcarshop"))
		{
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
	            ps=ct.prepareStatement("select * from the_relationship");
	            rs=ps.executeQuery();	
	            String str = new String (request.getParameter("id").trim().getBytes("ISO8859_1"),"utf-8");
		    	userId_bean bean1=new userId_bean();
		    	  //保存该用户相关信息
		    	boolean find=false;
	            while(rs.next())
	            {
	          	  //数据库里面数据类型必须是varchar类型，char数据类型获取出来以下比较不相等。
		            if(bean1.userId.equals(rs.getString(1))&&str.equals(rs.getString(2)))  //该函数获取每一行的每一个属性
		            {
		            	find=true;
		            	System.out.println("该物已有，需加1");
			            ps=ct.prepareStatement("update the_relationship set much=? where userId=? and order_number=?");
			            ps.setString(1, String.valueOf(Integer.parseInt(rs.getString("much"))+1));
			            ps.setString(2, bean1.userId);
			            ps.setString(3, str);
			            ps.executeUpdate();	
			            break;
		            }
	            }
                if(find==false)
                {
				   System.out.println("该物没有，加入购物车");
	               ps=ct.prepareStatement("INSERT into the_relationship(userId,order_number,much,to_adress) VALUES(?,?,?,?)");
		    	   //插入用户名
		    	   ps.setString(1, bean1.userId);
		    	   //插入userId
		    	   ps.setString(2, str);
		    	   ps.setString(3, "1");  //数量默认是开始是  1
		    	   ps.setString(4, "0");  //地址默认开始是     0
		    	   //执行插入SQL语句
		    	   rs=ps.executeQuery();
                 }
	        
	         }catch(Exception e){} 	
		}
		/*
		 * 
		 * 
		 * 3.显示购物车内容
		 *
		公用serch_result_bean
		
		*/
		else if(option.equals("getcardata"))
		{
			serch_result_bean bean2=new serch_result_bean();
			bean2.All_bean1();
			userId_bean bean1=new userId_bean();
			System.out.println("到这了");
			try{
			   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
               ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
               ps=ct.prepareStatement("select the_book.order_number,the_book.photo_small,book_name,the_relationship.much,the_book.price from the_relationship,the_book where the_relationship.order_number=the_book.order_number and the_relationship.userId=?");
               ps.setString(1, bean1.userId);
               rs=ps.executeQuery();
               System.out.println( bean1.userId);
               while(rs.next())
	            {
            	   //书编号
            	   //书小图
            	   //书名字
            	   //书数量
            	   //书单价-提交书总价
	          		bean2.set1(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));		
	            }
             
               //购物车没有东西，返回发fail
               if(bean2.geti().size()==0)
               {
            	   render(response,"  {\"status\":\"fail\"}  ");
               }else   //购物车有东西返回复合数据str字符串
               {
            	String str=new String();
   	            str="{";    //str="{   	            
   	            for(int i=0;i<bean2.geti().size();i++)
   	            {
   	            	//获取出单价数量计算出总价格
   	            	String total = String.valueOf(Integer.parseInt(bean2.getl().get(i))*Integer.parseInt(bean2.getm().get(i)));
   	            	str+="\""+bean2.geti().get(i)+"\":{\"id\":\""+bean2.geti().get(i)+"\",\"photo\":\""+bean2.getj().get(i)+"\",\"bookname\":\""+bean2.getk().get(i)+"\",\"much\":\""+bean2.getl().get(i)+"\",\"price\":\""+bean2.getm().get(i)+"\",\"total\":\""+total+"\"},";
   	            }
   	            str = str.substring(0,str.length() - 1);
   	            str+="}";   //str补右面  }"
   	            System.out.println("str="+str);
   	            render(response,str);
               }
			}catch(Exception e){}
		}
		/*
		 * 
		 * 
		 * 4.接收最终数量替换原数据库much数值，且将地址插入该人地址信息中，在表adress中，注意id自己加。
		 * 
		 * 
		 */
		else if(option.equals("alldatareturn"))
		{
			userId_bean bean3=new userId_bean();
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");	         
	            String str = new String (request.getParameter("data").trim().getBytes("ISO8859_1"),"utf-8");
		    	System.out.println(str);
		    	//解析to_adres或者adress_Id
		    	//去掉2个大括号
		    	str=str.substring(0,str.length() - 1);
		    	str=str.substring(1,str.length());	    
		    	//按照]号分开,string数组存放各个字符串，string[0]方to_adress,string[最后]放新加入的地址串
		        String	string[]=str.split("]");	    	
		    	System.out.println("str="+string.length);
		        //去掉每组中[];	
		    	for(int i=0;i<string.length;i++)
		    	{
			    	string[i]=string[i].substring(1,string[i].length());
		    	}		    			    	
		    	//上述准备工作成熟	    			    	
                if(string[0].equals("999"))
                {        
                	  //求出111用户地址最大题to_adress
                     ps=ct.prepareStatement("select adress_Id from the_adress");                 
                     rs=ps.executeQuery();
                     //设定计数长度，开辟数组Integer数组bi，然后选出max
                     int jishu=0;
                     while(rs.next())
                     {
                    	 jishu++; 
                     }
                     Integer[] bi=new Integer[jishu];
                     ps=ct.prepareStatement("select adress_Id from the_adress");                 
                     rs=ps.executeQuery();
                     jishu=0;
                     while(rs.next())
     	             {
                     	 bi[jishu]=Integer.parseInt(rs.getString("adress_Id"));
                     	 System.out.println(bi[jishu]+"  "+jishu);    
                     	 jishu++;
     	             }   
                     Integer max;
                     if(jishu==0) max=0;
                     else max=bubbleSort(bi);
                     System.out.println(max);
                    //将插入需要的信息解析到zuihoudao数组
                	String zuihoudao[]=string[string.length-1].split(",");
    		    	System.out.println("zuihudao"+zuihoudao[0]+"  "+zuihoudao[1]+"  "+zuihoudao[2]);
                    //插入地址库，更新数据，插入2个表,首先插入the_adress,   		                                     
                	ps=ct.prepareStatement("INSERT into the_adress(adress_Id,phone,adress,people_name) VALUES(?,?,?,?)");
                	ps.setString(1, String.valueOf(max+1));
                	ps.setString(2, String.valueOf(zuihoudao[1]));
                	ps.setString(3, String.valueOf(zuihoudao[2]));
                	ps.setString(4, String.valueOf(zuihoudao[0]));
                	ps.executeUpdate();	                   
                	rs.close();
                	ps.close();
                	//插入关系表the_parallelism,以后插入替换都用ps.executeUpdate();
                	System.out.println(bean3.userId+"  "+(max+1));                	
                	ps=ct.prepareStatement("INSERT into the_parallelism (userId,adress_Id) VALUES(?,?)");
                	ps.setString(1, bean3.userId);
                	ps.setString(2, String.valueOf(max+1));             
                	ps.executeUpdate();	    
                    //最后替换relationship的to_adress,much                  
                    //从第二个到倒数第二个
                    for(int k=1;k<string.length-1;k++)
                    {
                    	String []jubu=string[k].split(",");
                    	ps=ct.prepareStatement("update the_relationship set to_adress=?,much=? where the_relationship.userId=? and the_relationship.order_number=?");
                        ps.setString(1, String.valueOf(max+1));
                        ps.setString(2, jubu[1]);
                        ps.setString(3, bean3.userId);
                        ps.setString(4, jubu[0]);
                        ps.executeUpdate();                    	                    	               
                    }
                }
                //该地址adress_Id已经存在
                else
                {               
                	System.out.println(string[0]+"  "+bean3.userId);                	
                    //2:替换relationship里面各个订单order_number所对应的much,to_adress
                       //从第二个到最后一个
                    for(int k=1;k<string.length;k++)
                    {
                    	String []jubu=string[k].split(",");
                    	ps=ct.prepareStatement("update the_relationship set to_adress=?,much=? where the_relationship.userId=? and the_relationship.order_number=?");
                        ps.setString(1, string[0]);
                        ps.setString(2, jubu[1]);
                        ps.setString(3, bean3.userId);
                        ps.setString(4, jubu[0]);
                        ps.executeUpdate();                    	                    	               
                    }
                }
                //插入该用户备选数据库全部信息，之后删除该用户订单在表relationship即可
                ps=ct.prepareStatement("select the_relationship.order_number,book_name,photo_small,much,price,to_adress,phone,adress,adress_Id from the_relationship,the_book,the_adress where the_relationship.order_number=the_book.order_number and the_adress.adress_Id=to_adress and the_relationship.userId=? and to_adress!=?"); 
                ps.setString(1, bean3.userId);
                //不等于0表示该物添加了地址，买了
                ps.setString(2, "0");
                rs=ps.executeQuery();
                copy_bean copy=new copy_bean();
                copy.All_bean();
                while(rs.next())
	            {
	                 copy.set_1(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
	            }            
                ps=ct.prepareStatement("select biaohao from the_copy");                 
                rs=ps.executeQuery();          
                //设定计数长度，开辟数组Integer数组bi，然后选出max
                int jishu=0;
                while(rs.next())
                {
               	 jishu++; 
                }       
                Integer[] bi=new Integer[jishu];
                ps=ct.prepareStatement("select biaohao from the_copy");                 
                rs=ps.executeQuery();
                jishu=0;
                while(rs.next())
	             {
                	 bi[jishu]=Integer.parseInt(rs.getString("biaohao"));
                	 System.out.println(bi[jishu]);    
                	 jishu++;
	             }
                Integer max;
                if(jishu==0)  max=0;
                else max=bubbleSort(bi);
                System.out.println(max);     
                System.out.println("长度="+copy.geta().size());
                for(int i=0;i<copy.geta().size();i++)
                {
                	 ps=ct.prepareStatement(" insert into the_copy values(?,?,?,?,?,?,?,?,?,?,?)"); 
                     //max必须要自己加，否则主码不行
                	 max=max+1;
                	 ps.setString(1, String.valueOf(max));
                     ps.setString(2, bean3.userId);
                     ps.setString(3, copy.geta().get(i));
                     ps.setString(4, copy.getb().get(i));
                     ps.setString(5, copy.getc().get(i));
                     ps.setString(6, copy.getd().get(i));
                     ps.setString(7, copy.gete().get(i));
                     ps.setString(8, copy.getf().get(i));
                     ps.setString(9, copy.getg().get(i));
                     ps.setString(10, copy.geth().get(i));
                     ps.setString(11, copy.geti().get(i));
                     ps.executeUpdate(); 
                }
                //删除该用户订单
                ps=ct.prepareStatement("delete from the_relationship where userId=? and to_adress!=?");
                ps.setString(1, bean3.userId);
                ps.setString(2,"0");
                rs=ps.executeQuery();                                                                               
	         }catch(Exception e){} 	
		}
		/*
		 * 
		 * 5.返回该用户已经保存的地址信息
		 * 
		 * 
		 */
		else if(option.equals("getmsg"))
		{
			adress_bean ad_bean=new adress_bean();
			ad_bean.All_bean();
			userId_bean bean1=new userId_bean();
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
	            ps=ct.prepareStatement("select the_adress.adress_Id,phone,adress,people_name from the_adress,the_parallelism where the_adress.adress_Id=the_parallelism.adress_Id and the_parallelism.userId=?");
	            ps.setString(1, bean1.userId);
	            rs=ps.executeQuery();			    	
		    	  //保存该用户相关信息
	            while(rs.next())
	            {
		            ad_bean.set(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4));
	            }     
	            if(ad_bean.geta().size()==0)
	              {
	            	   render(response,"{\"status\":\"fail\"}");
	              }
	            else   //购物车有东西返回复合数据str字符串
	              {
	            	String str=new String();
	   	            str="{\"status\":\"success\",";    //str="{   	            
	   	            for(int i=0;i<ad_bean.geta().size();i++)
	   	              {
	   	            	//获取出单价数量计算出总价格	   	         
	   	            	str+="\""+ad_bean.geta().get(i)+"\":{\"id\":\""+ad_bean.geta().get(i)+"\",\"phone\":\""+ad_bean.getb().get(i)+"\",\"address\":\""+ad_bean.getc().get(i)+"\",\"person\":\""+ad_bean.getd().get(i)+"\"},";
	   	              }
	   	            str = str.substring(0,str.length() - 1);
	   	            str+="}";   //str补右面  }"
	   	            System.out.println("str="+str);
	   	            render(response,str);
	               }
	         }catch(Exception e){} 	
		}
		/*
		 * 
		 * 6.返回历史订单
		 * 
		 * 
		 */
		else if(option.equals("getorderdata"))
		{
			System.out.println("option="+option);
			userId_bean g=new userId_bean();
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
	            ps=ct.prepareStatement("select * from the_copy where userId=?"); 
                ps.setString(1, g.userId);
	            rs=ps.executeQuery();		            	                                   	               
                //全部数据获得，显示出来后返回去
	          	String str=new String();
	        	System.out.println("option="+option);
	   	        str="{";    //str="{   	            
	   	        while(rs.next())
	                {		   	 //获取出单价数量计算出总价格		   	     	          
	   	        	    String total=String.valueOf(Integer.parseInt(rs.getString(6))*Integer.parseInt(rs.getString(7)));
	   	        	    System.out.println(total);   
	   	        	    str+="\""+rs.getString(1)+"\":{\"id\":\""+rs.getString(1)+"\",\"photo\":\""+rs.getString(5)+"\",\"bookname\":\""+rs.getString(4)+"\",\"much\":\""+rs.getString(6)+"\",\"total\":\""+total+"\",\"person\":\""+rs.getString(8)+"\",\"phone\":\""+rs.getString(9)+"\",\"address\":\""+rs.getString(10)+"\"},";
	   	                System.out.println("str="+str);
	                }
	   	            str = str.substring(0,str.length() - 1);
	   	            str+="}";   //str补右面  }"
	   	            System.out.println("str="+str);
	   	            render(response,str);
	               
	         }catch(Exception e){} 		
		}		
		/*
		 * 
		 * 7.添加地址,然后返回全部地址
		 * 
		 * 
		 */						
		else if(option.equals("addAddress"))
		{
			String str=new String (request.getParameter("data").trim().getBytes("ISO8859_1"),"utf-8");
			userId_bean bean3=new userId_bean();
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");	         
	         	System.out.println(str);
		    	//解析to_adres或者adress_Id
		    	//去掉2个大括号
		    	str=str.substring(0,str.length() - 1);
		    	str=str.substring(1,str.length());	    
		    	//按照]号分开,string数组存放各个字符串，string[0]方to_adress,string[最后]放新加入的地址串
		        String	string[]=str.split("]");	    	
		    	System.out.println("str="+string.length);
		        //去掉每组中[];	
		    	for(int i=0;i<string.length;i++)
		    	{
			    	string[i]=string[i].substring(1,string[i].length());
		    	}		    			    	
		    	//上述准备工作成熟	    			    	                     
                	  //求出111用户地址最大题to_adress
                     ps=ct.prepareStatement("select adress_Id from the_adress");                 
                     rs=ps.executeQuery();
                     //设定计数长度，开辟数组Integer数组bi，然后选出max
                     int jishu=0;
                     while(rs.next())
                     {
                    	 jishu++; 
                     }
                     Integer[] bi=new Integer[jishu];
                     ps=ct.prepareStatement("select adress_Id from the_adress");                 
                     rs=ps.executeQuery();
                     jishu=0;
                     while(rs.next())
     	             {
                     	 bi[jishu]=Integer.parseInt(rs.getString("adress_Id"));
                     	 System.out.println(bi[jishu]+"  "+jishu);    
                     	 jishu++;
     	             }   
                    Integer max=bubbleSort(bi);
                    System.out.println(max);
                    String []zuihoudao =string[1].split(",");           
                    //插入地址库，更新数据，插入2个表,首先插入the_adress,   		                                     
                	ps=ct.prepareStatement("INSERT into the_adress(adress_Id,phone,adress,people_name) VALUES(?,?,?,?)");
                	ps.setString(1, String.valueOf(max+1));
                	ps.setString(2, String.valueOf(zuihoudao[1]));
                	ps.setString(3, String.valueOf(zuihoudao[2]));
                	ps.setString(4, String.valueOf(zuihoudao[0]));
                	ps.executeUpdate();	                   
                	rs.close();
                	ps.close();
                	//插入关系表the_parallelism,以后插入替换都用ps.executeUpdate();                         	
                	ps=ct.prepareStatement("INSERT into the_parallelism (userId,adress_Id) VALUES(?,?)");
                	ps.setString(1, bean3.userId);
                	ps.setString(2, String.valueOf(max+1));             
                	ps.executeUpdate();	
                	render(response,"la");
                    //最后替换relationship的to_adress          
			}catch(Exception e){} 	
			
		}
             
	}//dopost结束
    //排序函数
	public static Integer bubbleSort(Integer[] numbers) {   
	    Integer temp; // 记录临时中间值   
	    int size = numbers.length; // 数组大小   
	    for (int i = 0; i < size - 1; i++) {   
	        for (int j = i + 1; j < size; j++) {   
	            if (numbers[i] < numbers[j]) { // 交换两数的位置   
	                temp = numbers[i];   
	                numbers[i] = numbers[j];   
	                numbers[j] = temp;   
	            }   
	        }   
	    }   
	    return numbers[0];
	}  
}//类结束
