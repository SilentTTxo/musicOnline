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
	$(".slider")
    .slider({
        max: 100,
        value: 100
    })
    .slider("pips", {
        rest: false
    }).on("slidechange", function(e,ui) {
    	volume = ui.value/100;
    	player.volume = volume;
    });
    $(".btn-play").click(function(e){
    	if($("#pic-play").attr("class")=="glyphicon glyphicon-pause"){
    		player.pause();
    		$("#pic-play").removeClass("glyphicon-pause");
    		$("#pic-play").addClass("glyphicon-play");
    		return false;
    	}
    	if($("#pic-play").attr("class")=="glyphicon glyphicon-play"){
    		player.play();
    		$("#pic-play").removeClass("glyphicon-play");
    		$("#pic-play").addClass("glyphicon-pause");
    		return false;
    	}
    });
})
changeMusic = function(taget){//换歌!
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
	$("#list-group-item").removeClass("active");
	taget.addClass("active");
	player.play();
	$("#pic-play").removeClass();
	$("#pic-play").addClass("glyphicon glyphicon-pause");
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
	player.value=0.5;
}
changeListData = function(data){
	$("#list").empty();
	var jsondata = eval(data);
	$(jsondata).each(function(index){
		var val = jsondata[index];
		music = val.title+"-"+val.album+"-"+val.artist;
		$("#list").append("<a href='#' class='list-group-item' id='"+val.id+"'>"+music+"</a>")
	});
	$(".list-group-item").click(function(e){
		$(".list-group-item").removeClass("active");
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