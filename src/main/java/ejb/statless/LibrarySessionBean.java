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

import javax.ejb.*;
import java.util.*;

@Stateless
public class LibrarySessionBean implements LibrarySessionBeanRemote {

    List<String> bookShell = new ArrayList<String>();

    public LibrarySessionBean() {
    }

    @Override
    public void addBook(String bookName) {
        System.out.println(bookShell + "book shell....................");
        System.out.println(bookName + "book name....................");
        bookShell.add(bookName);
    }

    @Override
    public List<String> getBooks() {
        return bookShell;
    }
}
