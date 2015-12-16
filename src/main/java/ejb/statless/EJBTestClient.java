/*
 *   (C) Copyright 1996-2015 hSenid Software International (Pvt) Limited.
 *   All Rights Reserved.
 *
 *   These materials are unpublished, proprietary, confidential source code of
 *   hSenid Software International (Pvt) Limited and constitute a TRADE SECRET
 *   of hSenid Software International (Pvt) Limited.
 *
 *   hSenid Software International (Pvt) Limited retains all title to and intellectual
 *   property rights in these materials.
 *
 */
package ejb.statless;

import javax.naming.*;
import java.io.*;
import java.util.*;

public class EJBTestClient {

    BufferedReader brConsoleReader = null;
    Properties props;
    InitialContext ctx;
    {
/*        props = new Properties();
        try {
            props.load(new FileInputStream("jndi.properties"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        hashtable.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        hashtable.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        try {
            ctx = new InitialContext(hashtable);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) {

        EJBTestClient ejbTester = new EJBTestClient();

        ejbTester.testStatelessEjb();
    }

    private void showGUI(){
        System.out.println("**********************");
        System.out.println("Welcome to Book Store");
        System.out.println("**********************");
        System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
    }

    private void testStatelessEjb(){

        try {
            int choice = 1;

            LibrarySessionBeanRemote libraryBean = (LibrarySessionBeanRemote) ctx.lookup ("LibrarySessionBean/remote");

            while (true) {
                String bookName;
                showGUI();
                String strChoice = brConsoleReader.readLine();

                choice = Integer.parseInt(strChoice);
                if (choice == 1) {
                    System.out.print("Enter book name: ");
                    bookName = brConsoleReader.readLine();
                    assert libraryBean != null;
                    libraryBean.addBook(bookName);
                } else if (choice == 2) {
                    break;
                }
            }

            List<String> booksList = libraryBean.getBooks();

            System.out.println("Book(s) entered so far: " + booksList.size());
            int i = 0;
            for (String book:booksList) {
                System.out.println((i+1)+". " + book);
                i++;
            }
            LibrarySessionBeanRemote libraryBean1 =
                    (LibrarySessionBeanRemote)ctx.lookup("LibrarySessionBean/remote");
            List<String> booksList1 = libraryBean1.getBooks();
            System.out.println(
                    "***Using second lookup to get library stateless object***");
            System.out.println(
                    "Book(s) entered so far: " + booksList1.size());
            for (i = 0; i < booksList1.size(); ++i) {
                System.out.println((i+1)+". " + booksList1.get(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if(brConsoleReader !=null){
                    brConsoleReader.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
