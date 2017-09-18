$(document).ready(function(e){
	var selectedIDPodforuma;

	checkIfLoggedIn();
	loadPodforumi();

	$("#btn-brisi-podforum").on("click", function(e){
		$.ajax({
			headers:{
				"Authorization":"Bearer " + sessionStorage.getItem("token"),
				"Content-Type":"application/json"
			},
			type:"DELETE",
			url:"http://localhost:8080/forum/webapi/podforumi/" + selectedIDPodforuma,
			dataType:"text"
		})
		.done(function(data, status){
			loadPodforumi();
		})
		.fail(function(jqXHR, textStatus){
			alert("error");
			console.log(jqXHR);
		});
	});

	$("#btn-izmeni-podforum").on("click", function(e){

		var data = {
			naziv: $("#izmeni-naziv").val(),
			opis: $("#izmeni-opis").val(),
			pravila: $("#izmeni-pravila").val()
		}

		jsonData = JSON.stringify(data);

		$.ajax({
			headers:{
				"Authorization":"Bearer " + sessionStorage.getItem("token"),
				"Content-Type":"application/json"
			},
			type:"PUT",
			url:"http://localhost:8080/forum/webapi/podforumi/" + selectedIDPodforuma,
			dataType:"json",
			data: jsonData
		})
		.done(function(data, status){
			loadPodforumi();
		})
		.fail(function(jqXHR, textStatus){
			alert("error");
			console.log(jqXHR);
		});
	});

	// popunjavanje input polja za izmenu
	$("#btn-izmena-podforum").on("click", function(e){
		// e.preventDefault();
		$.ajax({
			headers:{
				"Authorization": "Bearer " + sessionStorage.getItem("token"),
				"Content-Type":"application/json"
			},
			type:"GET",
			url:"http://localhost:8080/forum/webapi/podforumi/" + selectedIDPodforuma,
			dataType:"json"
		})
		.done(function(data, status){
			
			$("#izmeni-naziv").val(data.naziv);
			$("#izmeni-opis").val(data.opis);
			$("#izmeni-pravila").val(data.pravila);
		})
		.fail(function(jqXHR, textStatus){
			alert("error");
			console.log(jqXHR);
		});
	});
	
	$("#btn-dodaj-podforum").on("click", function(e){
		e.preventDefault();

		var data = {
			naziv: $("#novi-naziv").val(),
			opis: $("#novi-opis").val(),
			pravila: $("#novi-pravila").val(),
		}

		var jsonData = JSON.stringify(data);

		$.ajax({
			headers:{
				"Authorization":"Bearer " + sessionStorage.getItem("token"),
				"Content-Type":"application/json"
			},
			type:"POST",
			url:"http://localhost:8080/forum/webapi/podforumi",
			dataType:"json",
			data: jsonData
		})
		.done(function(data, status){
			$("#table-podforumi").append("<tr><td style='display:none;'>" + data.id + "</td><td>" + data.ikonica + "</td><td>" + data.naziv + "</td></tr>");
		})
		.fail(function(jqXHR, textStatus){
			alert("error");
			console.log(jqXHR);
		});
	});

	$("#link-logout").on("click", function(e){
		$.ajax({
			headers:{
				"Authorization":"Bearer " + sessionStorage.getItem("token")
			},
			type: "GET",
			url: "http://localhost:8080/forum/webapi/users/" + sessionStorage.getItem('token') + "/logout",
			dataType: "text"

		})
		.done(function(data, status){
			sessionStorage.setItem('token', "");
			sessionStorage.setItem('username', "");
			sessionStorage.setItem('role', "");
			location.href = "http://localhost:8080/forum/";
		})
		.fail(function(jqXHR, textStatus){
			alert("error");
			console.log(jqXHR);
		});
	});

	function checkIfLoggedIn(){
		var role = sessionStorage.getItem("role");
		// ako nije ulogovan
		if(sessionStorage.getItem('token') === null || sessionStorage.getItem('token') == "") {
			$('.logged-out').removeClass('hide');
			$('.logged-in').addClass('hide');
		}
		else {
			if (role == "ADMIN") {
				$(".admin").removeClass("hide");
			}
			else if (role == "MODERATOR") {
				$(".moderator").removeClass("hide");
			}
		}
	}

	$(".selectable tbody").on("click", "tr", function(){
		$(".selected").removeClass("selected");
		$(this).addClass("selected");

		selectedIDPodforuma = $(this).find("td").eq(0).html();
		console.log(selectedIDPodforuma);
		
	});

	function loadPodforumi(){

		$.ajax({
			headers:{
				'Content-Type': 'application/json',
				'Authorization': "Bearer " + sessionStorage.getItem('token')
			},
			type:'GET',
			url:'http://localhost:8080/forum/webapi/podforumi',
			dataType:'json'
		})
		.done(function(data){
			$("#table-podforumi").find("tr:gt(0)").remove();

			for (var i = data.length - 1; i >= 0; i--) {
				$('#table-podforumi').append('<tr><td style="display:none;">' + data[i].id + '</td><td><img src="" alt="image" height="100" width="100" /><td><a href="http://localhost:8080/forum/webapi/podforumi/' + data[i].id + '">' + data[i].naziv + '</td></tr>');
			}
		})
		.fail(function(jqXHR, textStatus){
			alert('error');
			console.log(jqXHR);
		});
	}
});