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
            LocalDate date = new LocalDate();
            Integer days = 7;
            Report report = new Report(date, days);
        %>

        <table>
            <tr>
                <th>Lift</th>
                <%
                    for (int i=days; i >= 0; i--) {
                %>
                <th><%=date.minusDays(i).toString() %></th>
                <%
                    }
                %>
            </tr>
            <%

                for (int row = 0; row < report.liftMap.getLiftCount(); row++) {
                    String name = report.liftMap.getLiftNameByIndex(row);
            %>
            <tr>
                <td><%= name %></td>
                <%
                    for (int col=days-1; col >= 0; col--) {
                        LocalDate offsetDate = date.minusDays(col);
                        String status = report.liftMap.get(name, offsetDate.toString());
                        if (status == null) {
                            status = "[no data]";
                        }
                %>
                        <td><%= status %></td>
                <%
                    }
                %>
                
            </tr>
            <%
                }
            %>
        </table>

    </body>
</html>
