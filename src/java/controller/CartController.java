/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ductm.category.CartDTO;
import ductm.category.CategoryDAO;
import ductm.category.CategoryDTO;
import ductm.category.Item;
import ductm.product.ProductDAO;
import ductm.product.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ACER
 */
@WebServlet(name = "AddToCartController", urlPatterns = {"/AddToCartController"})
public class CartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int id = 100;
        id = Integer.parseInt(request.getParameter("id")) ;
        String op = request.getParameter("op");
        switch (op) {
            case "add_to_cart":
                addToCart(request, response);
                break;
            case "checkout":
                checkout(request, response);
                break;
            case "Delete":
                DeleteProduct(request, response);
                break;
            case "Update": 
                UpdateProduct(request, response);
                

        }
        System.out.println(id);
    }
    protected void UpdateProduct(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        CartDTO cart = new CartDTO();
        HttpSession session = request.getSession(); 
        HashMap<Integer, Item> lst = (HashMap<Integer, Item>) session.getAttribute("cart");
        if(lst != null){
            cart.setCart(lst);
            cart.Update(id, quantity);
            lst = cart.getList();
        }
        session.setAttribute("cart", lst);
        request.getRequestDispatcher("ViewCart.jsp").forward(request, response);
        
    }
    protected void DeleteProduct(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CartDTO cart = new CartDTO();
        HttpSession session = request.getSession(); 
        HashMap<Integer, Item> lst = (HashMap<Integer, Item>) session.getAttribute("cart");
        if(lst != null){
            cart.setCart(lst);
            cart.deleteProduct(id);
            lst = cart.getList();
        }
        session.setAttribute("cart", lst);
        request.getRequestDispatcher("ViewCart.jsp").forward(request, response);
        
    }
        protected void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ID = request.getParameter("id");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Item item = new Item(ID, quantity, name);
        //Tạo đối tượng session
        HttpSession session = request.getSession();
        //Lấy cart từ session
        CartDTO getCart = new CartDTO();
        HashMap<Integer, Item> cart = (HashMap<Integer, Item>) session.getAttribute("cart");
        //Nếu trong session chưa có cart thì sẽ tạo cart mới
        if (cart != null) {
            getCart.setCart(cart);
        }
        //Thêm item vào cart
        getCart.add(item);
        //Để cart vào session
         cart = getCart.getList();
        session.setAttribute("cart", cart);
        request.getRequestDispatcher("/HomeController").forward(request, response);
    }
    
    protected void checkout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Tạo đối tượng session
        HttpSession session = request.getSession();
        //Lấy cart từ session
        CartDTO getCart = new CartDTO();
        HashMap<Integer, Item> cart = (HashMap<Integer, Item>) session.getAttribute("cart"); 
        if (cart != null) {// xóa giỏ hàng
            getCart.setCart(cart);
            getCart.empty();
            cart = getCart.getList();
        }
        //cho  hiện trang chekout.jsp
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
