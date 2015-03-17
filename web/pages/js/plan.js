function PlanList (wnd,container) {
	this.wnd=wnd;
	this.container=container;
	this.init();
}
PlanList.SIZE=10;
/**
 *初始化方法
 */
PlanList.prototype.init = function() {
	this.container.empty();
	this.newId=null;
	this.newObj=null;
	this.oldObj=null;
	this.oldId=null;
	this.listNew(50);
};
PlanList.prototype.listNew=function(size){
	
	if(this.isListing){
		return;
	}
	var self=this;
	$.ajax({
			type: "POST",
			url: "/user/plan.do",
			data: { action: 'listnew',id:self.newId,size:size?size:PlanList.SIZE},
			success: function(data) {
				    if(!data){
				    	return;
				    }
				    for (var i =0;i< data.length ; i++) {
				    	self.addPlan(data[i]);
				    };
				    self.isListing=false;
			},
			error: function (xhr, textStatus, errorThrown) {
					self.isListing=false;
				    showMessage('出现错误');
				 }
	});
	this.isListing=true;
};
PlanList.prototype.listOld=function(size){
	
	if(this.isListing){
		return;
	}
	var self=this;
	$.ajax({
			type: "POST",
			url: "/user/plan.do",
			data: { action: 'listold',id:self.oldId,size:size?size:PlanList.SIZE},
			success: function(data) {
				    if(!data){
				    	return;
				    }
				    for (var i =0;i< data.length ; i++) {
				    	self.addPlan(data[i]);
				    };
				    self.isListing=false;
			},
			error: function (xhr, textStatus, errorThrown) {
					self.isListing=false;
				    showMessage('出现错误');
				 }
	});
	this.isListing=true;
};
/**
 *
 <div class="">
					<h7>
					<span style="color: #1abc9c">云中鹤</span>发表了新年计划：<span
						style="font-weight: bold">游泳</span></h7>
					<button type="submit" class="btn btn-default"
						style="background-color: #FF3300" style="font-color:red">约</button>
					<hr />
				</div>
 */
PlanList.prototype.addPlan=function(data,isNew){
	var div=$('<div>');
	var titleH=$('<h7><span style="color: #1abc9c">'+data.usercaption+'</span>发表了新年计划：<span style="font-weight: bold">'+data.content+'</span></h7>');
	div.append(titleH);
	if(loginId!=data.userId){
		var btn=$('<button>');
		btn.addClass('btn');
		btn.addClass('btn-default');
		btn.css('background-color','#FF3300');
		btn.css('font-color','red');
		btn.text('约');
		btn.click(function(){
			//
			$.ajax({
					type: "POST",
					url: "/user/plan.do",
					data: { action: 'agree',plan:data.id},
					success: function() {
						   window.open('../static/chat/main/index.html?' +
						 	'me=' + loginId +
						 	'&to=' + data.userId +
						 	'&name=' + data.usercaption +
						 	'&success=true');
					},
					error: function (xhr, textStatus, errorThrown) {
							self.isListing=false;
						    showMessage('出现错误');
						 }
			});
			
		});
		div.append(btn);
	}
	
	
	div.append($('<hr>'));

	if (!isNew) {
		this.oldId=data.id;
		if(!this.oldObj){
			this.newObj=div;
			this.container.append(div);
		}else{
			div.after(this.oldObj);
			this.newObj=div;
		}
	}else{
		this.newId=data.id;
		this.container.append(div);
	}
};
