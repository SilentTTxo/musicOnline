$(function(){
	getMusicList();
})
changeMusic = function(taget){
	var player = $("#player");
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
	player.attr("src","./music?musicid="+taget.attr("id"));
	player.play();
}
getMusicList = function(){
	$.ajax({
		type:"GET",
		url:"./musiclist",
		data:{cmd:1},
		dataType:"json",
		success:function(data){
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
		}
	});
}