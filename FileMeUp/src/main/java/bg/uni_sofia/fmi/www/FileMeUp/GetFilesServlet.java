package bg.uni_sofia.fmi.www.FileMeUp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/view-all")
public class GetFilesServlet extends HttpServlet{
	private static final String HTML_TOP = "<html><head><meta charset=\"UTF-8\"><title>All files</title><style>"
				+ "body {background-color: #f2f2f2; color: grey; font-family:\"Lucida Console\", Monaco, monospace;}"
				+ "table, tr, td, th {border: 1px solid grey; border-collapse: collapse;}"
				+ "#fixedbutton { position: fixed; bottom: 15px; right: 15px; background-color: orange; color: white; border: none; text-align: center; text-decoration: none; padding: 15px 32px; display: inline-block; font-family:\"Lucida Console\", Monaco, monospace;}"
				+ "th {height: 50px; font-weight: bold; font-size: 25px}"
				+ "</style></head><body><table><tr><th>File Name</th><th>Download</th></tr>";
	private static final String HTML_BOTTOM = "<form action=\"/\"><input type=\"submit\" value=\"Go back to upload\" id=\"fixedButton\" /></form></body></html>";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		File dir = new File("../data");
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                System.err.println("Failed to create directory!");
            }
        }
		File[] listOfFiles = dir.listFiles();
		
		List<String> fileNames = new ArrayList<>();
		
		for(int i=0; i<listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()) {
				fileNames.add(listOfFiles[i].getName().toString());
			}
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(HTML_TOP);
		for(String fileName: fileNames) {
			System.out.println(fileName);
			out.println("<tr><td>"+fileName+"</td><td align=\"center\">"
					+ "<a href="+"download?fileName="+fileName+"><img src=\"http://www.drodd.com/images12/icon-download29.png\" width=\"40\" height=\"40\"></a>"
					+ "</td></tr>");
		}
		out.println(HTML_BOTTOM);

	    
	}
}
