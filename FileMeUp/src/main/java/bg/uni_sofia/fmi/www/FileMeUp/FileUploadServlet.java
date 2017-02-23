package bg.uni_sofia.fmi.www.FileMeUp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload-file")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		List<Part> fileParts = req.getParts().stream().filter(part -> "file".equals(part.getName()))
				.collect(Collectors.toList());

		for (Part filePart : fileParts) {
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			try (InputStream fileContent = filePart.getInputStream();
					OutputStream outputStream = new FileOutputStream(new File("../data/" + fileName));) {

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = fileContent.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resp.sendRedirect("/");
	}

}
