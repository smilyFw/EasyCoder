<%@page import="com.happyelements.fortuna.lua.common.util.DateUtils" %>
<%@page import="java.util.List" %>
<%@page import="com.happyelements.fortuna.lua.common.model.ActivityRecordInfo" %>
<%@page import="com.happyelements.fortuna.lua.common.util.RecordUtils" %>
<%@page import="org.apache.commons.lang.math.NumberUtils" %>
<%@page import="com.happyelements.fortuna.lua.common.util.SsoUtils" %>
<%@page import="com.twofishes.config.manager.ConfigManager" %>
<%@ page import="com.happyelements.fortuna.lua.activity.act-{actId}.config.-{ProjectId}Config" %>
<%@ page import="com.happyelements.fortuna.lua.activity.act-{actId}.-{ProjectId}Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    static final String CONTEXT = ConfigManager.getAppName().startsWith("fortuna") ? "luatool" : "lua";
    static final int ACT_ID = -{ProjectId}Config.ActivitySerialId;

    static String getValue(String[] array, int index) {
        if (null != array && index >= 0 && index < array.length) {
            return array[index];
        }
        return "-";
    }
    static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

%>

<%
    long userId;
    -{ProjectId}Data data;
    -{ProjectId}Config cfg;

    List<String> recordList = null;
    int i;

    try {
        int type = NumberUtils.toInt(request.getParameter("type"));
        userId = NumberUtils.toLong(request.getParameter("uid"));

        boolean redirect = true;
        switch (type) {
            // clear
            case 1:
                if (userId > 0) {
                    -{ProjectId}Data empty = new -{ProjectId}Data();
                    empty.userId = userId;
                    empty.setSerialId(ACT_ID);
                    empty.init();
                    empty.save();

                    RecordUtils.delete(userId, ACT_ID);
                }
                break;
            // update
            case 2:
                if (userId > 0) {
                    -{ProjectId}Data update =-{ProjectId}Data.fetch(userId);

                    update.logAll(SsoUtils.getUserName() + "修改前");
-{paramUpdate}

                    update.save();
                    update.logAll(SsoUtils.getUserName()+"修改后");
                }
                break;
            // read
            default:
                redirect = false;
                break;
        }

        if (redirect) {
            response.sendRedirect("/" + CONTEXT + "/" + ACT_ID + ".jsp?type=0&uid=" + userId);
            return;
        }

        if (userId > 0) {
            data = -{ProjectId}Data.fetch(userId);
            ActivityRecordInfo info = RecordUtils.get(userId, ACT_ID);

            if (info != null) {
                recordList = info.getLog();
            }
        } else {
            data = new -{ProjectId}Data();
            data.init();
        }

        cfg = data.config();
    } catch (Exception e) {
        out.print(e.getMessage());
        return;
    }
%>

<html>
<head>
    <title>活动名称</title>
    <style>
        body {
            padding: 50px 50px 20px;
        }

        table {
            border-collapse: collapse;
            text-align: center;
            display: table;
            border-spacing: 2px;
            border-color: gray;
        }

        thead {
            display: table-header-group;
            vertical-align: middle;
            border-color: inherit;
        }

        tbody {
            display: table-row-group;
            vertical-align: middle;
            border-color: inherit;
        }

        tr {
            display: table-row;
            vertical-align: inherit;
            border-color: inherit;
        }

        th {
            background-color: #BCBECC;
            font-weight: bold;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 0 10px;
            display: table-cell;
            vertical-align: inherit;
            height: 28px;
        }

        table > caption {
            font-weight: bold;
        }

        .alignL {
            text-align: left;
        }

        .alignR {
            text-align: right;
        }

        td input[type=number], td input[type=text], td select {
            border: none;
            text-align: left;
            width: 100%;
            height: 100%;
            background-color: inherit;
        }

        td input[type=checkbox] {
            margin: 0;
            float: left;
            background-color: inherit;
        }

        .btn {
            padding: 2px 15px;
        }

        .ribbon {
            display: inline-block;
            width: 100%;
            text-align: right;
        }

        .pnl {
            display: inline-block;
            vertical-align: top;
            margin-right: 35px;
            margin-top: 20px;
            overflow: hidden;
        }

        .red {
            color: #F00;
            font-weight: bold;
        }

        /*=========================================*/
        .r0 {
            background-color: rgba(249, 249, 249, 0.2);
        }

        .r1 {
            background-color: rgba(188, 188, 199, 0.2);
        }

        .list {

        }

        .list caption {
            background-color: #fff;
            position: relative;
            z-index: 10;
        }

        .list td, .list th {
            position: relative;
        }

        tr:hover {
            background-color: rgba(0, 255, 231, 0.5);
        }

        .list td:hover::after, .list th:hover::after {
            content: "";
            position: absolute;
            background-color: rgba(0, 255, 231, 0.55);
            left: 0;
            top: -5000px;
            height: 10000px;
            width: 100%;
            z-index: -1;
        }

        /*=================================== webkit scroll bar =================================== */
        ::-webkit-scrollbar {
            width: 20px;
            height: 20px;
        }

        ::-webkit-scrollbar-track,
        ::-webkit-scrollbar-thumb {
            border-radius: 999px;
            border: 5px solid transparent;
        }

        ::-webkit-scrollbar-track {
            box-shadow: 1px 1px 5px rgba(0, 0, 0, .2) inset;
        }

        ::-webkit-scrollbar-thumb {
            min-height: 50px;
            background-clip: content-box;
            box-shadow: 0 0 0 5px rgba(0, 0, 0, .2) inset;
        }

        ::-webkit-scrollbar-corner {
            background: transparent;
        }
    </style>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            addScrollListener();

            document.getElementById('qBtn').onclick = function () {
                var v = document.getElementById('qUserId').value;
                var uid = parseInt(v, 10);
                if (!uid || v != uid || uid <= 0) {
                    alert('请输入正确的[用户ID]');
                    return;
                }

                this.disabled = 'disabled';
                window.location.href = '/<%=CONTEXT%>/<%=ACT_ID%>.jsp?type=0&uid=' + uid + '&r=' + Math.random();
            };

            document.getElementById('qUserId').onkeyup = function (event) {
                if (event.keyCode == 13) {
                    document.getElementById('qBtn').onclick();
                }
            };

            document.getElementById('clear').onclick = function () {
                var v = document.getElementById('qUserId').value;
                var uid = parseInt(v, 10);
                if (!uid || v != uid || uid <= 0) {
                    alert('请输入正确的[用户ID]');
                    return;
                }

                var msg = '确定清除用户[' + uid + ']的数据?';
                if (false == window.confirm(msg)) {
                    return;
                }

                this.disabled = 'disabled';
                window.location.href = '/<%=CONTEXT%>/<%=ACT_ID%>.jsp?type=1&uid=' + uid + '&r=' + Math.random();
            };

            document.getElementById('submit').onclick = function () {
                if ('true' == '<%=SsoUtils.isProductEnvironment()%>') {
                    var msg = '当前为线上环境, 确定修改玩家数据?';
                    if (false == window.confirm(msg)) {
                        return;
                    }
                }

                var param = [];
                var trList = document.querySelectorAll('#data>tr');

                for (var i = 0; i < trList.length; i++) {
                    var input = trList[i].querySelector('input[id]');
                    if (!input) {
                        continue;
                    }

                    var text = trList[i].firstElementChild.innerText;
                    var id = input.id;
                    var val = input.value;

                    var intVal = parseInt(val, 10);
                    if (val != intVal) {
                        alert('请输入正确的[' + text + ']');
                        return;
                    }
                    param.push(id + '=' + intVal);
                }

                param.push('r=' + Math.random());
                this.disabled = 'disabled';
                window.location.href = '/<%=CONTEXT%>/<%=ACT_ID%>.jsp?type=2&' + param.join('&');
            };
        });

        function addScrollListener() {
            var grid = document.querySelector('#pop_grid');
            var title = document.querySelector('#pop_title');
            var head = document.querySelector('#pop_head');
            title.appendChild(adapt(head));

            document.onscroll = function () {
                if (elementInViewport(head) || !elementInViewport(grid)) {
                    title.style.display = 'none';
                } else {
                    title.style.top = '' + document.body.scrollTop + 'px';
                    title.style.display = 'block';
                }
            };
        }

        function elementInViewport(el) {
            var top = el.offsetTop;
            var left = el.offsetLeft;
            var width = el.offsetWidth;
            var height = el.offsetHeight;

            while (el.offsetParent) {
                el = el.offsetParent;
                top += el.offsetTop;
                left += el.offsetLeft;
            }

            return (top < (window.pageYOffset + window.innerHeight)
            && left < (window.pageXOffset + window.innerWidth)
            && (top + height) > window.pageYOffset
            && (left + width) > window.pageXOffset);
        }

        function adapt(el) {
            var copy = el.cloneNode(true);
            var from = el.firstElementChild.children;
            var to = copy.firstElementChild.children;

            for (var i = 0; i < from.length; i++) {
                to[i].style.width = from[i].clientWidth - 20;
                //to[i].style.height = from[i].clientHeight;
            }
            return copy;
        }
    </script>
</head>
<body>
<h1>活动-{actId}</h1>

<div class="ribbon">
    <label for="qUserId">用户ID:</label>
    <input type="number" id="qUserId" value="<%=data.userId%>">
    <button id="qBtn" class="btn">查询</button>
</div>
<hr>

<div class="pnl">
    <table>
        <caption>基本信息</caption>
        <thead>
        <tr>
            <th>属性</th>
            <th>值</th>
        </tr>
        </thead>
        <tbody id="data">

        <tr>
            <td class="alignL">用户ID</td>
            <td class="alignR"><label>
                <input type="number" id="uid" value="<%=data.userId%>" readonly>
            </label></td>
        </tr>

        <!--基本信息-添加-->

        <tr>
            <td class="alignL">当前时间</td>
            <td class="alignR"><label>
                <input type="text" value="<%=DateUtils.toText(data.curCalendar)%>" readonly>
            </label></td>
        </tr>
-{paramSet}
        <tr>
            <td style="text-align: center;" colspan="2">
                <button class="btn red" id="clear">清除</button>
                <button class="btn" id="submit">提交</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<br/>

<div class="pnl list">
    <table>
        <caption>用户数据</caption>
        <thead id="pop_head">
        <tr>
            <th>序号</th>
            <th>时间</th>
            <th>说明</th>
-{paramName}
        </tr>
        </thead>
        <tbody id="pop_grid">
        <%
            if (recordList != null) {
                i = 0;
                for (String record : recordList) {
                    String[] ss = RecordUtils.splitRecord(record);
                    out.print("<tr class=\"r");
                    out.print(i & 0x1);
                    out.print("\"><td>");
                    out.print(++i);
                    out.print("</td>");

                    // n个th，下面< n-1
                    for (int x = 0; x < -{paramNum}; x++) {
                        out.print("<td>");
                        out.print(getValue(ss, x));
                        out.print("</td>");
                    }
                    out.print("</tr>");
                }
            }
        %>
        </tbody>
    </table>
    <table id="pop_title" style="display: none; position: absolute;"></table>
</div>

</body>
</html>
