package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Phaser;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BangPhanQuyenDAO;
import DAO.ChiNhanhDAO;
import DAO.PhongBanDAO;
import Models.ChiNhanh;
import Models.KhieuNai;
import Models.LoginBean;
import Models.PhongBan;

@WebServlet("/chinhanh")
public class ChiNhanhController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChiNhanhDAO cn;
	private BangPhanQuyenDAO bpq;
	public void init(ServletConfig config) throws ServletException {
		cn = new ChiNhanhDAO();
		bpq = new BangPhanQuyenDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		System.out.println("Action: " + action);
		try {
			switch (action) {
			case "listChinhanh":
				listChinhanh(request, response);
				break;
			case "taoCN":
				taoCN(request, response);
				break;
			case "suaCN":
				suaCN(request, response);
				break;
			case "xoaCN":
				xoaCN(request, response);
				break;
			case "capnhatGDCN":
				capnhatGDCN(request, response);
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

		System.out.println("Action: " + action);
		try {
			switch (action) {
			case "listChinhanh":
				listChinhanh(request, response);
				break;
			case "taoCN":
				taoCN(request, response);
				break;
			case "suaCN":
				suaCN(request, response);
				break;
			case "xoaCN":
				xoaCN(request, response);
				break;
			case "capnhatGDCN":
				capnhatGDCN(request, response);
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
	
	private void listChinhanh(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {	
		List<ChiNhanh> listCN = cn.SelectAllChiNhanh();
		request.getSession().setAttribute("listCN", listCN);
	    response.sendRedirect(request.getContextPath() + "/pages/chinhanh_thongtin.jsp");
	}
	
	private void taoCN(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
	    try {
	        String macn =  request.getParameter("macn_input1");
	        String tencn =  request.getParameter("tencn_input1");

	        if (macn != null && tencn != null && !macn.isEmpty() && !tencn.isEmpty()) {
	            Random random = new Random();
	            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	            String mapq = random.ints(3, 0, alphabet.length())
	                                .mapToObj(alphabet::charAt)
	                                .map(Object::toString)
	                                .collect(Collectors.joining());

	            cn.taoCN(macn, tencn);
	            bpq.insertBPQonCN(mapq, "giamdoc", macn);
	            listChinhanh(request, response);
	        } else {
	        	response.setContentType("text/html;charset=UTF-8");
	        	PrintWriter out = response.getWriter(); 
	        	// Hiển thị thông báo cảnh báo nếu thiếu thông tin
	            String alertMessage = "Thông để rỗng hoặc bị trùng vui lòng điền lại";
	            out.println("<script>alert('" + alertMessage + "');</script>");
	                    	
	        }
	    } catch (Exception e) {
	    	response.setContentType("text/html;charset=UTF-8");
	    	PrintWriter out = response.getWriter(); 
        	// Hiển thị thông báo cảnh báo nếu thiếu thông tin
            String alertMessage = "Lỗi từ hệ thống";
            out.println("<script>alert('" + alertMessage + "');</script>"); 
	    }
	}

	private void suaCN(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String maCN =  request.getParameter("macn_input2");
		String maGD =  request.getParameter("magd_input2");
		String tenCN =  request.getParameter("tencn_input2");
		String trangThai =  request.getParameter("trangthai");

		cn.updateQuyenCN(maGD,maCN);
		cn.suaCN(maCN,tenCN,maGD,trangThai);    
		listChinhanh(request, response);
	}
	private void xoaCN(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String maCN =  request.getParameter("macn_input4");	
		cn.xoaCN(maCN);    
		listChinhanh(request, response);
	}
	
	private void capnhatGDCN(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String maNV =  request.getParameter("magd_input3");	
		String maCN =  request.getParameter("macn_input3");	
		cn.updateQuyenCN(maNV,maCN);    
		listChinhanh(request, response);
	}
	

}
