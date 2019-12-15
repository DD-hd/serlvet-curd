import dao.impl.ResourceDaoImpl;
import domain.Resource;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class IndexSerlvert extends HttpServlet {
    ResourceDaoImpl resourceDao;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        resourceDao=new ResourceDaoImpl();
    }

    @Override
    public void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        String method=servletRequest.getMethod();
        String path=servletRequest.getRequestURI();
        this.log("method:"+method+"path:"+path+"gg"+method.toUpperCase()+String.valueOf(method.toUpperCase()));
        if(method.toUpperCase().equals("GET")&&path.equals("/resource")){
            int id=Integer.parseInt(servletRequest.getParameter("id"));
            Resource resource=resourceDao.queryLearnResouceById(id);
            if(resource!=null){
                end(servletResponse,"success:"+resource.getTitle());
            }else{
                end(servletResponse,"success:"+null);
            }
        }else if(method.toUpperCase().equals("GET")&&path.equals("/resources")){
            List list= resourceDao.queryLearnResouceList(0,10);
            end(servletResponse,"success:"+list.size());
        }else if(method.toUpperCase().equals("POST")&&path.equals("/resource")){
            Resource resource=new Resource();
            String url=servletRequest.getParameter("url");
            String title=servletRequest.getParameter("title");
            String author=servletRequest.getParameter("author");
            resource.setUrl(url);
            resource.setTitle(title);
            resource.setAuthor(author);
            int id=resourceDao.add(resource);
            end(servletResponse,"success:"+id);
        }else if(method.toUpperCase().equals("DELETE")&&path.equals("/resource")){
            int id=Integer.parseInt(servletRequest.getParameter("id"));
            resourceDao.deleteById(id);
            end(servletResponse,"success");
        }else{
            servletResponse.setStatus(404);
            end(servletResponse,"not found");
        }

    }

    private void end(HttpServletResponse servletResponse, String content) throws IOException {
        servletResponse.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer= servletResponse.getWriter();
        writer.print(content);
        writer.close();
    }
    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
