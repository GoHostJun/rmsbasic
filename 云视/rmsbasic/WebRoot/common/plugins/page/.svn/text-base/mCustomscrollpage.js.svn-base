(function($){
	$.addscrollpage=function(t,p){
		var setting={
				pagesize:6,
				currentnum:1,
				totalnum:0,
				drawnodata:"",
				ele:"",
				isloadnodata:0,
				callback:function(){
					
				}
		};
		p=$.extend(setting,p);
		var g={
			nextclick:function(){
			//	console.log(p.currentnum+"nextclick,"+g.getTotalPage())
				if(p.currentnum+1>g.getTotalPage()){
					if(p.isloadnodata==0){
						$(t).find(p.ele).append(p.drawnodata)
						p.isloadnodata++;
					}
					return ;
				}
				p.currentnum++;
				p.callback(p.pagesize,p.currentnum, this);
			},
			getTotalPage:function(){
				return (p.totalnum%p.pagesize==0)?p.totalnum/p.pagesize:(Math.ceil(p.totalnum/p.pagesize))
			},
			resetPageParater:function(totalnum,currentnum){
				if(currentnum){
					p.currentnum=currentnum;
				}
				if(totalnum){
					p.totalnum=totalnum;
				}
			//	console.log(p.currentnum+"resetPageParater,"+totalnum)
				g.getTotalPage();
			},
			mCustomScroll:function(){
				$(t).mCustomScrollbar({theme:"minimal", callbacks:{
					onScroll:function(){
						var that=this;
					if(that.mcs.topPct==100){
						g.nextclick();
					}
			    }}});
			}
		
		};
		g.mCustomScroll();
		t.p=p;
		t.grid=g;
		return t;
	};
	
	$.fn.addscroll=function(page){
		return	this.each(function(){
			var that=this;
			$.addscrollpage(that,page);
		})
	};
	$.fn.getPageParameter = function() {
		return this[0].p;
	}
	$.fn.resetPageParameter = function(count,pageNow) {
		return this.each(function() {
			this.grid.resetPageParater(count,pageNow);
		});

	}
})(jQuery);