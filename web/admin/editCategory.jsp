<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<script>
$(function(){
	
	$("#editForm").submit(function(){
		if(!checkEmpty("name","分类名称"))
			return false;
		if(!checkEmpty("categoryPic","分类图片"))
			return false;
		return true;
	});
});

</script>

<title>编辑分类</title>

<div class="workingArea">

	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分类</a></li>
	  <li class="active">编辑分类</li>
	</ol>

	<div class="panel panel-warning editDiv">
	  <div class="panel-heading">编辑分类</div>
	  <div class="panel-body">
	    	<form id="editForm" action="admin_category_update" enctype="multipart/form-data" method="post">
	    		<table class="editTable">
	    			<tbody><tr>
	    				<td>分类名称</td>
	    				<td><input name="name" class="form-control" id="name" type="text" value="${c.name}"></td>
	    			</tr>
	    			<tr>
	    				<td>分类图片</td>
	    				<td>
	    					<input name="filepath" id="categoryPic" type="file">
	    				</td>
	    			</tr>	    			
	    			<tr class="submitTR">
	    				<td align="center" colspan="2">
	    					<input name="id" type="hidden" value="${c.id}">
	    					<button class="btn btn-success" type="submit">提 交</button>
	    				</td>
	    			</tr>
	    		</tbody></table>
	    	</form>
	  </div>
	</div>	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>