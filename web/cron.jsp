<%-- 
    Document   : cron
    Created on : Oct 6, 2011, 9:58:02 PM
    Author     : abythell
--%>

<%@page import="org.joda.time.LocalDate"%>
<%@page import="alfweb.Cron"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AlfWeb Cron</title>
    </head>
    <body>
        <h1>AlfWeb Cron</h1>
        <%
                    Cron cron = new Cron();
        %>
        <ul>
            <li>Date: <%= new LocalDate()%></li>
            <li>Number of lifts found: <%= cron.getLiftCount()%></li>
            <li>Number of lifts updated: <%= cron.getUpdateCount()%></li>
            <li>Status: <%= cron.getStatus() %></li>
        </ul>
    </body>
</html>
