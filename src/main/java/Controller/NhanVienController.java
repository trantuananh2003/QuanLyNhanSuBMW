package Controller;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import DAO.NhanVienDAO;
import Models.NhanVien;
import DAO.TaiKhoanDAO;
import Models.TaiKhoan;

@WebServlet("/nhanviencontrol")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	    maxFileSize = 1024 * 1024 * 10,       // 10MB
	    maxRequestSize = 1024 * 1024 * 50     // 50MB
	)

public class NhanVienController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NhanVienDAO nhanvienDAO;
	private TaiKhoanDAO taikhoanDAO;
	private List<FileItem> files;

	public NhanVienController() {
		super();
		nhanvienDAO = new NhanVienDAO();
		taikhoanDAO = new TaiKhoanDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String action = null;
		String filePath =null;
		if (session.getAttribute("action") != null) {
			action = (String) session.getAttribute("action");
			session.removeAttribute("action");
			System.out.println("Action from session: " + action);
		} else {
			action = request.getParameter("action");
			filePath = request.getParameter("filePath");
			System.out.println("Action from request parameter: " + action);
			System.out.println("parameter: " + filePath);
		}

		System.out.println("Action: " + action);
		try {
			switch (action) {
			case "delete":
				deleteNhanVien(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			case "themnhanvien":
				showInsertForm(request, response);
				break;
			case "list":
				listNhanVien(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/login.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		try {
			switch (action) {
			case "insert":
				themNhanVien(request,response);
				break;
			case "update":
				capNhapNhanVien(request,response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/login.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void themNhanVien(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException  {
		try {
			String maNV = request.getParameter("inputMaNV");
			String hoTenNV = request.getParameter("inputHoTen");
			float luongCoBan = Float.parseFloat(request.getParameter("inputLuongCoBan"));
			String emailCV = request.getParameter("inputEmailCV");
			String trangThaiCV = request.getParameter("cbbTrangThai");
			String duongDanAnh = luuFileAnh("inputDuongDanAnh",request,response);
			
			NhanVien newNV = new NhanVien(maNV, hoTenNV, luongCoBan, emailCV, trangThaiCV, duongDanAnh);
			nhanvienDAO.insertNhanVien(newNV);
			taikhoanDAO.insertTK(new TaiKhoan(maNV, "1", maNV));
			if (newNV != null) {
				request.setAttribute("nhanvien", newNV);
				request.setAttribute("hanhdongthemnhanvien", "hosoForm");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/themnhanvien.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void capNhapNhanVien(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException  {
		try {
			String maNV = request.getParameter("inputMaNV");
			String hoTenNV = request.getParameter("inputHoTen");
			float luongCoBan = Float.parseFloat(request.getParameter("inputLuongCoBan"));
			String emailCV = request.getParameter("inputEmailCV");
			String trangThaiCV = request.getParameter("cbbTrangThai");

			Part filePart = request.getPart("inputDuongDanAnh");
			if(filePart.getSize()> 0)
			{
				String duongDanAnh = luuFileAnh("inputDuongDanAnh",request,response);
				NhanVien nv = new NhanVien(maNV, hoTenNV, luongCoBan, emailCV, trangThaiCV, duongDanAnh);
				nhanvienDAO.updateNhanVien(nv);
			}
			else
			{
				NhanVien nv = new NhanVien(maNV, hoTenNV, luongCoBan, emailCV, trangThaiCV);
				nhanvienDAO.updateNhanVienKhongAnh(nv);
			}
			System.out.println("Đang them nhân viên" + hoTenNV);
			listNhanVien(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String luuFileAnh(String nameInput,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Part filePart = request.getPart(nameInput);
		String maNV = request.getParameter("inputMaNV");
		String fileName = maNV+".jpg";
		
		// Xác định tên thư mục lưu trữ file trong project
		String uploadDirectory = "AnhCaNhan/";
		
		// Lấy đường dẫn thực tế của thư mục lưu trữ file
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + uploadDirectory + fileName;
		System.out.println(savePath);
		
		//thực hiện việc lưu
		InputStream inputStream = filePart.getInputStream();
		FileOutputStream outputStream = new FileOutputStream(savePath);
		int bytesRead = -1;
		byte[] buffer = new byte[1024];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
		outputStream.close();
		inputStream.close();
		
		return fileName;
	}
	

	
	private void deleteNhanVien(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String maNV = request.getParameter("manv");
		nhanvienDAO.deleteNhanVien(maNV);
		response.sendRedirect("list");
	}

	public void listNhanVien(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<NhanVien> listNhanVien = nhanvienDAO.selectAllNhanVien();
		request.setAttribute("listNhanVien", listNhanVien);
		RequestDispatcher dispatcher = request.getRequestDispatcher("pages/danhsachnhanvien.jsp");
		dispatcher.forward(request, response);
	}
	

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String maNV = request.getParameter("manv");
		System.out.println("Action from session: " + maNV);

		NhanVien existingNV = nhanvienDAO.selectNhanVien(maNV);
		request.setAttribute("nhanvien", existingNV);
		request.setAttribute("hanhdongtacdong", "edit");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/themnhanvien.jsp");
		dispatcher.forward(request, response);
	}

	private void showInsertForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		response.sendRedirect(url + "/pages/themnhanvien.jsp");
	}


	public static void main(String[] args) {
	}
}
