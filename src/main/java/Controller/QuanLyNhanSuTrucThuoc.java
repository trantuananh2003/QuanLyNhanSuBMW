package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BangPhanQuyenDAO;
import DAO.HoSoDAO;
import DAO.HopDongDAO;
import DAO.NhanVienDAO;
import DAO.PhongBanDAO;
import DAO.QuaTrinhCongTacDAO;
import Models.BangPhanQuyen;
import Models.HoSo;
import Models.HopDong;
import Models.LoginBean;
import Models.NhanVien;
import Models.PhongBan;
import Models.QuaTrinhCongTac;
import Models.TaiKhoan;
import Util.HandleException;
import Util.JDBCUtils;


@WebServlet("/quanlynhansutructhuoccontroller")
public class QuanLyNhanSuTrucThuoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BangPhanQuyenDAO bpqDAO;
	private NhanVienDAO nvDAO;
	private HoSoDAO hsDAO;
	private HopDongDAO hdDAO;
	private QuaTrinhCongTacDAO qtctDAO;
	private PhongBanDAO pbDAO;
	
	public QuanLyNhanSuTrucThuoc() {
		super();
		bpqDAO = new BangPhanQuyenDAO();
		nvDAO = new NhanVienDAO();
		hsDAO = new HoSoDAO();
		hdDAO = new HopDongDAO();
		qtctDAO = new QuaTrinhCongTacDAO();
		pbDAO = new PhongBanDAO();	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String maPB = request.getParameter("mapb");
		System.out.println("Mã phòng ban: " + maPB);
		System.out.println("Action: " + action);
		if(action==null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/quanlynhansu_tructhuoc.jsp");
			dispatcher.forward(request, response);
			return;
		}
		try {
			switch (action) {
			case "laythongtin":
				LayThongTinPhongBan(request, response, action);
				break;
			case "xemhs":
				LayThongTinCaNhan(request, response);
				break;
			case "themnv":
				ThemNVvaoPB(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/quanlynhansu_tructhuoc.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void LayThongTinCaNhan(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		try {
			String maNV = request.getParameter("manv");

			NhanVien nvAccLogin = nvDAO.selectNhanVien(maNV);
			HoSo hsAccLogin = hsDAO.selectHoSoByMaNV(maNV);
			List<HopDong> listHopDongAccLogin = hdDAO.selectAllHopDongTheoMaHS(hsAccLogin.getMaHS());
			List<QuaTrinhCongTac> listQTCTAccLogin = qtctDAO.selectAllQTCTTheoMaHS(hsAccLogin.getMaHS());

			request.setAttribute("nhanvien", nvAccLogin);
			request.setAttribute("hoso", hsAccLogin);
			request.setAttribute("listQTCT", listQTCTAccLogin);
			request.setAttribute("listHopDong", listHopDongAccLogin);
			request.setAttribute("hanhdongtacdong", "xemthongtincanhan");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/thongtincanhan.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String kiemTraQuyenCaoNhat(String maNV) throws SQLException {		
		String quyenHan = bpqDAO.selectQuyenHanCaoNhat(maNV);
		if(quyenHan == null) {
			return "khongcoquyen";
		}
		else{
			return quyenHan;
		}
	}
	
	public String kiemTraQuyenHanByMaPB(String maNV,String maPB,String maCN) throws SQLException {		
		BangPhanQuyen bpq1 = bpqDAO.selectBangPhanQuyenByMaPBMaNV(maNV, maPB);
		BangPhanQuyen bpq2 = bpqDAO.selectBangPhanQuyenByMaCNMaNV(maNV, maCN);
		
		if(bpq1 == null && bpq2 == null) {
			return "khongcoquyen";
		}
		else if (bpq1 != null){
			System.out.println("Check");
			return bpq1.getMaPQ();
		}
		else if (bpq2 != null){
			System.out.println("Check");
			return bpq2.getMaPQ();
		}
		else
		{
			return "khongcoquyen";
		}
	}
	
	
	private void LayThongTinPhongBan(HttpServletRequest request, HttpServletResponse response, String action) throws SQLException, IOException, ServletException {

	    try {
	        String maPB = request.getParameter("mapb");
	        String maCN = request.getParameter("macn");

	        HttpSession session = request.getSession();
	        LoginBean tk = (LoginBean) session.getAttribute("accLogin");

	        if (tk == null) {
	            // Redirect to login page if user is not logged in
	            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
	            return;
	        }

	        String kiemTra = kiemTraQuyenHanByMaPB(tk.getMaNhanvien(), maPB, maCN);
	        if (kiemTraQuyenCaoNhat(tk.getMaNhanvien()).equals("admin")) {
	            kiemTra = "coquyen";
	        }
	        System.out.println(kiemTra);

	        if (kiemTra.equals("khongcoquyen")) {
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/KhongCoQuyen.jsp");
	            dispatcher.forward(request, response);
	        } else {
	            List<BangPhanQuyen> listBPQ = bpqDAO.selectAllBangPhanQuyenByMaPB(maPB);
	            PhongBan pb = pbDAO.SelectPBByMaPB(maPB);

	            request.setAttribute("listBPQ", listBPQ);
	            request.setAttribute("pb", pb);

	            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/quanlynhansu_tructhuoc.jsp");
	            dispatcher.forward(request, response);
	        }
	    } catch (Exception e) {
	        String alertMessage = "Lỗi từ hệ thống";
			sendCatchError(request,response, alertMessage);
	    }
	}
	
	private void sendCatchError(HttpServletRequest request, HttpServletResponse response, String alertMessage)  
			throws SQLException, IOException, ServletException 
	{
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter(); 
    	// Hiển thị thông báo cảnh báo nếu thiếu thông tin
        out.println("<script>alert('" + alertMessage + "');</script>"); 
	}
	
	private void ThemNVvaoPB(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String maNV = request.getParameter("inputMaNV");
		String maPB = request.getParameter("maPB");
		String maCN = request.getParameter("maCN");

		Random random = new Random();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String mapq = random.ints(3, 0, alphabet.length())
				.mapToObj(alphabet::charAt)
				.map(Object::toString)
				.collect(Collectors.joining());

		bpqDAO.insertNvtoPb(mapq, maNV,maPB,maCN );

		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/quanlynhansu_tructhuoc.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}


}
