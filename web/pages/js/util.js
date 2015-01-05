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
			    	showMessage('出现错误' + textStatus);
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
/**
 *@param 获取年龄对应的字符串
 */
function getAgeRangeText(age){
	var userAgeRange='未知年龄段';
	switch(age){
		case(1):{
			userAgeRange='18-20';
			break;
		}
		case(2):{
			userAgeRange='23-26';
			break;
		}
		case(3):{
			userAgeRange='20-23';
			break;
		}
		case(4):{
			userAgeRange='26-30';
			break;
		}
		case(5):{
			userAgeRange='30+';
			break;
		}
	}
	return userAgeRange;
};

/**
 * 仅仅是显示提示信息，标题上有一个关闭按钮，下面没有确定取消按钮
 * @param msg
 * @param title
 * @returns
 */
function showMessage(msg,title){
	if(!this._msgdialog){
		var dom = document.createElement("div");
		this._msgdialog = $(dom).dialog({
			autoOpen : false,
			draggable : true,
			modal : true,
			resizable: false
		});
		this._msgdialog.content = dom;
	}
	this._msgdialog.content.innerHTML = msg;
	this._msgdialog.dialog("option", "title", (title || "提示"));
	this._msgdialog.dialog("option", "position", { my: "center", at: "center", of: this });
	this._msgdialog.dialog("open");
}

/**
 * 确认对话框，有两个按钮，确定和取消
 * @param msg
 * @param title
 * @param okfunc
 * @param cancelfunc
 */
function showConfirm(msg,title,okfunc,cancelfunc){
	if(!this._msgdialog){
		var dom = document.createElement("div");
		this._cfmdialog = $(dom).dialog({
			autoOpen : false,
			draggable : true,
			modal : true,
			resizable: false
		});
		this._cfmdialog.content = dom;
	}
	this._cfmdialog.content.innerHTML = msg;
	this._msgdialog.dialog("option", "title", (title || "提示"));
	this._cfmdialog.dialog("option", "position", { my: "center", at: "center", of: this });
	this._cfmdialog.dialog( "option", "buttons", 
	[{
      text: "确定",
      click: function() {
    	  if(typeof okfunc === "function"){
    		  okfunc();
    	  }
    	  $(this ).dialog("close" );
      }
	},{
	      text: "取消",
	      click: function() {
	    	  if(typeof cancelfunc === "function"){
	    		  cancelfunc();
	    	  }
	    	  $(this).dialog( "close" );
	      }
	}]);
	this._cfmdialog.dialog("open");
}
