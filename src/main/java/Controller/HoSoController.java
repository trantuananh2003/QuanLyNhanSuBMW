package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HoSoDAO;
import DAO.NhanVienDAO;
import Models.HoSo;
import Models.NhanVien;

@WebServlet("/hosocontrol")
public class HoSoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HoSoDAO hsDAO;
	NhanVienDAO nvDAO;
	
	public HoSoController() {
		super();
		hsDAO = new HoSoDAO();
		nvDAO = new NhanVienDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		System.out.println("Action: " + action);
		try {
			switch (action) {
			case "insertHS":
				insertHoSo(request, response);
				break;
			case "updateHS":
				updateHoSo(request,response);
				break;
			case "edit":
				showEditForm(request,response);
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

	
	private void updateHoSo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	    try {
	        String maHS = request.getParameter("inputMaHS");
	        String maNV = request.getParameter("inputMaNV");
	        String cccd = request.getParameter("inputCCCD");
	        String noiCapCCCD = request.getParameter("inputNoiCapCCCD");
	        LocalDate ngayCapCCCD = LocalDate.parse(request.getParameter("inputNgayCapCCCD"));
	        String maSoThue = request.getParameter("inputMSThue");
	        LocalDate ngayCapMST = LocalDate.parse(request.getParameter("inputNgayCapMSThue"));
	        String soDienThoai = request.getParameter("inputSDT");
	        Boolean gioiTinh = "1".equals(request.getParameter("cbbGioiTinh"));
	        String quocTich = request.getParameter("inputQuocTich");
	        String danToc = request.getParameter("inputDanToc");
	        String tonGiao = request.getParameter("inputTonGiao");
	        LocalDate ngaySinh = LocalDate.parse(request.getParameter("inputNgaySinh"));
	        String noiSinh = request.getParameter("inputNoiSinh");
	        String diaChi = request.getParameter("inputDiaChi");
	        String tinhThanh = request.getParameter("city");
	        String quanHuyen = request.getParameter("district");
	        String phuongXa = request.getParameter("ward");
	        String emailCaNhan = request.getParameter("inputEmailCN");
	        String tinhTrangHN = request.getParameter("cbbTinhTrangHonNhan");
	        String trinhDoVanHoa = request.getParameter("cbbTrinhDoHV");
	        String trinhDoHocVan = request.getParameter("inputTrinhDoVH");

	        HoSo updateHoSo = new HoSo(maHS, maNV, cccd, noiCapCCCD, ngayCapCCCD, maSoThue, ngayCapMST, soDienThoai,
	                gioiTinh, quocTich, danToc, tonGiao, ngaySinh, noiSinh, diaChi, tinhThanh, quanHuyen, phuongXa,
	                emailCaNhan, tinhTrangHN, trinhDoVanHoa, trinhDoHocVan);
	        
	        if(maHS != null)
	        {
		        hsDAO.updateHoSo(updateHoSo);
		        
		        NhanVienController nvControl = new NhanVienController();
		        nvControl.listNhanVien(request, response);
	        }
	        else {
	            PrintWriter printWriter = response.getWriter();
	            printWriter.println("Lấy hồ sơ thất bại");
	        }

	        	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	

	private void insertHoSo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		try {
			String maHS = request.getParameter("inputMaHS");
			String maNV = request.getParameter("inputMaNV");
			String cccd = request.getParameter("inputCCCD");
			String noiCapCCCD = request.getParameter("inputNoiCapCCCD");
			LocalDate ngayCapCCCD = LocalDate.parse(request.getParameter("inputNgayCapCCCD"));
			String maSoThue = request.getParameter("inputMSThue");
			LocalDate ngayCapMST = LocalDate.parse(request.getParameter("inputNgayCapMSThue"));
			String soDienThoai = request.getParameter("inputSDT");
			Boolean gioiTinh = "1".equals(request.getParameter("cbbGioiTinh"));
			String quocTich = request.getParameter("inputQuocTich");
			String danToc = request.getParameter("inputDanToc");
			String tonGiao = request.getParameter("inputTonGiao");
			LocalDate ngaySinh = LocalDate.parse(request.getParameter("inputNgaySinh"));
			String noiSinh = request.getParameter("inputNoiSinh");
			String diaChi = request.getParameter("inputDiaChi");
			String tinhThanh = request.getParameter("city");
			String quanHuyen = request.getParameter("district");
			String phuongXa = request.getParameter("ward");
			String emailCaNhan = request.getParameter("inputEmailCN");
			String tinhTrangHN = request.getParameter("cbbTinhTrangHonNhan");
			String trinhDoHocVan = request.getParameter("cbbTrinhDoHV");
			String trinhDoVanHoa = request.getParameter("cbbTrinhDoVH");

			HoSo newHoSo = new HoSo(maHS, maNV, cccd, noiCapCCCD, ngayCapCCCD, maSoThue, ngayCapMST, soDienThoai,
					gioiTinh, quocTich, danToc, tonGiao, ngaySinh, noiSinh, diaChi, tinhThanh, quanHuyen, phuongXa,
					emailCaNhan, tinhTrangHN, trinhDoVanHoa, trinhDoHocVan);

			hsDAO.insertHoSo(newHoSo);
			request.setAttribute("hoso", newHoSo);
			request.setAttribute("hanhdongthemnhanvien", "hopdong");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/themnhanvien.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String maNV = request.getParameter("manv");

		HoSo existingHS = hsDAO.selectHoSoByMaNV(maNV);
		System.out.println(existingHS);

		NhanVien existingNV = nvDAO.selectNhanVien(maNV);
		if(existingHS != null)
		{
			request.setAttribute("hoso", existingHS);

			request.setAttribute("nhanvien", existingNV);
			request.setAttribute("hanhdongthemnhanvien", "hosoForm");
			request.setAttribute("hanhdongtacdong", "edit");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/themnhanvien.jsp");
			dispatcher.forward(request, response);
		}
		else if(existingHS == null)
		{
			String maHS = request.getParameter("inputMaHS");

			HoSo hsNull = new HoSo(maHS, existingNV.getMaNV(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
			request.setAttribute("hoso", hsNull);

			request.setAttribute("nhanvien", existingNV);
			request.setAttribute("hanhdongthemnhanvien", "hosoForm");
			request.setAttribute("hanhdongtacdong", "edit");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/themnhanvien.jsp");
			dispatcher.forward(request, response);
		}


	}
	



}
