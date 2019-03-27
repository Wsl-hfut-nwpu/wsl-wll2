package wangluo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class deng
 */
//以后 
@WebServlet("/deng")
public class denglu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public denglu() {
        super();
      
        // TODO Auto-generated constructor stub
    }
    
  /*  public static void render(HttpServletResponse response,String text) { 
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
    }  */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
		  System.out.println("APP数据成功");
		  String userid=request.getParameter("account");
		  String password=request.getParameter("password");
		  System.out.println("userId="+userid+"     "+"password="+password);
		
	    //下面两条必须有，一个是译码返回的方式，一个是防止出现乱码。
	   /* respone.setContentType("text/html");  //下面两条必须有，一个是译码返回的方式，一个是防止出现乱码。
    	respone.setCharacterEncoding("utf-8");
		PrintWriter out=respone.getWriter();

		//PrintWriter out=((ServletResponse) request).getWriter();
	    
	    PreparedStatement ps=null;  //预编译sql语句的接口  2
		Connection ct=null;    //链接数据库的接口  1
		ResultSet rs=null;   //用来接收返回查询数据库之后的结果  3
	    
		//String userId=request.getParameter("userId");
		String userId=new String (request.getParameter("userId").trim().getBytes("ISO8859_1"),"utf-8");
		String option=new String (request.getParameter("option").trim().getBytes("ISO8859_1"),"utf-8");
		String password=request.getParameter("password");
		System.out.println("userId="+userId);
		System.out.println("option="+option);
		System.out.println("password="+password);
		userId_bean bean=new userId_bean();
		bean.userId=userId;
		boolean find=false;
		//如果是登录选项
		if(option.equals("login"))
		{
			try{
	              Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	              ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
	              ps=ct.prepareStatement("select * from the_customer");
	              rs=ps.executeQuery();	
		    	  //保存该用户相关信息
	              while(rs.next())
	              {
	            	  //数据库里面数据类型必须是varchar类型，char数据类型获取出来以下比较不相等。
		            if(userId.equals(rs.getString(1))&&password.equals(rs.getString(2)))  //该函数获取每一行的每一个属性
		            {
		              find=true;
		              System.out.println("find="+find);
		            }
	              }
                }catch(Exception e){} 	
			//登录失败
			if(find==false)
			{
				System.out.println("登录失败");
				render(respone,"{\"status\":\"fail\"}");  
		    }
			//登录成功，跳入主界面
			else
			{
				System.out.println("登录成功");
				render(respone,"{\"status\":\"success\"}"); 
			}
		}
		//如果是注册名字检查
		else if(option.equals("checkid"))
		{
			try{
	              Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	              ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
	              ps=ct.prepareStatement("select * from the_customer");
	              rs=ps.executeQuery();	
	              boolean g=false;
		    	  //保存该用户相关信息
	              while(rs.next())
	              {
	            	  //数据库里面数据类型必须是varchar类型，char数据类型获取出来以下比较不相等。
		            if(userId.equals(rs.getString(1)))  //该函数获取每一行的每一个属性
		            {
		            	System.out.println("该账号不可注册");
		            	g=true;
						render(respone,"{\"status\":\"fail\"}");  
						break;
		            }
	              }
	              if(g==false)
	              {
	          	    System.out.println("该账号可以注册");
				    render(respone,"{\"status\":\"success\"}");
	              }
              }catch(Exception e){} 	
		}
		//真正注册插入数据库
		else if(option.equals("register"))
		{
			try {
				System.out.println("可以成功");
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          		ct=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=网络","sa","123");
				ps=ct.prepareStatement("INSERT into the_customer VALUES(?,?)");
				ps.setString(1, userId);			
				ps.setString(2, password);
				System.out.println("注册成功");
			    render(respone,"{\"status\":\"success\"}"); 
			    //必须有地下这一句，不然执行不成
				rs=ps.executeQuery();
		        }catch(Exception e){};
		}
		else System.out.println("无此选项");*/
	}
}