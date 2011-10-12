<%-- 
    Document   : fakedata
    Created on : Oct 11, 2011, 4:57:07 PM
    Author     : abythell
--%>

<%@page import="org.joda.time.DateMidnight"%>
<%@page import="alfd.WhistlerResort"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <ul>
        <%
            for (int i=0; i<7; i++) {
                WhistlerResort resort = new WhistlerResort();
                DateMidnight startdate = resort.getLocalDate();
                resort.fetch();
                resort.setDate(startdate.minusDays(i));
        %>
        <li>Saving date <%=resort.getDate().toString()%></li>
        <%
                resort.save();
                resort.load(resort.getDate());
        %>
        <li>Loading date <%=resort.getDate().toString()%></li>
        <%
            }
        %>
        </ul>
    </body>
</html>
