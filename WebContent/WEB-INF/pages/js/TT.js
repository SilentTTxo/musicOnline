var TYPE = "GET";
var duration = 0;

$(function(){
	getMusicList();
	$("#search").click(function(e){
		$("#list").hide();
		$("#load").show();
		$.ajax({
			type:TYPE,
			url:"./musiclist",
			data:{cmd:5,name:$("#name").val()},
			dataType:"json",
			success:function(data){
				changeListData(data);
			}
		});
	});
})
changeMusic = function(taget){
	var playerc = $("#player");
	/*$.ajax({
		type:"GET",
		url:"./musiclist",
		data:{cmd:7,musicid:taget.attr("id")},
		dataType:"json",
		success:function(data){
			var music = eval(data);
			$.ajax({
				type:"GET",
				url:music.url,
				success:function(data){
					console.log(data);
				}
			});
			player.attr("src",music.url);
		}
	})*/
	$.ajax({
		type:"GET",
		url:"./musiclist",
		data:{cmd:6,musicid:taget.attr("id")},
		dataType:"json",
		success:function(data){
			var music = eval(data);
			duration = music.duration;
			//player.attr("src",music.url);
		}
	})
	playerc.attr("src","./music?musicid="+taget.attr("id"));
	$("a").removeClass("active");
	taget.addClass("active");
	player.play();
}
getMusicList = function(){
	$.ajax({
		type:TYPE,
		url:"./musiclist",
		data:{cmd:1},
		dataType:"json",
		success:function(data){
			changeListData(data);
		}
	});
}
changeListData = function(data){
	$("#list").empty();
	var jsondata = eval(data);
	$(jsondata).each(function(index){
		var val = jsondata[index];
		music = val.title+"-"+val.album+"-"+val.artist;
		$("#list").append("<a href='#' class='list-group-item' id='"+val.id+"'>"+music+"</a>")
	});
	$("a").click(function(e){
		$("a").removeClass("active");
		This = $(e.target);
		This.addClass("active");
		changeMusic(This);
		return false;
	})
	$("#load").hide();
	$("#list").show();
}
timeupdate = function(){
	time = player.currentTime;
	atime = duration;
	$("#plen").width(time/atime*100+"%");
	$("#psent").text(Math.floor(time/60)+":"+Math.floor(time%60));
}