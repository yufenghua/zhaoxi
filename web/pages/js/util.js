function initLogOut(){
	var global_LogOut_Btn=$("a.logout-btn");
	if (global_LogOut_Btn) {
		global_LogOut_Btn.click(function(){
			$.ajax({
			    type: "POST",
			    url: "/zhaoxi/user/login.do?action=logout",
			    async: false,
			    success: function(data) {
			    	window.location.href='/zhaoxi/index.jsp';
			    },
			    error: function (xhr, textStatus, errorThrown) {
			    	alert('出现错误' + textStatus);
			    }
			});
		})
	};
};
initLogOut();
/**
 * 验证函数，默认进行非空验证，如果有其他逻辑，请实现验证器的逻辑
 * 
 * @param value
 *            待验证的值
 * @param validator
 *            验证器
 */
function validate(value, validator) {
	if (!validator) {
		return isEmpty(value);
	}
};
/**
 * 验证值是否为空
 * 
 * @param str
 */
function isEmpty(str) {
	return str == undefined || str == null || str == '';
};

