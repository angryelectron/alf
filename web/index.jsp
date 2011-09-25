<%-- 
    Document   : index
    Created on : Sep 18, 2011, 6:22:18 PM
    Author     : abythell
--%>

<%@page import="alfd.Lift"%>
<%@page import="alfd.WhistlerResort"%>
<%@page import="alfd.Resort"%>
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
            Resort resort = new WhistlerResort();
            resort.scrape();
        %>
        <p>Whistler Resort contains <%= resort.getLifts().size() %> lifts </p>
        <ol>
        <%
            for (Lift lift : resort.getLifts()) {
        %>

            <li><%= lift.getName() %> <%= lift.getStatus().toString() %> </li>
        <%
            }

            (new Resort()).save();
        %>
        
        </ol>
        

    </body>
</html>
