package com.escape.controller.product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escape.controller.SuperClass;
import com.escape.dao.ProductDao;
import com.escape.model.Product;
import com.escape.utility.Paging;

public class ProductAllListController extends SuperClass{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 상품 목록을 조회합니다.
		super.doGet(request, response);

		String pageNumber = request.getParameter("pageNumber") ;
		String pageSize = request.getParameter("pageSize") ;
		String modeGenre = request.getParameter("modeGenre") ;
		String modeLevel = request.getParameter("modeLevel") ;
		String modeArea = request.getParameter("modeArea") ;
		String keyword = request.getParameter("keyword") ;

		if(modeGenre==null) {modeGenre="all";}
		if(modeLevel==null) {modeLevel="all";}
		if(modeArea==null) {modeArea="all";}
		if(keyword==null) {keyword="";}

		boolean isGrid = false ;

		ProductDao dao = new ProductDao();
		List<Product> lists = null ;

		try {
			int totalCount = dao.GetTotalRecordCountPr(modeGenre, modeLevel, modeArea, keyword);
			String url = super.getUrlInfo("prAllList");

			Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, modeGenre, modeLevel, modeArea, keyword, isGrid);
			System.out.println(this.getClass());
			System.out.println(pageInfo);

			lists = dao.SelectAll(pageInfo);
			
				// 검색 결과가 없을 때
				if(lists.isEmpty()) {
					String message = "조건에 맞는 테마가 없습니다.";
					String url02 = super.getUrlInfo("prAllList");;
					String alertMessage = "alert('" + message + "'); location.href='" + url02 + "'";
					request.setAttribute("alertMessage", alertMessage);
					
					System.out.println("if : " +lists);
				}
			
			request.setAttribute("datalist", lists);
			request.setAttribute("pageInfo", pageInfo);

			super.GotoPage("product/prAllList.jsp");

			//System.out.println(lists);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
