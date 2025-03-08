package com.crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class repository {

    private static final String JDBC_Driver = "com.mysql.jdbc.Driver";
    private static final String Db_Url = "jdbc:mysql://localhost/library";
    private static final String User = "";
    private static final String Pass = "";

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {
//        1. insert new book to database / (CREATE)
//        insertNewBook("Naruto", "Masashi Kishimoto");

//        2. get all book from database / (READ)
//        List<Book> allBook = getAllBook();
//        for (Book book : allBook) {
//            System.out.println("Book Title = "+ book.title+", created by "+ book.author);
//        }

//        3. update book title with author name
//        updateBookTitleByAuthor("Bernard", "Introduction to Java Programming Language");

//        4. delete book with book title
//        deleteBookByTitle("Naruto");
    }
    public static void operationSet(String adHoc){
        try{
            conn = DriverManager.getConnection(Db_Url, User, Pass);
            stmt = conn.createStatement();

            String query = adHoc ;
            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<Book> operationGet(String adHoc){
        List<Book> books = new ArrayList<Book>();
        try{
            conn = DriverManager.getConnection(Db_Url, User, Pass);
            stmt = conn.createStatement();

            String query = adHoc ;
            rs = stmt.executeQuery(query);
            while(rs.next()){
                books.add(new Book(rs.getString("title"), rs.getString("author")));
            }

            stmt.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }
    public static void insertNewBook(String title, String author){
        String sql = String.format("""
                    INSERT INTO book(title, author)
                    VALUE("%s", "%s")
                    """, title, author);

        operationSet(sql);
    }
    public static List<Book> getAllBook(){
        String sql = String.format(" SELECT * FROM book");

        return operationGet(sql);
    }
    public static void updateBookTitleByAuthor(String author, String title){
        String sql = String.format("""
                UPDATE book
                SET title = "%s"
                WHERE author = "%s"
                """, title, author);

        operationSet(sql);
    }
    public static void deleteBookByTitle(String title){
        String sql = String.format("""
                DELETE FROM book WHERE title = "%s"
                """, title);

        operationSet(sql);
    }
}
