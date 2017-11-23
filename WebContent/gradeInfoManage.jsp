<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>班级信息管理</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css" >
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

		var url;
		
		function searchGrade(){  //查找班级
			//datagrid的load方法
			$('#dg').datagrid('load',{
				gradeName:$('#s_gradeName').val()    //这里意思是传一个gradeName参数
			});
		}
		
		function deleteGrade(){  //删除班级
			//datagrid的一个方法，直接获取选中行集合，在eclipse这里设断点是进不去的，要到firefox里调试
			var selectedRows=$("#dg").datagrid('getSelections');
			if(selectedRows.length==0){
				$.messager.alert("系统提示:", "请选择要删除的数据");
				return;
			}
			var strIds=[];
			for(i=0;i<selectedRows.length;i++){
				strIds.push(selectedRows[i].id);
			}
			var ids=strIds.join(",");
			alert(ids);
			$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
				if(r){
					$.post("gradeDelete",{delIds:ids},function(result){
						if(result.success){
							$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
							$("#dg").datagrid("reload");
						}else{
							$.messager.alert('系统提示',result.errorMsg);
						}
					},"json");
				}
			});
		}
		
		function openGradeModifyDialog(){
			var selectedRows = $("#dg").datagrid('getSelections');
			if(selectedRows.length!=1){
				$.messager.alert("系统提示","请选择一条要编辑的数据！");
				return;
			}
			var row = selectedRows[0];
			$("#dig").dialog("open").dialog("setTitle","编辑班级信息");
			$("#fm").form("load",row);   //把row的数据放入对话框666~fm是对话框表单的id
			url="gradeSave?id="+row.id;  //在这里加入了id属性，日狗了找半天，saveGrade函数根据url传到servlet
		}
		
		function openGradeAddDialog(){  //打开添加对话框
			$("#dig").dialog("open").dialog("setTitle","添加班级");
			url="gradeSave";
		}
		
		function closeGradeDialog(){   //关闭对话框
			$("#dig").dialog("close");
			resetValue();
		}
		
		function saveGrade(){  //保存修改或添加
			$("#fm").form("submit",{
				url:url,
				onSubmit:function(){
					return $(this).form("validate");
				},
				success:function(result){
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg);
						return;
					}
					else{
						$.messager.alert("系统提示","保存成功");
						resetValue();
						$("#dlg").dialog("close");
						$("#dg").datagrid("reload");
						return;
					}
				}
			});
		}
		
		function resetValue(){   //把添加弹出对话框的文本置空
			$("#gradeName").val("");
			$("#gradeDesc").val("");
		}
</script>
</head>
<body style="margin:5px">
<!-- table可以加上fit="true"，看个人喜好  -->
		<table id="dg" title="班级信息" class="easyui-datagrid" fitColumns="true"
		pagination="true" rownumbers="true" url="gradeList" fit="true" toolbar="#tb">
				<thead>
						<tr>
							<th field="cb" checkbox="true"></th>
							<th field="id" width="50">编号</th>
							<th field="gradeName" width="100">班级名称</th>
							<th field="gradeDesc" width="250">班级描述</th>
					  </tr>
		</thead>
		</table>
		
		<div id="tb">
				<div>
						<a href="javascript:openGradeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
						<a href="javascript:openGradeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
						<a href="javascript:deleteGrade()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
				</div>
				<div>&nbsp;班级名称：&nbsp;<input type="text" name="s_gradeName" id="s_gradeName" />
				<!-- a标签的超链接可以去除改成自己需要的在点击a标签时触发的事件 -->
				<a href="javascript:searchGrade()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
				</div>
		</div>
		
		<div id="dig" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
			<form id="fm" method="post" buttons="#dig-buttons">
				<table>
					<tr>
						<td>班级名称:</td>
						<td><input type="text" name="gradeName" id="gradeName" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td valign="top">班级描述:</td>
						<td><textarea rows="7" cols="30" name="gradeDesc" id="gradeDesc"></textarea></td>
					</tr>
				</table>
				
		<div id="dig-buttons">
			<a href="javascript:saveGrade()"  class="easyui-linkbutton"  iconCls="icon-ok">保存</a>
			<a href="javascript:closeGradeDialog()"  class="easyui-linkbutton"  iconCls="icon-cancel" >取消</a>
		</div>
			</form>
		</div>
</body>
</html>