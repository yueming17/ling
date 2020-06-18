var DEFAULT_PAGE_SIZE = 5;

function initRoles(rolesDiv, roleName) {
	$("#" + rolesDiv + "").empty();
	$.ajax({
		url : "/api/roles",
		type : "get",
		contentType: "application/json",
		success : function (rs) {
			var checkboxs = "";
			$.each(rs, function(i, value) {
				checkboxs += "<input name='"+ roleName + "' value='" + 
						value.roleId +"' type='checkbox'>" + value.roleName + "&nbsp;&nbsp;";
			});
			$("#" + rolesDiv + "").append(checkboxs);
		},
		error : function (data) {
			layer.alert(data.responseText, {icon: 0});
		}
	});
}