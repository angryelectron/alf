<%-- 
    Document   : index
    Created on : Sep 18, 2011, 6:22:18 PM
    Author     : abythell
--%>

<%@page import="org.joda.time.DateMidnight"%>
<%@page import="alfd.Resort.ResortStatus"%>
<%@page import="alfweb.Report"%>
<%@page import="alfd.Resort"%>
<%@page import="alfd.Lift"%>
<%@page import="alfd.WhistlerResort"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css">
        <title>7-Day Lift Status</title>
    </head>
    <body>
        <h1>7-Day Lift Status</h1>
        <%
            WhistlerResort resort = new WhistlerResort();
            DateMidnight date = resort.getLocalDate();
            Integer days = 7;
            Report report = new Report(date, days);
        %>

        <p>
            Here is the 7-day lift status report for Whistler Blackcomb for
            <%= date.toString("EE, MMM d y z") %>.  More features are in development.
            Stay tuned.

        </p>
        <p>
            Legend:  [X] closed, [S] standby,  [ ] open
        </p>

        <table>
            <tr>
                <td class="header">Lift</td>
                <%
                    for (int i=days-1; i >= 0; i--) {
                %>
                <td class="header"><%=date.minusDays(i).toLocalDate().toString("E d") %></td>
                <%
                    }
                %>
            </tr>
            <%

                for (int row = 0; row < report.liftMap.getLiftCount(); row++) {
                    String name = report.liftMap.getLiftNameByIndex(row);
            %>
            <tr>
                <td class="lift"><%= name %></td>
                <%
                    for (int col=days-1; col >= 0; col--) {
                        DateMidnight offsetDate = date.minusDays(col);
                        String status = report.liftMap.get(name, offsetDate.toString());
                        if (status == null) {
                            status = "[no data]";
                        }
                        else if (status == "OPEN") {
                            status = "";
                        }
                        else if (status == "STANDBY") {
                            status = "S";
                        }
                        else if (status == "CLOSED") {
                            status = "X";
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
        
        <h3>Details</h3>
        <p>
            This report is updated hourly from 7am - 5pm Pacific time and records the
            highest status achieved by each lift during that time.  From lowest to
            highest, the lift statuses are closed (X), standby (S), and open ( ).
        </p>
        <p>
            Thus, a lift will display 'X' (closed) only if that lift did not open
            at any point during that day.
        </p>
        <p>
            <a href="mailto:finlaythecat@gmail.com">contact</a>
        </p>

    </body>
</html>
