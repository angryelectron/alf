<%-- 
    Document   : index
    Created on : Sep 18, 2011, 6:22:18 PM
    Author     : abythell
--%>

<%@page import="alfd.Resort.ResortStatus"%>
<%@page import="alfweb.Report"%>
<%@page import="org.joda.time.LocalDate"%>
<%@page import="alfd.Resort"%>
<%@page import="alfd.Lift"%>
<%@page import="alfd.WhistlerResort"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alpine Lift Forecast</title>
    </head>
    <body>
        <h1>Alpine Lift Forecast</h1>

        <%
            Report report = new Report(new LocalDate());
            if (report.getStatus() == ResortStatus.OK) {
        %>
        <p>For <%=report.getDate() %>, Whistler Resort contains <%= report.getLifts().size() %> lifts </p>
        <ol>
        <%
            for (Lift lift : report.getLifts()) {
        %>

            <li><%= lift.getName() %> <%= lift.getStatus().toString() %> </li>
        <%
            }
        }
        else {
        %>
        <p>Whistler Resort Status = <%= report.getStatus().toString() %></p>

        <%
            }
        %>
        
        </ol>
        

    </body>
</html>
