<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<%

%>


<head><title></title>
    <script type="text/javascript">
    </script>
</head>
<body>
<form action="index.jsp" method="post">
    <input type='hidden' name='op' id='op' value='update'/>
    <table>
        <tr>
            <%= tip%>
        </tr>
        <tr>
            <td>鱼1销售数量：<input type='text' name='saleNum1' value='<%=oleSaleNum1 %>'/></td>
            <td>鱼2销售数量: <input type='text' name='saleNum2' value='<%=oleSaleNum2%>'/></td>
            <td><input type='submit' value='submit' id='btn'></td>
        </tr>
    </table>
</form>
</body>
</html>
