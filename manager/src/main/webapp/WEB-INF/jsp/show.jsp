<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="/common/css/bootstrap.min.css" rel="stylesheet" media="screen">

<title>数据展示</title>
</head>
<body>
	<h1>数据展示   <a href="/jump/index">首页</a></h1>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="/common/js/bootstrap.min.js"></script>
	
	<div class="btn-group">
      <button  class="btn" id="getBtn">获取一个</button>
      <button  class="btn" id="clearBtn">清空所有</button>
    </div>
    <br>
	
    <table class="table table-striped" id="showtable">
<!-- 	     <tr class="success">
		    <td>1</td>
		    <td>TB - Monthly</td>
	     </tr> -->
    </table>
    
	
</body>

<script type="text/javascript">
	
	var num = 1;
	
	$("#getBtn").click(function(){
		
		$.getJSON("/code/get", function(result){
			
			if(result.success == true){
				
				$("#showtable").append("<tr class=\"success\"><td>"+num+"</td><td>"+result.data+"</td></tr>");
				num = num + 1;
			}else{
				alert(result.message);
			}
			
			
		});
		
		
		
	});
	
	$("#clearBtn").click(function(){
		$("#showtable").empty();
		num =1;
	});

</script>


</html>