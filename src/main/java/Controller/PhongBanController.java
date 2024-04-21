package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
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
import Models.PhongBan;

@WebServlet("/phongban")
public class PhongBanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PhongBanDAO pb;
	private ChiNhanhDAO cn;
	private BangPhanQuyenDAO bpq;
	public void init(ServletConfig config) throws ServletException {
		pb= new PhongBanDAO();
		cn= new ChiNhanhDAO();
		bpq = new BangPhanQuyenDAO();
		}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		System.out.println("Action: " + action);
		try {
			switch (action) {
			case "listPhongBan":
				listPhongBan(request, response);
				break;
			case "taoPB":
				taoPB(request, response);
				break;
			case "suaPB":
				suaPB(request, response);
				break;
			case "xoaPB":
				xoaPB(request, response);
				break;
			case "capnhatGDPB":
				capnhatGDPB(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listPhongBan(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {	
		List<PhongBan> listPB = pb.SelectPB();
		request.getSession().setAttribute("listPB", listPB);
	    response.sendRedirect(request.getContextPath() + "/pages/phongban_thongtin.jsp");
	}
	
	private void taoPB(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String mapb =  request.getParameter("mapb_input1");
		String tenpb =  request.getParameter("tenpb_input1");
		String chinhanh =  request.getParameter("macn_input1");

		Random random = new Random();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String mapq = random.ints(3, 0, alphabet.length())
				.mapToObj(alphabet::charAt)
				.map(Object::toString)
				.collect(Collectors.joining());

		List<ChiNhanh> list = this.cn.SelectAllChiNhanh();
		System.out.println(list);
		boolean chinhanhExists = false;
		for (ChiNhanh cn : list) {
			if (cn.getMaCN().equals(chinhanh)) {
				chinhanhExists = true;
				break;
			}
		}
		if (chinhanh == null || chinhanhExists==false) {
			// If chinhanh does not exist, set an error message in the request and forward back to the form
			request.setAttribute("errorMessage", "Chi Nhanh does not exist");
			response.sendRedirect(request.getContextPath() + "/pages/phongban_thongtin.jsp");
		}  else {
			pb.taoPB(mapb,tenpb,chinhanh);
			bpq.insertBPQonPB(mapq,"truongphong",chinhanh,mapb);
			listPhongBan(request, response);
		}
	}
	private void suaPB(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String mapb =  request.getParameter("mapb_input2");
		String maGD =  request.getParameter("matp_input2");
		String tenpb =  request.getParameter("tenpb_input2");
		String trangThai =  request.getParameter("trangthai");
		
		pb.suaPB(mapb,tenpb,maGD,trangThai);
		pb.updateQuyenPB(maGD,mapb);
		listPhongBan(request, response);
	}
	private void xoaPB(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String mapb =  request.getParameter("mapb_input4");	
		pb.xoaPB(mapb);    
		listPhongBan(request, response);
	}
	
	private void capnhatGDPB(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String maNV =  request.getParameter("matp_input3");	
		String maPB =  request.getParameter("mapb_input3");	
		pb.updateQuyenPB(maNV,maPB);    
		listPhongBan(request, response);
	}



}
